package ru.practicum.ewm.service.authorized.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.model.*;
import ru.practicum.ewm.service.authorized.PrivateRequestService;
import ru.practicum.ewm.storage.EventRepository;
import ru.practicum.ewm.storage.RequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService {


    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getRequestsCurrentUser(Long userId, Long eventId) {
        List<Event> events = eventRepository.findAllByInitiator(User.builder().id(userId).build());
        List<Request> requestList = requestRepository.findAll((root, query, criteriaBuilder) ->
                root.get("event").in(events.stream().map(Event::getId).collect(Collectors.toList())));

        return RequestMapper.toListParticipationRequestDto(requestList);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        Event event = eventOne(eventId, userId, true);
        Long requestsCount = requestsCount(event);
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= requestsCount) {
            throw new ValidationException("Event request limit reached");
        }

        Request request = requestOne(reqId, event);
        if (event.getParticipantLimit() == 0 && !event.getRequestModeration()) {
            return RequestMapper.toParticipationRequestDto(request);
        }
        request.setStatus(RequestState.CONFIRMED);
        ParticipationRequestDto requestDto = RequestMapper.toParticipationRequestDto(requestRepository.save(request));
        event.increaseConfirmedReq();
        eventRepository.save(event);

        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == requestsCount + 1) {
            List<Request> requestList = requestRepository.findAll(((root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("event"), event.getId()),
                            criteriaBuilder.equal(root.get("status"), RequestState.PENDING)
                    )));
            requestList.forEach(e -> e.setStatus(RequestState.REJECTED));
            requestRepository.saveAll(requestList);
        }

        return requestDto;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        Event event = eventOne(eventId, userId, true);
        Request request = requestOne(reqId, event);
        if (request.getStatus().equals(RequestState.CONFIRMED)) {
            event.decreaseConfirmedReq();
            eventRepository.save(event);
        }
        request.setStatus(RequestState.REJECTED);

        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getAll(Long userId) {
        List<Request> toParticipationRequestDto = requestRepository
                .findAllByRequester(User.builder().id(userId).build());

        return RequestMapper.toListParticipationRequestDto(toParticipationRequestDto);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ParticipationRequestDto add(Long userId, Long eventId) {
        Request request = RequestMapper.buildNewRequest(userId, eventId);
        Event event = eventOne(eventId, userId, false);
        Long requestsCount = requestsCount(event);
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= requestsCount) {
            throw new ValidationException("Event request limit reached");
        }
        requestRepository.findByRequesterAndEvent(
                        User.builder().id(userId).build(),
                        Event.builder().id(eventId).build())
                .ifPresent(e -> {
                    throw new ValidationException("Request already exists");
                });
        if (!event.getRequestModeration()) {
            request.setStatus(RequestState.CONFIRMED);
            event.increaseConfirmedReq();
            eventRepository.save(event);
        }

        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        Request request = requestRepository.findByIdAndRequester(requestId, User.builder().id(userId).build())
                .orElseThrow(() -> new NotFoundException("ParticipationRequest not found"));
        Event event = eventRepository.findOne(((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("id"), request.getEvent().getId()),
                        criteriaBuilder.equal(root.get("state"), EventState.PUBLISHED)
                ))).orElseThrow(() -> new NotFoundException("Event not found"));
        if (request.getStatus().equals(RequestState.CONFIRMED)) {
            event.decreaseConfirmedReq();
            eventRepository.save(event);
        }
        request.setStatus(RequestState.CANCELED);

        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    private Event eventOne(Long eventId, Long userId, Boolean initiator) {
        return eventRepository.findOne(((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("id"), eventId),
                        (initiator) ? criteriaBuilder.equal(root.get("initiator"), userId) : criteriaBuilder.notEqual(root.get("initiator"), userId),
                        criteriaBuilder.equal(root.get("state"), EventState.PUBLISHED)
                ))).orElseThrow(() -> new NotFoundException("Event not found"));
    }

    private Request requestOne(Long reqId, Event event) {
        return requestRepository.findOne((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("id"), reqId),
                        criteriaBuilder.equal(root.get("event"), event.getId())
                )).orElseThrow(() -> new NotFoundException("Event not found"));
    }

    private Long requestsCount(Event event) {
        return requestRepository.count((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("event"), event.getId()),
                        criteriaBuilder.equal(root.get("status"), RequestState.CONFIRMED)
                ));
    }
}

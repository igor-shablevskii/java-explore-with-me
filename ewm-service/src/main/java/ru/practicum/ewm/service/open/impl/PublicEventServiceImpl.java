package ru.practicum.ewm.service.open.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.EventSort;
import ru.practicum.ewm.model.EventState;
import ru.practicum.ewm.service.open.PublicEventService;
import ru.practicum.ewm.storage.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepository repository;
    private final EventMapper eventMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public EventFullDto getOne(Long eventId) {
        Event event = repository.findByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<EventShortDto> getAll(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                      LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {

        Page<Event> events = repository.findAll((root, query, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.equal(root.get("state"), EventState.PUBLISHED.ordinal()),
                        root.get("category").in(categories),
                        criteriaBuilder.equal(root.get("paid"), paid),
                        (rangeStart != null && rangeEnd != null) ? criteriaBuilder.and(
                                criteriaBuilder.greaterThan(root.get("eventDate"), rangeStart),
                                criteriaBuilder.lessThan(root.get("eventDate"), rangeEnd)
                        ) : criteriaBuilder.lessThan(root.get("eventDate"), LocalDateTime.now()),
                        (onlyAvailable) ? criteriaBuilder.or(
                                criteriaBuilder.equal(root.get("participantLimit"), 0),
                                criteriaBuilder.and(
                                        criteriaBuilder.notEqual(root.get("participantLimit"), 0),
                                        criteriaBuilder.greaterThan(root.get("participantLimit"),
                                                root.get("confirmedRequests"))
                                )) : root.isNotNull(),
                        criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")),
                                        "%" + text.toLowerCase() + "%"),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                                        "%" + text.toLowerCase() + "%")
                        )), PageRequest.of(from / size, size, getSortType(sort)));

        return eventMapper.toListEventShortDto(events);
    }

    private Sort getSortType(String sort) {
        EventSort eventSort;
        try {
            eventSort = EventSort.valueOf(sort);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("EventSort not valid");
        }
        switch (eventSort) {
            case EVENT_DATE:
                return Sort.by(Sort.Direction.ASC, "eventDate");
            case VIEWS:
                return Sort.by(Sort.Direction.ASC, "views");
            default:
                return Sort.by(Sort.Direction.ASC, "id");
        }
    }
}
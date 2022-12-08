package ru.practicum.ewm.service.authorized.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.UpdateEventRequestDto;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.EventState;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.service.authorized.PrivateEventService;
import ru.practicum.ewm.storage.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventRepository repository;
    private final EventMapper eventMapper;

    @Override
    public List<EventShortDto> getAll(Long userId, Integer from, Integer size) {
        Page<Event> events = repository.findAll(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                        root.get("initiator"), User.builder().id(userId).build()),
                PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id")));

        return eventMapper.toListEventShortDto(events);
    }

    @Override
    public EventFullDto create(Long userId, NewEventDto newEventDto) {
        if (LocalDateTime.now().plusHours(2).isAfter(newEventDto.getEventDate())) {
            throw new ValidationException("Data not valid");
        }
        Event event = EventMapper.toEvent(userId, newEventDto);

        return eventMapper.toEventFullDto(repository.save(event));
    }

    @Override
    public EventFullDto edit(Long userId, UpdateEventRequestDto updateEventRequestDto) {
        if (LocalDateTime.now().plusHours(2).isAfter(updateEventRequestDto.getEventDate())) {
            throw new ValidationException("Data not valid");
        }
        Event event = repository.findById(updateEventRequestDto.getEventId()).orElseThrow(() -> new NotFoundException("Event not found"));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("No rights to edit");
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("Event cannot be edited");
        }
        if (event.getState().equals(EventState.CANCELED)) {
            event.setState(EventState.PENDING);
        }
        editEventFields(event, updateEventRequestDto);

        return eventMapper.toEventFullDto(repository.save(event));
    }

    @Override
    public EventFullDto getOne(Long userId, Long eventId) {
        Event event = repository.findByIdAndInitiator(eventId, User.builder().id(userId).build())
                .orElseThrow(() -> new NotFoundException("Event not found"));

        return eventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        Event event = repository.findByIdAndInitiator(eventId, User.builder().id(userId).build())
                .orElseThrow(() -> new NotFoundException("Event not found"));
        event.setState(EventState.CANCELED);

        return eventMapper.toEventFullDto(repository.save(event));
    }

    private void editEventFields(Event event, UpdateEventRequestDto dto) {
        if (dto.getAnnotation() != null && !dto.getAnnotation().isEmpty()) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            event.setCategory(Category.builder().id(dto.getCategory()).build());
        }
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            event.setTitle(dto.getTitle());
        }
    }
}
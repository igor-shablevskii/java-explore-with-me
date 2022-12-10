package ru.practicum.ewm.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.EventState;
import ru.practicum.ewm.model.Location;
import ru.practicum.ewm.service.admin.AdminEventService;
import ru.practicum.ewm.storage.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository repository;
    private final EventMapper eventMapper;

    @Override
    public List<EventFullDto> getAll(List<Long> users, List<String> states, List<Long> categories,
                                     LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {

        Page<Event> events = repository.findAll((root, query, criteriaBuilder) -> criteriaBuilder.and(
                        (users != null) ? root.get("initiator").in(users) : root.isNotNull(),
                        (states != null) ? root.get("state").in(states.stream()
                                .map(EventState::valueOf)
                                .collect(Collectors.toList())) : root.isNotNull(),
                        (categories != null) ? root.get("category").in(categories) : root.isNotNull(),
                        (rangeStart != null && rangeEnd != null) ? criteriaBuilder.and(
                                criteriaBuilder.greaterThan(root.get("eventDate"), rangeStart),
                                criteriaBuilder.lessThan(root.get("eventDate"), rangeEnd)
                        ) : criteriaBuilder.lessThan(root.get("eventDate"), LocalDateTime.now())
                ),
                PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id")));

        return eventMapper.toListEventFullDto(events);
    }

    @Override
    public EventFullDto edit(Long eventId, AdminUpdateEventRequestDto adminUpdateEventRequestDto) {
        if (LocalDateTime.now().plusHours(2).isAfter(adminUpdateEventRequestDto.getEventDate())) {
            throw new ValidationException("Data not valid");
        }
        Event event = repository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found"));
        editEventFields(event, adminUpdateEventRequestDto);
        if (adminUpdateEventRequestDto.getLocation() != null) {
            Location location = Location.builder()
                    .lon(adminUpdateEventRequestDto.getLocation().getLon())
                    .lat(adminUpdateEventRequestDto.getLocation().getLat())
                    .build();
            event.setLocation(location);
        }
        if (adminUpdateEventRequestDto.getRequestModeration() != null) {
            event.setRequestModeration(adminUpdateEventRequestDto.getRequestModeration());
        }

        return eventMapper.toEventFullDto(repository.save(event));
    }

    @Override
    public EventFullDto publish(Long eventId) {
        Event event = repository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found"));
        event.setState(EventState.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());

        return eventMapper.toEventFullDto(repository.save(event));
    }

    @Override
    public EventFullDto reject(Long eventId) {
        Event event = repository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found"));
        event.setState(EventState.CANCELED);

        return eventMapper.toEventFullDto(repository.save(event));
    }

    private void editEventFields(Event event, AdminUpdateEventRequestDto dto) {
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
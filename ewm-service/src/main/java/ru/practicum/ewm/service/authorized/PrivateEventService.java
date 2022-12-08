package ru.practicum.ewm.service.authorized;

import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.UpdateEventRequestDto;

import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> getAll(Long userId, Integer from, Integer size);

    EventFullDto create(Long userId, NewEventDto newEventDto);

    EventFullDto edit(Long userId, UpdateEventRequestDto updateEventRequestDto);

    EventFullDto getOne(Long userId, Long eventId);

    EventFullDto cancelEvent(Long userId, Long eventId);
}

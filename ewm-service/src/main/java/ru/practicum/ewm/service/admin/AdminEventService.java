package ru.practicum.ewm.service.admin;

import ru.practicum.ewm.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.ewm.dto.event.EventFullDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {

    List<EventFullDto> getAll(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart,
                              LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto edit(Long eventId, AdminUpdateEventRequestDto adminUpdateEventRequestDto);

    EventFullDto publish(Long eventId);

    EventFullDto reject(Long eventId);
}
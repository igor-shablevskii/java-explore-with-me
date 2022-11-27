package ru.practicum.ewm.controller.authorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.UpdateEventRequestDto;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.service.authorized.PrivateEventService;
import ru.practicum.ewm.service.authorized.PrivateRequestService;
import ru.practicum.ewm.utils.Create;
import ru.practicum.ewm.utils.Update;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Validated
public class PrivateEventController {

    private final PrivateEventService privateEventService;
    private final PrivateRequestService privateRequestService;

    @GetMapping
    public List<EventShortDto> getAll(@PathVariable Long userId,
                                      @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                      @RequestParam(defaultValue = "10") @Positive Integer size) {
        return privateEventService.getAll(userId, from, size);
    }

    @PatchMapping
    public EventFullDto edit(@PathVariable Long userId,
                             @RequestBody @Validated(Update.class) UpdateEventRequestDto updateEventRequestDto) {
        return privateEventService.edit(userId, updateEventRequestDto);
    }

    @PostMapping
    public EventFullDto create(@PathVariable Long userId,
                               @RequestBody @Validated(Create.class) NewEventDto newEventDto) {
        return privateEventService.create(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventCurrentUser(@PathVariable Long userId,
                                            @PathVariable Long eventId) {
        return privateEventService.getOne(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId) {
        return privateEventService.cancelEvent(userId, eventId);
    }

    @GetMapping("{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsCurrentUser(@PathVariable Long userId,
                                                                @PathVariable Long eventId) {
        return privateRequestService.getRequestsCurrentUser(userId, eventId);
    }

    @PatchMapping("{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable Long userId,
                                                  @PathVariable Long eventId,
                                                  @PathVariable Long reqId) {
        return privateRequestService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable Long userId,
                                                 @PathVariable Long eventId,
                                                 @PathVariable Long reqId) {
        return privateRequestService.rejectRequest(userId, eventId, reqId);
    }
}
package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.service.admin.AdminEventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

    private final AdminEventService adminEventService;

    @GetMapping
    public List<EventFullDto> getAll(@RequestParam(required = false) List<Long> users,
                                     @RequestParam(required = false) List<String> states,
                                     @RequestParam(required = false) List<Long> categories,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                     @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                     @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Method: GET, path = /admin/events, AdminEventController/getAll, params: users={}, states={}, " +
                        "categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);

        return adminEventService.getAll(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto editEvent(@PathVariable Long eventId,
                                  @RequestBody AdminUpdateEventRequestDto adminUpdateEventRequestDto) {
        log.info("Method: PUT, path = /admin/events/{eventId}, AdminEventController/editEvent, param: eventId = {}," +
                "requestBody: {}", eventId, adminUpdateEventRequestDto);

        return adminEventService.edit(eventId, adminUpdateEventRequestDto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publish(@PathVariable Long eventId) {
        log.info("Method: Patch, path = /admin/events/{eventId}/publish, AdminEventController/publish, " +
                "param: eventId = {}", eventId);

        return adminEventService.publish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto reject(@PathVariable Long eventId) {
        log.info("Method: Patch, path = /admin/events/{eventId}/reject, AdminEventController/reject, " +
                "param: eventId = {}", eventId);

        return adminEventService.reject(eventId);
    }
}
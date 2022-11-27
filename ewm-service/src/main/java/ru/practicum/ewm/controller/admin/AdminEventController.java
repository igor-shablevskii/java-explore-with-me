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

        return adminEventService.getAll(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto editEvent(@PathVariable Long eventId,
                                  @RequestBody AdminUpdateEventRequestDto adminUpdateEventRequestDto) {
        return adminEventService.edit(eventId, adminUpdateEventRequestDto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publish(@PathVariable Long eventId) {
        return adminEventService.publish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto reject(@PathVariable Long eventId) {
        return adminEventService.reject(eventId);
    }
}
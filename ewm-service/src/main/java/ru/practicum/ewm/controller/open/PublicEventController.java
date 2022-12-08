package ru.practicum.ewm.controller.open;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.service.open.PublicEventService;
import ru.practicum.ewm.service.stats.StatsClient;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {

    private final PublicEventService publicEventService;
    private final StatsClient statsClient;

    @GetMapping("/{id}")
    public EventFullDto getOne(HttpServletRequest request,
                               @PathVariable Long id) {
        log.info("Method: Get, path = /events/{id}, PublicEventController/getOne, pathVariable: id={}", id);
        statsClient.saveEndpointHit(request);

        return publicEventService.getOne(id);
    }

    @GetMapping
    public List<EventShortDto> getAll(HttpServletRequest request,
                                      @RequestParam(required = false) String text,
                                      @RequestParam(required = false) List<Long> categories,
                                      @RequestParam(required = false) Boolean paid,
                                      @RequestParam(required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                      @RequestParam(required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                      @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                                      @RequestParam(required = false, defaultValue = "ID") String sort,
                                      @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                      @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Method: GET, path = /events, PublicEventController/getAll, params: text={}, categories={}, " +
                        "paid={}, rangeStart={}, rangeEnd={}, onlyAvailable={}, sort={}, from={}, size={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        statsClient.saveEndpointHit(request);

        return publicEventService.getAll(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
    }
}
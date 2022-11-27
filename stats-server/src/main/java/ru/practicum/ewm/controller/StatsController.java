package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStats;
import ru.practicum.ewm.service.StatsService;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public void addHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        statsService.createHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getViewStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        LocalDateTime decodeStart = LocalDateTime.parse(java.net.URLDecoder.decode(start, StandardCharsets.UTF_8),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime decodeEnd = LocalDateTime.parse(java.net.URLDecoder.decode(end, StandardCharsets.UTF_8),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return statsService.getStats(decodeStart, decodeEnd, uris, unique);
    }
}
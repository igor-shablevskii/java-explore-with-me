package ru.practicum.ewm.service.stats;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.client.Client;
import ru.practicum.ewm.dto.stats.EndpointHit;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.ewm.dto.stats.ViewStats;
import ru.practicum.ewm.model.Event;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsClientImpl implements StatsClient {
    private final Client statsClient;
    @Value("${EVENT_VIEW_URI}")
    private String eventViewUri;
    @Value("${app.name}")
    private String appName;

    @Override
    public void saveEndpointHit(HttpServletRequest request) {
        EndpointHit endpointHit = EndpointHit.builder()
                .app(appName)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();

        statsClient.sendHit(endpointHit);
    }

    @Override
    public Long getViewsForEvent(Event event, Boolean unique) {
        String start = event.getCreatedOn().withNano(0).toString().replace("T", " ");
        String end = LocalDateTime.now().withNano(0).toString().replace("T", " ");
        List<ViewStats> views = statsClient.get(start, end, List.of(String.format(eventViewUri, event.getId())), unique);
        if (views.isEmpty()) {
            return 0L;
        }
        return views.get(0).getHits();
    }
}
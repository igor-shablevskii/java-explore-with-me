package ru.practicum.ewm.service.stats;

import ru.practicum.ewm.model.Event;

import javax.servlet.http.HttpServletRequest;

public interface StatsClient {

    void saveEndpointHit(HttpServletRequest request);

    Long getViewsForEvent(Event event, Boolean unique);
}
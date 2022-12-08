package ru.practicum.ewm.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.dto.ViewStats;
import ru.practicum.ewm.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT new ru.practicum.ewm.dto.ViewStats(e.app, e.uri, COUNT (e.ip)) from EndpointHit e " +
            "WHERE e.timestamp > ?1 AND e.timestamp < ?2 AND e.uri IN ?3 GROUP BY e.app, e.uri")
    List<ViewStats> findAll(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.ewm.dto.ViewStats(e.app, e.uri, COUNT (DISTINCT e.ip)) from EndpointHit e " +
            "WHERE e.timestamp> ?1 AND e.timestamp< ?2 AND e.uri IN ?3 GROUP BY e.app, e.uri")
    List<ViewStats> findAllUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
}
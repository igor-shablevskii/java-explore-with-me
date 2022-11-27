package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStats;
import ru.practicum.ewm.mapper.StatMapper;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.storage.StatsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;

    @Override
    public void createHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = StatMapper.toEndpointHit(endpointHitDto);
        repository.save(endpointHit);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (CollectionUtils.isEmpty(uris)) {
            return new ArrayList<>();
        }

        List<ViewStats> viewStats;
        if (!unique) {
            viewStats = repository.findAll(start, end, uris);
        } else {
            viewStats = repository.findAllUnique(start, end, uris);
        }

        for (String uri : uris) {
            if (viewStats.stream().noneMatch(e -> e.getUri().equals(uri))) {
                viewStats.add(ViewStats.builder().app("ewm").uri(uri).hits(0L).build());
            }
        }

        return viewStats;
    }
}
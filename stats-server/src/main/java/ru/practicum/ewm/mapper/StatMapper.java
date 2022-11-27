package ru.practicum.ewm.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.model.EndpointHit;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatMapper {

    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder()
                .app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri())
                .timestamp(endpointHitDto.getTimestamp())
                .ip(endpointHitDto.getIp())
                .build();
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return EndpointHitDto.builder()
                .id(endpointHit.getId())
                .uri(endpointHit.getUri())
                .app(endpointHit.getApp())
                .ip(endpointHit.getIp())
                .timestamp(endpointHit.getTimestamp())
                .build();
    }
}
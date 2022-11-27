package ru.practicum.ewm.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.RequestState;
import ru.practicum.ewm.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> toListParticipationRequestDto(List<Request> requestList) {
        return requestList.stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    public static Request buildNewRequest(Long userId, Long eventId) {
        return Request.builder()
                .created(LocalDateTime.now())
                .event(Event.builder().id(eventId).build())
                .requester(User.builder().id(userId).build())
                .status(RequestState.PENDING)
                .build();
    }
}
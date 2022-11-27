package ru.practicum.ewm.service.authorized;

import ru.practicum.ewm.dto.request.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestService {

    List<ParticipationRequestDto> getRequestsCurrentUser(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId);

    List<ParticipationRequestDto> getAll(Long userId);

    ParticipationRequestDto add(Long userId, Long eventId);

    ParticipationRequestDto cancel(Long userId, Long requestId);
}

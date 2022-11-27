package ru.practicum.ewm.controller.authorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.service.authorized.PrivateRequestService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {

    private final PrivateRequestService service;

    @GetMapping
    public List<ParticipationRequestDto> getAll(@PathVariable Long userId) {
        return service.getAll(userId);
    }

    @PostMapping
    public ParticipationRequestDto add(@PathVariable Long userId,
                                       @RequestParam Long eventId) {
        return service.add(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable Long userId,
                                          @PathVariable Long requestId) {
        return service.cancel(userId, requestId);
    }
}
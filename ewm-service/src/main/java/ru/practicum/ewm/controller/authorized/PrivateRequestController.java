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
        log.info("Method: Get, path = /users/{userId}/requests, PrivateRequestController/getAll, " +
                "pathVariable: userId={}", userId);

        return service.getAll(userId);
    }

    @PostMapping
    public ParticipationRequestDto add(@PathVariable Long userId,
                                       @RequestParam Long eventId) {
        log.info("Method: Post, path = /users/{userId}/requests, " +
                "PrivateRequestController/add, pathVariable: userId={}, requestParam: eventId={}",
                userId, eventId);

        return service.add(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable Long userId,
                                          @PathVariable Long requestId) {
        log.info("Method: Patch, path = /users/{userId}/requests/{requestId}/cancel, " +
                "PrivateRequestController/cancel, pathVariables: userId={}, requestId={}",
                userId, requestId);

        return service.cancel(userId, requestId);
    }
}
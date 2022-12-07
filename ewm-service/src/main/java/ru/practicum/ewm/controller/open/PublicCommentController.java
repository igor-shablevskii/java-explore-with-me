package ru.practicum.ewm.controller.open;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.service.open.PublicCommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class PublicCommentController {

    PublicCommentService publicCommentService;

    @GetMapping
    public List<CommentDto> getAllForEvent(@RequestParam Long eventId,
                                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Method: Get, path = /comment, PublicCommentController/getAllForEvent, " +
                "params: eventId={}, from={}, size={}", eventId, from, size);

        return publicCommentService.getAllForEvent(eventId, from, size);
    }
}
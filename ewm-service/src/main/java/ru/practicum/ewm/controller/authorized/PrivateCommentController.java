package ru.practicum.ewm.controller.authorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.NewCommentDto;
import ru.practicum.ewm.service.authorized.PrivateCommentService;
import ru.practicum.ewm.utils.Create;
import ru.practicum.ewm.utils.Update;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/comments")
@Validated
public class PrivateCommentController {

    private final PrivateCommentService privateCommentService;

    @PostMapping("/events/{eventId}")
    public CommentDto create(@PathVariable Long userId,
                             @PathVariable Long eventId,
                             @RequestBody @Validated(Create.class) NewCommentDto newCommentDto) {
        log.info("Method: Post, path = /users/{userId}/comments, PrivateCommentController/create, " +
                "pathVariable: userId={}, param: eventId={}, requestBody={}", userId, eventId, newCommentDto);

        return privateCommentService.create(userId, eventId, newCommentDto);
    }

    @PatchMapping
    public CommentDto update(@PathVariable Long userId,
                             @RequestBody @Validated(Update.class) CommentDto commentDto) {
        log.info("Method: Patch, path = /users/{userId}/comments, PrivateCommentController/update, " +
                "pathVariable: userId={}, requestBody={}", userId, commentDto);

        return privateCommentService.update(userId, commentDto);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long userId,
                       @PathVariable Long commentId) {
        log.info("Method: Delete, path = /users/{userId}/comments/{commentId}, PrivateCommentController/delete, " +
                "pathVariables: userId={}, commentId={}", userId, commentId);

        privateCommentService.delete(userId, commentId);
    }
}
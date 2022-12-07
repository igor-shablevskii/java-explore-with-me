package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.service.admin.AdminCommentService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    @PatchMapping("/{commentId}/publish")
    public CommentDto publish(@PathVariable("commentId") Long commentId) {
        log.info("Method: Patch, path = /admin/comments/{commentId}/publish, AdminCommentService/publish, " +
                "param: eventId = {}", commentId);

        return adminCommentService.publish(commentId);
    }

    @PatchMapping("/{commentId}/reject")
    public CommentDto reject(@PathVariable("commentId") Long commentId) {
        log.info("Method: Patch, path = /admin/comments/{commentId}/reject, AdminCommentService/reject, " +
                "param: eventId = {}", commentId);

        return adminCommentService.reject(commentId);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId) {
        log.info("Method: Delete, path = /admin/comments/{commentId}, AdminCommentService/delete, " +
                "pathVariable: commentId = {}", commentId);

        adminCommentService.delete(commentId);
    }
}
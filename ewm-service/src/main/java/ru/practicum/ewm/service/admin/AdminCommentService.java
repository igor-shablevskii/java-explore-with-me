package ru.practicum.ewm.service.admin;

import ru.practicum.ewm.dto.comment.CommentDto;

public interface AdminCommentService {

    CommentDto publish(Long commentId);

    CommentDto reject(Long commentId);

    void delete(Long commentId);
}
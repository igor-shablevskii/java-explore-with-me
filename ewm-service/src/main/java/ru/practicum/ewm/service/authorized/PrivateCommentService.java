package ru.practicum.ewm.service.authorized;

import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.NewCommentDto;

public interface PrivateCommentService {

    CommentDto create(Long userId, Long eventId, NewCommentDto commentDto);

    CommentDto update(Long userId, CommentDto commentDto);

    void delete(Long userId, Long commentId);
}
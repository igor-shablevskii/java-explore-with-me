package ru.practicum.ewm.service.open;

import ru.practicum.ewm.dto.comment.CommentDto;

import java.util.List;

public interface PublicCommentService {

    List<CommentDto> getAllForEvent(Long eventId, Integer from, Integer size);
}
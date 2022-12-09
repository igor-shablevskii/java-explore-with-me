package ru.practicum.ewm.service.open.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.CommentState;
import ru.practicum.ewm.service.open.PublicCommentService;
import ru.practicum.ewm.storage.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCommentServiceImpl implements PublicCommentService {

    private final CommentRepository repository;

    @Override
    public List<CommentDto> getAllForEvent(Long eventId, Integer from, Integer size) {
        List<Comment> comments = repository.findAllByEventId(eventId,
                PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "createdOn")))
                .stream()
                .filter(c -> c.getState().equals(CommentState.PUBLISHED))
                .collect(Collectors.toList());

        return CommentMapper.toListCommentDto(comments);
    }
}
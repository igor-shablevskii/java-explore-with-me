package ru.practicum.ewm.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.CommentState;
import ru.practicum.ewm.service.admin.AdminCommentService;
import ru.practicum.ewm.storage.CommentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentRepository repository;

    @Override
    @Transactional
    public CommentDto publish(Long commentId) {
        Comment comment = getComment(commentId);
        comment.setState(CommentState.PUBLISHED);
        Comment publishedComment = repository.save(comment);

        return CommentMapper.toCommentDto(publishedComment);
    }

    @Override
    @Transactional
    public CommentDto reject(Long commentId) {
        Comment comment = getComment(commentId);
        comment.setState(CommentState.REJECTED);
        Comment rejectedComment = repository.save(comment);

        return CommentMapper.toCommentDto(rejectedComment);
    }

    @Override
    @Transactional
    public void delete(Long commentId) {
        repository.deleteById(commentId);
    }

    private Comment getComment(Long commentId) {
        return repository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));
    }
}
package ru.practicum.ewm.service.authorized.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.NewCommentDto;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.EventState;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.service.authorized.PrivateCommentService;
import ru.practicum.ewm.storage.CommentRepository;
import ru.practicum.ewm.storage.EventRepository;
import ru.practicum.ewm.storage.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CommentDto create(Long userId, Long eventId, NewCommentDto commentDto) {
        Event event = getEvent(eventId);
        getUser(userId);
        if (event.getState().equals(EventState.PUBLISHED)) {
            Comment comment = commentRepository.save(CommentMapper.toComment(commentDto, userId, eventId));
            return CommentMapper.toCommentDto(comment);
        } else {
            throw new ValidationException("Event not published");
        }
    }

    @Override
    @Transactional
    public CommentDto update(Long userId, Long eventId, CommentDto commentDto) {
        User user = getUser(userId);
        getEvent(eventId);
        Comment comment = getComment(commentDto.getId());

        if (user.equals(comment.getAuthor())) {
            comment.setText(commentDto.getText());
        } else {
            throw new ValidationException("No rights for update");
        }
        return CommentMapper.toCommentDto(comment);
    }


    @Override
    @Transactional
    public void delete(Long userId, Long commentId) {
        User user = getUser(userId);
        Comment comment = getComment(commentId);
        if (user.equals(comment.getAuthor())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ValidationException("No rights for delete");
        }
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
    }
}

package ru.practicum.ewm.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.NewCommentDto;
import ru.practicum.ewm.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static Comment toComment(NewCommentDto newCommentDto, Long userId, Long eventId) {
        return Comment.builder()
                .author(User.builder().id(userId).build())
                .event(Event.builder().id(eventId).build())
                .text(newCommentDto.getText())
                .createdOn(LocalDateTime.now())
                .state(CommentState.PENDING)
                .build();
    }

//    public static Comment toComment(CommentDto commentDto, Long userId, Long eventId) {
//        return Comment.builder()
//                .author(User.builder().id(userId).build())
//                .event(Event.builder().id(eventId).build())
//                .text(commentDto.getText())
//                .createdOn(LocalDateTime.now())
//                .build();
//    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .author(CommentDto.UserShortDto.builder()
                        .id(comment.getAuthor().getId())
                        .name(comment.getAuthor().getName())
                        .build())
                .event(CommentDto.EventShortDto.builder()
                        .id(comment.getEvent().getId())
                        .annotation(comment.getEvent().getAnnotation())
                        .title(comment.getEvent().getTitle())
                        .eventDate(comment.getEvent().getEventDate())
                        .build())
                .state(comment.getState())
                .build();
    }

    public static List<CommentDto> toListCommentDto(List<Comment> list) {
        return list.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
package ru.practicum.ewm.dto.comment;

import lombok.*;
import ru.practicum.ewm.model.CommentState;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentDto {

    @NotNull
    private Long id;
    @NotNull
    private UserShortDto author;
    @NotNull
    private EventShortDto event;
    @NotEmpty
    private String text;
    @NotNull
    private LocalDateTime createdOn;
    @NotNull
    private CommentState state;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class UserShortDto {

        private Long id;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class EventShortDto {

        private Long id;
        @NotEmpty
        private String annotation;
        @NotNull
        private LocalDateTime eventDate;
        @NotEmpty
        private String title;
    }
}
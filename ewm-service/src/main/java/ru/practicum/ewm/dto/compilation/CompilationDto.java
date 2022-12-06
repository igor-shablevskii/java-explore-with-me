package ru.practicum.ewm.dto.compilation;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CompilationDto {

    private Long id;
    @Builder.Default
    private List<EventShortDto> events = new ArrayList<>();
    private Boolean pinned;
    private String title;

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
        private CategoryDto category;
        private Integer confirmedRequests;
        @NotNull
        private LocalDateTime eventDate;
        @NotNull
        private UserShortDto initiator;
        @NotNull
        private Boolean paid;
        @NotEmpty
        private String title;
        private Long views;

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
        public static class CategoryDto {

            private Long id;
            private String name;
        }
    }
}
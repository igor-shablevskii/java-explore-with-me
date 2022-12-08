package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NewEventDto {

    @NotEmpty(groups = {Create.class})
    @Size(min = 16, max = 1024, groups = {Create.class})
    private String annotation;
    @NotNull(groups = {Create.class})
    private Long category;
    @NotEmpty(groups = {Create.class})
    @Size(min = 16, max = 2048, groups = {Create.class})
    private String description;
    @NotNull(groups = {Create.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull(groups = {Create.class})
    private Location location;
    @Builder.Default
    private Boolean paid = false;
    @Builder.Default
    private Integer participantLimit = 0;
    @Builder.Default
    private Boolean requestModeration = true;
    @NotEmpty(groups = {Create.class})
    @Size(min = 4, max = 128, groups = {Create.class})
    private String title;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Location {

        private Float lat;
        private Float lon;
    }
}

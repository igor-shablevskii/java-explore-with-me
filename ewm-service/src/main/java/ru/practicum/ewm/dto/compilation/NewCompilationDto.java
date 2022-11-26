package ru.practicum.ewm.dto.compilation;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class NewCompilationDto {
    private List<Long> events;
    private Boolean pinned;
    @NotEmpty
    private String title;
}
package ru.practicum.ewm.dto.compilation;

import lombok.*;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class NewCompilationDto {

    private List<Long> events;
    @Builder.Default
    private Boolean pinned = false;
    @NotEmpty(groups = {Create.class})
    private String title;
}
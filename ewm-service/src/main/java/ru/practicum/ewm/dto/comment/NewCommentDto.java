package ru.practicum.ewm.dto.comment;

import lombok.*;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NewCommentDto {

    @NotEmpty(groups = {Create.class})
    private String text;
}

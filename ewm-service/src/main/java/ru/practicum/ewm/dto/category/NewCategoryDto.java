package ru.practicum.ewm.dto.category;

import lombok.*;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCategoryDto {

    @NotEmpty(groups = {Create.class})
    private String name;
}
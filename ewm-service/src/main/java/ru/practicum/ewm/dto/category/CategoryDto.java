package ru.practicum.ewm.dto.category;

import lombok.*;
import ru.practicum.ewm.utils.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategoryDto {

    @NotNull(groups = {Update.class})
    private Long id;
    @NotEmpty(groups = {Update.class})
    private String name;
}
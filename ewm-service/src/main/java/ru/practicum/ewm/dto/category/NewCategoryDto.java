package ru.practicum.ewm.dto.category;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class NewCategoryDto {

    @NotEmpty
    private String name;
}
package ru.practicum.ewm.dto.category;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CategoryDto {
    private Long id;
    @NotEmpty
    private String name;
}
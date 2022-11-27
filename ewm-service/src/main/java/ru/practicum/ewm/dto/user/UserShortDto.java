package ru.practicum.ewm.dto.user;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class UserShortDto {

    @NotNull
    private Long id;
    @NotEmpty
    private String name;
}
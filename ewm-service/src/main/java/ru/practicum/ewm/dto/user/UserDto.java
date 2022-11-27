package ru.practicum.ewm.dto.user;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class UserDto {

    @NotNull
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
}
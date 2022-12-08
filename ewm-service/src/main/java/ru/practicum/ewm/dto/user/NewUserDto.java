package ru.practicum.ewm.dto.user;

import lombok.*;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NewUserDto {

    @NotEmpty(groups = {Create.class})
    @Size(max = 128, groups = {Create.class})
    private String name;
    @Email(groups = {Create.class})
    @NotEmpty(groups = {Create.class})
    @Size(max = 256, groups = {Create.class})
    private String email;
}

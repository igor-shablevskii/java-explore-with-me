package ru.practicum.ewm.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserShortDto {
    private Long id;
    private String name;
}
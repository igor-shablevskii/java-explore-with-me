package ru.practicum.ewm.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewStats {

    private String app;
    private String uri;
    private Long hits = 0L;
}
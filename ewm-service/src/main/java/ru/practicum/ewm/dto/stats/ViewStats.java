package ru.practicum.ewm.dto.stats;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@ToString
public class ViewStats {

    private String app;
    private String uri;
    private Long hits;
}

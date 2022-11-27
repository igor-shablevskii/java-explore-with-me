package ru.practicum.ewm.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Location {

    private Float lat;
    private Float lon;
}
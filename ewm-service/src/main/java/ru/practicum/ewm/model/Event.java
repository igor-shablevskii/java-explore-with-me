package ru.practicum.ewm.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2000)
    private String annotation;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
    @Builder.Default
    private Integer confirmedRequests = 0;
    private LocalDateTime createdOn;
    @Column(length = 4000)
    private String description;
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User initiator;
    @Embedded
    private Location location;
    private Boolean paid;
    @Builder.Default
    private Integer participantLimit = 0;
    private LocalDateTime publishedOn;
    @Builder.Default
    private Boolean requestModeration = true;
    @Enumerated(EnumType.STRING)
    private EventState state;
    private String title;

    public void increaseConfirmedReq() {
        confirmedRequests++;
    }

    public void decreaseConfirmedReq() {
        confirmedRequests--;
    }
}
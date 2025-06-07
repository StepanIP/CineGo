package com.screening.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Seat extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rowsNumber;

    private int seatInRow;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;

    private Long userId;

    @Enumerated(value = EnumType.STRING)
    private SeatStatus status;

}

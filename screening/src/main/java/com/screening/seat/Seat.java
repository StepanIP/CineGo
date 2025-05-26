package com.screening.seat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.screening.cinema.Cinema;
import com.screening.common.entity.AbstractEntity;
import com.screening.screening.Screening;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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

package com.screening.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "screening")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Screening extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime time;

    @Column(name = "film_id", nullable = false)
    private Long filmId;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL)
    private List<Seat> seats;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
}

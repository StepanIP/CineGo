package com.screening.cinema;

import java.util.List;

import com.screening.screening.Screening;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cinema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(nullable = false)
    private String city;

    @OneToMany
    @JoinColumn(name = "screening_id")
    private List<Screening> screenings;
}

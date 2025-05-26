package com.screening.cinema;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaDto {

    private Long id;

    private String name;

    private String address;

    private String city;
}

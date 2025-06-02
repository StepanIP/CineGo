package com.screening.domain.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserRequest {

    private Long id;

    private String username;

    private boolean isStudent;

    private LocalDate dateOfBirth;
}

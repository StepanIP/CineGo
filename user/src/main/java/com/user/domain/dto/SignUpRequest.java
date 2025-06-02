package com.user.domain.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @Size(min = 5, max = 50)
    @NotBlank
    private String username;

    @Size(min = 5, max = 255)
    @NotBlank
    @Email
    private String email;

    @Size(max = 255)
    private String password;

    private LocalDate birthDate;
}
package com.service.film.dto;

import java.time.LocalDateTime;

public record CommentResponseDto(
    Long id,
    Long userId,
    Long filmId,
    String text,
    Integer rating,
    LocalDateTime createdAt
) {} 
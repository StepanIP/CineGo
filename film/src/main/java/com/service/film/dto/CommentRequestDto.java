package com.service.film.dto;

public record CommentRequestDto(
    String text,
    Integer rating
) {} 
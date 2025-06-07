package com.service.film.mapper;

import com.service.film.domain.Comment;
import com.service.film.domain.Film;
import com.service.film.dto.CommentRequestDto;
import com.service.film.dto.CommentResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    @DisplayName("Map CommentRequestDto to Comment successfully")
    void mapCommentRequestDtoToCommentSuccessfully() {
        CommentRequestDto requestDto = new CommentRequestDto("Great movie!", 5);

        Comment comment = commentMapper.requestDtoToEntity(requestDto);

        assertEquals("Great movie!", comment.getText());
        assertEquals(5, comment.getRating());
    }

    @Test
    @DisplayName("Map Comment to CommentResponseDto successfully")
    void mapCommentToCommentResponseDtoSuccessfully() {
        Film film = Film.builder()
            .id(1L)
            .title("Inception")
            .description("A mind-bending thriller")
            .releaseDate(LocalDate.now())
            .build();
        Comment comment = new Comment(1L, 1L, film, "Great movie!", 5, LocalDateTime.now());

        CommentResponseDto responseDto = commentMapper.entityToDto(comment);

        assertEquals(1L, responseDto.id());
        assertEquals("Great movie!", responseDto.text());
        assertEquals(5, responseDto.rating());
        assertEquals(1L, responseDto.filmId());
    }
}
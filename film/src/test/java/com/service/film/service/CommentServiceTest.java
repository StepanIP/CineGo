package com.service.film.service;

import com.service.film.domain.Comment;
import com.service.film.domain.Film;
import com.service.film.dto.CommentRequestDto;
import com.service.film.dto.CommentResponseDto;
import com.service.film.exception.exceptions.NotFoundException;
import com.service.film.mapper.CommentMapper;
import com.service.film.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private FilmService filmService;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create comment successfully")
    void createCommentSuccessfully() {
        Film film = Film.builder()
            .id(1L)
            .title("Inception")
            .description("A mind-bending thriller")
            .releaseDate(LocalDate.now())
            .build();

        CommentRequestDto requestDto = new CommentRequestDto("Great movie!", 5);
        Comment comment = new Comment(null, 1L, film, "Great movie!", 5, null);
        Comment savedComment = new Comment(1L, 1L, film, "Great movie!", 5, LocalDateTime.now());
        CommentResponseDto responseDto = new CommentResponseDto(1L, 1L, 1L,"Great movie!", 5, LocalDateTime.now());

        when(filmService.findById(1L)).thenReturn(film);
        when(commentMapper.requestDtoToEntity(requestDto)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(savedComment);
        when(commentMapper.entityToDto(savedComment)).thenReturn(responseDto);

        CommentResponseDto result = commentService.createComment(1L, 1L, requestDto);

        assertEquals(responseDto, result);
        verify(filmService).findById(1L);
        verify(commentMapper).requestDtoToEntity(requestDto);
        verify(commentRepository).save(comment);
        verify(commentMapper).entityToDto(savedComment);
    }

    @Test
    @DisplayName("Get comments by film successfully")
    void getCommentsByFilmSuccessfully() {
        Film film = Film.builder()
            .id(1L)
            .title("Inception")
            .description("A mind-bending thriller")
            .releaseDate(LocalDate.now())
            .build();
        Comment comment = new Comment(1L, 1L, film, "Great movie!", 5, LocalDateTime.now());
        CommentResponseDto responseDto = new CommentResponseDto(1L, 1L, 1L,"Great movie!", 5, LocalDateTime.now());

        when(commentRepository.findByFilmId(1L)).thenReturn(List.of(comment));
        when(commentMapper.entityToDto(comment)).thenReturn(responseDto);

        List<CommentResponseDto> result = commentService.getCommentsByFilm(1L);

        assertEquals(1, result.size());
        assertEquals(responseDto, result.get(0));
        verify(commentRepository).findByFilmId(1L);
        verify(commentMapper).entityToDto(comment);
    }

    @Test
    @DisplayName("Delete comment successfully")
    void deleteCommentSuccessfully() {
        Film film = Film.builder()
            .id(1L)
            .title("Inception")
            .description("A mind-bending thriller")
            .releaseDate(LocalDate.now())
            .build();
        Comment comment = new Comment(1L, 1L, film, "Great movie!", 5, LocalDateTime.now());

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        assertDoesNotThrow(() -> commentService.deleteComment(1L, 1L));
        verify(commentRepository).findById(1L);
        verify(commentRepository).delete(comment);
    }

    @Test
    @DisplayName("Delete comment throws exception when not found")
    void deleteCommentThrowsExceptionWhenNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> commentService.deleteComment(1L, 1L));

        assertEquals("Comment not found with id 1", exception.getMessage());
        verify(commentRepository).findById(1L);
    }
}
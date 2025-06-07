package com.service.film.controller;

import com.service.film.dto.CommentRequestDto;
import com.service.film.dto.CommentResponseDto;
import com.service.film.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private CommentRequestDto commentRequestDto;
    private CommentResponseDto commentResponseDto;
    private List<CommentResponseDto> commentResponseDtos;
    private Long filmId;
    private Long userId;
    private Long commentId;

    @BeforeEach
    void setUp() {
        filmId = 1L;
        userId = 1L;
        commentId = 1L;
        
        commentRequestDto = new CommentRequestDto("Test comment", 5);
        
        commentResponseDto = new CommentResponseDto(
            commentId,
            userId,
            filmId,
            "Test comment",
            5,
            LocalDateTime.now()
        );
        
        commentResponseDtos = Arrays.asList(commentResponseDto);
    }

    @Test
    void createComment_ShouldReturnCreatedComment() {
        // Given
        when(commentService.createComment(filmId, userId, commentRequestDto)).thenReturn(commentResponseDto);

        // When
        CommentResponseDto result = commentController.createComment(filmId, userId, commentRequestDto);

        // Then
        assertEquals(commentResponseDto, result);
        verify(commentService).createComment(filmId, userId, commentRequestDto);
    }

    @Test
    void getFilmComments_ShouldReturnListOfComments() {
        // Given
        when(commentService.getCommentsByFilm(filmId)).thenReturn(commentResponseDtos);

        // When
        List<CommentResponseDto> result = commentController.getFilmComments(filmId);

        // Then
        assertEquals(commentResponseDtos, result);
        verify(commentService).getCommentsByFilm(filmId);
    }

    @Test
    void deleteComment_ShouldCallServiceDelete() {
        // When
        commentController.deleteComment(commentId, userId);

        // Then
        verify(commentService).deleteComment(commentId, userId);
    }

    @Test
    void getUserComments_ShouldReturnListOfComments() {
        // Given
        when(commentService.getCommentsByUser(userId)).thenReturn(commentResponseDtos);

        // When
        List<CommentResponseDto> result = commentController.getUserComments(userId);

        // Then
        assertEquals(commentResponseDtos, result);
        verify(commentService).getCommentsByUser(userId);
    }
} 
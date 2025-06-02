package com.service.film.controller;

import com.service.film.dto.CommentRequestDto;
import com.service.film.dto.CommentResponseDto;
import com.service.film.service.CommentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments/{filmId}/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(
        @PathVariable Long filmId,
        @RequestHeader("X-User-Id") Long userId,
        @RequestBody CommentRequestDto dto) {
        return commentService.createComment(filmId, userId, dto);
    }

    @GetMapping
    public List<CommentResponseDto> getFilmComments(@PathVariable Long filmId) {
        return commentService.getCommentsByFilm(filmId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
        @PathVariable Long commentId,
        @RequestHeader("X-User-Id") Long userId) {
        commentService.deleteComment(commentId, userId);
    }

    @GetMapping("/user")
    public List<CommentResponseDto> getUserComments(@RequestHeader("X-User-Id") Long userId) {
        return commentService.getCommentsByUser(userId);
    }
} 
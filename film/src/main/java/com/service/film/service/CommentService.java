package com.service.film.service;

import com.service.film.domain.Comment;
import com.service.film.domain.Film;
import com.service.film.dto.CommentRequestDto;
import com.service.film.dto.CommentResponseDto;
import com.service.film.exception.exceptions.NotFoundException;
import com.service.film.mapper.CommentMapper;
import com.service.film.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final FilmService filmService;

    private final CommentMapper commentMapper;

    @Transactional
    public CommentResponseDto createComment(Long filmId, Long userId, CommentRequestDto dto) {
        Film film = filmService.findById(filmId);

        Comment comment = commentMapper.requestDtoToEntity(dto);
        comment.setFilm(film);
        comment.setUserId(userId);

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.entityToDto(savedComment);
    }

    public List<CommentResponseDto> getCommentsByFilm(Long filmId) {
        return commentRepository.findByFilmId(filmId).stream()
            .map(commentMapper::entityToDto)
            .collect(Collectors.toList());
    }

    public List<CommentResponseDto> getCommentsByUser(Long userId) {
        return commentRepository.findByUserId(userId).stream()
            .map(commentMapper::entityToDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundException("Comment not found with id " + commentId));

        if (!comment.getUserId().equals(userId)) {
            throw new IllegalStateException("User can only delete their own comments");
        }

        commentRepository.delete(comment);
    }
} 
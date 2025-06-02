package com.service.film.repository;

import com.service.film.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByFilmId(Long filmId);

    List<Comment> findByUserId(Long userId);
} 
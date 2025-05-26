package com.service.film.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.service.film.domain.Film;
import com.service.film.domain.Genre;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findByGenresContaining(Genre genre);

    boolean existsByTitle(String title);
}

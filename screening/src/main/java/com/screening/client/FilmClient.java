package com.screening.client;

import com.screening.domain.dto.FilmRequest;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "film-service", url = "${application.config.films-url}")
public interface FilmClient {

    @GetMapping("{id}")
    ResponseEntity<FilmRequest> findById(@PathVariable("id") Long id);

    @GetMapping("{id}")
    FilmRequest findFilmById(@PathVariable("id") Long id);

}


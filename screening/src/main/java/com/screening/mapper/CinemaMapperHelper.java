package com.screening.mapper;

import com.screening.domain.model.Cinema;
import com.screening.repository.CinemaRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CinemaMapperHelper {

    @Autowired
    private CinemaRepository cinemaRepository;

    public Cinema map(Long cinemaId) {
        return cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new EntityNotFoundException("Cinema not found with id: " + cinemaId));
    }
}
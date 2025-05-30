package com.screening.service;

import com.screening.domain.dto.CinemaDto;
import com.screening.repository.CinemaRepository;
import com.screening.domain.model.Cinema;
import com.screening.mapper.CinemaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaService {

    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;

    public List<CinemaDto> findAll() {
        return cinemaRepository.findAll().stream()
                .map(cinemaMapper::toDto)
                .collect(Collectors.toList());
    }

    public CinemaDto findById(Long id) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema not found with id: " + id));
        return cinemaMapper.toDto(cinema);
    }

    public CinemaDto create(CinemaDto dto) {
        Cinema cinema = cinemaMapper.toEntity(dto);
        Cinema saved = cinemaRepository.save(cinema);
        return cinemaMapper.toDto(saved);
    }

    public CinemaDto update(Long id, CinemaDto dto) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema not found with id: " + id));
        cinema.setName(dto.getName());
        cinema.setAddress(dto.getAddress());
        cinema.setCity(dto.getCity());
        Cinema updated = cinemaRepository.save(cinema);
        return cinemaMapper.toDto(updated);
    }

    public void delete(Long id) {
        cinemaRepository.deleteById(id);
    }
}

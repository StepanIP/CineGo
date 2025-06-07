package com.screening.service;

import com.screening.domain.dto.CinemaDto;
import com.screening.domain.dto.DiscountRequest;
import com.screening.domain.dto.DiscountResponse;
import com.screening.domain.dto.FilmRequest;
import com.screening.domain.dto.UserRequest;
import com.screening.mapper.CinemaMapper;
import com.screening.domain.model.Screening;
import com.screening.domain.dto.CreatedScreeningDto;
import com.screening.domain.dto.ScreeningAvailableSeats;
import com.screening.domain.dto.ScreeningRequestDto;
import com.screening.domain.dto.ScreeningResponseDto;
import com.screening.facade.ScreeningFacade;
import com.screening.mapper.ScreeningMapper;
import com.screening.repository.ScreeningRepository;
import com.screening.client.FilmClient;
import com.screening.exceptions.NotFoundException;
import com.screening.domain.model.Seat;
import com.screening.facade.SeatFacade;
import com.screening.domain.model.SeatStatus;
import com.screening.validator.ScreeningValidate;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.screening.service.ScreeningService.ErrorMessages.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Log4j2
public class ScreeningService implements ScreeningFacade {

    private final ScreeningRepository screeningRepository;

    private final ScreeningMapper mapper;

    private final ScreeningValidate validate;

    private final FilmClient filmClient;

    private final SeatFacade seatFacade;

    private final CinemaMapper cinemaMapper;

    @Transactional
    public CreatedScreeningDto saveScreening(ScreeningRequestDto screeningRequestDto, Long filmId) {
        FilmRequest film = validateAndGetFilm(filmId);
        validateScreeningData(screeningRequestDto, film);

        Screening screening = createAndSaveScreening(screeningRequestDto, film);
        assignSeatsToScreening(screening);

        log.info("Saved Screening with ID {}", screening.getId());
        return mapper.createdEntityToDto(screening);
    }

    private FilmRequest validateAndGetFilm(Long filmId) {
        FilmRequest film = filmClient.findFilmById(filmId);
        log.info("Film found: {}", film.id());
        return film;
    }

    public void bookingSets(Long id, int rowNumber, int seatsNumber) {
        seatFacade.checkSeatsAvailability(id, rowNumber, seatsNumber);
    }

    @Transactional
    public void validateScreeningData(ScreeningRequestDto screeningRequestDto, FilmRequest film) {
        validate.dataValidation(screeningRequestDto, film);
    }

    private Screening createAndSaveScreening(ScreeningRequestDto screeningRequestDto, FilmRequest film) {
        Screening screening = mapper.dtoToEntity(screeningRequestDto);
        screening.setFilmId(film.id());
        return screeningRepository.save(screening);
    }

    private void assignSeatsToScreening(Screening screening) {
        List<Seat> seats = createSeats();
        screening.setSeats(seats);
    }

    public ScreeningResponseDto getScreeningWithFilm(Long id) {
        Screening screening = screeningRepository.findById(id).orElseThrow();
        ResponseEntity<FilmRequest> filmResponse = filmClient.findById(screening.getFilmId());
        FilmRequest film = filmResponse.getBody();
        CinemaDto cinema = cinemaMapper.toDto(screening.getCinema());
        return new ScreeningResponseDto(screening.getId(), screening.getDate(), screening.getTime(), film, cinema);
    }

    public List<ScreeningResponseDto> getScreeningByFilmId(Long filmId) {
        List<Screening> screening = screeningRepository.findByFilmId(filmId);
        log.info("Found screenings {} with film id {}", screening, filmId);
        return screening.stream()
            .map(screeningItem -> getScreeningWithFilm(screeningItem.getId()))
            .collect(Collectors.toList());
    }

    public List<ScreeningResponseDto> getScreeningsByDate(LocalDate date) {
        validate.checkCorrectData(date);

        List<Screening> screenings = screeningRepository.findScreeningsByDate(date);

        return screenings.stream()
            .map(screening -> getScreeningWithFilm(screening.getId()))
            .collect(Collectors.toList());
    }

    public List<ScreeningResponseDto> getScreeningsByDateRange(LocalDate startDate, LocalDate endDate) {
        validate.checkCorrectDateRange(startDate, endDate);

        List<Screening> screenings = screeningRepository.findScreeningsByDateBetween(startDate, endDate);

        return screenings.stream()
            .map(screening -> getScreeningWithFilm(screening.getId()))
            .collect(Collectors.toList());
    }

    public Screening findById(Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() -> new NotFoundException(SCREENING_NOT_FOUND, screeningId));
        log.info("Found screening with id {}", screeningId);
        return screening;
    }

    public ScreeningAvailableSeats findAvailableSeats(Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() -> new NotFoundException(SCREENING_NOT_FOUND, screeningId));
        log.info("Found screening with id {}", screeningId);
        return mapper.screeningToSeatsDto(screening);
    }

    public List<ScreeningResponseDto> getScreeningsByCinemaId(Long filmLong) {
        List<Screening> screenings = screeningRepository.findByCinemaId(filmLong);
        return screenings.stream()
            .map(screening -> getScreeningWithFilm(screening.getId()))
            .collect(Collectors.toList());
    }

    @Transactional
    public List<Seat> createSeats() {
        return IntStream.rangeClosed(1, 10)
            .boxed()
            .flatMap(rowNumber -> IntStream.rangeClosed(1, 10)
                .mapToObj(seatInRow -> seatFacade.createSeat(rowNumber, seatInRow, SeatStatus.AVAILABLE)))
            .collect(Collectors.toList());

    }

    public DiscountResponse getDiscount(DiscountRequest discountRequest) {


        return null;
    }

    static final class ErrorMessages {
        static final String SCREENING_NOT_FOUND = "The screening with id %s not found";
    }

}

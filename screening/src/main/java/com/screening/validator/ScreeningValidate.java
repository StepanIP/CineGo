package com.screening.validator;

import com.screening.exceptions.InvalidDateRangeException;
import com.screening.exceptions.NotFoundException;
import com.screening.exceptions.TimeDifferenceException;
import com.screening.exceptions.TooLateException;
import com.screening.exceptions.TooManyScreeningException;
import com.screening.domain.model.Screening;
import com.screening.domain.dto.Film;
import com.screening.domain.dto.ScreeningRequestDto;

import com.screening.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
class ScreeningValidate {

    private final ScreeningRepository repository;

    public void checkCorrectData(LocalDate date) {
        if (!repository.existsByDate(date)) {
            throw new NotFoundException("Screening with date %s not found".formatted(date));
        }
    }

    public void checkCorrectDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException("Start date cannot be after end date");
        }

        if (repository.findScreeningsByDateBetween(startDate, endDate).isEmpty()) {
            throw new NotFoundException("No screenings found between dates %s and %s".formatted(startDate, endDate));
        }
    }

    public void checkCorrectTime(ScreeningRequestDto newScreening) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        if (newScreening.date().isAfter(today)) {
            return;
        }

        if (newScreening.date().isEqual(today) && newScreening.time().isAfter(now)) {
            return;
        }

        throw new TooLateException("It isn't possible to create new screening earlier than the current time");
    }

    public void dataValidation(ScreeningRequestDto screeningRequestDto, Film film) {
        List<Screening> screeningsOnSameDay = getScreeningsByDate(screeningRequestDto.date());

        checkNumberOfScreeningsDuringDay(screeningsOnSameDay);
        checkCorrectTime(screeningRequestDto);
        checkMinTimeDifference(screeningRequestDto, film, screeningsOnSameDay);
    }

    private List<Screening> getScreeningsByDate(LocalDate date) {
        return repository.findScreeningsByDate(date);
    }

    private void checkMinTimeDifference(ScreeningRequestDto newScreening, Film film, List<Screening> screeningsOnSameDay) {
        for (Screening existingScreening : screeningsOnSameDay) {
            long timeDifference = Duration.between(existingScreening.getTime(), newScreening.time()).toMinutes();
            if (Math.abs(timeDifference) < film.durationFilmInMinutes() + 20) {
                throw new TimeDifferenceException("Time difference is too small to start a new screening");
            }
        }
    }

    public void checkNumberOfScreeningsDuringDay(List<Screening> screeningsOnSameDay) {
        if (screeningsOnSameDay.size() >= 5) {
            throw new TooManyScreeningException("It is impossible to add another screening because there are already five");
        }
    }
}

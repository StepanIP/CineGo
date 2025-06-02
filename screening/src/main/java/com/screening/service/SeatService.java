package com.screening.service;

import com.screening.exceptions.AlreadyTakenException;
import com.screening.exceptions.NotFoundException;
import com.screening.domain.model.Screening;
import com.screening.domain.model.Seat;
import com.screening.facade.ScreeningFacade;
import com.screening.facade.SeatFacade;
import com.screening.repository.SeatRepository;
import com.screening.domain.model.SeatStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import static com.screening.service.SeatService.ErrorMessages.*;

@Service
@AllArgsConstructor
@Log4j2
public class SeatService implements SeatFacade {

    private final ScreeningFacade screeningFacade;

    private final SeatRepository seatRepository;

    @Transactional
    public void checkSeatsAvailability(Long screeningId, int rowsNumber, int seatInRow) {
        Screening screening = screeningFacade.findById(screeningId);

        Seat seat = seatRepository.findByScreeningAndRowsNumberAndSeatInRow(screening, rowsNumber, seatInRow);

        if (seat != null) {
            if (seat.getStatus() == SeatStatus.AVAILABLE) {
                seat.setStatus(SeatStatus.TAKEN);
                seatRepository.save(seat);

            } else if (seat.getStatus() == SeatStatus.TAKEN) {
                throw new AlreadyTakenException(SEAT_ALREADY_TAKEN);
            }
        } else {
            throw new NotFoundException(NOT_FOUND_SEAT, rowsNumber, seatInRow);
        }

    }

    public Seat createSeat(int rowNumber, int seatInRow, SeatStatus status) {
        Seat seat = new Seat();
        seat.setRowsNumber(rowNumber);
        seat.setSeatInRow(seatInRow);
        seat.setStatus(status);
        return seat;
    }

    public Seat findById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Seat not found with ID: " + id));
    }

    static final class ErrorMessages {

        static final String NOT_FOUND_SEAT = "Seat not found for provided row %d and seat number %d.";

        static final String SEAT_ALREADY_TAKEN = "This seat is already taken.";

    }

}

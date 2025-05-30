package com.screening.facade;

import com.screening.domain.model.Seat;
import com.screening.domain.model.SeatStatus;

public interface SeatFacade {

    void checkSeatsAvailability(Long screeningId, int rowsNumber, int seatInRow);

    Seat createSeat(int rowNumber, int seatInRow, SeatStatus status);
}

package com.screening.repository;

import java.util.List;

import com.screening.domain.model.Screening;
import com.screening.domain.model.Seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Seat findByScreeningAndRowsNumberAndSeatInRow(Screening screening, int rowsNumber, int seatInRow);

    List<Seat> findByScreeningId(Long screeningId);
}

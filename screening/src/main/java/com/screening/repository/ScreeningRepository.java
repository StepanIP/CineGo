package com.screening.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import com.screening.domain.model.Screening;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT s FROM Screening s WHERE s.date = :date")
    List<Screening> findScreeningsByDate(@Param("date") LocalDate date);

    @Query("SELECT s FROM Screening s WHERE s.date BETWEEN :startDate AND :endDate")
    List<Screening> findScreeningsByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    boolean existsByDate(LocalDate date);

    List<Screening> findByCinemaId(Long cinemaId);
}

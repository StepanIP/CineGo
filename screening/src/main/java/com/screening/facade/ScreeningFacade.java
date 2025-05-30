package com.screening.facade;

import com.screening.domain.model.Screening;

public interface ScreeningFacade {

    Screening findById(Long screeningId);
}

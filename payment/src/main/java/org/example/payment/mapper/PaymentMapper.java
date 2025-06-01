package org.example.payment.mapper;

import org.example.payment.domain.dto.PaymentRequestDto;
import org.example.payment.domain.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toEntity(PaymentRequestDto dto);
}

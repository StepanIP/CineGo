package org.example.payment.mapper;

import org.example.payment.domain.dto.PaymentRequestDto;
import org.example.payment.domain.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "amount", source = "total")
    Payment toEntity(PaymentRequestDto dto);
}

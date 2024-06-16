package com.mapper;

import org.mapstruct.Mapper;

import com.dto.PaymentDetailsDTO;
import com.model.PaymentDetails;

 @Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDetailsDTO toPaymentDTO(PaymentDetails paymentDetails);

    PaymentDetails toPayment(PaymentDetailsDTO paymentDetailsDto);
}

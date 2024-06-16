package com.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardDTO {

    private String cardHolderName;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cvv;

}

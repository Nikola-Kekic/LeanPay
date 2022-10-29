package com.example.loancalculator.service;

import com.example.loancalculator.dto.PaymentDto;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    PaymentDto save(PaymentDto payment);
    Optional<PaymentDto> findById(long id);
    List<PaymentDto> findPaymentsByLoanId(long loanId);

}

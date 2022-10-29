package com.example.loancalculator.service;

import com.example.loancalculator.dto.LoanRequestDto;
import com.example.loancalculator.dto.PaymentDto;


import java.util.List;

public interface PaymentsScheduleService {
    List<PaymentDto> getSchedule(double loanAmount, double interestRate, int numberOfPayments, String type) throws Exception;
    List<PaymentDto> createSchedule(LoanRequestDto loanRequest) throws Exception;

}

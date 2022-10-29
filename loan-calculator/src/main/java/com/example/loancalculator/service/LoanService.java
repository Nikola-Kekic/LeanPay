package com.example.loancalculator.service;

import com.example.loancalculator.dto.LoanRequestDto;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    List<LoanRequestDto> findAll();
    LoanRequestDto save(LoanRequestDto loanRequest);
    Optional<LoanRequestDto> findById(long id);
    LoanRequestDto update(LoanRequestDto loanRequest);
    void delete(long id);

}

package com.example.loancalculator.mapper;

import com.example.loancalculator.dto.LoanRequestDto;
import com.example.loancalculator.model.Loan;
import com.example.loancalculator.model.enumeration.LoanTermType;
import org.springframework.stereotype.Component;

@Component
public class LoanRequestMapper implements GenericMapper<Loan, LoanRequestDto> {

    @Override
    public Loan toEntity(LoanRequestDto dto) {
        return new Loan(
               dto.getLoanAmount(),
               dto.getInterestRate(),
               dto.getLoanTerm(),
                LoanTermType.valueOf(dto.getType())
               );
    }

    @Override
    public LoanRequestDto toDto(Loan entity) {
        return new LoanRequestDto(
                entity.getLoanAmount(),
                entity.getInterestRate(),
                entity.getLoanTerm(),
                entity.getType().toString()
        );
    }
}

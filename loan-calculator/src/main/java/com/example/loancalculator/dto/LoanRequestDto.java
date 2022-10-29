package com.example.loancalculator.dto;

import com.example.loancalculator.dto.stringValidation.TypeSubset;
import com.example.loancalculator.model.enumeration.LoanTermType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class LoanRequestDto implements Dto{
    @Min(value = 100, message = "Min value for loan amount is 100.")
    private double loanAmount;

    @Min(value = 0, message = "Min value for interest rate is 0.")
    @Max(value = 100, message = "Max value for interest rate 100.")
    private double interestRate;

    @Min(value = 2, message = "Min value for loan term is 2.")
    @Max(value = 360, message = "Max value for loan term is 360.")
    private int loanTerm;

    @NotBlank(message = "Term type is mandatory.")
    @TypeSubset(anyOf = {LoanTermType.Month, LoanTermType.Year}, message = "Invalid loan term type.")
    private String type;

    public LoanRequestDto(double loanAmount, double interestRate, int loanTerm, String type) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTerm = loanTerm;
        this.type = type;
    }

}

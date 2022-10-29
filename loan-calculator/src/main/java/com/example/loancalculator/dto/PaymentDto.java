package com.example.loancalculator.dto;

import com.example.loancalculator.model.Loan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PaymentDto implements Dto {

    private long id;

    private double paymentAmount;

    private double principalAmount;

    private double interestAmount;

    private double balanceOwed;

    private LoanRequestDto loan;

    public PaymentDto(double paymentAmount, double principalAmount, double interestAmount, double balanceOwed, LoanRequestDto loan) {
        this.paymentAmount = paymentAmount;
        this.principalAmount = principalAmount;
        this.interestAmount = interestAmount;
        this.balanceOwed = balanceOwed;
        this.loan = loan;
    }

    public PaymentDto(double balanceOwed, double interestAmount, double paymentAmount, double principalAmount) {
        this.paymentAmount = paymentAmount;
        this.principalAmount = principalAmount;
        this.interestAmount = interestAmount;
        this.balanceOwed = balanceOwed;
    }

}

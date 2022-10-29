package com.example.loancalculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@javax.persistence.Entity
@Table(name = "payment")
@RequiredArgsConstructor
public class Payment implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "payment_amount", nullable = false)
    private double paymentAmount;

    @Column(name = "principal_amount", nullable = false)
    private double principalAmount;

    @Column(name = "interest_amount", nullable = false)
    private double interestAmount;

    @Column(name = "balance_owed", nullable = false)
    private double balanceOwed;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "loan_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Loan loan;

    public Payment(double paymentAmount, double principalAmount, double interestAmount, double balanceOwed, Loan loan) {
        this.paymentAmount = paymentAmount;
        this.principalAmount = principalAmount;
        this.interestAmount = interestAmount;
        this.balanceOwed = balanceOwed;
        this.loan = loan;
    }

    public Payment(double balanceOwed, double interestAmount, double paymentAmount, double principalAmount) {
        this.paymentAmount = paymentAmount;
        this.principalAmount = principalAmount;
        this.interestAmount = interestAmount;
        this.balanceOwed = balanceOwed;
    }
}

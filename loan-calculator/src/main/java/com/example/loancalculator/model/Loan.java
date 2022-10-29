package com.example.loancalculator.model;

import com.example.loancalculator.model.enumeration.LoanTermType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@javax.persistence.Entity
@Table(name = "loan")
@RequiredArgsConstructor
public class Loan implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "loan_amount", nullable = false)
    private double loanAmount;

    @Column(name = "interest_rate", length = 3, nullable = false)
    private double interestRate;

    @Column(name = "loan_term", length = 3, nullable = false)
    private int loanTerm;

    @Column(name = "type_of_term", nullable = false)
    private LoanTermType type;

    @OneToMany(mappedBy = "loan")
    private List<Payment> payments;

    public Loan(double loanAmount, double interestRate, int loanTerm, LoanTermType type) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTerm = loanTerm;
        this.type = type;
    }

}

package com.example.loancalculator.repository;

import com.example.loancalculator.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Number> {

    @Query(value = "SELECT * " +
                   "FROM Loan l WHERE l.loan_amount = :loanAmount " +
                                 "AND l.loan_term = :loanTerm " +
                                 "AND l.interest_rate = :interestRate " +
                                 "AND l.type_of_term = :type ", nativeQuery = true)
    Optional<List<Loan>> findByAllArgs(@Param("loanAmount") double loanAmount,
                                      @Param("loanTerm") int loanTerm,
                                      @Param("interestRate") double interestRate,
                                      @Param("type") int type);

}

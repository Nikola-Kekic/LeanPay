package com.example.loancalculator.repository;

import com.example.loancalculator.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT * FROM Payment p WHERE p.loan_id = :loanId ", nativeQuery = true)
    Optional<List<Payment>> findByLoanId(@Param("loanId") Long loanId);
}

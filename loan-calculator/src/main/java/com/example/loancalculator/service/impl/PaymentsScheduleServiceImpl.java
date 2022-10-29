package com.example.loancalculator.service.impl;

import com.example.loancalculator.dto.LoanRequestDto;
import com.example.loancalculator.dto.PaymentDto;
import com.example.loancalculator.mapper.LoanRequestMapper;
import com.example.loancalculator.mapper.PaymentMapper;
import com.example.loancalculator.model.Loan;
import com.example.loancalculator.model.Payment;
import com.example.loancalculator.model.enumeration.LoanTermType;
import com.example.loancalculator.repository.LoanRepository;
import com.example.loancalculator.repository.PaymentRepository;
import com.example.loancalculator.service.PaymentsScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class PaymentsScheduleServiceImpl implements PaymentsScheduleService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private LoanRequestMapper loanRequestMapper;

    @Override
    public List<PaymentDto> getSchedule(double loanAmount, double interestRate, int numberOfPayments, String type) throws Exception{
        LoanRequestDto loan = new LoanRequestDto(round(loanAmount, 2), round(interestRate,2), numberOfPayments, type);
        return createSchedule(loan);
    }

    @Override
    public List<PaymentDto> createSchedule(LoanRequestDto loanRequestDto) throws Exception {

        List<Payment> payments;
        Loan loan = loanRequestMapper.toEntity(loanRequestDto);

        switch (loan.getType()) {
            case Month:
                payments = calculate(loan, 1);
                break;

            case Year:
                payments = calculate(loan, 12);
                break;

            default:
                throw new Exception("Unknown loan term type value!");
        }

        loan.setPayments(payments);
        loanRepository.save(loan);

        return payments.stream().map(entity -> paymentMapper.toDto(entity)).collect(Collectors.toList());
    }

    public List<Payment> calculate(Loan loanRequest, int multiplyFactor) {

        List<Payment> payments = new ArrayList<>();

        double interestFactor = loanRequest.getInterestRate() / 100 / 12;
        double numberOfPayments = loanRequest.getLoanTerm() * multiplyFactor;
        double m = 1+interestFactor;

        double paymentAmount = round((loanRequest.getLoanAmount()*interestFactor* Math.pow(m, numberOfPayments))/(Math.pow(m, numberOfPayments)-1),2);

        double balanceOwed = loanRequest.getLoanAmount();
        //double totalPayment = round(calculateMonth(loanRequest) * loanRequest.getLoanTerm() * multiplyFactor, 2);
        //totalInterestPayment = round(totalPayment - loanRequest.getLoanAmount(), 2);

        for (int i = 0; i < loanRequest.getLoanTerm() * multiplyFactor; i++) {

            double interestAmount = getInterestAmount(balanceOwed, loanRequest.getInterestRate());
            double principalAmount = round(paymentAmount - interestAmount, 2);
            balanceOwed = round(balanceOwed - principalAmount, 2);

            // Cancellation of the balance owed
            if (i == loanRequest.getLoanTerm() * multiplyFactor - 1){
                paymentAmount = round(paymentAmount + balanceOwed,2);
                principalAmount = round(paymentAmount - interestAmount, 2);
                balanceOwed = 0.00;
            }

            Payment payment = new Payment(paymentAmount,
                    principalAmount,
                    interestAmount,
                    balanceOwed,
                    loanRequest);

            paymentRepository.save(payment);
            payments.add(payment);
        }

        return payments;
    }

    public double getInterestAmount(double balanceOwed, double interestRatePercent) {
        double interestRate = interestRatePercent/100/12;
        return round(interestRate*balanceOwed,2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}

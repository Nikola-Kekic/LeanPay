package com.example.loancalculator.service.impl;

import com.example.loancalculator.dto.PaymentDto;
import com.example.loancalculator.mapper.PaymentMapper;
import com.example.loancalculator.model.Payment;
import com.example.loancalculator.repository.PaymentRepository;
import com.example.loancalculator.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentMapper paymentMapper;

    @Override
    public PaymentDto save(PaymentDto payment) {
        return paymentMapper.toDto(paymentRepository.save(paymentMapper.toEntity(payment)));
    }

    @Override
    public Optional<PaymentDto> findById(long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isPresent()) {
            return Optional.of(paymentMapper.toDto(payment.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<PaymentDto> findPaymentsByLoanId(long loanId){
        List<Payment> payments = this.paymentRepository.findByLoanId(loanId).get();
        return payments.stream().map(entity -> paymentMapper.toDto(entity)).collect(Collectors.toList());
    }

}

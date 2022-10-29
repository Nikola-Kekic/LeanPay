package com.example.loancalculator.mapper;


import com.example.loancalculator.dto.PaymentDto;
import com.example.loancalculator.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper implements GenericMapper<Payment, PaymentDto> {

    @Autowired
    LoanRequestMapper loanRequestMapper;

    @Override
    public Payment toEntity(PaymentDto dto) {
        return new Payment(
                dto.getPaymentAmount(),
                dto.getPrincipalAmount(),
                dto.getInterestAmount(),
                dto.getBalanceOwed(),
                loanRequestMapper.toEntity(dto.getLoan())

        );
    }

    @Override
    public PaymentDto toDto(Payment entity) {
        return new PaymentDto(
                entity.getPaymentAmount(),
                entity.getPrincipalAmount(),
                entity.getInterestAmount(),
                entity.getBalanceOwed(),
                loanRequestMapper.toDto(entity.getLoan())
        );
    }
}

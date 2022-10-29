package com.example.loancalculator.service.impl;

import com.example.loancalculator.dto.LoanRequestDto;
import com.example.loancalculator.mapper.LoanRequestMapper;
import com.example.loancalculator.model.Loan;
import com.example.loancalculator.repository.LoanRepository;
import com.example.loancalculator.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanRequestMapper loanRequestMapper;

    @Override
    public List<LoanRequestDto> findAll() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map(entity -> loanRequestMapper.toDto(entity)).collect(Collectors.toList());
    }

    @Override
    public LoanRequestDto save(LoanRequestDto loanRequest) {
        return loanRequestMapper.toDto(loanRepository.save(loanRequestMapper.toEntity(loanRequest)));
    }

    @Override
    public Optional<LoanRequestDto> findById(long id) {
        Optional<Loan> loan = loanRepository.findById(id);
        if(loan.isPresent()) {
            return Optional.of(loanRequestMapper.toDto(loan.get()));
        }
        return Optional.empty();
    }

    @Override
    public LoanRequestDto update(LoanRequestDto loanRequest) {
        return null;
    }

    @Override
    public void delete(long id) { }

}

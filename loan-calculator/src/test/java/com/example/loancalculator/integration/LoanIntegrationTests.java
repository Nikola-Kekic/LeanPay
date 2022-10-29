package com.example.loancalculator.integration;

import com.example.loancalculator.dto.LoanRequestDto;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import com.example.loancalculator.model.Loan;
import com.example.loancalculator.model.Payment;
import com.example.loancalculator.model.enumeration.LoanTermType;
import com.example.loancalculator.repository.LoanRepository;
import com.example.loancalculator.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoanIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PaymentRepository paymentRepository;
    public LoanRequestDto loanRequestDto;
    public List<Payment> expectedPayments = new ArrayList<>();


    @Before
    public void setup() {
        loanRequestDto = new LoanRequestDto(1000, 5, 5, "Month");
        expectedPayments.add( new Payment(801.66, 4.17, 202.51, 198.34));
        expectedPayments.add( new Payment(602.49, 3.34, 202.51, 199.17));
        expectedPayments.add( new Payment(402.49, 2.51,202.51,200));
        expectedPayments.add( new Payment(201.66, 1.68, 202.51,200.83));
        expectedPayments.add( new Payment( 0, 0.84, 202.5, 201.66));
    }

    @Test
    public void loanCalculationWorksThroughAllLayers() throws Exception {
        List<Loan> alreadyExistingLoans = loanRepository.findByAllArgs(loanRequestDto.getLoanAmount(), loanRequestDto.getLoanTerm(), loanRequestDto.getInterestRate(), 0).get();

        mockMvc.perform(post("/loan")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loanRequestDto)))
                        .andExpect(status().isOk());

        List<Loan> updatedLoans = loanRepository.findByAllArgs(loanRequestDto.getLoanAmount(), loanRequestDto.getLoanTerm(), loanRequestDto.getInterestRate(), 0).get();
        assertThat(updatedLoans.size()).isEqualTo(alreadyExistingLoans.size()+1);

        Loan loan = updatedLoans.stream().max(Comparator.comparing(l -> l.getId())).get();

        List<Payment> payments = paymentRepository.findByLoanId(loan.getId()).get();

        assertThat(loan.getLoanTerm()).isEqualTo(loanRequestDto.getLoanTerm());
        assertThat(loan.getLoanAmount()).isEqualTo(loanRequestDto.getLoanAmount());
        assertThat(loan.getInterestRate()).isEqualTo(loanRequestDto.getInterestRate());
        assertThat(loan.getType()).isEqualTo(LoanTermType.valueOf(loanRequestDto.getType()));

        for(int i=0; i<payments.size(); i++) {
            AssertionsForClassTypes.assertThat(payments.get(i).getBalanceOwed()).isEqualTo(expectedPayments.get(i).getBalanceOwed());
            AssertionsForClassTypes.assertThat(payments.get(i).getInterestAmount()).isEqualTo(expectedPayments.get(i).getInterestAmount());
            AssertionsForClassTypes.assertThat(payments.get(i).getPaymentAmount()).isEqualTo(expectedPayments.get(i).getPaymentAmount());
            AssertionsForClassTypes.assertThat(payments.get(i).getPrincipalAmount()).isEqualTo(expectedPayments.get(i).getPrincipalAmount());
        }

    }

}

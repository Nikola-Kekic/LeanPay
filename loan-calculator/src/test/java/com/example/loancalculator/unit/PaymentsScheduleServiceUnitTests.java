package com.example.loancalculator.unit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;


import com.example.loancalculator.dto.LoanRequestDto;
import com.example.loancalculator.dto.PaymentDto;
import com.example.loancalculator.mapper.LoanRequestMapper;
import com.example.loancalculator.mapper.PaymentMapper;
import com.example.loancalculator.model.Loan;
import com.example.loancalculator.model.Payment;
import com.example.loancalculator.model.enumeration.LoanTermType;
import com.example.loancalculator.repository.LoanRepository;
import com.example.loancalculator.repository.PaymentRepository;
import com.example.loancalculator.service.PaymentService;
import java.util.ArrayList;
import java.util.List;

import com.example.loancalculator.service.impl.PaymentsScheduleServiceImpl;
import org.junit.Test;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class PaymentsScheduleServiceUnitTests {
    @InjectMocks
    private PaymentsScheduleServiceImpl service;
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentService paymentService;
    @Mock
    private PaymentMapper paymentMapper;
    @Mock
    private LoanRequestMapper loanRequestMapper;

    List<PaymentDto> expectedPaymentDtoMonthList= new ArrayList<>();
    List<PaymentDto> expectedPaymentDtoYearList = new ArrayList<>();
    List<Payment> expectedPaymentYearList = new ArrayList<>();
    List<Payment> expectedPaymentMonthList = new ArrayList<>();

    double totalPaymentMonth, totalPaymentYear;
    double loanAmountMonth, loanAmountYear;
    double interestRateMonth, interestRateYear;
    int numberOfPaymentsMonth, numberOfPaymentsYear;
    String typeMonth, typeYear;
    PaymentDto paymentDtoMonth, paymentDtoYear;
    Payment paymentMonth, paymentYear;
    Loan loanRequestMonth, loanRequestYear;
    LoanRequestDto loanRequestDtoMonth, loanRequestDtoYear;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);

        totalPaymentMonth = 20250.69;
        loanAmountMonth = 20000;
        interestRateMonth = 5;
        numberOfPaymentsMonth = 5;
        typeMonth = "Month";

        loanRequestMonth = new Loan(loanAmountMonth, interestRateMonth, numberOfPaymentsMonth, LoanTermType.valueOf(typeMonth));
        loanRequestDtoMonth = new LoanRequestDto(loanAmountMonth, interestRateMonth, numberOfPaymentsMonth, typeMonth);


        paymentMonth = new Payment(4050.14, 3966.81,
                              83.33,16033.19, loanRequestMonth);
        expectedPaymentMonthList.add(paymentMonth);

        paymentMonth = new Payment(4050.14, 3983.34,
                               66.80, 12049.85, loanRequestMonth);
        expectedPaymentMonthList.add(paymentMonth);

        paymentMonth = new Payment(4050.14, 3999.93,
                               50.21, 8049.92, loanRequestMonth);
        expectedPaymentMonthList.add(paymentMonth);

        paymentMonth = new Payment(4050.14, 4016.60,
                               33.54, 4033.32, loanRequestMonth);
        expectedPaymentMonthList.add(paymentMonth);

        paymentMonth = new Payment(4050.13, 4033.32,
                               16.81, 0.00, loanRequestMonth);
        expectedPaymentMonthList.add(paymentMonth);

        paymentDtoMonth =  new PaymentDto(
                4050.14,
                3983.81,
                83.33,
                16033.19,
                new LoanRequestDto(20000, 5, 5, "Month")
        );
        expectedPaymentDtoMonthList.add(paymentDtoMonth);

        paymentDtoMonth =  new PaymentDto(
                4050.14,
                3983.34,
                66.80,
                12049.85,
                new LoanRequestDto(20000, 5, 5, "Month")
        );
        expectedPaymentDtoMonthList.add(paymentDtoMonth);

        paymentDtoMonth =  new PaymentDto(
                4050.14,
                3999.93,
                50.21,
                8049.92,
                new LoanRequestDto(20000, 5, 5, "Month")
        );
        expectedPaymentDtoMonthList.add(paymentDtoMonth);

        paymentDtoMonth =  new PaymentDto(
                4050.14,
                4016.60,
                33.54,
                4033.32,
                new LoanRequestDto(20000, 5, 5, "Month")
        );
        expectedPaymentDtoMonthList.add(paymentDtoMonth);

        paymentDtoMonth =  new PaymentDto(
                4050.13,
                4033.33,
                16.81,
                0.00,
                new LoanRequestDto(20000, 5, 5, "Month")
        );
        expectedPaymentDtoMonthList.add(paymentDtoMonth);


        totalPaymentYear = 20545.80;
        loanAmountYear = 20000;
        interestRateYear = 5;
        numberOfPaymentsYear = 1;
        typeYear = "Year";

        loanRequestYear = new Loan(loanAmountYear, interestRateYear, numberOfPaymentsYear, LoanTermType.valueOf(typeYear));
        loanRequestDtoYear = new LoanRequestDto(loanAmountYear, interestRateYear, numberOfPaymentsYear, typeYear);

        paymentYear = new Payment(1712.15, 1628.82,
                83.33,18371.18, loanRequestYear);
        expectedPaymentYearList.add(paymentYear);

        paymentYear = new Payment(1712.15, 1635.6,
                76.55, 16735.58, loanRequestYear);
        expectedPaymentYearList.add(paymentYear);

        expectedPaymentYearList.add(paymentYear);
        expectedPaymentYearList.add(paymentYear);
        expectedPaymentYearList.add(paymentYear);
        expectedPaymentYearList.add(paymentYear);
        expectedPaymentYearList.add(paymentYear);
        expectedPaymentYearList.add(paymentYear);
        expectedPaymentYearList.add(paymentYear);
        expectedPaymentYearList.add(paymentYear);
        expectedPaymentYearList.add(paymentYear);

        paymentYear = new Payment(1712.15, 1705.05,
                7.1, 0.00, loanRequestMonth);
        expectedPaymentYearList.add(paymentYear);

        paymentDtoYear =  new PaymentDto(
                1712.15,
                1628.82,
                83.33,
                18371.18,
                new LoanRequestDto(20000, 5, 1, "Year")
        );
        expectedPaymentDtoYearList.add(paymentDtoYear);

        paymentDtoYear =  new PaymentDto(
                1712.15,
                1635.6,
                76.55,
                16735.58,
                new LoanRequestDto(20000, 5, 1, "Year")
        );
        expectedPaymentDtoYearList.add(paymentDtoYear);

        expectedPaymentDtoYearList.add(paymentDtoYear);
        expectedPaymentDtoYearList.add(paymentDtoYear);
        expectedPaymentDtoYearList.add(paymentDtoYear);
        expectedPaymentDtoYearList.add(paymentDtoYear);
        expectedPaymentDtoYearList.add(paymentDtoYear);
        expectedPaymentDtoYearList.add(paymentDtoYear);
        expectedPaymentDtoYearList.add(paymentDtoYear);
        expectedPaymentDtoYearList.add(paymentDtoYear);
        expectedPaymentDtoYearList.add(paymentDtoYear);

        paymentDtoYear=  new PaymentDto(
                1712.15,
                1705.05,
                7.1,
                0.00,
                new LoanRequestDto(20000, 5, 1, "Year")
        );
        expectedPaymentDtoYearList.add(paymentDtoYear);

    }

    @Test
    public void givenParameters_whenCalculateMonthly_thenReturnPaymentList() {
        // Given
        when(loanRepository.save(loanRequestMonth)).thenReturn(loanRequestMonth);
        for(int i = 0; i < expectedPaymentMonthList.size(); i++){
            Payment payment = expectedPaymentMonthList.get(i);
            when(paymentRepository.save(payment)).thenReturn(payment);
        }

        // When
        //List<PaymentDto> paymentDtoList = service.getSchedule(loanAmount, interestRate, numberOfPayments, type);
        List<Payment> paymentList = service.calculate(loanRequestMonth, 1);

        // Then

        assertThat(paymentList)
                .isNotNull()
                .isNotEmpty()
                //.isEqualTo(expectedPaymentList)
                .size().isEqualTo(numberOfPaymentsMonth);
        for(int i=0; i<paymentList.size(); i++) {
            assertThat(paymentList.get(i).getBalanceOwed()).isEqualTo(expectedPaymentMonthList.get(i).getBalanceOwed());
            assertThat(paymentList.get(i).getInterestAmount()).isEqualTo(expectedPaymentMonthList.get(i).getInterestAmount());
            assertThat(paymentList.get(i).getPaymentAmount()).isEqualTo(expectedPaymentMonthList.get(i).getPaymentAmount());
            assertThat(paymentList.get(i).getPrincipalAmount()).isEqualTo(expectedPaymentMonthList.get(i).getPrincipalAmount());
        }

        assertThat(getTotalPaymentAmount(paymentList)).isEqualTo((totalPaymentMonth * 100.0)/100);
    }

    @Test
    public void givenParameters_whenCalculateYearly_thenReturnPaymentList() {
        // Given
        when(loanRepository.save(loanRequestYear)).thenReturn(loanRequestYear);
        for(int i = 0; i < expectedPaymentYearList.size(); i++){
            Payment payment = expectedPaymentYearList.get(i);
            when(paymentRepository.save(payment)).thenReturn(payment);
        }

        // When
        List<Payment> paymentList = service.calculate(loanRequestYear, 12);

        // Then

        assertThat(paymentList)
                .isNotNull()
                .isNotEmpty()
                .size().isEqualTo(numberOfPaymentsYear*12);

        assertThat(paymentList.get(1).getBalanceOwed()).isEqualTo(expectedPaymentYearList.get(1).getBalanceOwed());
        assertThat(paymentList.get(1).getInterestAmount()).isEqualTo(expectedPaymentYearList.get(1).getInterestAmount());
        assertThat(paymentList.get(1).getPaymentAmount()).isEqualTo(expectedPaymentYearList.get(1).getPaymentAmount());
        assertThat(paymentList.get(1).getPrincipalAmount()).isEqualTo(expectedPaymentYearList.get(1).getPrincipalAmount());

        assertThat(paymentList.get(11).getBalanceOwed()).isEqualTo(expectedPaymentYearList.get(11).getBalanceOwed());
        assertThat(paymentList.get(11).getInterestAmount()).isEqualTo(expectedPaymentYearList.get(11).getInterestAmount());
        assertThat(paymentList.get(11).getPaymentAmount()).isEqualTo(expectedPaymentYearList.get(11).getPaymentAmount());
        assertThat(paymentList.get(11).getPrincipalAmount()).isEqualTo(expectedPaymentYearList.get(11).getPrincipalAmount());

        assertThat(getTotalPaymentAmount(paymentList)).isEqualTo((totalPaymentYear * 100.0)/100);
    }

    @Test
    public void givenParameters_whenGetInterestAmount_thenReturnInterestAmount() {
        double balanceOwed = 18371.18;
        double interestRatePercent = 5;
        double expectedInterestAmount = 76.55;
        // When
        double interestAmount = service.getInterestAmount(balanceOwed, interestRatePercent);

        // Then
        assertThat(interestAmount)
                .isEqualTo(expectedInterestAmount);

    }
    public double getTotalPaymentAmount(List<Payment> paymentList) {

        double totalPaymentAmount = paymentList.stream()
                .map(pay -> pay.getPaymentAmount())
                .reduce(0.0, (p1, p2) -> p1 + p2);

        totalPaymentAmount = Math.round(totalPaymentAmount * 100.0) / 100.0;
        return totalPaymentAmount;
    }

}

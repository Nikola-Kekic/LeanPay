package com.example.loancalculator.controller;

import com.example.loancalculator.dto.LoanRequestDto;
import com.example.loancalculator.dto.stringValidation.TypeSubset;
import com.example.loancalculator.model.enumeration.LoanTermType;
import com.example.loancalculator.service.LoanService;
import com.example.loancalculator.service.impl.PaymentsScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("loan")
public class LoanController {
    @Autowired
    private PaymentsScheduleServiceImpl schedulePaymentService;
    @Autowired
    private LoanService loanService;

    @GetMapping()
    public @ResponseBody ResponseEntity<List<LoanRequestDto>> findAll() {
        return ResponseEntity.ok(loanService.findAll());
    }

    @PostMapping()
    public @ResponseBody ResponseEntity<Object> calculatePayments(@Valid @RequestBody LoanRequestDto loanRequestDto) {
        try{
            return new ResponseEntity<>(schedulePaymentService.createSchedule(loanRequestDto),
                                        new HttpHeaders(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    /*
    *  Not the best practice because Get method may not put data in DB.
    *  It is not safe, nor good for security.
    *  This is a good way to do all the operations in one client request.
    */
    @GetMapping("calculate")
    public @ResponseBody ResponseEntity<Object> calculatePayments(@RequestParam Double loanAmount,
                                                                  @RequestParam Double interestRate,
                                                                  @RequestParam Integer numberOfPayments,
                                                                  @RequestParam @TypeSubset(anyOf = {LoanTermType.Month, LoanTermType.Year}, message = "Invalid loan term type.") String type) {
        try{
            return new ResponseEntity<>(
                    schedulePaymentService.getSchedule(loanAmount, interestRate, numberOfPayments, type), new HttpHeaders(), HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

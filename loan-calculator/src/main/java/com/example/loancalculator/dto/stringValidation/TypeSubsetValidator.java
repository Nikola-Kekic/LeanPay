package com.example.loancalculator.dto.stringValidation;

import com.example.loancalculator.model.enumeration.LoanTermType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TypeSubsetValidator implements ConstraintValidator<TypeSubset, String> {
    private List<String> validEnumValues;

    @Override
    public void initialize(TypeSubset annotation) {
        this.validEnumValues = Arrays.stream(annotation.anyOf())
                .map(type -> type.toString()).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return validEnumValues.contains(value);
    }
}
package com.example.loancalculator.mapper;

import com.example.loancalculator.dto.Dto;
import com.example.loancalculator.model.Entity;

public interface GenericMapper <E extends Entity, D extends Dto>{

    E toEntity(D dto);
    D toDto(E entity);
}

package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRecordDTO(@NotBlank String name, @NotNull BigDecimal value) {
//Para que entre em vigor essas anotações acima (@NotBlank e @NotNull, é necessário na chamada utiliar a anotação @Valid

}

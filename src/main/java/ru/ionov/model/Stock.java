package ru.ionov.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Stock {

    @NotEmpty
    private String symbol;

    @NotNull
    private Long volume;
}

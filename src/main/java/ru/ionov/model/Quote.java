package ru.ionov.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Quote {

    private BigDecimal latestPrice;
}

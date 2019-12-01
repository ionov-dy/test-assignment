package ru.ionov.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Allocation {

    private String sector;

    private BigDecimal assetValue;

    private BigDecimal proportion;
}

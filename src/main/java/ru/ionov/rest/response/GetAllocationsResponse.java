package ru.ionov.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ru.ionov.model.Allocation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllocationsResponse {

    private BigDecimal value;

    private List<Allocation> allocations;
}

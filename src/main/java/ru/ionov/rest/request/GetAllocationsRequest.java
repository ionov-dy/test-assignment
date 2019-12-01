package ru.ionov.rest.request;

import ru.ionov.model.Stock;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class GetAllocationsRequest {

    @Valid
    @NotEmpty
    private List<Stock> stocks;
}

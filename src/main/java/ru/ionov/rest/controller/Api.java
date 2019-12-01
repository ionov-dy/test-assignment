package ru.ionov.rest.controller;

import org.springframework.web.bind.annotation.*;
import ru.ionov.rest.request.GetAllocationsRequest;
import ru.ionov.rest.response.GetAllocationsResponse;
import ru.ionov.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Api {

    private final StockService stockService;

    @PostMapping("/allocations")
    public ResponseEntity<GetAllocationsResponse> getAllocations(@RequestBody @Validated GetAllocationsRequest request) {
        return ResponseEntity.ok(stockService.getAllocations(request));
    }

}

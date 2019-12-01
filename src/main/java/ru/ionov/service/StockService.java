package ru.ionov.service;

import ru.ionov.rest.request.GetAllocationsRequest;
import ru.ionov.rest.response.GetAllocationsResponse;

public interface StockService {

    GetAllocationsResponse getAllocations(GetAllocationsRequest request);
}

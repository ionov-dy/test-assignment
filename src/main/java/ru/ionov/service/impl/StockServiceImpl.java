package ru.ionov.service.impl;

import lombok.RequiredArgsConstructor;
import ru.ionov.client.IEXClient;
import ru.ionov.model.IEXInfo;
import ru.ionov.model.Allocation;
import ru.ionov.model.Stock;
import ru.ionov.rest.request.GetAllocationsRequest;
import ru.ionov.rest.response.GetAllocationsResponse;
import ru.ionov.service.StockService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private static final int SCALE_VALUE = 3;
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
    private static final Long ZERO = 0L;

    private final IEXClient iexClient;

    @Override
    public GetAllocationsResponse getAllocations(GetAllocationsRequest request) {
        Map<String, IEXInfo> iexInfo = iexClient.getInfoForSymbols(
                request.getStocks().stream()
                .map(Stock::getSymbol)
                .collect(Collectors.toList())
        );
        Map<String, Long> symbolVolumes = request.getStocks().stream()
                .collect(Collectors.toMap(Stock::getSymbol, Stock::getVolume));
        BigDecimal totalCost = calculateCostFor(iexInfo.values(), symbolVolumes);
        Map<String, List<IEXInfo>> companiesBySector = iexInfo.values().stream()
                .collect(Collectors.groupingBy(i -> i.getCompany().getSector()));
        List<Allocation> allocations = calculateAllocations(companiesBySector, totalCost, symbolVolumes);
        return GetAllocationsResponse.builder()
                .value(totalCost)
                .allocations(allocations)
                .build();
    }

    private List<Allocation> calculateAllocations(Map<String, List<IEXInfo>> companiesBySector,
                                                  BigDecimal totalCost,
                                                  Map<String, Long> symbolsVolumes) {
        return companiesBySector.entrySet().stream()
                .map(e -> {
                    String sector = e.getKey();
                    List<IEXInfo> sectorCompanies = e.getValue();
                    BigDecimal sectorCost = calculateCostFor(sectorCompanies, symbolsVolumes);
                    return Allocation.builder()
                            .sector(sector)
                            .assetValue(sectorCost)
                            .proportion(sectorCost.divide(totalCost, ROUNDING_MODE))
                            .build();
                })
                .collect(Collectors.toList());
    }

    private BigDecimal calculateCostFor(Collection<IEXInfo> iexCompaniesInfo, Map<String, Long> symbolsVolumes) {
        return iexCompaniesInfo.stream()
                .map(i -> calculateCost(symbolsVolumes, i))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(SCALE_VALUE, ROUNDING_MODE);
    }

    private BigDecimal calculateCost(Map<String, Long> symbolsVolumes, IEXInfo iexCompanyInfo){
        Long volume = Optional.ofNullable(symbolsVolumes.get(iexCompanyInfo.getCompany().getSymbol())).orElse(ZERO);
        return iexCompanyInfo.getQuote().getLatestPrice().multiply(new BigDecimal(volume));
    }

}

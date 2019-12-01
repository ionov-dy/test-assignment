package ru.ionov.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ionov.client.IEXClient;
import ru.ionov.model.IEXInfo;
import ru.ionov.properties.IEXProperties;

import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class IEXClientImpl implements IEXClient {

    private static final String TYPES = "company,quote";
    private static final String FILTERS = "symbol,sector,latestPrice";

    private final RestTemplate restTemplate;
    private final IEXProperties iexProperties;

    @Override
    public Map<String, IEXInfo> getInfoForSymbols(Collection<String> symbols) {
        ParameterizedTypeReference<Map<String, IEXInfo>> responseType =
                new ParameterizedTypeReference<Map<String, IEXInfo>>() {};
        ResponseEntity<Map<String, IEXInfo>> response =
        restTemplate.exchange(getUri(symbols), HttpMethod.GET, null, responseType);
        return response.getBody();
    }

    private String getUri(Collection<String> symbols) {
        return UriComponentsBuilder.newInstance()
                .scheme(iexProperties.getScheme())
                .host(iexProperties.getHost())
                .path(iexProperties.getPath())
                .queryParam("symbols", String.join(",", symbols))
                .queryParam("types", TYPES)
                .queryParam("filter", FILTERS)
                .queryParam("token", iexProperties.getToken())
                .toUriString();
    }

}

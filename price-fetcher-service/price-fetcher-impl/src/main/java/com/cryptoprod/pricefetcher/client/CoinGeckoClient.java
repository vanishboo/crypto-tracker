package com.cryptoprod.pricefetcher.client;

import com.cryptoprod.pricefetcher.dto.CoinPriceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Slf4j
@Component
public class CoinGeckoClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${coingecko.api-key}")
    private String apiKey;

    @Value("${coingecko.api-url}")
    private String apiUrl;

    @Value("${coingecko.coins}")
    private String coins;

    public Map<String, CoinPriceDto> fetchPrices() {

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/simple/price")
                .queryParam("ids", coins)
                .queryParam("vs_currencies", "usd")
                .queryParam("x_cg_demo_api_key", apiKey)
                .toUriString();

        log.info("Fetching prices from api {}", coins);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, CoinPriceDto>>() {}
                ).getBody();
    }
}

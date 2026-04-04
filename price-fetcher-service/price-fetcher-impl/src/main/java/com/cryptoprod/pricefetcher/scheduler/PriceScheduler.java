package com.cryptoprod.pricefetcher.scheduler;

import com.cryptoprod.common.events.PriceUpdateEvent;
import com.cryptoprod.pricefetcher.client.CoinGeckoClient;
import com.cryptoprod.pricefetcher.dto.CoinPriceDto;
import com.cryptoprod.pricefetcher.producer.PriceProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceScheduler {

    private final PriceProducer producer;
    private final CoinGeckoClient client;

    private static final Map<String, String> COIN_SYMBOLS = new HashMap<>() {{
        put("bitcoin", "BTC");
        put("ethereum", "ETH");
        put("solana", "SOL");
    }};


    @Scheduled(fixedDelay = 10000, initialDelay = 5000)
    public void fetchAndPublish() {
        log.info("Fetching prices...");

        try {
            Map<String, CoinPriceDto> prices = client.fetchPrices();

            prices.forEach((coinId, price) -> {
                String symbol = COIN_SYMBOLS.getOrDefault(coinId, coinId.toUpperCase());
                log.info("Fetching price for {}: {}", symbol, price);

                PriceUpdateEvent event = new PriceUpdateEvent(
                        coinId,
                        symbol,
                        price.getUsd(),
                        Instant.now()
                );

                producer.send(event);

            });
        } catch (Exception e) {
            log.error("Error fetching prices: {}", e.getMessage());
        }
    }
}

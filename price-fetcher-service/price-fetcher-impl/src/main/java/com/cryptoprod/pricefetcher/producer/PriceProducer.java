package com.cryptoprod.pricefetcher.producer;

import com.cryptoprod.common.events.PriceUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceProducer {

    private final KafkaTemplate<String, PriceUpdateEvent> kafkaTemplate;

    private final static String TOPIC = "price-updates";

    public void send(PriceUpdateEvent event) {
        kafkaTemplate.send(TOPIC, event.coinId(), event);

        log.info("Sent price update: {} = ${}", event.symbol(), event.price());

    }
}

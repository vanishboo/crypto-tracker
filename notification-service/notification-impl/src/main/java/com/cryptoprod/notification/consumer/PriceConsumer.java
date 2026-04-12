package com.cryptoprod.notification.consumer;

import com.cryptoprod.common.events.PriceUpdateEvent;
import com.cryptoprod.notification.service.AlertMatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PriceConsumer {

    private final AlertMatchingService alertMatchingService;

    @KafkaListener(
            topics = "${spring.kafka.consumer.topics}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(PriceUpdateEvent event) {
        log.info("Received price event: {} = ${}", event.symbol(), event.price());
        alertMatchingService.processPrice(event);
    }
}

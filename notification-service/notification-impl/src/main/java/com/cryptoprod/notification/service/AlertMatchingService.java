package com.cryptoprod.notification.service;

import com.cryptoprod.common.events.PriceUpdateEvent;
import com.cryptoprod.notification.client.AlertClient;
import com.cryptoprod.notification.dto.AlertResponse;
import com.cryptoprod.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlertMatchingService {

    private final AlertClient alertClient;
    private final NotificationSender notificationSender;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${notification.cooldown-minutes}")
    private int cooldownMinutes;

    public void processPrice(PriceUpdateEvent event) {
        log.info("Processing price: {} = ${}", event.symbol(), event.price());

        List<AlertResponse> alerts = alertClient.getAlertsByCoin(event.coinId());
        if (alerts.isEmpty()) {
            log.info("No alerts found for coin: {}", event.coinId());
        }

        for (AlertResponse alert : alerts) {
            processAlert(alert, event.price());
        }
    }

    private void processAlert(AlertResponse alert, BigDecimal actualPrice) {
        if (!isTriggered(alert, actualPrice)) {
            return;
        }
        log.info("Alert {} triggered: {} {} {} (actual: {})",
                alert.getId(), alert.getSymbol(),
                alert.getCondition(), alert.getTargetPrice(), actualPrice);

        String lockKey = "alert-lock:" + alert.getId();
        if (redisTemplate.hasKey(lockKey)) {
            log.debug("Alert {} is in cooldown, skipping", alert.getId());
        }

        redisTemplate.opsForValue().set(
                lockKey,
                "locked",
                Duration.ofMinutes(cooldownMinutes)
        );
        notificationSender.send(alert.getUserId(), alert, actualPrice);
    }


    private boolean isTriggered(AlertResponse alert, BigDecimal actualPrice) {
        return switch (alert.getCondition()) {
            case "BELOW" -> actualPrice.compareTo(alert.getTargetPrice()) < 0;
            case "ABOVE" -> actualPrice.compareTo(alert.getTargetPrice()) > 0;
            default -> false;
        };
    }
}

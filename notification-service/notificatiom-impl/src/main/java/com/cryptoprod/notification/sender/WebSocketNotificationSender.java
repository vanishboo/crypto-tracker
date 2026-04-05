package com.cryptoprod.notification.sender;

import com.cryptoprod.notification.dto.AlertResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketNotificationSender implements NotificationSender {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void send(UUID userId, AlertResponse alert, BigDecimal actualPrice) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("alertId", alert.getId());
        payload.put("coinId", alert.getCoinId());
        payload.put("symbol", alert.getSymbol());
        payload.put("condition", alert.getCondition());
        payload.put("targetPrice", alert.getPrice());
        payload.put("actualPrice", actualPrice);
        payload.put("message", buildMessage(alert, actualPrice));

        String destination = String.format("/topic/alerts/%s", userId);

        simpMessagingTemplate.convertAndSend(destination, payload);

        log.info("WebSocket notification sent to user {} — {} {} {}",
                userId, alert.getSymbol(), alert.getCondition(), actualPrice);
    }


    private String buildMessage(AlertResponse alert, BigDecimal actualPrice) {
        String direction = "BELOW".equals(alert.getCondition()) ? "упал ниже" : "вырос выше";
        return String.format("%s %s $%s (текущая цена: $%s)",
                alert.getSymbol(),
                direction,
                alert.getPrice(),
                actualPrice);
    }

}

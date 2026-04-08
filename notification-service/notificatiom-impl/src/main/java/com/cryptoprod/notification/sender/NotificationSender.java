package com.cryptoprod.notification.sender;

import com.cryptoprod.notification.dto.AlertResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface NotificationSender {
    void send(UUID userId, AlertResponse alert, BigDecimal price);
}

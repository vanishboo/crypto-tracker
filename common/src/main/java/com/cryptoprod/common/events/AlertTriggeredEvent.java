package com.cryptoprod.common.events;

import java.math.BigDecimal;
import java.time.Instant;

public record AlertTriggeredEvent(
        Long alertId,
        Long userId,
        String coinId,
        String symbol,
        BigDecimal targetPrice,
        BigDecimal actualPrice,
        String condition,
        Instant triggeredAt
) {}
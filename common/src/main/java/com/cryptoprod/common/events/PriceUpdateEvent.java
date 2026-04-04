package com.cryptoprod.common.events;

import java.math.BigDecimal;
import java.time.Instant;

public record PriceUpdateEvent(
    String coinId,
    String symbol,
    BigDecimal price,
    Instant timestamp
) {}

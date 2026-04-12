package com.cryptoprod.alertservice.dto;

import com.cryptoprod.alertservice.model.Alert;
import com.cryptoprod.alertservice.model.Condition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AlertResponse {

    private UUID id;
    private UUID userId;
    private String coinId;
    private String symbol;
    private BigDecimal targetPrice;
    private Condition condition;
    private boolean active;
    private Instant createdAt;


    public static AlertResponse from(Alert alert) {
        return new AlertResponse(
                alert.getId(),
                alert.getUserId(),
                alert.getCoinId(),
                alert.getSymbol(),
                alert.getTargetPrice(),
                alert.getCondition(),
                alert.isActive(),
                alert.getCreatedAt()
        );
    }
}
package com.cryptoprod.notification.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertResponse {

    private UUID id;
    private UUID userId;
    private String coinId;
    private String symbol;
    private BigDecimal targetPrice;
    private String condition;
    private boolean active;
}

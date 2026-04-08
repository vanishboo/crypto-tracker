package com.cryptoprod.notification.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertResponse {

    private UUID id;
    private UUID userId;
    private String coinId;
    private String symbol;
    private BigDecimal price;
    private String condition;
    private boolean active;
}

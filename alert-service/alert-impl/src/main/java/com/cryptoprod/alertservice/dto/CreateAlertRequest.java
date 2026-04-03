package com.cryptoprod.alertservice.dto;

import com.cryptoprod.alertservice.model.Condition;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAlertRequest {

    @NotBlank
    private String coinId;

    @NotBlank
    private String symbol;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal targetPrice;

    @NotNull
    private Condition condition;
}

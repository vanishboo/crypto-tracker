package com.cryptoprod.alertservice.controller;

import com.cryptoprod.alertservice.dto.AlertResponse;
import com.cryptoprod.alertservice.dto.CreateAlertRequest;
import com.cryptoprod.alertservice.model.Alert;
import com.cryptoprod.alertservice.service.AlertService;
import com.cryptoprod.common.security.AccountResponse;
import com.cryptoprod.common.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
@Tag(name = "Alerts", description = "Alert manage")
@SecurityRequirement(name = "Bearer")
public class AlertController {

    private final AlertService alertService;

    @Operation(summary = "Create alert")
    @PostMapping
    public ResponseEntity<AlertResponse> create(@Valid @RequestBody CreateAlertRequest request) {
        UUID userId = getAccountResponse().getId();
        return ResponseEntity.status(201)
                .body(alertService.create(request, userId));
    }

    @Operation(summary = "Get all my alerts")
    @GetMapping
    public ResponseEntity<List<AlertResponse>> getAll() {
        UUID userId = getAccountResponse().getId();
        return ResponseEntity.ok(alertService.getAllByUser(userId));
    }

    @Operation(summary = "delete alert")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        UUID userId = getAccountResponse().getId();
        alertService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }


    private AccountResponse getAccountResponse() {
        return (AccountResponse) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}

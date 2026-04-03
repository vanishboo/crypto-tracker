package com.cryptoprod.alertservice.service;

import com.cryptoprod.alertservice.dto.AlertResponse;
import com.cryptoprod.alertservice.dto.CreateAlertRequest;
import com.cryptoprod.alertservice.model.Alert;
import com.cryptoprod.alertservice.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;

    @Transactional
    public AlertResponse create(CreateAlertRequest request, UUID userId) {
        Alert alert = Alert.builder()
                .userId(userId)
                .createdAt(Instant.now())
                .coinId(request.getCoinId())
                .symbol(request.getSymbol())
                .targetPrice(request.getTargetPrice())
                .condition(request.getCondition())
                .build();

        return AlertResponse.from(alertRepository.save(alert));
    }

    @Transactional
    public List<AlertResponse> getAllByUser(UUID userId) {
        return alertRepository.findByUserIdAndActiveTrue(userId)
                .stream()
                .map(AlertResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(UUID alertId, UUID userId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found"));
        if (!alert.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed to delete alert");
        }

        alertRepository.delete(alert);
    }

}

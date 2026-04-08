package com.cryptoprod.notification.client;

import com.cryptoprod.notification.dto.AlertResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class AlertClient {

    private final WebClient webClient;

    public AlertClient(@Value("${alert-service.url}") String url) {
        webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public List<AlertResponse> getAlertsByCoin(String coinId) {

        try {
            List<AlertResponse> alerts = webClient.get()
                    .uri("/api/v1/internal/alerts/coin/{coinId}", coinId)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<AlertResponse>>() {
                    })
                    .block();

            return alerts != null ? alerts : Collections.emptyList();
        } catch (Exception e) {
            log.error("Failed to get alerts for coin {}:{}",coinId, e.getMessage());
            return Collections.emptyList();
        }
    }


}

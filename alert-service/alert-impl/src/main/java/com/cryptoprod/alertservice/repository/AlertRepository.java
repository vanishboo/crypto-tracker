package com.cryptoprod.alertservice.repository;

import com.cryptoprod.alertservice.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlertRepository extends JpaRepository<Alert, UUID> {

    List<Alert> findByUserId(UUID userId);

    List<Alert> findByUserIdAndActiveTrue(UUID userId);

    List<Alert> findByCoinIdAndActiveTrue(String coinId);

}

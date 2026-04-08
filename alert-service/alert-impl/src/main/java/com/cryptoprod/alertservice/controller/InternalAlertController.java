package com.cryptoprod.alertservice.controller;

import com.cryptoprod.alertservice.dto.AlertResponse;
import com.cryptoprod.alertservice.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/internal")
@RequiredArgsConstructor
public class InternalAlertController {

    private final AlertService alertService;

    @GetMapping("/alerts/coin/{coinId}")
    public List<AlertResponse> getAllertsByCoin(@PathVariable("coinId") String coinId){
        return alertService.getAllByCoinId(coinId);
    }
}

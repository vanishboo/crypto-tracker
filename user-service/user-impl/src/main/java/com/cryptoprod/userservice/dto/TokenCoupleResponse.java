package com.cryptoprod.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TokenCoupleResponse {
    private final String accessToken;
    private final String refreshToken;
    private final UUID userId;

}
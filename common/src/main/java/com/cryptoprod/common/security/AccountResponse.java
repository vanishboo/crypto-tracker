package com.cryptoprod.common.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AccountResponse {
    private final UUID id;
    private final String email;
}

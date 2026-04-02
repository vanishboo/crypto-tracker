package com.cryptoprod.userservice.controllers;


import com.cryptoprod.userservice.dto.LoginRequest;
import com.cryptoprod.userservice.dto.RefreshRequest;
import com.cryptoprod.userservice.dto.RegisterRequest;
import com.cryptoprod.userservice.dto.TokenCoupleResponse;
import com.cryptoprod.userservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Логин пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный вход"),
            @ApiResponse(responseCode = "401", description = "Неверные данные")
    })
    @PostMapping("/login")
    public TokenCoupleResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная регистрация"),
            @ApiResponse(responseCode = "409", description = "Email уже занят")
    })
    @PostMapping("/register")
    public TokenCoupleResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @Operation(summary = "Обновление refresh токена")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Токен обновлён"),
            @ApiResponse(responseCode = "401", description = "Невалидный или истёкший refresh token")
    })
    @PostMapping("/refresh")
    public TokenCoupleResponse refresh(@Valid @RequestBody RefreshRequest refreshRequest) {
        return authService.refresh(refreshRequest);
    }

}

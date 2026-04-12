package com.cryptoprod.userservice.service;


import com.cryptoprod.common.security.JwtService;
import com.cryptoprod.userservice.dto.LoginRequest;
import com.cryptoprod.userservice.dto.RefreshRequest;
import com.cryptoprod.userservice.dto.RegisterRequest;
import com.cryptoprod.userservice.dto.TokenCoupleResponse;
import com.cryptoprod.userservice.model.RefreshToken;
import com.cryptoprod.userservice.model.Role;
import com.cryptoprod.userservice.model.User;
import com.cryptoprod.userservice.repository.RefreshTokenRepository;
import com.cryptoprod.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private Long refreshTokenExpiration;

    @Transactional
    public TokenCoupleResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .role(Role.USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userRepository.save(user);

        return buildTokenCouple(user);
    }

    @Transactional
    public TokenCoupleResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        return buildTokenCouple(user);
    }

    @Transactional
    public TokenCoupleResponse refresh(RefreshRequest refreshRequest) {

        RefreshToken token = refreshTokenRepository.findByToken(
                UUID.fromString(refreshRequest.getRefreshToken()))
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        if (token.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new BadCredentialsException("Refresh token expired");
        }
        refreshTokenRepository.delete(token);
        return buildTokenCouple(token.getUser());
    }

    private TokenCoupleResponse buildTokenCouple(User user) {
        String accessToken = jwtService.generateAccessToken(
                user.getEmail(),
                user.getId(),
                Collections.singletonList(user.getRole().name()));

        UUID refreshToken = UUID.randomUUID();
        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.save(RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiresAt(Instant.now().plusMillis(refreshTokenExpiration))
                .build());
        return new TokenCoupleResponse(accessToken, refreshToken.toString(), user.getId());
    }
}

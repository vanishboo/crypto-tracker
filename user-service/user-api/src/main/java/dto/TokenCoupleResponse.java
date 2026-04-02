package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenCoupleResponse {
    private final String accessToken;
    private final String refreshToken;
}
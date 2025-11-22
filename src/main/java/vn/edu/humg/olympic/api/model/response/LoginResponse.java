package vn.edu.humg.olympic.api.model.response;

import org.springframework.http.ResponseCookie;

public record LoginResponse(
        ResponseCookie refreshCookie,
        AuthResponse authResponse
) {
}

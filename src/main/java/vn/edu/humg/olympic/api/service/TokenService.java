package vn.edu.humg.olympic.api.service;

import org.springframework.security.core.Authentication;

public interface TokenService {

    String generateAccessToken(Authentication authentication);

    String generateRefreshToken(Authentication authentication);

    String getUsernameFromToken(String token);

    boolean validateAccessToken(String token);

    boolean validateRefreshToken(String token);

}

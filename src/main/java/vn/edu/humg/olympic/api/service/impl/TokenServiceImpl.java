package vn.edu.humg.olympic.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import vn.edu.humg.olympic.api.service.TokenService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @Value("${application.security.jwt.access-expiration}")
    private int accessExpirationMinutes;

    @Value("${application.security.jwt.refresh-expiration}")
    private int refreshExpirationMinutes;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Override
    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, accessExpirationMinutes, "ACCESS");
    }

    @Override
    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, refreshExpirationMinutes, "REFRESH");
    }

    @Override
    public String getUsernameFromToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getSubject();
    }

    @Override
    public boolean validateAccessToken(String token) {
        return validateTokenInternal(token, "ACCESS");
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return validateTokenInternal(token, "REFRESH");
    }

    private String generateToken(Authentication authentication, int expirationMinutes, String type) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities()
                                     .stream()
                                     .map(GrantedAuthority::getAuthority)
                                     .collect(Collectors.joining(" "));

        String username = authentication.getName();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .subject(username)
                                          .issuedAt(now)
                                          .expiresAt(now.plus(expirationMinutes, ChronoUnit.MINUTES))
                                          .claim("scope", scope)
                                          .claim("type", type)
                                          .build();

        JwtEncoderParameters params = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256)
                                                                         .build(), claims);

        return jwtEncoder.encode(params)
                         .getTokenValue();
    }

    private boolean validateTokenInternal(String token, String expectedType) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            String type = jwt.getClaimAsString("type");
            if (!expectedType.equals(type)) {
                throw new BadJwtException("Invalid token type: expected " + expectedType);
            }
            return true;
        } catch (JwtException ex) {
            log.error("Error while trying to validate {} token", expectedType, ex);
            throw new BadCredentialsException("Invalid JWT token", ex);
        }
    }
}

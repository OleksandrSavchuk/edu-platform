package com.example.eduplatform.security;

import com.example.eduplatform.dto.jwt.JwtResponse;
import com.example.eduplatform.dto.user.UserResponse;
import com.example.eduplatform.entity.enums.Role;
import com.example.eduplatform.props.JwtProperties;
import com.example.eduplatform.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long userId, String email, Role role) {
        Claims claims = Jwts.claims()
                .subject(email)
                .add("id", userId)
                .add("role", role.name())
                .build();
        Instant expiration = Instant.now().plus(jwtProperties.getAccess(), ChronoUnit.MILLIS);
        return buildToken(claims, expiration);
    }

    public String createRefreshToken(Long userId, String email, Role role) {
        Claims claims = Jwts.claims()
                .subject(email)
                .add("id", userId)
                .add("role", role.name())
                .build();
        Instant expiration = Instant.now().plus(jwtProperties.getRefresh(), ChronoUnit.MILLIS);
        return buildToken(claims, expiration);
    }

    private String buildToken(Claims claims, Instant expiration) {
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(expiration))
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshTokens(String refreshToken) {
        if (!isValid(refreshToken)) {
            throw new AccessDeniedException("Invalid refresh token");
        }

        Long userId = getUserId(refreshToken);
        UserResponse user = userService.getById(userId);

        return JwtResponse.builder()
                .id(userId)
                .accessToken(createAccessToken(userId, user.getEmail(), Role.valueOf(user.getRole())))
                .refreshToken(createRefreshToken(userId, user.getEmail(), Role.valueOf(user.getRole())))
                .build();

    }

    public boolean isValid(String token) {
        Claims claims = getAllClaims(token);
        return claims.getExpiration().after(new Date());
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authentication getAuthentication(String token) {
        String email = getEmail(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    private String getEmail(String token) {
        return getAllClaims(token).getSubject();
    }

    private Long getUserId(String token) {
        return getAllClaims(token).get("id", Long.class);
    }

}

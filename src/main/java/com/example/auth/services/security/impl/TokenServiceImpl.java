package com.example.auth.services.security.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.auth.domain.user.LoginResponseDTO;
import com.example.auth.domain.user.User;
import com.example.auth.services.security.TokenService;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${jwt.private.key}") private RSAPrivateKey privateKey;
    @Value("${jwt.public.key}") private RSAPublicKey publicKey;
    @Autowired private JwtEncoder jwtEncoder;
    @Autowired private JwtDecoder jwtDecoder;
    private static final String TIMEZONE_BRAZIL = "-03:00";
    private static final String ISSUER_NAME = "auth-api";
    private static final String RUNTIME_ERROR_DESCRIPTION = "Error while generating token";

    public LoginResponseDTO generateToken(User user) {
        Instant now = Instant.now();
        long expiresIn = 300L;
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .claim("scope", user.getRole())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .subject(user.getLogin())
                .build();
            String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
            return new LoginResponseDTO(token, expiresIn);
    }

    public String validateToken(String token){
        try {
            Jwt jwt = jwtDecoder.decode(token);
            Instant tokenExpires = jwt.getExpiresAt();
            if (Optional.ofNullable(tokenExpires).isPresent()) {
                if (tokenExpires.isBefore(Instant.now())) {
                    throw new RuntimeException("Token has expired");
                }
            }
            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid token.");
        }
    }
}

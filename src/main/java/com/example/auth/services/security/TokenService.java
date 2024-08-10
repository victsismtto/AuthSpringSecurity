package com.example.auth.services.security;

import com.example.auth.domain.user.User;

public interface TokenService {
    String generateToken(User user);
    String validateToken(String token);
}

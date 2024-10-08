package com.example.auth.services.security;

import com.example.auth.domain.user.LoginResponseDTO;
import com.example.auth.domain.user.User;

public interface TokenService {
    LoginResponseDTO generateToken(User user);
    String validateToken(String token);
}

package com.example.auth.services.authentication;

import com.example.auth.domain.user.AuthenticationDTO;
import com.example.auth.domain.user.LoginResponseDTO;
import com.example.auth.domain.user.RegisterDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    LoginResponseDTO login(AuthenticationDTO data);
    ResponseEntity<?> createUser(RegisterDTO data);
}

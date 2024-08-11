package com.example.auth.services.authentication.impl;

import com.example.auth.domain.user.AuthenticationDTO;
import com.example.auth.domain.user.LoginResponseDTO;
import com.example.auth.domain.user.RegisterDTO;
import com.example.auth.domain.user.User;
import com.example.auth.repositories.UserRepository;
import com.example.auth.services.authentication.AuthenticationService;
import com.example.auth.services.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserRepository repository;
    @Autowired private TokenService tokenService;
    @Override
    public LoginResponseDTO login(AuthenticationDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());
    }

    @Override
    public ResponseEntity<?> createUser(RegisterDTO data) {
        Optional<UserDetails> userDetails = repository.findByLogin(data.login());
        if (userDetails.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());
        repository.save(newUser);
        return ResponseEntity.ok().build();
    }
}

package com.example.auth.filter;


import com.example.auth.repositories.UserRepository;
import com.example.auth.services.security.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired private TokenService tokenService;
    @Autowired private UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);
        if(Optional.ofNullable(token).isPresent()) {
            String login = tokenService.validateToken(token);
            Optional<UserDetails> user = userRepository.findByLogin(login);
            Collection<? extends GrantedAuthority> collection = user.<Collection<? extends GrantedAuthority>>map(UserDetails::getAuthorities).orElse(null);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, collection);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (Optional.ofNullable(authHeader).isEmpty()) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
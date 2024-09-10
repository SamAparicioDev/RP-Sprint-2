package com.example.user.webflux.services.impl;

import com.example.user.webflux.config.JwtUtils;
import com.example.user.webflux.services.AuthService;
import com.example.user.webflux.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;
    @Override
    public Mono<String> authenticate(String username, String password) {
        return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                username,
                                password)
                ).map(auth -> {
                    UserDetails userDetails = (UserDetails) auth.getPrincipal();
                    String jwt = jwtUtil.generateToken(userDetails.getUsername());
                    return jwt;
                })
                .doOnError(e -> new UserNotFoundException("Incorrect Credentials"));
    }
}

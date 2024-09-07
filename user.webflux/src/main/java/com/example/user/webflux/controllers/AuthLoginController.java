package com.example.user.webflux.controllers;


import com.example.user.webflux.config.JwtUtils;
import com.example.user.webflux.dto.Login;
import com.example.user.webflux.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/auth")
public class AuthLoginController {
    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;


    @PostMapping("/login")
    public Mono<ResponseEntity<String>> authenticateUser(@RequestBody Login login) {
        return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                login.username(),
                                login.password()
                        )).map(auth -> {
                    UserDetails userDetails = (UserDetails) auth.getPrincipal();
                    String jwt = jwtUtil.generateToken(userDetails.getUsername());
                    return ResponseEntity.ok(jwt);
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));

    }
}

package com.example.user.webflux.controllers;


import com.example.user.webflux.config.JwtUtils;
import com.example.user.webflux.dto.Login;
import com.example.user.webflux.services.UserService;
import com.example.user.webflux.services.impl.AuthServiceImpl;
import com.example.user.webflux.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/auth")
public class AuthLoginController {

    @Autowired
    private AuthServiceImpl authServiceImpl;


    @PostMapping("/login")
    public ResponseEntity<Mono<String>> authenticateUser(@RequestBody Login login) {
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(authServiceImpl.authenticate(login.username(),login.password()));
    }
}

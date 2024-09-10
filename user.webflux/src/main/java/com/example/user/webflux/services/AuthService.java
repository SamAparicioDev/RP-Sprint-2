package com.example.user.webflux.services;

import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<String> authenticate(String username, String password);
}

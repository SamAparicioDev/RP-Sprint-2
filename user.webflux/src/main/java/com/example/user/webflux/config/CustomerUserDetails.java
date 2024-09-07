package com.example.user.webflux.config;

import com.example.user.webflux.repositories.UserRepository;
import com.example.user.webflux.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class CustomerUserDetails implements ReactiveUserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.getUserByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(username)))
                .map(userDto -> new User(userDto.getUsername(),userDto.getPassword(),new ArrayList<>()));
    }
}

package com.example.user.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
     @Bean
     public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
         return  http
                 .csrf(ServerHttpSecurity.CsrfSpec::disable)
                 .authorizeExchange(authorizeExchangeSpec ->
                     authorizeExchangeSpec.pathMatchers("/api/v2/auth/**").permitAll()
                             .anyExchange().permitAll())
                     .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                 .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                 .build();
     }

     @Bean
    public ReactiveAuthenticationManager authenticationManager(CustomerUserDetails customerUserDetail) {
         UserDetailsRepositoryReactiveAuthenticationManager authenticationManager
                 = new UserDetailsRepositoryReactiveAuthenticationManager(customerUserDetail);
         authenticationManager.setPasswordEncoder(passwordEncoder());
         return authenticationManager;
     }
    @Bean
    public  BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

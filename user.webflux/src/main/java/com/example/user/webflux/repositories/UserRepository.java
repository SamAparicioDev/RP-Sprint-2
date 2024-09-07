package com.example.user.webflux.repositories;

import com.example.user.webflux.entities.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity,Long> {
    Mono<UserEntity> findByUsername(String username);
}

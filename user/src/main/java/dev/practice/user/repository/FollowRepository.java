package dev.practice.user.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface FollowRepository extends R2dbcRepository<FollowEntity, Long> {


    // todo, test code
    Flux<FollowEntity> findByToUserId(Long toUserId);
}

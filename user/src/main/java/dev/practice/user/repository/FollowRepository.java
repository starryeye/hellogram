package dev.practice.user.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface FollowRepository extends R2dbcRepository<FollowEntity, Long> {
}

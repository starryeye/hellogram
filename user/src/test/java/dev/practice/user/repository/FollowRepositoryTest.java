package dev.practice.user.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @AfterEach
    void tearDown() {
        r2dbcEntityTemplate.delete(FollowEntity.class)
                .all()
                .block();
    }

    @Test
    void smokeTest() {

        assertThat(followRepository).isNotNull();
        assertThat(r2dbcEntityTemplate).isNotNull();
    }

}
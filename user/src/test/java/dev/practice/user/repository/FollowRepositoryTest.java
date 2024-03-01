package dev.practice.user.repository;

import dev.practice.user.testconfig.R2dbcAuditingTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@Import(R2dbcAuditingTestConfig.class) // EnableR2dbcAuditing
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

    @DisplayName("save 동작 테스트")
    @Test
    void save() {

        // given
        FollowEntity followEntity = FollowEntity.create(1L, 2L);

        // when
        Mono<FollowEntity> saved = followRepository.save(followEntity);

        // then
        StepVerifier.create(saved)
                .assertNext(
                        follow -> {
                            System.out.println(follow.toString());
                            System.out.println(followEntity.toString());

                            assertThat(follow.getId()).isNotNull();
                            assertThat(follow.getCreatedAt()).isNotNull();

                            assertThat(follow.getFromUserId()).isEqualTo(1L);
                            assertThat(follow.getToUserId()).isEqualTo(2L);
                        }
                )
                .verifyComplete();
    }
}
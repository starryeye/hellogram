package dev.practice.user.repository;

import dev.practice.user.testconfig.R2dbcAuditingTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

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

    @DisplayName("save 정상 동작")
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
                            /**
                             * todo,
                             *  h2 로 test 하자
                             */
                            System.out.println("given = " + followEntity.toString());
                            System.out.println("saved = " + follow.toString());
                            System.out.println("given.id = " + followEntity.getId());
                            System.out.println("saved.id = " + follow.getId());
                            System.out.println("given.createdAt = " + followEntity.getCreatedAt());
                            System.out.println("saved.createdAt = " + follow.getCreatedAt());


                            assertThat(follow.getId()).isNotNull();
                            assertThat(follow.getCreatedAt()).isNotNull();

                            assertThat(follow.getFromUserId()).isEqualTo(1L);
                            assertThat(follow.getToUserId()).isEqualTo(2L);
                        }
                )
                .verifyComplete();
    }

    @DisplayName("findByToUserId 메서드 쿼리 정상 동작")
    @Test
    void findByToUserId() {

        // given
        Long followerId = 20L;
        FollowEntity followEntity1 = FollowEntity.create(10L, followerId);
        FollowEntity followEntity2 = FollowEntity.create(11L, followerId);
        FollowEntity followEntity3 = FollowEntity.create(12L, followerId);

        Flux<FollowEntity> saved = followRepository.saveAll(List.of(followEntity1, followEntity2, followEntity3));

        // when
        Flux<FollowEntity> result = saved
                .thenMany(
                        followRepository.findByToUserId(followerId)
                );

        // then
        StepVerifier.create(result)
                .assertNext(
                        followEntity -> {
                            assertThat(followEntity.getFromUserId()).isEqualTo(10L);
                            assertThat(followEntity.getToUserId()).isEqualTo(followerId);
                        }
                )
                .assertNext(
                        followEntity -> {
                            assertThat(followEntity.getFromUserId()).isEqualTo(11L);
                            assertThat(followEntity.getToUserId()).isEqualTo(followerId);
                        }
                )
                .assertNext(
                        followEntity -> {
                            assertThat(followEntity.getFromUserId()).isEqualTo(12L);
                            assertThat(followEntity.getToUserId()).isEqualTo(followerId);
                        }
                )
                .verifyComplete();
    }
}
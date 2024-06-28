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
        Long fromUserId = 1L;
        Long toUserId = 2L;
        String description = "school friend";
        FollowEntity followEntity = FollowEntity.create(fromUserId, toUserId, description);

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
                            System.out.println("given.id = " + followEntity.getId());
                            System.out.println("given.createdAt = " + followEntity.getCreatedAt());
                            System.out.println("saved = " + follow.toString());
                            System.out.println("saved.id = " + follow.getId());
                            System.out.println("saved.fromUserId = " + follow.getFromUserId());
                            System.out.println("saved.toUserId = " + follow.getToUserId());
                            System.out.println("saved.description = " + follow.getDescription());
                            System.out.println("saved.createdAt = " + follow.getCreatedAt());
                            System.out.println("saved.updatedAt = " + follow.getUpdatedAt());


                            assertThat(follow.getId()).isNotNull();
                            assertThat(follow.getCreatedAt()).isNotNull();
                            assertThat(follow.getUpdatedAt()).isNotNull();

                            assertThat(follow.getFromUserId()).isEqualTo(fromUserId);
                            assertThat(follow.getToUserId()).isEqualTo(toUserId);
                            assertThat(follow.getDescription()).isEqualTo(description);
                        }
                )
                .verifyComplete();
    }

    @DisplayName("update 정상 동작")
    @Test
    void update() {

        // given
        Long fromUserId = 1L;
        Long toUserId = 2L;
        String beforeDescription = "school friend";
        String afterDescription = "my love";

        FollowEntity followEntity = FollowEntity.create(fromUserId, toUserId, beforeDescription);
        FollowEntity saved = followRepository.save(followEntity).block();

        assert saved != null;
        System.out.println("saved.createdAt = " + saved.getCreatedAt());

        // when
        Mono<FollowEntity> changed = followRepository.findById(saved.getId())
                .flatMap(foundEntity -> {
                    System.out.println("found.createdAt = " + foundEntity.getCreatedAt());
                    FollowEntity changedEntity = foundEntity.changeDescription(afterDescription);
                    System.out.println("change.createdAt = " + changedEntity.getCreatedAt());
                    return followRepository.save(changedEntity);
                });

        // then
        StepVerifier.create(changed)
                .assertNext(updatedEntity -> {
                    assertThat(updatedEntity.getId()).isEqualTo(saved.getId());
                    assertThat(updatedEntity.getFromUserId()).isEqualTo(saved.getFromUserId());
                    assertThat(updatedEntity.getToUserId()).isEqualTo(saved.getToUserId());
                    assertThat(updatedEntity.getDescription()).isEqualTo(afterDescription);
                    assertThat(updatedEntity.getCreatedAt()).isEqualTo(saved.getCreatedAt());
                    assertThat(updatedEntity.getUpdatedAt()).isAfter(saved.getUpdatedAt());
                })
                .verifyComplete();
    }

    @DisplayName("findByToUserId 메서드 쿼리 정상 동작")
    @Test
    void findByToUserId() {

        // given
        Long followerId = 20L;
        FollowEntity followEntity1 = FollowEntity.create(10L, followerId, "school friend 1");
        FollowEntity followEntity2 = FollowEntity.create(11L, followerId, "school friend 2");
        FollowEntity followEntity3 = FollowEntity.create(12L, followerId, "school friend 3");

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
                            assertThat(followEntity.getDescription()).isEqualTo("school friend 1");
                        }
                )
                .assertNext(
                        followEntity -> {
                            assertThat(followEntity.getFromUserId()).isEqualTo(11L);
                            assertThat(followEntity.getToUserId()).isEqualTo(followerId);
                            assertThat(followEntity.getDescription()).isEqualTo("school friend 2");
                        }
                )
                .assertNext(
                        followEntity -> {
                            assertThat(followEntity.getFromUserId()).isEqualTo(12L);
                            assertThat(followEntity.getToUserId()).isEqualTo(followerId);
                            assertThat(followEntity.getDescription()).isEqualTo("school friend 3");
                        }
                )
                .verifyComplete();
    }
}
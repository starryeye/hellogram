package dev.practice.user.service;

import dev.practice.user.repository.FollowEntity;
import dev.practice.user.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public Flux<String> getFollowerIds(String followeeId) {

//        // 임시
//        return List.of(
//                followeeId + "1",
//                followeeId + "2",
//                followeeId + "3"
//        );

        return followRepository.findByToUserId(Long.getLong(followeeId))
                .map(
                        followEntity -> String.valueOf(followEntity.getFromUserId())
                );
    }
}

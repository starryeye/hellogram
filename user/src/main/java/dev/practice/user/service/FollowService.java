package dev.practice.user.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    public List<String> getFollowerIds(String followeeId) {

        // 임시
        return List.of(
                followeeId + "1",
                followeeId + "2",
                followeeId + "3"
        );
    }
}

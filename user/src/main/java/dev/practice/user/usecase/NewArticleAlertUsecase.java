package dev.practice.user.usecase;

import dev.practice.user.event.CreatedNotificationEvent;
import dev.practice.user.service.FollowService;
import dev.practice.user.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NewArticleAlertUsecase {

    private final FollowService followService;
    private final NotificationService notificationService;

    /**
     * article 에서 message 발행시 해당 message 를 구독하여 새로운 message 발행
     */
    public Flux<CreatedNotificationEvent> execute(String articleId, String creatorId) {

        String message = notificationService.getCreatedArticleMessage(creatorId, articleId);

        return followService.getFollowerIds(creatorId)
                        .map(
                                followerId -> notificationService.getCreatedNotificationEvent(followerId, creatorId, message)
                        );
    }
}

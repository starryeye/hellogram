package dev.practice.user.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatedArticleEvent {

    private final String articleId;
    private final String creatorUserId;

    /**
     * @JsonCreator, @JsonProperty ...
     * ObjectMapper 로 String 을 Object 로 역직렬화 할때 no argument constructor 가 필요한데..
     * immutable 객체에선 제공해주기가 불가능하므로.. 해당 annotation 을 사용하여 역직렬화 생성자로 만들어줌
     *
     * todo, record type 을 사용하면 어떨까?
     */
    @JsonCreator
    @Builder
    private CreatedArticleEvent(
            @JsonProperty("articleId") String articleId,
            @JsonProperty("creatorUserId") String creatorUserId
    ) {
        this.articleId = articleId;
        this.creatorUserId = creatorUserId;
    }
}

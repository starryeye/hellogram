package dev.practice.article.controller.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateArticleRequest {

    private final String title;
    private final String content;
    private final List<String> thumbnailIds; // article 을 생성 시, 이미지는 이미 업로드 되었다고 가정

    @Builder
    private CreateArticleRequest(String title, String content, List<String> thumbnailIds) {
        this.title = title;
        this.content = content;
        this.thumbnailIds = thumbnailIds;
    }
}

package dev.practice.article.usecase;

import dev.practice.article.entity.Article;
import dev.practice.article.entity.ArticleThumbnail;
import dev.practice.article.repository.ArticleRepository;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import javax.inject.Named;
import java.util.List;

@RequiredArgsConstructor
@Named // spring 에서 @Named 를 읽어서 spring bean 으로 등록해준다. (core 모듈에는 spring 의존성이 없다.)
public class CreateArticleUsecase {

    private final ArticleRepository articleRepository;

    @EqualsAndHashCode
    public static class Input {
        private final String title;
        private final String content;
        private final String creatorId;
        private final List<String> thumbnailImageIds;

        @Builder
        private Input(String title, String content, String creatorId, List<String> thumbnailImageIds) {
            this.title = title;
            this.content = content;
            this.creatorId = creatorId;
            this.thumbnailImageIds = thumbnailImageIds;
        }
    }

    public Mono<Article> execute(Input input) {


        Article article = Article.create(
                input.title,
                input.content,
                input.thumbnailImageIds.stream()
                        .map(ArticleThumbnail::createById)
                        .toList(),
                input.creatorId
        );

        return articleRepository.save(article);
    }
}

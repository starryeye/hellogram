package dev.practice.article.datastore.mongo.document;

import dev.practice.article.entity.Article;
import dev.practice.article.entity.ArticleThumbnail;

public class ArticleDocumentConverterFactory {

    public static ArticleDocument fromEntity(Article article) {
        return new ArticleDocument(
                null,
                article.getTitle(),
                article.getContent(),
                article.getThumbnails().stream()
                        .map(ArticleThumbnail::getId)
                        .toList(),
                article.getCreatorId()
        );
    }

    public static Article fromDocument(ArticleDocument articleDocument) {
        return Article.builder()
                .id(articleDocument.getId().toHexString())
                .title(articleDocument.getTitle())
                .content(articleDocument.getContent())
                .thumbnails(
                        articleDocument.getThumbnailIds().stream()
                                .map(ArticleThumbnail::createById)
                                .toList()
                )
                .creatorId(articleDocument.getCreatorId())
                .build();
    }
}

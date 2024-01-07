package dev.practice.article.datastore.mongo.document;

import dev.practice.article.entity.Article;
import dev.practice.article.entity.ArticleThumbnail;

public class ArticleDocumentConverterFactory {

    private ArticleDocumentConverterFactory() {}

    public static ArticleDocument fromEntity(Article article) {
        return ArticleDocument.builder()
                .id(null)
                .title(article.getTitle())
                .content(article.getContent())
                .thumbnailIds(
                        article.getThumbnails().stream()
                                .map(ArticleThumbnail::getId)
                                .toList()
                )
                .creatorId(article.getCreatorId())
                .build();
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

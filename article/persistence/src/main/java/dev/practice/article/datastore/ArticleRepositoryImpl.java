package dev.practice.article.datastore;

import dev.practice.article.datastore.mongo.document.ArticleDocument;
import dev.practice.article.datastore.mongo.document.ArticleDocumentConverterFactory;
import dev.practice.article.datastore.mongo.repository.ArticleMongoRepository;
import dev.practice.article.entity.Article;
import dev.practice.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ArticleRepositoryImpl implements ArticleRepository {

    // todo, unit test code 작성

    private final ArticleMongoRepository articleMongoRepository;

    @Override
    public Mono<Article> save(Article article) {

        ArticleDocument documentToSave = ArticleDocumentConverterFactory.fromEntity(article);

        Mono<ArticleDocument> result = articleMongoRepository.save(documentToSave);

        return result.map(ArticleDocumentConverterFactory::fromDocument);
    }
}

package dev.practice.article.datastore.mongo.repository;

import dev.practice.article.datastore.mongo.document.ArticleDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ArticleMongoRepository extends ReactiveMongoRepository<ArticleDocument, ObjectId> {
}

package dev.practice.article.datastore.mongo.repository;

import dev.practice.article.datastore.mongo.document.ArticleDocument;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Testcontainers // test container 사용
@DataMongoTest // MonoDB repository Test 에 필요한 빈 + 사용자의 빈들이 띄어지는 듯..
class ArticleMongoRepositoryTest {

    // todo, embedded 로 해볼 것

    @Autowired
    private ArticleMongoRepository articleMongoRepository;

    private static String MONGODB_DATABASE_NAME = "article-mongodb";

    @Container
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.19")
            .withEnv("MONGO_INITDB_DATABASE", MONGODB_DATABASE_NAME)
            .withExposedPorts(27017)
            .withSharding();

    @DynamicPropertySource
    private static void configureProperties(DynamicPropertyRegistry registry) {
        String url = mongoDBContainer.getReplicaSetUrl(MONGODB_DATABASE_NAME);
        registry.add("spring.data.mongodb.uri", () -> url);
    }


    @DisplayName("ArticleMongoRepository context load test")
    @Test
    void contextLoad() {

        // todo, test 는 정상 수행되나.. 알수 없는 mongodb exception 이 발생한다.

        // given
        String title = "test-title";
        String content = "test-content";
        List<String> thumbnailIds = List.of("11", "22", "33");
        String creatorId = "321";
        ArticleDocument articleDocumentToSave = ArticleDocument.create(title, content, thumbnailIds, creatorId);

        // when
        Mono<ArticleDocument> result = articleMongoRepository.save(articleDocumentToSave);


        // then
        StepVerifier.create(result)
                .assertNext(
                        savedArticleDocument -> {
                            assertNotNull(savedArticleDocument.getId());
                            assertEquals(title, savedArticleDocument.getTitle());
                            assertEquals(content, savedArticleDocument.getContent());
                            assertEquals(thumbnailIds, savedArticleDocument.getThumbnailIds());
                            assertEquals(creatorId, savedArticleDocument.getCreatorId());

                        }
                ).verifyComplete();
    }
}
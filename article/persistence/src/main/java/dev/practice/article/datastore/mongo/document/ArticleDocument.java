package dev.practice.article.datastore.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Slf4j
@Getter
@Document(collection = "articles")
//@AllArgsConstructor // For object mapping, instance creation (유일한 생성자라 동작, property population 을 단계를 건너뛰게 만들어서 성능이점)
public class ArticleDocument {

    // final properties
    @Id
    private final ObjectId id; // auto create identifier property
    private final String title;
    private final String content;
    private final List<String> thumbnailIds;
    private final String creatorId;

    // 접근제어자 바꿔보기
    public ArticleDocument(ObjectId id, String title, String content, List<String> thumbnailIds, String creatorId) {
        log.info("all argument constructor");
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumbnailIds = thumbnailIds;
        this.creatorId = creatorId;
    }

    // static factory method
    public static ArticleDocument create(String title, String content, List<String> thumbnailIds, String creatorId) {
        log.info("static factory method");
        return new ArticleDocument(null, title, content, thumbnailIds, creatorId);
    }
}

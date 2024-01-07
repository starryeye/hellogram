package dev.practice.article.datastore.mongo.document;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Document(collection = "articles")
public class ArticleDocument {

    // final properties
    @Id
    private final ObjectId id; // auto create identifier property
    private final String title;
    private final String content;
    private final List<String> thumbnailIds;
    private final String creatorId;

    // For object mapping, instance creation (유일한 생성자라 동작, property population 을 단계를 건너뛰게 만들어서 성능이점)
    // 참고로 insert 전의 객체와 결과로 받은 객체의 인스턴스는 서로 다르다.
    @Builder
    private ArticleDocument(ObjectId id, String title, String content, List<String> thumbnailIds, String creatorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumbnailIds = thumbnailIds;
        this.creatorId = creatorId;
    }

    // static factory method, 편의
    public static ArticleDocument create(String title, String content, List<String> thumbnailIds, String creatorId) {
        return ArticleDocument.builder()
                .id(null)
                .title(title)
                .content(content)
                .thumbnailIds(thumbnailIds)
                .creatorId(creatorId)
                .build();
    }
}

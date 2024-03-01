package dev.practice.user.repository;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Table("FOLLOW")
public class FollowEntity extends BaseEntity{ // auditing

    @Id
    private Long id; // auto generate

    private Long fromUserId;
    private Long toUserId;

    @Builder // 유일한 생성자 (For object mapping, instance creation)
    private FollowEntity(Long id, Long fromUserId, Long toUserId) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    // static 편의
    public static FollowEntity create(Long fromUserId, Long toUserId) {
        return FollowEntity.builder()
                .id(null)
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .build();
    }
}

package dev.practice.user.repository;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;

//    @LastModifiedDate
//    private LocalDateTime updatedAt;
}

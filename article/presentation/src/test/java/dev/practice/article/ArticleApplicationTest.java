package dev.practice.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ArticleApplicationTest {

    @Autowired
    private ArticleApplication articleApplication;

    @DisplayName("스프링 부팅 테스트")
    @Test
    void contextLoads() {

        // given
        // when
        // then
        assertThat(articleApplication).isNotNull();
    }
}
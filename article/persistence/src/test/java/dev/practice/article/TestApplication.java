package dev.practice.article;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {

    /**
     * persistence module 에 SpringBootApplication 이 없다.
     *
     * 따라서 persistence module 에서 Test 시..
     * component scan 을 하지 못하여 빈 등록 및 DI 가 작동하지 않음
     *
     * 그래서 test 에서는 @ComponentScan 을 해준다.
     *
     * 뿐만아니라, @SpringBootConfiguration, @EnableAutoConfiguration 도 활성화 해야하므로
     * 그냥 @SpringBootApplication 을 사용하면된다.
     *
     * 참고로..
     * @SpringBootTest, @DataMongoTest 등을 실제 Test code class 에 붙이면(사용하면),
     * @SpringBootApplication 이 붙은 클래스가 수행되면서
     * 사용한 어노테이션에 맞는 환경을 꾸며주게된다.
     */
}

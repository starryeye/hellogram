# hellogram
spring reactive stack 으로 구현하는 모의 SNS


## architecture
- 모든 application 은 reactive 기반이다.(with Netty)
- <img width="932" alt="image" src="https://github.com/starryeye/hellogram/assets/33487061/de913d0c-23b2-47ef-ad84-382da7be523f">
 
## api
- 각 프로젝트 http 디렉토리에 존재

## projects
- article
  - Clean Architecture
    - multi module
  - 주요 의존성
    - spring-boot-starter-webflux
    - spring-boot-starter-data-mongodb-reactive
    - spring-cloud-starter-circuitbreaker-reactor-resilience4j
    - spring-cloud-stream-binder-kafka-reactive
    - testcontainers
- image
  - 주요 의존성
    - spring-boot-starter-webflux
    - spring-boot-starter-data-redis-reactive
- user
  - 주요 의존성
    - spring-cloud-stream-binder-kafka-reactive
- gateway
  - 주요 의존성
    - spring-cloud-starter-gateway

## todo
- user db mysql
- notification application

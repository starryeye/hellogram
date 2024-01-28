# hellogram
reactive stack 으로 구현하는 SNS, 모의 instagram

## architecture
- 모든 application 은 reactive 기반이다.(with Netty)
- <img width="929" alt="image" src="https://github.com/starryeye/hellogram/assets/33487061/6d0b6445-b6ce-48c9-8b1b-93faed010ded">
 
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

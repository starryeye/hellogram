# hellogram
spring reactive stack 으로 구현하는 모의 SNS


## architecture
- 모든 application 은 reactive 기반이다.(with Netty)
- <img width="949" alt="image" src="https://github.com/starryeye/hellogram/assets/33487061/ea4e7207-975b-48a2-b397-66df21239018">
 
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
    - spring-cloud-stream
    - spring-cloud-stream-binder-kafka-reactive
    - testcontainers
- image
  - 주요 의존성
    - spring-boot-starter-webflux
    - spring-boot-starter-data-redis-reactive
- user
  - 주요 의존성
    - spring-cloud-stream
    - spring-cloud-stream-binder-kafka-reactive
    - spring-boot-starter-data-r2dbc
    - r2dbc-mysql:1.0.5
- gateway
  - 주요 의존성
    - spring-cloud-starter-gateway

## todo
- notification application

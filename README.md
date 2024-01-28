# hellogram
reactive stack 으로 구현하는 SNS, 모의 instagram

## 주요 기술
- spring-boot-starter-webflux
- spring-cloud-starter-circuitbreaker-reactor-resilience4j
- spring-cloud-stream
 - spring-cloud-stream-binder-kafka-reactive
- spring-boot-starter-data-mongodb-reactive
- testcontainers
- spring-cloud-starter-gateway

## architecture
- <img width="945" alt="image" src="https://github.com/starryeye/hellogram/assets/33487061/a3db2437-2a47-4cbd-a737-b025341e85bc">
 
## api
- 각 프로젝트에 http 디렉토리에 존재

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

CREATE TABLE FOLLOW (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_user_id BIGINT,
    to_user_id BIGINT NOT NULL,
    description VARCHAR(20),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
);

insert into FOLLOW(id, from_user_id, to_user_id, description, created_at, updated_at)
values (1, 1001, 100, 'school friend', '2024-03-04 21:50:01.000000', '2024-03-04 21:50:01.000000'),
       (2, 1002, 100, 'school friend', '2024-03-04 21:50:02.000000', '2024-03-04 21:50:02.000000'),
       (3, 1003, 100, 'my love', '2024-03-04 21:50:03.000000', '2024-03-04 21:50:03.000000')
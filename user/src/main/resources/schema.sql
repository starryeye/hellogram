CREATE TABLE FOLLOW (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_user_id BIGINT,
    to_user_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL
);

insert into FOLLOW(id, from_user_id, to_user_id, created_at)
values (1, 1001, 100, '2024-03-04 21:50:01'),
       (2, 1002, 100, '2024-03-04 21:50:02'),
       (3, 1003, 100, '2024-03-04 21:50:03')
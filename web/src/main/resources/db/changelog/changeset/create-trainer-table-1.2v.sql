--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:create-trainer-table1.2

create table trainer
(
    id            bigint generated by default as identity primary key,
    category      varchar(100) not null,
    kind_of_sport varchar(100) not null,
    user_id       bigint       not null,
    foreign key (user_id)
        references users (id)
);
--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:create-trainer-table

create table trainer
(
    id            bigserial    not null primary key,
    category      varchar(100) not null,
    kind_of_sport varchar(100) not null,
    user_id       bigint       not null,
    foreign key (user_id)
        references users (id)
);
--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:create-users-table

create table users
(
    id       bigserial    not null primary key,
    email    varchar(255) not null,
    name     varchar(100) not null,
    password varchar(255) not null,
    role     varchar(30)  not null,
    status   varchar(30)  not null,
    surname  varchar(150) not null
);
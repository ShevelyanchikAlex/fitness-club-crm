--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:create-service-table

create table service
(
    id          bigserial      not null primary key,
    name        varchar(100)   not null,
    description varchar(400)   not null,
    price       numeric(19, 2) not null
);
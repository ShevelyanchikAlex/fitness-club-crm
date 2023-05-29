--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:create-news_article-table

create table news_article
(
    id                bigserial    not null primary key,
    title             varchar(100) not null,
    content           text         not null,
    author            varchar(150) not null,
    image_url         varchar(255) not null,
    created_date_time timestamp    not null
);
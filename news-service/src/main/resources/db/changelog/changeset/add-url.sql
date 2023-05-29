--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:add-url

ALTER TABLE news_article
    ADD url varchar(300) default null;
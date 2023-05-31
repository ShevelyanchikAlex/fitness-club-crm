--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:change-image_url-def-val

ALTER TABLE news_article
    ALTER image_url set default null;
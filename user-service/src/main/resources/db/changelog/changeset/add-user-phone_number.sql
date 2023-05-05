--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:add-user-phone_number

ALTER TABLE users
    ADD phone_number varchar(20) not null default '+375293333333';
--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:add-user_profile_image_id

ALTER TABLE users
    ADD profile_image_id varchar(200) default null;
--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:create-fitness-club-info-table

create table fitness_club_info
(
    id          bigserial    not null primary key,
    address     varchar(70)  not null,
    description varchar(400) not null
);
--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:create-schedule-table

create table schedule
(
    id                       bigserial   not null primary key,
    training_start_date_time timestamp   not null,
    service_id               bigint      not null,
    trainer_id               bigint      not null,
    available_spots          bigint      not null,
    service_type             varchar(30) not null,
    foreign key (service_id)
        references service (id)
);
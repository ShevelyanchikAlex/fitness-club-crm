--liquibase formatted sql

--changeset Aliaksandr_Shavialianchyk:create-orders-table

create table orders
(
    id                       bigserial   not null primary key,
    created_date_time        timestamp   not null,
    order_status             varchar(30) not null,
    training_start_date_time timestamp   not null,
    service_id               bigint      not null,
    trainer_id               bigint      not null,
    user_id                  bigint      not null,
    foreign key (service_id)
        references service (id)
);
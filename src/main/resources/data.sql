/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE drivers

insert into customers (id, title, is_deleted, created_at) values ('123e4567-e89b-12d3-a456-556642440000', 'MOCK USER1', false, now());
insert into customers (id, title, is_deleted, created_at) values ('05ba2e33-bb7b-4d17-acec-b54a28089e00', 'MOCK USER2', false, now());
insert into customers (id, title, is_deleted, created_at) values ('3651ef15-7a53-480d-bf91-b334064cec90', 'MOCK USER3', false, now());
insert into customers (id, title, is_deleted, created_at) values ('a6021d18-e340-42c6-a866-dbd3854347f5', 'MOCK USER4', false, now());
insert into customers (id, title, is_deleted, created_at) values ('0c83704f-bfc1-4ee0-8efe-1d8be1732fb8', 'MOCK USER5', false, now());
insert into customers (id, title, is_deleted, created_at) values ('f9497d49-3fa2-449f-8f7c-3e4311b1cde7', 'MOCK USER6', false, now());
-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into product (id, customer_id, title, description, price, is_deleted, created_at)
values ('f6d5a527-df96-43a5-b8ec-239dc1b867b4', '123e4567-e89b-12d3-a456-556642440000', 'Shoes', 'Shoes of size 42', 100.00, false, now());
insert into product (id, customer_id, title, description, price, is_deleted, created_at)
values ('25aa6a54-f488-4943-9d54-4196bc241488', '123e4567-e89b-12d3-a456-556642440000', 'Shirt', 'Shirt of size 42', 100.00, false, now());
insert into product (id, customer_id, title, description, price, is_deleted, created_at)
values ('36c41483-306d-4fce-a5f9-3351e6ce08cd', '123e4567-e89b-12d3-a456-556642440000', 'Cap', 'Cap of black color', 100.00, false, now());
insert into product (id, customer_id, title, description, price, is_deleted, created_at)
values ('97319968-6978-4135-8464-3b5ca5a2ae7e', '3651ef15-7a53-480d-bf91-b334064cec90', 'Soap', 'Soap of dove', 100.00, false, now());
-- SECURITY: USER ACCOUNT
DROP TABLE IF EXISTS account;
CREATE TABLE account (
 account_name VARCHAR(255) NOT NULL,
 "password" VARCHAR(255 ) NOT NULL,
 "id" serial primary key,
 enabled boolean DEFAULT true,
 account_non_expired boolean default true,
 credentials_non_expired boolean default true,
 account_non_locked boolean default true
);

insert into account(account_name, password) values ('guest', 'password');
insert into account(account_name, password) values ('admin', 'password');

-- JOURNAL TABLE: 
DROP TABLE IF EXISTS journal;
CREATE TABLE journal (
"id" serial primary key,
created timestamp DEFAULT NULL,
summary VARCHAR(255) DEFAULT NULL,
title VARCHAR(255) DEFAULT NULL);

insert into journal(title,summary,created) values('Get to know Spring Boot','Today I will
learn Spring Boot','2016-01-02 00:00:00.00');
insert into journal(title,summary,created) values('Simple Spring Boot Project','I will do my
first Spring Boot project','2016-01-03 00:00:00.00');
insert into journal(title,summary,created) values('Spring Boot Reading','Read more about
Spring Boot','2016-02-02 00:00:00.00');
insert into journal(title,summary,created) values('Spring Boot in the Cloud','Learn Spring
Boot using Cloud Foundry','2016-02-05 00:00:00.00');
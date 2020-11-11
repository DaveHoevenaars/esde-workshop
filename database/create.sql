create database IF NOT EXISTS esde;
create database IF NOT EXISTS esde_test;

use esde;

CREATE TABLE IF NOT EXISTS submissions
(
    id       int auto_increment primary key not null,
    imageUrl varchar(255)                   not null,
    comment  text,
    uploader varchar(255),
    token    varchar(255)                   NOT NULL
);

use esde_test;

CREATE TABLE IF NOT EXISTS submissions
(
    id       int auto_increment primary key not null,
    imageUrl varchar(255)                   not null,
    comment  text,
    uploader varchar(255),
    token    varchar(255)                   NOT NULL
);
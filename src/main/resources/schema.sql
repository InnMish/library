CREATE TABLE book (
    id SERIAL PRIMARY KEY ,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(64) NOT NULL,
    presence BOOLEAN NOT NULL
);
 CREATE TABLE user (
    id SERIAL PRIMARY KEY,
    login VARCHAR(64) NOT NULL,
    password VARCHAR(96) NOT NULL
 )
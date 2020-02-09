DROP SCHEMA IF EXISTS "world_of_manga" CASCADE;

CREATE SCHEMA "world_of_manga";

DROP TABLE IF EXISTS world_of_manga.user CASCADE;

CREATE TABLE world_of_manga.user (
    "user_id" SERIAL PRIMARY KEY,
    "username" VARCHAR(40) NOT NULL UNIQUE,
    "password" varchar(100) NOT NULL,
    "email" VARCHAR(100) NOT NULL UNIQUE,
    "first_name" VARCHAR(40),
    "last_name" VARCHAR(40),
    "is_enabled" boolean NOT NULL DEFAULT FALSE
);

DROP TABLE IF EXISTS world_of_manga.role CASCADE;

CREATE TABLE world_of_manga.role (
    "role_id" SERIAL PRIMARY KEY,
    "name" VARCHAR(30) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS world_of_manga.user_role CASCADE;

CREATE TABLE world_of_manga.user_role(
	"user_id" int,
    "role_id" int,
    PRIMARY KEY("user_id", "role_id"),
	CONSTRAINT "FK_USER_ROLE" FOREIGN KEY ("user_id") REFERENCES world_of_manga.user("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT "FK_ROLE_USER" FOREIGN KEY ("role_id") REFERENCES world_of_manga.role("role_id") ON DELETE NO ACTION ON UPDATE NO ACTION
); 
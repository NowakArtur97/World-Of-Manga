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

DROP TABLE IF EXISTS world_of_manga.language CASCADE;

CREATE TABLE world_of_manga.language (
    "language_id" SERIAL PRIMARY KEY,
    "locale" VARCHAR(6) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS world_of_manga.manga CASCADE;

CREATE TABLE world_of_manga.manga (
    "manga_id" SERIAL PRIMARY KEY,
	"image" BYTEA NOT NULL
);

DROP TABLE IF EXISTS world_of_manga.author CASCADE;

CREATE TABLE world_of_manga.author (
    "author_id" SERIAL PRIMARY KEY,
	"full_name" VARCHAR(50) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS world_of_manga.manga_author CASCADE;

CREATE TABLE world_of_manga.manga_author (
    "manga_id" SERIAL,
    "author_id" SERIAL,
    PRIMARY KEY ("manga_id" , "author_id"),
    CONSTRAINT "FK_MANGA_AUTHOR" FOREIGN KEY ("manga_id")
        REFERENCES world_of_manga.manga ("manga_id")
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT "FK_AUTHOR_MANGA" FOREIGN KEY ("author_id")
        REFERENCES world_of_manga.author ("author_id")
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS world_of_manga.manga_translation CASCADE;

CREATE TABLE world_of_manga.manga_translation (
    "manga_translation_id" SERIAL PRIMARY KEY,
    "manga_id" int,
    "language_id" int,
    "title" VARCHAR(50) NOT NULL,
    "description" VARCHAR(1000) NOT NULL,
    CONSTRAINT "FK_MANGA_TRANSLATION" FOREIGN KEY ("manga_id")
        REFERENCES world_of_manga.manga ("manga_id")
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT "FK_TRANSLATION_LANGUAGE" FOREIGN KEY ("language_id")
        REFERENCES world_of_manga.language ("language_id")
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS world_of_manga.manga_rating CASCADE;

CREATE TABLE world_of_manga.manga_rating (
    "manga_id" SERIAL,
    "user_id" SERIAL,
    "rating" DECIMAL(3 , 2 ),
    PRIMARY KEY ("manga_id" , "user_id"),
    CONSTRAINT "FK_RATING_MANGA_USER" FOREIGN KEY ("manga_id")
        REFERENCES world_of_manga.manga ("manga_id")
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT "FK_RATING_USER_MANGA" FOREIGN KEY ("user_id")
        REFERENCES world_of_manga.user ("user_id")
        ON DELETE NO ACTION ON UPDATE NO ACTION,
	    CONSTRAINT "CHK_MANGA_RATING_MAX_VALUE" CHECK ("rating" <= 5)
);
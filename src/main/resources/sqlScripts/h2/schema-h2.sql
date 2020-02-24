DROP SCHEMA IF EXISTS world_of_manga;

CREATE SCHEMA world_of_manga;

DROP TABLE IF EXISTS world_of_manga.user;

CREATE TABLE world_of_manga.user (
    user_id INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    username VARCHAR(40) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(40),
    last_name VARCHAR(40),
    is_enabled boolean NOT NULL DEFAULT FALSE
);

DROP TABLE IF EXISTS world_of_manga.role;

CREATE TABLE world_of_manga.role (
    role_id INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS world_of_manga.user_role;

CREATE TABLE world_of_manga.user_role(
	user_id INT(11),
    role_id INT(11),
    PRIMARY KEY(user_id, role_id),
	CONSTRAINT "FK_USER_ROLE" FOREIGN KEY (user_id) REFERENCES world_of_manga.user(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT "FK_ROLE_USER" FOREIGN KEY (role_id) REFERENCES world_of_manga.role(role_id) ON DELETE NO ACTION ON UPDATE NO ACTION
); 

DROP TABLE IF EXISTS world_of_manga.language;

CREATE TABLE world_of_manga.language (
    language_id INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    locale VARCHAR(6) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS world_of_manga.manga;

CREATE TABLE world_of_manga.manga (
    manga_id INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
	image BYTEA NOT NULL
);

DROP TABLE IF EXISTS world_of_manga.author;

CREATE TABLE world_of_manga.author (
    author_id INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
	full_name VARCHAR(50) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS world_of_manga.manga_author;

CREATE TABLE world_of_manga.manga_author (
    manga_id INT(11) AUTO_INCREMENT NOT NULL,
    author_id INT(11) AUTO_INCREMENT NOT NULL,
    PRIMARY KEY (manga_id , author_id),
    CONSTRAINT "FK_MANGA_AUTHOR" FOREIGN KEY (manga_id)
        REFERENCES world_of_manga.manga (manga_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT "FK_AUTHOR_MANGA" FOREIGN KEY (author_id)
        REFERENCES world_of_manga.author (author_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS world_of_manga.manga_translation;

CREATE TABLE world_of_manga.manga_translation (
    manga_translation_id INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    manga_id INT(11),
    language_id INT(11),
    title VARCHAR(50) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    CONSTRAINT "FK_MANGA_TRANSLATION" FOREIGN KEY (manga_id)
        REFERENCES world_of_manga.manga (manga_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT "FK_TRANSLATION_LANGUAGE" FOREIGN KEY (language_id)
        REFERENCES world_of_manga.language (language_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS world_of_manga.manga_rating;

CREATE TABLE world_of_manga.manga_rating (
    manga_id INT(11) AUTO_INCREMENT NOT NULL,
    user_id INT(11) AUTO_INCREMENT NOT NULL,
    rating INT,
    PRIMARY KEY (manga_id, user_id),
    CONSTRAINT "FK_RATING_MANGA_USER" FOREIGN KEY (manga_id)
        REFERENCES world_of_manga.manga (manga_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT "FK_RATING_USER_MANGA" FOREIGN KEY (user_id)
        REFERENCES world_of_manga.user (user_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
	    CONSTRAINT "CHK_MANGA_RATING_MAX_VALUE" CHECK (rating <= 5)
);

DROP TABLE IF EXISTS world_of_manga.favourite_manga;

CREATE TABLE world_of_manga.favourite_manga (
    manga_id INT(11) AUTO_INCREMENT NOT NULL,
    user_id INT(11) AUTO_INCREMENT NOT NULL,
    PRIMARY KEY (manga_id, user_id),
    CONSTRAINT "FK_FAVOURITE_MANGA_USER" FOREIGN KEY (manga_id)
        REFERENCES world_of_manga.manga (manga_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT "FK_FAVOURITE_USER_MANGA" FOREIGN KEY (user_id)
        REFERENCES world_of_manga.user (user_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
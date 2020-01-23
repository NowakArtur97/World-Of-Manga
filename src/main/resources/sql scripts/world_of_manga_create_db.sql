DROP DATABASE IF EXISTS `world_of_manga`;

CREATE DATABASE `world_of_manga`;

USE `world_of_manga`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    `user_id` INT(11) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(40) NOT NULL,
    `password` varchar(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `first_name` VARCHAR(40),
    `last_name` VARCHAR(40),
    `is_enabled` TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `USER_USER_NAME_UNIQUE` (`username`),
    UNIQUE KEY `USER_EMAIL_UNIQUE` (`email`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
    `role_id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE `ROLE_NAME_UNIQUE` (`name`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role`(
	`user_id` int(11) NOT NULL,
    `role_id` int(11) NOT NULL,
    PRIMARY KEY(`user_id`, `role_id`),
	CONSTRAINT `FK_USER_ROLE` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `FK_ROLE_USER` FOREIGN KEY (`role_id`) REFERENCES `role`(`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

INSERT INTO `user`(`username`, `password`, `email`, `is_enabled`) values 
('user', '$2a$10$FnkNozkIGR1ec3tsByXTG.PjPlu6Ntj7cW.lgPLIFkigHe6jreSw2', 'user@email.com', '1'),
('admin', '$2a$10$21qh5sabaXLO/IVFOmNSbOPjLTSpUdjvpD7kiIx3Ckr9NovR7CRZO', 'admin@email.com', '1');

INSERT INTO `role` (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (1, 1), (2, 1), (2, 2);
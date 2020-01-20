DROP DATABASE IF EXISTS `world_of_manga`;

CREATE DATABASE `world_of_manga`;

USE `world_of_manga`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`(
	`user_id` int(11) NOT NULL AUTO_INCREMENT,
    `user_name` varchar(40) NOT NULL, 
    `email` varchar(100) NOT NULL,
    `first_name` varchar(40),
    `last_name` VARCHAR(40),
    `is_enabled` tinyint NOT NULL DEFAULT 0,
    
    PRIMARY KEY (`user_id`), 	
    UNIQUE KEY `USER_USER_NAME_UNIQUE`(`user_name`),
    UNIQUE KEY `USER_EMAIL_UNIQUE`(`email`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role`(
	`role_id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(30) NOT NULL,
    
    PRIMARY KEY(`role_id`),
    UNIQUE `ROLE_NAME_UNIQUE` (`name`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
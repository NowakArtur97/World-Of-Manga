# WorldOfManga

## Table of Contents
* [General info](#general-info)
* [Demo](#demo)
* [Setup](#setup)
* [Built With](#built-with)
* [Features](#features)
* [Status](#status)
* [Screenshots](#screenshots)

## General info
An application for manga fans to help manage their list of read titles.

## Demo
The application demo is available on the Heroku platform: https://world-of-manga.herokuapp.com/
It may take a while for the application to start. If the page loads without a manga list, please refresh the page after a while.

To log in as a user please provide:
- Username: user
- Password: user

To log in as a admin please provide:
- Username: admin
- Password: admin

## Setup

To start the application, in the folder, enter the following commands in command line:

- `mvnw clean package -Dspring-boot.run.profiles=docker -DskipTests`
- `docker-compose up -d`
  Go to: `http://YOUR_DOCKER_IP_OR_LOCALHOST:8080`,
  where `YOUR_DOCKER_IP` is your docker machine IP address (or localhost).
  In case of problems, also edit the docker-compose file and change the url to the database (default is `192.168.99.100`).
  To shut down the containers enter:
- `docker-compose down`

For a development use commands:
- `docker-compose -f docker-compose.dev.yml up -d`
  Then run the application in your favorite editor by setting the `dev` profile and go to: `http://localhost:8080`.
- `docker-compose -f docker-compose.dev.yml down`

For a testing use commands:
- `docker-compose -f docker-compose.test.yml up -d`
- `mvnw test -Dspring-boot.run.profiles=test`
- `docker-compose -f docker-compose.test.yml down`

## Built With 
- Java 11
- Spring (Boot, MVC, Security, Data JPA) - 2.2.3
- Thymeleaf
- Lombok - 1.18.10
- jUnit5 - 5.5.2
- Mockito - 3.1.0
- Selenium, Selenium Grid - 3.141.59
- Model Mapper - 2.3.0
- Maven
- MySQL
- PostgreSQL
- H2
- CircleCI
- Docker

## Features

- Continuous integration and deployment with CircleCI
- Deployment on Heroku
- Loading data at application startup
- Database selection depending on the profile

Available for all users:
- Select language (available options are: English and Polish)
- Recommendations
- View all added manga with genres

Available for unlogged users:
- Login
- Register

Available to users with "User" or "Admin" role:
- Rate manga
- Add manga to favorites
- Add manga to list based on status
- Browse manga by status
 
Available to users with only "Admin" role:
- Add new author
- Add, edit and delete manga

## Status
Project is: finished

## Screenshots
![Main Page](./screenshots/main.png)
<p style="text-align: center">Main Page</p>

![Login Page](./screenshots/login.png)
<p style="text-align: center">Login Page</p>

![Registration Page](./screenshots/register.png)
<p style="text-align: center">Registration Page</p>

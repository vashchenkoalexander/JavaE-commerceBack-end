# JavaDiploma
![Static Badge](https://img.shields.io/badge/spring-version--3.0.0-green?style=plastic&logo=springboot)
![Static Badge](https://img.shields.io/badge/postgreSQL-black?style=plastic&logo=postgresql&color=ffffff)
![Static Badge](https://img.shields.io/badge/MongoDB-white?style=plastic&logo=mongodb&color=ffffff)

## This is a diploma project for my master degree

### Description

The project wrote on Java 17 with mainly using Spring Boot. For e-commerce back-end. Main idea was created REST API.
In this project you can register your profile, create new items, add items into your cart for future checkout.
All data about user and their shopping history will be stored in postgreSQL database.

### Technology which I used:
- Spring Web
- Spring DevTools
- Spring Data JPA
- Spring Validation
- Spring Security
- Spring AOP
- Spring Mail 
- Spring Artemis

### Databases which I worked with in this project:
- postgreSQL
- mongoDB

### Patterns in project
There are a bunch of packages whom all store by logical names. Design pattern of project is MVC where Model is Entity, Controller is controller without View.
Also in project was consumed Repository pattern which stored in api.repository package for object-oriented access to database.

### Spring Web

For create REST end points with can be consumed by front-end or just by link in different project or with some app like Postman

### Spring DevTools

For easy rebuild project with-out full restart him

### Spring Validation

For validate information which was received for correctness and handle errors and help user to find their mistakes

### Spring Security

For secure end-points by authority and restrict usage from non authorize users

### Spring AOP

Aspect Oriented programming for develop new features which not responsible for business logic like sending mails for newly created users or tokens by email or logging work of project

### Spring Mail

For only sending mails using the SMTP(Simple Mail Transfer Protocol) and Java Mail Sender

### Spring Artemis

Message broker for receiving or sending messages to a dedicated server asynchronously for future interacting with them

### Spring Data JPA
For create an entity classes whom will be stored in database for this back-end I chose Spring JPA for work with data for databases in object-oriented way.
Package with name api.entity for storing entity classes in project. For store entity in database is used class Repository stereotype of Spring which extends behavior of JpaRepository.

### postgreSQL

Main database for storing all information about this project

### mongoDB

Second database for interact with messages with was sent to a message broker in this project is Apache Active MQ
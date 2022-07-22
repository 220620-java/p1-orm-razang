# Project 1 Object Relational Mapper - Team Razang
A simple ORM to facilitate the usage of JDBC and a Java application.

## Description

Project 1 is part of a Revature curriculum to learn basic web application structure through various concepts and technologies such as REST APIs, Servlets, PostgreSQL, Amazon Web Services, Jackson, ORM, and Java. Our Object Relational Mapper is developed along side our [web application](https://github.com/220620-java/p1-web-razang), but can be compiled and redeployed to other projects.

## Getting Started

### Dependencies

* Java 8 JDK
* Maven
* JUnit 5
* PostgreSQL
* Mockito

### Installing

* Clone this repository and open it with Eclipse or VSCode.
* Compile with _mvn clean package install_ and put the ORM as a dependency in pom.xml
```
<!-- Razang ORM -->
<dependency>
  <groupId>com.revature</groupId>
  <artifactId>razangorm</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```
* Database credentials must be put in src/main/resources/ as database.properties with the following fields inside:
```
url=jdbc:postgresql://????:5432/postgres?currentSchema=myBank
usr=????
pwd=????
```

### Usage

* Use basic CRUD methods the ORM offers to interact with the database and its tables.
```
ObjectRelationalMapper orm = new ObjectRelationalMapperImpl(); // Instantiate the ORM
orm.create (user, tableName); // Inserts the user object into the database with all of its fields
```
* Custom annotations are used to filter fields and grab superclass fields.
```
@Id - flags the id field for the primary key
@Username - flags the username field to find the user by username
@Subclass - flags the class to grab the superclass fields
@ORMIgnore - flags the field to be ignored by the ORM, it will not be included in the database
```

## Authors

Contact us for any support or questions:

* [Raza Ghulam](https://github.com/raza-bot)
* [Colby Tang](https://github.com/colbyktang) colby051@revature.net

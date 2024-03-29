# Logging_Multimodule

This project contains a multimodule application built using Spring MVC framework. It provides functionality for handling both HTTP POST and GET methods. Additionally, the project is equipped with logging capabilities to track and store project activities in a file.

## Table of Contents

- [Introduction](#introduction)
- [Requirements](#requirements)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Building the Application](#building-the-application)
- [AWS Details](#aws-details)
- [Docker Details](#docker-details)
- [Commands To Remember](#commands-to-remember)



## Introduction

This project is a multimodule application developed using the Spring MVC framework. It provides a flexible and scalable architecture for building web applications. The main focus of this project is to handle HTTP POST and GET methods effectively. Additionally, it incorporates logging functionality to capture and store all relevant information about the project.

## Requirements

- Ubuntu operating system
- IntelliJ IDEA IDE
- Maven build tool
- MySQL database
- Java 17 JDK


## Project Structure


The project consists of the following modules and directories:

- **Application**
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - BoxApplication.java
                        - ServletInitializer.java
            - **resources**
                - application-dev.properties
                - application-prod.properties
                - application.properties
                - log4j2.xml
                - templates
                    - addUser.html
                    - Home.html
                    - users1.html
                    - users.html

- **Controller**
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - SecurityConfig.java
                        - UserController.java
            - resources

- **Data_Module**
    - dhinesh_db.sql
    - dknew.sql

- **Dockerfile**

- **Exception**
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - CustomException.java
        - test
            - java

- **Model**
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - UserEncrypt.java
                        - UserEntity2.java
                        - UserEntity.java

- **pom.xml**
- **README.md**

- **Repository**
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - UserEncryptRepository.java
                        - UserEntity2Repository.java
                        - UserRepository.java

- **Service**
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - JpaUserDetailsService.java
                        - UserService.java
            - resources
        - test
            - java


## Installation

To install and set up the project locally, follow these steps:



Step 1: clone the project
- Clone the repository from GitHub using the following command:

      git clone git@github.com:Dhinesh-07/DhineshkumarS.git

Step 2: Create a database (MySQL)
- install mysql.
- log in to MySQL as the root user using the following command:

      mysql -u root -p
- create a database.

      CREATE DATABASE name;

Step 3: Import Database Schema
- Locate the dhines_db.sql file inside the Data_Module directory of the cloned project.
- Use this command to import the schema:

      mysql -u your_mysql_username -p dhinesh < Data_Module/dhines_db.sql
- Check whether the database has been successfully created or not.

Step 4: Update Application Configuration

- Open the **application.properties** file in your Spring Boot project.
- Update the following properties with your MySQL database credentials:

       spring.datasource.url=jdbc:mysql://localhost:3306/name
       spring.datasource.username=your_mysql_username
       spring.datasource.password=your_mysql_password

Step 5:Build and Run the Project
- Navigate to the project directory and clean and build the project using the command: mvn clean install.
- Then, start running the project by executing:

 
## Building the Application

_Admin credential for both development and production_


    Username:admin
    Password:123

**_Script was added to run development and production_**

**Development**

    /.local dev 

**Production**

    /.local dev


**Development for Docker**

    /.run dev 

**Production for Dcoker**

    /.run dev


## AWS Details


**EC2 Instance**
- **Public IPv4 DNS**
        
        ec2-13-48-193-15.eu-north-1.compute.amazonaws.com

**RDS Instance for Psql**
- **Endpoint**:                            

    database-postgres.c0x7yiddfdt2.eu-north-1.rds.amazonaws.com

- **Port**: 5432
- **Username**: dhinesh
- **Password**: Dhinesh7

      mysql -h mydbinstance.crezjjaofuyj.eu-north-1.rds.amazonaws.com -P 3306 -u dhinesh -p

## Docker Details

**docker user name= dhineshdk07**



## Commands to remember
**Connect to EC2 Instance**

To connect to your EC2 instance, follow these steps:

Set the appropriate permissions for your key file:

    chmod 400 Spring_boot.pem

Connect to your instance using SSH and the Public DNS:

    ssh -i "Spring_boot.pem" ubuntu@ec2-13-48-193-15.eu-north-1.compute.amazonaws.com

**Connetcing to rds**

    psql -h database-postgres.c0x7yiddfdt2.eu-north-1.rds.amazonaws.com -p 5432 -U postgres -W



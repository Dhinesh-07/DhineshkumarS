# Logging_Multimodule

This project contains a multimodule application built using Spring MVC framework. It provides functionality for handling both HTTP POST and GET methods. Additionally, the project is equipped with logging capabilities to track and store project activities in a file.

## Table of Contents

- [Introduction](#introduction)
- [Requirements](#requirements)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Project Usage Instructions](#project-usage-instructions)
- [Database Connection Details](#database-connection-details)


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
            - resources
                - application.properties
                - log4j2.xml

- **Controller**
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - UserController.java

- **Data_Module**
    - dhinesh_db.sql

- **Model**
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - UserEntity.java

- **Repository**
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - UserRepository.java

- **pom.xml**
- **README.md**

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

      mvn spring-boot:run.


## Project Usage Instructions

**Locally**

- After following the previous setup steps, the project will be running locally on localhost:8080.
- To retrieve all data from the project, execute the following command in your terminal:

      curl http://localhost:8080/all

To add new data to the project, use the following command:

      curl -X POST -H "Content-Type: application/json" -d '{"name":"","email":""}' http://localhost:8080/add



**On AWS Server**
- you can access the project using the following URL: http://ec2-13-53-168-180.eu-north-1.compute.amazonaws.com:8080.


**Get**

- To retrieve all data from the project, you can send an HTTP GET request to the server using the following command:

      curl http://ec2-13-53-168-180.eu-north-1.compute.amazonaws.com:8080/all

- The response will be a JSON array containing the retrieved data. Here's an example output:

[{"id":1,"name":"","email":""},{"id":2,"name":"dk","email":"kumar"},{"id":3,"name":"dhinesh","email":"dhinesh@gmail.com"},{"id":4,"name":"kumar","email":"kumar@gmail.com"}]


**Post**

- If you want to add new data to the project on the server, you can send an HTTP POST request with the required data in the request body. Use the following command:

      curl -X POST -H "Content-Type: application/json" -d '{"name":"", "email":""}' http://ec2-13-53-168-180.eu-north-1.compute.amazonaws.com:8080/add

  - Upon successful addition of the data, the server will respond with the message
             
        "Saved Successfully."

## AWS Details


**EC2 Instance**
- **Public IPv4 DNS**
        
        ec2-13-53-168-180.eu-north-1.compute.amazonaws.com

**RDS Instance**
- **Endpoint**:                            
                  
      http://mydbinstance.crezjjaofuyj.eu-north-1.rds.amazonaws.com/

- **Port**: 3306
- **Username**: dhinesh
- **Password**: Dhinesh7


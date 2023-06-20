# Logging_Multimodule 

This project contains a multimodule application built using Spring MVC framework. It provides functionality for handling both HTTP POST and GET methods. Additionally, the project is equipped with logging capabilities to track and store project activities in a file. 

## Table of Contents

- [Introduction](#introduction)
- [Requirements](#requirements)
- [Project Structure](#project-structure)
- [Installation](#installation)



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

- Application
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

- Controller
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - UserController.java

- Data_Module
    - dhinesh_db.sql

- Model
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - UserEntity.java

- Repository
    - pom.xml
    - src
        - main
            - java
                - com
                    - atdxt
                        - UserRepository.java

- pom.xml
- README.md

## Installation

To install and set up the project locally, follow these steps:



Step 1: clone the project
- Clone the repository from GitHub using the following command:git clone git@github.com:Dhinesh-07/DhineshkumarS.git

Step 2: Create a database (MySQL)
- install mysql and create a database named as "dhinesh"
- Locate the dhines_db.sql file inside the Data_Module directory of the cloned project.
- Use this command to import the schema: mysql -u your_mysql_username -p dhinesh < Data_Module/dhines_db.sql
- Check whether the database has been successfully created or not.

Step 3:Build and Run the Project
- Navigate to the project directory and clean and build the project using the command: mvn clean install.
- Then, start running the project by executing: mvn spring-boot:run.

After executing the previous steps, the project will be running locally on localhost:8080. 
- To retrieve all data from the project, you can send a GET request using curl to the endpoint:  curl http://localhost:8080/all
- To add new data to the project, you need to send a POST request using curl to the endpoint: curl -X POST http://localhost:8080/add


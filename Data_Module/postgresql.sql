-- PostgreSQL version
-- Host: localhost    Database: dhinesh
-- ------------------------------------------------------

-- Table structure for table "dhinesh_demo"
CREATE TABLE IF NOT EXISTS "dhinesh_demo" (
    "id" serial PRIMARY KEY,
    "name" varchar(255) DEFAULT NULL,
    "email" varchar(255) DEFAULT NULL,
    "created_on" timestamp DEFAULT '2023-06-28',
    "modify_time" timestamp DEFAULT '2023-06-28',
    "age" integer DEFAULT NULL,
    "phone_number" varchar(255) DEFAULT NULL,
    CONSTRAINT "unique_email" UNIQUE ("email"),
    CONSTRAINT "unique_name" UNIQUE ("name")
);

-- Table structure for table "dhinesh_demo2"
CREATE TABLE IF NOT EXISTS "dhinesh_demo2" (
    "id" serial PRIMARY KEY,
    "city" varchar(255) DEFAULT NULL,
    "country" varchar(255) DEFAULT NULL,
    "created_on" timestamp DEFAULT NULL,
    "modify_time" timestamp DEFAULT NULL,
    "image_url" varchar(255) DEFAULT NULL
);

-- Table structure for table "dhinesh_demo3"
CREATE TABLE IF NOT EXISTS "dhinesh_demo3" (
    "id" serial PRIMARY KEY,
    "created_on" timestamp DEFAULT NULL,
    "modify_time" timestamp DEFAULT NULL,
    "password" varchar(255) NOT NULL,
    "username" varchar(255) NOT NULL,
    "confirmpassword" varchar(255) NOT NULL,
    CONSTRAINT "UK_ldha0y2cm7qcfkdl5qoavmqxh" UNIQUE ("username")
);



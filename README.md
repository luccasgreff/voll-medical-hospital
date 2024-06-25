# Voll Medical Hospital

Project created studying through Alura Education classes, in Java & Spring Boot Formation.

## Description

Rest API responsible for the back-end processes of a fictional hospital. Registering, consulting, updating & deleting data from medics, patients & appointments.

## Technologies

- **Java 17**
- **Spring Framework**
- **H2 Database**
- **Flyway Migrations**
- **Lombok**

## Features

1. Security
   - In order to perform the main requests in the application, it's necessary to authenticate;
   - First, registering a user in the database;
   - When the user is registered, the same login is used to retrieve a JWT Token;
   - The JWT Token must be used when performing the main requests;

2. C.R.U.D Operations for medics, patients & appointments
   - The application performs the main database operations: CREATE, READ, UPDATE & DELETE;
   - The C.R.U.D operations are performed through HTTP Protocol and it's methods: POST, GET, PUT & DELETE;

3. Exception Treatment
   - The most common exceptions are treated and return a detailed message from the error;
   - Based on the first business rule below, there was a need for a custom exception, and it's called UnactiveException;

## Business Rules

1. Records from patients and medics can't be deleted 
   - It's not allowed to completely delete a medic or patient record in the database, that's because these data may be of great value in a nearby future, 
   or, in the future, the medic or patient can be active in the hospital once again;
   - When the DELETE operation is performed in a patient or medic record, the request sets the record as unactive;
   -  It's not possible to update or get an unactive record;

2. Only some data can be updated
   - In medic's and patient's records, it's only allowed to update name, phone & address
   - In appointment's records, it's only allowed to update time & date;

### Language Conventions

- Medic CRM is the brazilian medic ID;
- Patient SSN is equivalent to brazilian CPF (can be written in both formats);
- Address District is equivalent to brazilian "bairro";




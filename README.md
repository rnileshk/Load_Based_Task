# Load-Based Task Distribution System - Backend

This is the backend service for the **Load-Based Task Distribution System**. It is built using **Spring Boot**, **Spring Data JPA**, and **MySQL**. The system helps distribute tasks among employees based on their current workload.

---

## Features

- Create, update, delete, and fetch tasks
- Create, update, delete, and fetch users
- Automatic task assignment to the employee with the least workload
- Track task status
- Manage task deadlines
- RESTful APIs for frontend integration
- MySQL database integration

---

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven

---

## Project Structure

```text
src/
 └── main/
     ├── java/com/nilesh/Backed/
     │    ├── controller
     │    ├── model
     │    ├── repository
     │    ├── service
     │    └── BackedApplication.java
     └── resources/
          └── application.properties

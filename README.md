# Task Management Application

## Overview

This is a Task Management application built using Spring Boot for the backend and Angular for the frontend. The application allows users to authenticate, create, read, update, and delete tasks. It also includes features such as JWT-based authentication, task filtering, sorting, and responsive UI components.

---

## Features

- **User Authentication**: Login functionality with JWT token generation.
- **Task Management**:
  - Create new tasks.
  - View all tasks with optional filtering by completion status.
  - Update existing tasks.
  - Delete tasks.
- **UI Components**:
  - Responsive design using PrimeNG components.
  - Toast notifications for user feedback.
- **Backend API**:
  - RESTful endpoints for task CRUD operations.
  - Secure authentication endpoint.

---

## Prerequisites

Before running the application, ensure you have the following installed:

1. **Node.js**
   - Version: 22.14.0
   - Download from: https://nodejs.org

2. **npm**
   - Version: 11.1
   - Ensure your npm version matches your specified Node.js version.

3. **Java Development Kit (JDK)**
   - Version: 21
   - Download from: https://www.oracle.com/java/technologies/javase-jdk21-downloads.html or use an open-source alternative like Adoptium.

4. **Maven**
   - Version: 3.8.x or higher.
   - Download from: https://maven.apache.org/download.cgi

5. **Angular CLI**
   - Install using the following command:
     ```
     npm install -g @angular/cli
     ```

---

## Project Structure

The project consists of two main parts:

1. **Backend (Spring Boot)**:
   - Located in the `Api` directory.
   - Handles API endpoints and business logic.

2. **Frontend (Angular)**:
   - Located in the `Client` directory.
   - Provides the user interface and interacts with the backend via HTTP requests.

---

## Setup Instructions

### Backend Setup

1. Navigate to the backend directory:
  cd Api
2. Build the project using Maven:
   mvn clean install

3. Start the Spring Boot application:
   mvn spring-boot:run

By default, the backend will run on http://localhost:8080.

**Database Configuration**:
- The application uses an **H2 database** by default. You can access the H2 Console at http://localhost:8080/h2-console.
- Ensure the `application.properties` file in the backend is configured correctly for H2:
  ```
  spring.h2.console.enabled=true
  spring.datasource.url=jdbc:h2:mem:testdb
  spring.datasource.driverClassName=org.h2.Driver
  spring.datasource.username=sa
  spring.datasource.password=password
  spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
  ```

### Frontend Setup

1. Navigate to the frontend directory:
   cd Client

2. Install the required dependencies:
   npm install

3. Start the Angular development server:
   ng serve

The frontend will be accessible at http://localhost:4200.

---

## Usage

1. **Login**:
- Visit http://localhost:4200/login in your browser.
- Enter valid credentials to log in. If no accounts exist, you may need to configure the backend to allow user registration or use predefined credentials.

2. **Task Management**:
- After logging in, navigate to the task list page (http://localhost:4200/tasks).
- Use the provided UI to:
  - Add new tasks.
  - Edit existing tasks.
  - Delete tasks.
  - Filter tasks by completion status.
  - Sort tasks by creation date.

3. **Logout**:
- The application automatically logs out when the JWT token expires or when navigating back to the login page.

---

## API Endpoints

### Authentication

- **POST /api/auth/login**: Authenticate a user and return a JWT token.
  - Request Body:
 ```
 {
   "username": "user",
   "password": "password"
 }
 ```

### Task Management

- **POST /api/tasks**: Create a new task.
- **GET /api/tasks**: Retrieve all tasks (with optional filtering and sorting).
  - Query Parameters:
 - `completed`: Filter by completion status (`true` or `false`).
 - `sortBy`: Sort by field (e.g., `createdAt`).
- **GET /api/tasks/{id}**: Retrieve a task by ID.
- **PUT /api/tasks/{id}**: Update an existing task.
- **DELETE /api/tasks/{id}**: Delete a task.

---

## Technologies Used

- **Backend**:
  - Spring Boot
  - Spring Security
  - JWT for authentication
  - H2 Database (in-memory)

- **Frontend**:
  - Angular
  - PrimeNG for UI components
  - HttpClientModule for HTTP requests

- **Database**:
  - H2 (configured in the Spring Boot application properties)

---


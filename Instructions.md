# Movie Ticket Booking System - "Popcorn Palace"-Backend Development

## Overview

* This is a RESTful API for a Movie Ticket Booking System built with Java Spring Boot.
  * It manages movies, showtimes, and ticket bookings.
* This system leverages Docker to run a PostgreSQL database and Spring Boot for backend API functionality.

## Prerequisites

Before running the project, ensure you have the following tools installed:

1. **Java SDK (JDK 21)**:  
   [Download here](https://www.oracle.com/java/technologies/downloads/#java21)  
   Ensure you have Java 21 or higher installed and configured.

2. **Java IDE (e.g., IntelliJ IDEA)**:  
   [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download)  
   Alternatively, you may use another IDE of your choice.

3. **Docker**:  
   [Download Docker Desktop](https://www.docker.com/products/docker-desktop/)  
   Docker is required to run a PostgreSQL container locally.

## Setup Instructions

Follow these steps to set up and run the application:

### 1. Clone the Repository

Clone the repository to your local machine.

```bash
git clone https://github.com/LiavSeg/PopcornPalace-AT-T.git

cd <your-project-folder>
```


### 2. Open the Project in IntelliJ IDEA

1. Open IntelliJ IDEA and select **Open**.
2. Navigate to the project directory and open it.

### 3. Build the Project with IntelliJ’s Embedded Maven

IntelliJ IDEA has an embedded Maven tool, so you don't need to install Maven separately. Follow these steps to build and run the project:

1. In the IntelliJ IDE, open the **Maven** tool window (usually on the right side).
2. Locate the `pom.xml` file in your project. IntelliJ should automatically recognize it as a Maven project.
3. In the **Maven** tool window, find the `Lifecycle` section and double-click on **clean install** to build the project. This will download the necessary dependencies and package the application.

Alternatively, you can run the following from the **Terminal** in IntelliJ:

```bash
mvn clean install
```

### 4. Configure PostgreSQL Database with Docker

1. To run the PostgreSQL database locally, you can use Docker and the provided `docker-compose.yml` file.

**Steps**:

1. In the project folder, ensure the `docker-compose.yml` file is present.
2. Start the PostgreSQL container using Docker Compose:

```bash
   docker-compose up -d
```
3. Spring Boot with Hibernate will automatically create and update the database schema for you (via JPA annotations).

### 5. Configure Application Properties
   * The connection details for PostgreSQL are already configured in the application.yml file. 
   Please Ensure that it contains the correct database configuration:
   username: popcorn_palace
   password: popcorn_palace

### 6. Run the Application
Once the database container is up and running, you can start the Spring Boot application.

Using IntelliJ IDE:
To run the Spring Boot application using IntelliJ's embedded Maven:

1. In IntelliJ IDE, open the **Maven** tool window (usually located on the right side of the IDE).
2. In the **Maven** tool window, locate **Lifecycle**.
3. Double-click on **spring-boot:run** under the **Lifecycle** section to start the application. This will launch the Spring Boot application, and the RESTful API will be accessible locally.

Alternatively, you can run the application from the terminal within IntelliJ(or other IDE) if maven is installed locally on your machine:

```bash
mvn spring-boot:run
```
The application now should be available locally at http://localhost:8080.

### 8. API Testing
Once the application is running, you can test the various endpoints of the REST API. The common endpoints will include:
* **Movies APIs**
  * Get all movies:	GET /movies/all
  * Add a movie:	POST /movies
  * Update a movie:	POST /movies/update/{movieTitle}
  * Delete a movie: DELETE /movies/{movieTitle}

* **Showtimes APIs**
  * Get showtime by ID:	GET /showtimes/{showtimeId}
  * Add a showtime:	POST /showtimes
  * Update a showtime:	POST /showtimes/update/{showtimeId}
  * Delete a showtime:	DELETE /showtimes/{showtimeId}

* **Bookings APIs**
  * Book a ticket:	POST /bookings

* You can use tools like Postman or curl to send HTTP requests to these endpoints.
  * https://www.postman.com/

### 8. Testing the Application
* A test package is provided with unit and integration tests.
  * you can run the tests directly in IntelliJ or from the command line.

* Running Tests in IntelliJ IDE using embedded maven:

* Open the Maven tool window.
* Find Lifecycle > test and double-click on it to run the tests.
* Click Apply and then Run.
* All the tests provided will run now.

### 9. Shutting down 
* When you're done with the application, you can shut down the Docker container:

    inside your IDE terminal use the command:
    ```bash
      docker-compose down
    ```

### 10. Troubleshooting
   If you run into any issues, consider the following:

* Ensure Docker is running and the PostgreSQL container is up and healthy.
  * inside your IDE terminal use the command
    ```bash
    docker ps
    ```
  * look for a container named: popcorn-palace
  

* Make sure the application.yml is correctly configured with the proper database connection details.
  * username: popcorn_palace
  * password: popcorn_palace
  * db name: popcorn-palace
* Make sure that the application.yml fields is matching to the docker-compose.yml file 
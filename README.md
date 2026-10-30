# Ibyacu Connect Backend

- **Student:** HIRWA MUTEMBEREZI Gad
- **Student ID:** 27338
- **Project:** Ibyacu Connect
- **Course:** Web Technology and Internet, AUCA

Ibyacu Connect links residents of a community with local artisans. A resident opens a service request for a date, an artisan takes it, and the request moves from OPEN to ACCEPTED and ends FINISHED or DECLINED.

Built with Spring Boot, Spring Data JPA and PostgreSQL.

## Run the API

1. Install Java 17 or newer and PostgreSQL.
2. Create the database:
   ```sql
   CREATE DATABASE ibyacu_connect;
   ```
3. Put your PostgreSQL username and password in `src/main/resources/application.properties`.
4. Start the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   On Windows use `mvnw.cmd spring-boot:run`, or run `IbyacuConnectApplication` from VS Code.
5. The API runs on `http://localhost:8080`. Hibernate creates the tables on startup (`ddl-auto=update`).

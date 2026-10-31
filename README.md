# Ibyacu Connect Backend

- **Student:** HIRWA MUTEMBEREZI Gad
- **Student ID:** 27338
- **Project:** Ibyacu Connect
- **Course:** Web Technology and Internet, AUCA

Ibyacu Connect links residents of a community with local artisans. A resident opens a service request for a date, an artisan takes it, and the request moves from OPEN to ACCEPTED and ends FINISHED or DECLINED.
When a request is FINISHED, the artisan writes work reports about the job.

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
6. Import `ibyacu-connect-midterm.postman_collection.json` into Postman. Run the folder **Step 0 - Seed data** once on an empty database, then run **Load ids** whenever you re-import the collection.

## Entities

| Entity | Table | Relationships |
|---|---|---|
| Trade | trade | `@ManyToMany(mappedBy = "trades")` artisans, hidden from JSON |
| Workshop | workshop | none |
| Artisan | artisan | `@ManyToOne` workshop (workshop_id), `@ManyToMany` trades (join table artisan_trade) |
| Resident | resident | none, nationalId is unique |
| ServiceRequest | service_request | `@ManyToOne` resident (resident_id), `@ManyToOne` artisan (artisan_id) |
| WorkReport | work_report | `@ManyToOne` serviceRequest (service_request_id) |

## Endpoints

| ID | Method | Path |
|---|---|---|
| A2 | POST, GET, PUT, DELETE | `/api/{trades, workshops, artisans, residents, service-requests}/save`, `/all`, `/{id}`, `/update/{id}`, `/delete/{id}` |
| B1 | GET | `/api/residents/by-last-name?lastName=mugabo` |
| B2 | GET | `/api/service-requests/by-status?status=OPEN` |
| B3 | GET | `/api/service-requests/between?start=2026-10-01&end=2026-10-31` |
| C1 | GET | `/api/artisans/by-trade?name=plumbing` |
| C2 | GET | `/api/workshops/free` |
| C3 | GET | `/api/trades/unused` |
| C4 | GET | `/api/residents/of-artisan/{artisanId}` |
| D1 | POST | `/api/service-requests/save` |
| D2 | PATCH | `/api/service-requests/{id}/status?status=ACCEPTED` |
| D3 | PUT | `/api/artisans/{artisanId}/trades/{tradeId}` |
| D4 | PATCH | `/api/artisans/{artisanId}/workshop?workshopNumber=4` |
| E1 | POST | `/api/work-reports/save?serviceRequestId=...` |
| E2 | GET | `/api/work-reports/resident/{nationalId}` |
| E3 | DELETE | `/api/work-reports/delete/{id}` and `/api/service-requests/delete/{id}` |
| F1 | GET | `/api/service-requests/stats/by-status` |
| F2 | PATCH | `/api/service-requests/cancel-day?artisanId=...&date=2026-10-20` |
| Bonus | GET | `/api/dashboard/summary` |
| Bonus | GET | `/api/service-requests/page?page=0&size=5&sort=requestDate,desc` |

## Seed data (Step 0)

- Trades: Plumbing, Carpentry, Electrical, Tailoring (nobody holds Tailoring)
- Workshops 1 to 4 (workshop 4 has no artisan)
- Artisans: Eric Niyonzima (workshop 1, Plumbing and Electrical), Aline Uwase (workshop 2, Carpentry), Patrick Habimana (workshop 3, Plumbing)
- Residents: Alice Mugabo (nationalId 1199570023456789), Jean Mugabo, Grace Ingabire, Kevin Nshimiyimana, Diane Uwimana
- Ten service requests in October and November 2026 using all four statuses; Eric has two requests on 2026-10-20, one of them DECLINED

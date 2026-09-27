# Atta'a (عطاء) - Donation Platform

---

## About the Project

**Atta'a (عطاء)** is a backend platform that connects **donors** and **beneficiaries** for donations (books, clothes, electronics, stationery, and more). Beneficiaries must be verified by admins before creating donation requests. Donors advance request status through the API workflow. The system sends **WhatsApp** updates on request lifecycle changes and **email** when an admin verifies a beneficiary account. **Gemini AI** exposes endpoints for donation advice and item description generation.

---

## Tech Stack & Architecture

| Layer | Technology |
|--------|------------|
| Language & framework | Java 17, Spring Boot |
| Database & ORM | MySQL, Spring Data JPA / Hibernate |
| API | REST (`/api/v1/...`), JSON responses |
| Validation | Jakarta Bean Validation on entities (`@Valid` on REST controllers) |

**Third-party integrations**

- **UltraMsg API** — WhatsApp notifications (request created / status updates).
- **JavaMail (SMTP)** — Email when a beneficiary account is verified.
- **Gemini API** — AI donation advice and item descriptions (`/api/v1/openai`).

**Layered design**

- **Controller** — REST resources and HTTP mapping.
- **Service** — Business rules, notifications, AI calls.
- **Repository** — Persistence via Spring Data JPA.
- **Entity** — Domain model and validation constraints.

---

## Core Entities & System Design

| Entity | Role |
|--------|------|
| **Donor** | Manages donor profiles; owns donation items. |
| **Beneficiary** | Registers and requests items after admin verification. |
| **Donation Item** | Donated item (category, condition, pickup location, donor contact). |
| **Donation Request** | Claim lifecycle: `CREATED` → `IN_PROGRESS` → `DELIVERED`. |
| **Admin** | System administrators; verify beneficiaries and award badges. |
| **Badge & Rating** | Donor recognition and beneficiary feedback (1–5 stars). |

---

## REST API Overview (39 Endpoints)

Eight `@RestController` modules under `/api/v1`:

| # | Controller | Base path | Endpoints | Notes |
|---|------------|-----------|-----------|--------|
| 1 | Admin | `/api/v1/admin` | **6** | CRUD + verify beneficiary + award badge |
| 2 | Badge | `/api/v1/badge` | **3** | List all, by donor, delete |
| 3 | Beneficiary | `/api/v1/beneficiary` | **5** | CRUD + list unverified |
| 4 | Donation Item | `/api/v1/device` | **8** | CRUD + filter category/location, stats, by donor |
| 5 | Donation Request | `/api/v1/donation-request` | **6** | List / add / delete + status filter, count, workflow |
| 6 | Donor | `/api/v1/donor` | **4** | Full CRUD |
| 7 | Gemini AI | `/api/v1/openai` | **2** | Help decision + generate ad text |
| 8 | Rating | `/api/v1/rating` | **5** | CRUD + by donor + average summary |

**Totals:** **39** REST endpoints — **24** standard CRUD-style operations (`/get`, `/add`, `/update/{id}`, `/delete/{id}` where applicable) and **15** special operations (filters, counts, workflows, AI, admin actions).

---

### Special REST Endpoints (non-CRUD)

| Method | Path | Purpose |
|--------|------|---------|
| PUT | `/api/v1/admin/verify-beneficiary/{id}` | Verify beneficiary account |
| POST | `/api/v1/admin/give-badge/{donorId}/{badgeTitle}` | Award badge to donor |
| GET | `/api/v1/badge/donor/{donorId}` | Badges for one donor |
| GET | `/api/v1/beneficiary/unverified` | Pending verification queue |
| GET | `/api/v1/device/category/{category}` | Items by category |
| GET | `/api/v1/device/location/{location}` | Items by pickup location |
| GET | `/api/v1/device/stats` | Platform donation item stats |
| GET | `/api/v1/device/donor/{donorId}` | Donation items for one donor |
| GET | `/api/v1/donation-request/status/{status}` | Requests by status (`CREATED`, `IN_PROGRESS`, `DELIVERED`) |
| GET | `/api/v1/donation-request/count` | Total number of requests |
| PUT | `/api/v1/donation-request/update-status/{id}` | Advance request to next stage |
| GET | `/api/v1/rating/donor/{donorId}` | Ratings for one donor |
| GET | `/api/v1/rating/donor/{donorId}/summary` | Average score and count for a donor |
| GET | `/api/v1/openai/help-decision` | AI donation advice |
| GET | `/api/v1/openai/generate-ad` | AI item description |

---

### Donation request workflow (API)

1. **POST** `/api/v1/donation-request/add` — Beneficiary must be verified; duplicate requests for the same item are rejected; WhatsApp sent on success.
2. **PUT** `/api/v1/donation-request/update-status/{id}` — Moves `CREATED` → `IN_PROGRESS` → `DELIVERED`; WhatsApp sent on each change.
3. **DELETE** `/api/v1/donation-request/delete/{id}` — Removes a request.

---

## Project Structure (backend)

```
src/main/java/com/waleed/capstone2/
  Controller/   REST API controllers
  Entity/       JPA models + validation
  Repository/   Spring Data JPA
  Service/      Business logic, WhatsApp, email, Gemini
  Api/          Shared API response types
  Config/       Mail configuration
```


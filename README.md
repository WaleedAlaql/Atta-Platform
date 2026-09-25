# Atta'a (عطاء) - Smart In-Kind Donation Platform

---

## About the Project
**Atta'a (عطاء)** is a comprehensive, secure, and intelligent platform designed to bridge the gap between donors and needy beneficiaries. Unlike traditional platforms restricted to single item types, Atta'a supports universal in-kind donations (books, clothes, electronics, and more). It features automated beneficiary verifications, real-time multi-channel notifications (WhatsApp & Email), and Gemini AI integration to assist donors and generate professional ad descriptions.

---

## Tech Stack & Architecture
* **Language & Framework:** Java 17, Spring Boot 3
* **Database & ORM:** MySQL, Spring Data JPA / Hibernate
* **Third-Party Integrations:**
  * **UltraMsg API:** Automated WhatsApp notifications with smart phone number formatting.
  * **JavaMail (SMTP):** email notifications for account verification.
  * **Gemini API:** AI consultant for item condition evaluation and automated marketing text generation.

---

## Core Entities & System Design
* **Donor:** Registers and lists in-kind items for community support.
* **Beneficiary:** Submits claims and receives support after strict admin verification.
* **Donation Item:** The universal item being donated, categorized by type, condition, and pickup location.
* **Donation Request:** Tracks the lifecycle of an item claim (`CREATED` -> `IN_PROGRESS` -> `DELIVERED`).
* **Admin, Badges & Ratings:** System control, donor gamification, and user feedback mechanisms.

---

## API Endpoints Overview (34 Endpoints)
The platform is organized into 8 modular controllers:

1. **Admin (`/api/v1/admin`)** - 6 Endpoints
   * Manage system admins, verify beneficiary accounts, and award recognition badges.
2. **Badge (`/api/v1/badge`)** - 3 Endpoints
   * Retrieve and filter donor badges, or delete recognition records.
3. **Beneficiary (`/api/v1/beneficiary`)** - 4 Endpoints
   * Register and manage beneficiary profiles.
4. **Donation Item (`/api/v1/device`)** - 7 Endpoints
   * Manage items, filter by category/location.
5. **Donation Request (`/api/v1/donation-request`)** - 4 Endpoints
   * Create requests, track workflows, and update item statuses.
6. **Donor (`/api/v1/donor`)** - 4 Endpoints
   * Full CRUD operations for donor profiles.
7. **Gemini AI (`/api/v1/openai`)** - 2 Endpoints
   * AI-powered decision support (`/help-decision`) and automatic ad description generation (`/generate-ad`).
8. **Rating (`/api/v1/rating`)** - 4 Endpoints
   * Manage ratings between donors and beneficiaries.

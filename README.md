# 📚 Library Project

A full-stack **Library Management System** built with **Spring Boot** and **React (TypeScript)**. Users can browse, search, and checkout books, leave reviews, and communicate with library admins through a built-in Q&A messaging system. Admins can manage the book catalog and respond to user inquiries.

> **Live Demo**: Deployed on Google Cloud Run  
> **Backend**: `https://library-service-961766531040.europe-west1.run.app`  
> **Frontend**: `https://library-service-react-961766531040.europe-west1.run.app`

---

## 🏗️ Architecture Overview

```
┌──────────────────────┐        ┌──────────────────────┐        ┌──────────────┐
│   React Frontend     │  REST  │  Spring Boot Backend  │  JPA   │   MySQL 8    │
│   (TypeScript)       │◄──────►│  (Java 17)            │◄──────►│   Database   │
│   Port: 3000         │  JSON  │  Port: 8080           │        │   Port: 3306 │
└──────────┬───────────┘        └──────────┬────────────┘        └──────────────┘
           │                               │
           └───────────┬───────────────────┘
                       │
              ┌────────▼────────┐
              │   Okta OAuth2   │
              │   (OIDC + JWT)  │
              └─────────────────┘
```

---

## ✨ Features

### 📖 Public (No Login Required)
- Browse the book catalog with a homepage carousel
- Search books by **title** or filter by **category** (Front End, Back End, Data, DevOps)
- View detailed book information including availability and copies
- Read book reviews with star ratings

### 👤 Authenticated Users
- **Checkout books** — borrow up to **5 books** simultaneously with a 7-day loan period
- **Return books** — manage active loans from your personal shelf
- **Renew loans** — extend by 7 additional days (only if not overdue)
- **Checkout history** — view all past borrowings with dates
- **Leave reviews** — rate books with ⭐ 0–5 stars (half-star increments) and optional written feedback
- **Q&A messaging** — submit questions to library admins and track responses

### 🔑 Admin Panel
- **Add new books** — full form with image upload (base64 encoded)
- **Manage inventory** — increase/decrease book quantities or delete books entirely
- **Respond to messages** — view open user questions and submit admin responses

---

## 🛠️ Tech Stack

### Backend
| Technology | Purpose |
|---|---|
| **Java 17** | Language runtime |
| **Spring Boot 2.7** | Application framework |
| **Spring Data JPA** | ORM & database access |
| **Spring Data REST** | Auto-generated read-only REST endpoints |
| **Spring Security** | OAuth2 Resource Server with JWT validation |
| **Okta Spring Boot Starter** | Identity provider integration |
| **Lombok** | Boilerplate code reduction |
| **MySQL Connector/J** | Database driver |
| **Google Cloud SQL Socket Factory** | Cloud-native DB connectivity |
| **Maven** | Build tool & dependency management |

### Frontend
| Technology | Purpose |
|---|---|
| **React 19** | UI framework |
| **TypeScript** | Type-safe JavaScript |
| **React Router DOM 5** | Client-side routing |
| **Okta React SDK** | OAuth2/OIDC authentication |
| **Okta Sign-In Widget** | Pre-built login UI |
| **Bootstrap** | Responsive CSS framework |
| **Create React App** | Build tooling |

### Infrastructure
| Technology | Purpose |
|---|---|
| **MySQL 8** | Relational database |
| **Google Cloud Run** | Container hosting (backend & frontend) |
| **Google Cloud SQL** | Managed MySQL instance |
| **Okta** | Identity & access management (OAuth2/OIDC) |

---

## 🚀 Getting Started

### Prerequisites
- **Java 17+** (JDK)
- **Node.js 16+** & **npm**
- **MySQL 8** (local or remote)
- **Okta Developer Account** ([sign up free](https://developer.okta.com/signup/))

### 1. Database Setup

Create the MySQL database and seed it with the starter scripts:

```bash
mysql -u root -p -e "CREATE DATABASE reactlibrarydatabase;"
mysql -u root -p reactlibrarydatabase < starter/Scripts/React-Springboot-Add-Tables-Script-1.sql
mysql -u root -p reactlibrarydatabase < starter/Scripts/React-SpringBoot-Add-Books-Script-2.sql
mysql -u root -p reactlibrarydatabase < starter/Scripts/React-SpringBoot-Add-Books-Script-3.sql
mysql -u root -p reactlibrarydatabase < starter/Scripts/React-SpringBoot-Add-Books-Script-4.sql
mysql -u root -p reactlibrarydatabase < starter/Scripts/React-SpringBoot-Add-Books-Script-5.sql
```

### 2. Okta Configuration

1. Create an Okta application (SPA type) with redirect URI: `http://localhost:3000/login/callback`
2. Add a custom claim `userType` to your authorization server for admin role detection
3. Note your **Client ID** and **Issuer URL**

### 3. Backend Setup

```bash
cd backend/library-project
```

Configure environment variables (or edit `src/main/resources/application.properties`):

```bash
export DB_URL="jdbc:mysql://localhost:3306/reactlibrarydatabase?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC"
export DB_USERNAME="root"
export DB_PASSWORD="your_password"
export OKTA_CLIENT_ID="your_okta_client_id"
export OKTA_ISSUER="https://your-domain.okta.com/oauth2/default"
```

Run the application:

```bash
./mvnw spring-boot:run
```

The backend API will be available at `http://localhost:8080/api/`.

### 4. Frontend Setup

```bash
cd frontend/react-library
npm install
```

Update the API base URL in `src/Constants.ts` to point to your local backend:

```typescript
export const API_BASE_URL = "http://localhost:8080/api/";
```

Update Okta config in `src/lib/oktaConfig.ts` with your credentials.

Start the development server:

```bash
npm start
```

The frontend will be available at `http://localhost:3000`.

---

## 📁 Project Structure

```
library-project/
├── backend/
│   └── library-project/                 # Spring Boot application
│       ├── pom.xml                      # Maven dependencies
│       └── src/main/java/com/dishoo/library_project/
│           ├── config/
│           │   ├── MyDataRestConfig.java      # Spring Data REST config & CORS
│           │   └── SecurityConfiguration.java # OAuth2 JWT security
│           ├── controller/
│           │   ├── AdminController.java       # Book CRUD & quantity management
│           │   ├── BookController.java        # Checkout, return, renew
│           │   ├── HistoryController.java     # Checkout history
│           │   ├── MessagesController.java    # Q&A messaging
│           │   └── ReviewController.java      # Book reviews
│           ├── dao/                           # Spring Data JPA repositories
│           ├── entity/                        # JPA entities (Book, User, Checkout, etc.)
│           ├── filter/
│           │   └── UserAutoRegistrationFilter.java  # Auto-creates users on first auth
│           ├── requestmodels/                 # Incoming DTOs
│           ├── responsemodels/                # Outgoing DTOs
│           ├── service/                       # Business logic layer
│           └── utils/
│               └── JWTExtractor.java          # JWT claim extraction utility
│
├── frontend/
│   └── react-library/                   # React TypeScript application
│       ├── package.json
│       ├── tsconfig.json
│       └── src/
│           ├── App.tsx                        # Routes & Okta Security provider
│           ├── Constants.ts                   # API base URL
│           ├── lib/oktaConfig.ts              # Okta OIDC configuration
│           ├── models/                        # TypeScript data models
│           └── layout/
│               ├── HomePage/                  # Landing page + carousel
│               ├── SearchBookPage/            # Book search & filtering
│               ├── BookCheckoutPage/          # Book details, checkout & reviews
│               ├── ShelfPage/                 # User loans, returns & history
│               ├── ManageLibraryPage/         # Admin panel (CRUD, messaging)
│               ├── MessagesPage/              # User Q&A
│               ├── NavbarAndFooter/           # Global navigation
│               └── Utils/                     # Shared components (pagination, stars, etc.)
│
└── starter/
    ├── application.properties           # Sample backend configuration
    └── Scripts/                          # MySQL schema & seed data (SQL)
```

---

## 🗄️ Data Model

```
┌──────────┐       ┌───────────┐       ┌──────────┐
│   User   │1─────*│  Checkout │*─────1│   Book   │
│──────────│       │───────────│       │──────────│
│ id       │       │ id        │       │ id       │
│ email    │       │ user (FK) │       │ title    │
│ name     │       │ book (FK) │       │ author   │
│ role     │       │ checkoutDt│       │ descript.│
└────┬─────┘       │ returnDate│       │ copies   │
     │             └───────────┘       │ available│
     │                                 │ category │
     │1           ┌───────────┐        │ img      │
     └───────────*│  Review   │*──────1└──────────┘
                  │───────────│
                  │ id        │
                  │ user (FK) │        ┌───────────┐
                  │ book (FK) │        │  History  │
                  │ rating    │        │───────────│
                  │ descript. │        │ id        │
                  │ date      │        │ user (FK) │
                  └───────────┘        │ title     │
                                       │ author    │
┌──────────────┐                       │ checkoutDt│
│   Message    │                       │ returnedDt│
│──────────────│                       └───────────┘
│ id           │
│ userEmail    │
│ title        │
│ question     │
│ adminEmail   │
│ response     │
│ closed       │
└──────────────┘
```

---

## 🔌 API Reference

### Public Endpoints (No Auth)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/books?page=X&size=Y` | List books (paginated) |
| `GET` | `/api/books/{id}` | Get single book |
| `GET` | `/api/books/search/findByTitleContaining?title=` | Search by title |
| `GET` | `/api/books/search/findByCategory?category=` | Filter by category |
| `GET` | `/api/reviews/search/findByBookId?bookId=` | Get reviews for a book |
| `GET` | `/api/histories/search/findBooksByUserEmail?userEmail=` | Get checkout history |

### Authenticated User Endpoints (Bearer Token)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `PUT` | `/api/books/secure/checkout?bookId=` | Checkout a book |
| `PUT` | `/api/books/secure/return?bookId=` | Return a book |
| `PUT` | `/api/books/secure/renew/loan?bookId=` | Renew a loan (+7 days) |
| `GET` | `/api/books/secure/currentloans` | Get user's active loans |
| `GET` | `/api/books/secure/currentloans/count` | Get active loan count |
| `GET` | `/api/books/secure/ischeckedout/byuser?bookId=` | Check if book is checked out |
| `POST` | `/api/reviews/secure` | Submit a review |
| `GET` | `/api/reviews/secure/user/book?bookId=` | Check if user reviewed a book |
| `POST` | `/api/messages/secure/add/message` | Submit a question |

### Admin Endpoints (Bearer Token + `userType: admin`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/admin/secure/add/book` | Add a new book |
| `PUT` | `/api/admin/secure/increase/book/quantity?bookId=` | Increase copies by 1 |
| `PUT` | `/api/admin/secure/decrease/book/quantity?bookId=` | Decrease copies by 1 |
| `DELETE` | `/api/admin/secure/delete/book?bookId=` | Delete a book |
| `PUT` | `/api/messages/secure/admin/message` | Respond to a user message |

---

## 🔐 Authentication & Authorization

The application uses **Okta** as the identity provider with **OAuth2/OIDC** and **JWT** tokens:

- **Public pages** — accessible without authentication
- **User actions** — require a valid JWT (checkout, reviews, messages)
- **Admin actions** — require a JWT with a custom `userType: "admin"` claim
- **Auto-registration** — a servlet filter automatically creates a `User` record in the database on first authentication

---

## 📄 License

This project is for educational and portfolio purposes.

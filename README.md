# 🪶 Quillr

A full-stack blogging platform — register, customize your profile, and create, manage or explore posts written by the community.

Built with Java & Spring Boot on the backend and vanilla HTML/CSS/JavaScript on the frontend.

---

## ✨ Features

- 🔐 User registration and secure login with BCrypt password hashing
- 👤 Automatic profile creation upon registration
- 🎨 Customize profiles with bio, avatar URL, and personal website
- 📝 Complete CRUD operations for posts
- 🔍 Search posts by title across all users
- 📄 Pagination and sorting for posts
- ✅ Input validation with Jakarta Validation
- 📖 Swagger UI for API documentation and testing
- 🌍 CORS configured for local development and GitHub Pages

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5 |
| Database | PostgreSQL |
| Database Mapper | Spring Data JDBC |
| Security | Spring Security + BCrypt |
| Validation | Jakarta Validation |
| API Docs | Swagger UI (SpringDoc OpenAPI) |
| Utilities | Lombok |
| Build Tool | Maven |
| Frontend | HTML, CSS, JavaScript (Vanilla) |

---

## 📁 Project Structure

```
Quillr/
├── src/main/java/com/narcis/quillr/
│   ├── controller/
│   │   ├── UserController.java       ← /api/users
│   │   ├── ProfileController.java    ← /api/profile
│   │   └── PostController.java       ← /api/posts
│   ├── service/
│   │   ├── UserService.java
│   │   ├── ProfileService.java
│   │   └── PostService.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── ProfileRepository.java
│   │   └── PostRepository.java
│   ├── model/
│   │   ├── User.java
│   │   ├── Profile.java
│   │   └── Post.java
│   ├── SecurityConfig.java
│   └── QuillrApplication.java
└── frontend/
    ├── index.html      ← Login + Register (card swap animation)
    └── home.html       ← Dashboard with editor and post list
```

---

## 🔌 API Endpoints

### Users
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users/register` | Register a new user |
| POST | `/api/users/login` | Login |
| GET | `/api/users/{id}` | Get user by ID |

### Profiles
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/profile/{userId}` | Get user profile |
| PUT | `/api/profile/{userId}` | Update user profile |

### Posts
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/posts/create` | Create a new post |
| GET | `/api/posts/` | Get all posts |
| GET | `/api/posts/{id}` | Get post by ID |
| GET | `/api/posts/user/{userId}` | Get all posts by a user |
| GET | `/api/posts/search?query=` | Search posts by title |
| GET | `/api/posts/paged?page=0&size=10&sortBy=createdAt` | Get posts paginated |
| GET | `/api/posts/user/{userId}/paged` | Get user posts paginated |
| PUT | `/api/posts/{id}` | Update a post |
| DELETE | `/api/posts/{id}` | Delete a post |

---

## 📖 API Documentation

Swagger UI is available at:
```
http://localhost:8081/swagger-ui/index.html
```

---

## 🗄️ Database Schema

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE profile (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) UNIQUE,
    bio TEXT,
    avatar_url VARCHAR(255),
    website VARCHAR(255)
);
```

---

## 🚀 Setup & Installation

### Prerequisites
- Java 21
- PostgreSQL
- Maven

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/Narcis47/Quillr-Full-Stack-Blogging-Application.git
cd Quillr-Full-Stack-Blogging-Application
```

**2. Create the PostgreSQL database**
```sql
CREATE DATABASE quillr;
```

**3. Run the schema** (copy the SQL from above into your database client)

**4. Set environment variables**

In IntelliJ → Run/Debug Configurations → Environment Variables:
```
DB_USERNAME=your_postgres_username
DB_PASSWORD=your_postgres_password
```

Or in PowerShell:
```powershell
$env:DB_USERNAME="your_postgres_username"
$env:DB_PASSWORD="your_postgres_password"
```

**5. Run the backend**
```bash
./mvnw spring-boot:run
```

The API runs at `http://localhost:8081`
Swagger UI at `http://localhost:8081/swagger-ui/index.html`

---

## 📝 Example API Requests

**Register**
```json
POST /api/users/register
{
    "username": "narcis",
    "email": "narcis@example.com",
    "password": "securepassword"
}
```

**Login**
```json
POST /api/users/login
{
    "email": "narcis@example.com",
    "password": "securepassword"
}
```

**Create Post**
```json
POST /api/posts/create
{
    "userId": 1,
    "title": "My First Post on Quillr",
    "content": "Hello world! This is my first entry."
}
```

**Search Posts**
```
GET /api/posts/search?query=Death Note
```

**Get Posts Paginated**
```
GET /api/posts/paged?page=0&size=10&sortBy=createdAt
```

**Update Profile**
```json
PUT /api/profile/1
{
    "bio": "Software Developer & Writer",
    "avatarUrl": "https://example.com/avatar.png",
    "website": "https://narcis47.github.io"
}
```

---

## 👤 Author

**Narcis** — [@Narcis47](https://github.com/Narcis47)
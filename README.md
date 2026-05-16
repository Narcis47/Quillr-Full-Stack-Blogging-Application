# 🪶 Quillr API

A robust RESTful API backend for a blogging or social platform — register users, customize personal profiles, and create, manage, or explore posts.

Built with Java & Spring Boot, utilizing Spring Security for authentication and Spring Data Relational for database interactions.


---

## ✨ Features

- 🔐 User registration and secure login with BCrypt password hashing
- 👤 Automatic profile creation upon registration
- 🎨 Customize profiles with bio, avatar URL, and personal website links
- 📝 Complete CRUD operations for posts (Create, Read, Update, Delete)
- 🔍 Fetch posts globally or filter them by specific users
- 🌍 Pre-configured CORS for local development and GitHub Pages deployment

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Database Mapper | Spring Data Relational |
| Security | Spring Security + BCrypt |
| HTTP Client | RestTemplate |
| Utilities | Lombok |
| Build Tool | Maven / Gradle |

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
│   ├── SecurityConfig.java           ← CORS & CSRF configuration
│   └── QuillrApplication.java

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
| GET | `/api/profile/{userId}` | Get user profile by User ID |
| PUT | `/api/profile/{userId}` | Update user profile |

### Posts
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/posts/create` | Create a new post |
| GET | `/api/posts/` | Get all posts |
| GET | `/api/posts/{id}` | Get post by ID |
| GET | `/api/posts/user/{userId}`| Get all posts by a specific user |
| PUT | `/api/posts/{id}` | Update an existing post |
| DELETE | `/api/posts/{id}` | Delete a post |

---

## 🗄️ Database Schema

```sql
CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE posts(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE profile(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) UNIQUE ,
    bio TEXT,
    avatar_url VARCHAR(255),
    website VARCHAR(255)
);

```
## 🚀 Setup & Installation

### Prerequisites
- Java 21
- PostgreSQL
- Maven

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/Narcis47/Quillr-Full-Stack-Blogging-Application
cd GameLogged
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

**Update Profile**

```json
PUT /api/profile/1
{
  "bio": "Software Developer & Writer",
  "avatarUrl": "[https://imgur.com/myavatar.png](https://imgur.com/myavatar.png)",
  "website": "[https://narcis47.github.io](https://narcis47.github.io)"
}
```

**Create Post**

```json
POST /api/posts/create
{
  "userId": 1,
  "title": "My First Post on Quillr",
  "content": "Hello world! This is my first entry on this platform."
}
```

---

## 👤 Author

**Narcis** — [@Narcis47](https://github.com/Narcis47)
# Blogging Platform API

## Description

This project implements a **RESTful API** for managing a simple blogging platform. The API allows for managing blog posts and their associated comments. The core functionality includes:

- **Creating blog posts**.
- **Retrieving blog posts**.
- **Adding comments to blog posts**.
- **Retrieving specific blog posts with comments**.

The API has endpoints to manage these actions in a clean and simple way using Spring Boot.

### API Endpoints

#### 1. **GET /api/posts**
- **Description**: Retrieve a list of all blog posts.
- **Response**: A list of blog posts, including their titles and the number of comments associated with each post.
- **Example Response**:
  ```json
  [
    {
      "id": 1,
      "title": "Blog Post 1",
      "commentsCount": 2
    },
    {
      "id": 2,
      "title": "Blog Post 2",
      "commentsCount": 0
    }
  ]
  ```

#### 2. **POST /api/posts**
- **Description**: Create a new blog post.
- **Request Body**:
  ```json
  {
    "title": "New Blog Post Title",
    "content": "Content of the blog post."
  }
  ```
- **Response**: The created blog post, including its `id`, `title`, and `content`.
- **Example Response**:
  ```json
  {
    "id": 3,
    "title": "New Blog Post Title",
    "content": "Content of the blog post."
  }
  ```

#### 3. **GET /api/posts/{id}**
- **Description**: Retrieve a specific blog post by its ID, including its title, content, and a list of associated comments.
- **Example Response**:
  ```json
  {
    "id": 1,
    "title": "Blog Post 1",
    "content": "Content of Blog Post 1",
    "comments": [
      {
        "author": "Author 1",
        "content": "This is a comment"
      },
      {
        "author": "Author 2",
        "content": "This is another comment"
      }
    ]
  }
  ```

#### 4. **POST /api/posts/{id}/comments**
- **Description**: Add a new comment to a specific blog post.
- **Request Body**:
  ```json
  {
    "author": "Sample Author",
    "content": "This is a comment."
  }
  ```
- **Response**: The created comment with `author` and `content`.
- **Example Response**:
  ```json
  {
    "author": "Sample Author",
    "content": "This is a comment."
  }
  ```

---

## Technologies Used

- **Java 11+**
- **Spring Boot** for building the REST API
- **Spring Data JPA** for database operations
- **H2 Database** for in-memory database
- **Maven** for build and dependency management

---

## Setup and Run

### Prerequisites

- JDK 11 or higher
- Maven

### 1. Clone the Repository

```bash
git clone https://github.com/bolds07/blog.git
cd blog
```
### 2. Run tests
```bash
mvn test
```

### 3. Run the application
```bash
cd built
java -jar java-blog-application-1.0.0.jar
```

### 4. Run the application on debug mode
```bash
cd built
java -jar java-blog-application-1.0.0.jar --spring.profiles.active=dev
```


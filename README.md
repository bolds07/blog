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
- **H2 Database**  
For the project's purpose I found simpler to configure the database in-memory, to have a persistent database you just need to set the `spring.datasource.url=jdbc:h2:file:./data/testdb` in the `application.properties`
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

## Possible upgrades
Considering the purpose of this project I dedicated no more than 4 hours for its execution, therefore there are multiple optimizations possible for this project

### 1. Creation of a CI/CD pipeline
#### A) Automated tests
Any production-level project should have at least one pipeline to run automated tests and guarantee that commited code performs as intended.

#### B) Build and publish Docker image
I took the liberty of creating a `Dockerfile` for this project, which means this project is ready to be containerized. Thus, a pipeline that automates the build and publishing of a Docker image for this project would be a great extension, allowing fast deployment to a production server.

### 2. Use Spring Security for access control
In the current form, anyone can post, comment, and read the posts in the blog. By taking advantage of Spring Security, it would be easy to add an access control layer to better manage the privacy of the API data.  
I took the liberty of adding the autoditory fields to the domain classes, and by adding the necessary Spring Security configurations, the blog posts and comments would have their respective fields automatically filled.

### 3. Use Spring Actuator for api health status
I took liberty to include `spring-boot-starter-actuator` as the project's dependency, then spring will automatically populate a set of metrics for monitory the system's health at `http://localhost/actuator/`.  
However I didn't create any specific metric to monitore the api, hence the creation of timers and counters would allow the devops team to continously monitore the system's performance and health 

### 4. Pagination control
To have an endpoint that allows anyone to completely dump the API's database with one request is a huge risk to any system, to prevent this I included in the `application.propeties` file the fields:  
* spring.data.web.pageable.default-page-size=25
* spring.data.web.pageable.max-page-size=100

this will make any request to the `/api/posts` return at maximum 100 items per page, any request without page specification will return 25 items, users can pagenating by using the query params: `page`, `size` and `sort`.  
A possible improvement would be add a similar mechnism to the `/api/posts/{id}` considering that one blog post can reach thousands of comments, it would be safer to make user paginate accross the comment list

### 5. Logging
It would be important to add log records to monitor and diagnose the system's behavior, in the current state any bug undetected in the tests will have very little rastreability... logs would help to detect and find the root cause of eventual bugs
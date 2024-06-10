# Online Book Store

## Introduction

Welcome to the "Online Book Store" repository! This project was inspired by the need to facilitate the online purchase of books. With this application, users can explore a wide range of books, choose their favorites, and order them just like in a library. Whether you're a developer looking for a practical implementation of Spring technologies or a user needing a specific functionality for creating and supporting an online library, this project aims to provide a robust solution.

## Technologies Used

- **Spring Boot**: For creating a stand-alone, production-grade Spring-based application.
- **Spring Security**: To handle authentication and authorization.
- **Spring Data JPA**: For simplified data access and management.
- **Swagger**: For API documentation and testing.
- **Maven**: For project build and dependency management.
- **Docker**: To containerize the application, ensuring consistency across different environments and simplifying deployment.

## Project Structure and Functionality

The project is organized into several key components, each responsible for specific functionalities:

### Controllers

- **AuthController**: Manages user-related operations such as registration, login, and profile management.
- **BookController**: Manages book processing, including book creation and updating for admins, and viewing for users.
- **OrderController**: Manages order processing, including order creation, updating, and viewing order history.
- **CategoryController**: Manages category processing, including category creation, updating, and viewing.
- **ShoppingCartController**: Manages shopping cart processing, including creation, updating, and viewing items (books and other products).

### Services

- **OrderService**: Manages the order processing logic, including placing orders in the database, retrieving order history, and calculating the total price of all items.
- **BookService**: Manages the book processing logic, including book management for admins and book retrieval for users.
- **CategoryService**: Manages category processing logic, including category management for admins and category retrieval for users.
- **ShoppingCartService**: Handles the business logic related to shopping carts, including adding items and updating the cart.
- **UserService**: Contains business logic for user operations such as registration and user retrieval by username (email).

### Repositories

- **BookRepository**: Interface for CRUD operations on Book entities and special methods for finding books by category.
- **ShoppingCartRepository**: Interface for CRUD operations on ShoppingCart entities and methods for identifying a shopping cart by user ID.
- **CartItemRepository**: Interface for CRUD operations on CartItem entities.
- **OrderRepository**: Interface for CRUD operations on Order entities.
- **OrderItemRepository**: Interface for CRUD operations on OrderItem entities and methods for retrieving order items by order ID.
- **UserRepository**: Interface for CRUD operations on User entities and methods for checking if the user is already registered.
- **RoleRepository**: Interface for CRUD operations on Role entities, including methods for identifying user roles.

## Setup and Usage

To get started with this project, follow these steps:

1. **Clone the repository**:
    ```sh
    git clone https://github.com/bodya4243/online-book-store.git
    cd online-book-store
    ```

2. **Build the project using Maven**:
    ```sh
    mvn clean install
    ```

3. **Run the application**:
    ```sh
    mvn spring-boot:run
    ```
   
4. **Access the application**:
   - The application runs on: http://localhost:8080.
   - API documentation is available at: http://localhost:8080/swagger-ui.html.

### Docker Setup

**Swagger**:
   - You can use Swagger for manipulating API endpoints.

**Docker**:
   - You can download the container from Docker and start the API.

### environment variables
```
   # MySQL environment variables
   DB_PASSWORD=<your-db-password>
   DB_DATABASE=<your-db-name>
   DB_USER=<your-db-username>
   DB_LOCAL_PORT=3309
   DB_DOCKER_PORT=3306
   
   # Spring Boot application environment variables
   SPRING_LOCAL_PORT=8080
   SPRING_DOCKER_PORT=8080
   
   # Debug port
   DEBUG_PORT=5005
   
   # JWT secret key
   JWT_SECRET=<your-jwt-secret>
   
   MYSQL_DATABASE=<your-db-name>
   MYSQL_USER=<your-db-username>
   MYSQL_PASSWORD=<your-db-password>
```

1. **Build the Docker image**:
    ```sh
    docker-compose up --build -d
    ```

2. **Run the Docker container**:
    ```sh
    docker run -d -p 8080:8080 online-book-store
    ```

3. **Verify the container is running**:
    - Ensure the application is accessible at `http://localhost:8080`.

4. **Stopping the Services**:
   ```sh
    docker docker-compose down
    ```

* How It Works
The application uses JWT (JSON Web Token) for authentication and authorization.
When a user logs in, they receive a JWT token,
which must be included in the Authorization header of subsequent requests to access protected endpoints.

* User Registration
To register a new user, send a request to `POST /api/auth/registration` with the following JSON payload:

```json
   {
      "email": "tom.doe@example.com",
      "password": "securePassword123",
      "repeatPassword": "securePassword123",
      "firstName": "alice",
      "lastName": "Doe",
      "shippingAddress": "Kyiv, Shevchenko ave, 1"
   }
```

* User Login
  To login and receive a JWT token, send a `POST request to /api/auth/login` with the following JSON payload:

```json
   {
      "Username": "bob@example.com",
      "Password": "secure123"
   }
```

* The response will include a JWT token:

```json
   {
      "token": "your-jwt-token"
   }
```

* Admin Login
Use the following admin credentials to log in and test admin-only endpoints:

Username: `bob@example.com`
Password: `secure123`

* Include the JWT token in the Authorization header of your requests: `Authorization: Bearer your-jwt-token`
   
## Postman Collection

A collection of Postman requests has been created to help you test the APIs quickly. You can import the collection into Postman by following these steps:

1. Download the Postman collection file from https://api-test-3019.postman.co/workspace/API-test-Workspace~ec66d126-9a9d-4998-b0f6-ead375ba4d08/collection/34446571-b73cd808-c476-4548-b479-0cbe00e822da?action=share&creator=34446571.

This collection includes all the necessary requests to test user registration, login, product management, and order processing.

## Challenges and Solutions

Throughout the development of this project, several challenges were encountered and successfully overcome:

- **Authentication and Authorization**: Implementing a robust security layer using Spring Security required careful configuration and testing. By utilizing JWT tokens and configuring role-based access, a secure environment was established.
- **Data Persistence**: Ensuring seamless data operations with Spring Data JPA required understanding and implementing the correct JPA annotations and query methods.
- **API Documentation**: Integrating Swagger for API documentation and testing provided clarity and ease of use for developers interacting with the APIs.

## Conclusion

This project not only demonstrates the practical application of various Spring technologies but also showcases the ability to develop a full-fledged application from scratch. The detailed documentation, robust functionality, and thoughtful design aim to provide a valuable resource for both developers and users.

Feel free to explore the code, test the APIs, and contribute to the project. Your feedback and suggestions are highly appreciated!

---

Thank you for taking the time to review this project. If you have any questions or need further assistance, please do not hesitate to reach out.

Happy coding!

Bohdan Bohush

[linkedin](http://www.linkedin.com/in/bohdan-bohush-a2936b301)

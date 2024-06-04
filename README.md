# eCommerce API 

Spring Boot application that provides a CRUD API for eCommerce items (products, categories and wishlists) with authentication and password reset functionality.

### Getting Started

To start the application, run the following command in the root directory of the project:

```
./mvnw spring-boot:run
```

The application will start and listen on port 8080.

### API Documentation

The API documentation is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) after the application is started. It provides detailed information about the available endpoints, their request parameters, and response formats. 

### Database Configuration

The application uses an PostgreSQL database. The database configuration is located in the `application.properties` file. You can configure the database URL, username, and password.

The application uses Flyway to manage database migrations. The SQL scripts for creating the tables are located in the `src/main/resources/db/migration` directory.

### Items API

The application provides the following endpoints for managing items for an eCommerce store:

- `/products`: This endpoint is used to create, read, update, and delete products. Only admin users can create, update, and delete products. Regular users can only read products.
- `/categories`: This endpoint is used to create, read, update, and delete categories. Only admin users can create, update, and delete categories. Regular users can only read categories.
- `/wishlists`: This endpoint is used to create, read, update, and delete wishlists. Regular users can only create, read, update, and delete their own wishlists.

### Authentication

Authentication is handled by the `AuthenticationService` class. It uses Spring Security and JWT for authentication. The JWT secret and expiration time can be configured in the `application.properties` file. 

The application provides the following endpoints for authentication:

- `/auth/signup`: This endpoint is used to create a new user. It accepts a `RegistrationDTO` object containing the user's profile information (more details in the Swagger documentation).
-  `/auth/verify-account`: This endpoint is used to verify a user's account. It accepts a the user's email and verification token (sent to the user's email after registration).
- `/auth/login`: This endpoint is used to authenticate a user. It accepts a `AuthenticationDTO` object containing the user's login and password. If the authentication is successful, it returns a JWT token that can be used to access protected endpoints.

The application provides two types of users: admin and regular users. Admin users have full access to all endpoints, while regular users have limited access to certain endpoints.

The authentication process involves creating a user account, verifying the account, and then logging in to obtain a JWT token.

### Password Reset

The password reset functionality is provided by the `PasswordResetService` class. It provides two endpoints:

- `/password/request-reset`: This endpoint is used to request a password reset. It accepts a `PasswordResetRequestDTO` object which contains the email of the user who wants to reset their password.
  
- `/password/reset`: This endpoint is used to reset the password.  It receives the user's email, a password reset token, and a `PasswordResetDTO` containing the user's new password.

The password reset process involves generating an OTP, sending it to the user's email, and then validating the OTP when the user attempts to reset their password.

### Email Configuration

The application uses SMTP to send emails. The SMTP server configuration is located in the `application.properties` file. You can configure the SMTP server host, port, username, and password.

### Testing

The application includes unit tests. To run the tests, use the following command:

```
./mvnw test
```

### Building

To build the application, use the following command:

```
./mvnw package
```

This will create a .jar file in the `target` directory.

### Contributing and Next Steps

Contributions are welcome. Please make sure to update tests as appropriate.

Next steps for the application include:

- Add functionality to allow users to manager their own orders.
- Payment gateway integration.

### License

MIT

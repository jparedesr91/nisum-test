# Nisum Spring Boot JWT

![](https://img.shields.io/badge/build-success-brightgreen.svg)

# Stack

![](https://img.shields.io/badge/java_8-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/h2-✓-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)
![](https://img.shields.io/badge/swagger_2-✓-blue.svg)

***
# File structure

```
 ms-user/
 │
 ├── src/main/java/
 │   └── com.nisum.interviewtest.user
 │       ├── configuration
 │       │   └── SwaggerConfig.java
 │       │
 │       ├── controller
 │       │   └── UserController.java
 │       │
 │       ├── dto
 │       │   ├── PhoneDTO.java
 |       |   ├── UserDataDTO.java 
 │       │   └── UserResponseDTO.java
 │       │
 │       ├── errors
 │       │   ├── ApiError.java
 │       │   ├── CustomAccessDeniedHandler.java
 │       │   ├── CustomAuthenticationEntryPoint.java
 │       │   ├── EntityNotFoundException.java
 │       │   ├── ExistingValueException.java
 │       │   ├── InvalidTokenException.java
 │       │   ├── InvalidUserPasswordException.java
 │       │   ├── RestExceptionHandler.java
 │       │   └── LoweCaseClassNameResolver.java
 │       │
 │       ├── mapping
 │       │   └── UserMap.java 
 │       │ 
 │       ├── model
 │       │   ├── Phone.java
 │       │   ├── Role.java
 │       │   ├── User.java 
 │       │   └── UserTokenSession.java
 │       │
 │       ├── repository
 │       │   ├── UserRepository.java 
 │       │   └── UserTokenSessionRepository.java
 │       │
 │       ├── security
 │       │   ├── JwtTokenFilter.java
 │       │   ├── JwtTokenFilterConfigurer.java
 │       │   ├── JwtTokenProvider.java
 │       │   ├── UserDetailsUtil.java
 │       │   └── WebSecurityConfig.java
 │       │
 │       ├── service
 │       │   ├── UserService.java   
 │       │   ├── UserTokenSessionService.java 
 │       │   └── UserTokenSessionServiceImpl.java
 │       │
 │       ├── util   
 │       │   └── Util.java
 │       └── MsUserApplication.java
 │
 └── src/main/resources/
     └── application.yml

```
# How to use this code?

1. Make sure you have [Java 8](https://www.java.com/download/) and [Gradle](https://gradle.org/) installed

2. Fork this repository and clone it

```
$ git clone https://github.com/jparedesr91/nisum-test
```

3. Navigate into the folder

```
$ cd nisum-test/ms-user
```

4. Install dependencies

```
$ ./gradlew clean build
```

5. Run the project

```
$ ./gradlew bootRun
```

6. Navigate to `http://localhost:8081/swagger-ui.html` in your browser to check everything is working correctly. You can change the default port in the `application.yml` file

```yml
server:
  port: 8081
```

7. Make a GET request to `/users/me` to check you're not authenticated. You should receive a response with a `403` with an `Access Denied` message since you haven't set your valid JWT token yet

![401](https://github.com/jparedesr91/nisum-test/blob/main/user_me_401.png?raw=true "401")

8. Make a POST request to `/users/signup` to create a new user and get JWT token

![Create-User](https://github.com/jparedesr91/nisum-test/blob/main/create_user.png?raw=true "Create User")
![Created-User](https://github.com/jparedesr91/nisum-test/blob/main/created_user.png?raw=true "Created User")

9. Add the JWT token as a Header parameter and make the initial GET request to `/users/me` again

![Bearer Token](https://github.com/jparedesr91/nisum-test/blob/main/header.png?raw=true "Bearer Token")


10. And that's it, congrats! You should get a similar response to this one, meaning that you're now authenticated

![UserMe Response](https://github.com/jparedesr91/nisum-test/blob/main/user_me_response.png?raw=true "UserMe Response")


# Implementation Details

## H2 DB

This test is currently using an H2 database called **test_db** so you can run it quickly and out-of-the-box without much configuration. If you want to connect to a different database you have to specify the connection in the `application.yml` file inside the resource directory. Note that `hibernate.hbm2ddl.auto=create-drop` will drop and create a clean database each time we deploy:

```yml
spring:
  datasource:
    url: jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE   
    username: root
    password: root
  tomcat:
    max-wait: 20000
    max-active: 50
    max-idle: 20
    min-idle: 15
  jpa:
    hibernate:
      ddl-auto: create-drop
    properties:
      hibernate:
        dialect: org.hibernate.dialect.H2Dialect        
        format_sql: true
        id:
          new_generator_mappings: false
```

## ER Diagram

![ER-Diagram](https://github.com/jparedesr91/nisum-test/blob/main/er-diagram.png?raw=true "ER Diagram")






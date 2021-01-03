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
$ cd ms-user
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

```
$ curl -X GET http://localhost:8081/users/me
```

8. Make a POST request to `/users/signin` with the default admin user we programatically created to get a valid JWT token

```
$ curl -X POST 'http://localhost:8081/users/signin?username=admin&password=admin'
```

9. Add the JWT token as a Header parameter and make the initial GET request to `/users/me` again

```
$ curl -X GET http://localhost:8081/users/me -H 'Authorization: Bearer <JWT_TOKEN>'
```

10. And that's it, congrats! You should get a similar response to this one, meaning that you're now authenticated

```javascript
{
  "id": 1,
  "isactive": false,
  "created": "2020-10-19T03:19:24.91",
  "modified": "2020-10-19T03:19:24.91",
  "last_login": "2020-10-19T03:19:24.91",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqcGFyZWRlc0BnbWFpbC5jb20iLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE2MDMwOTE5NjUsImV4cCI6MTYwMzA5MTk2NX0.UkMrKsp5QEomnMBG0DslNM3SKtLMfrxreLFBMoeg07E"
}
```

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

![Alt text](file:///home/julio/Pictures/Screenshot%20from%202021-01-03%2013-38-03.png?raw=true "ER Diagram")






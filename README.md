# CookFoodAI #

### About ###
The use of AI in e-commerce in a food store

** IN PROGRESS  **

Start project: 01.03.2024

# CookFoodAI Backend service

[URL] [http://localhost:5000/api/]()

- [Java 17+]
- [Maven 3+]
- [Spring Boot 3.x]
- [Spring Security 6.x]
- [Hibernate]
- [JPA]
- [OpenAPI Swagger]
- [JWT]
- [Mockito 5.x] & [jUnit  5] TO DO
- [Spring AI] TO DO [https://docs.spring.io/spring-ai/reference/index.html]() 

## Configuration for Application

A sample configuration for an `application.properties` file to run this program is:

** properties :** [./src/main/resources/application.properties]()

```properties
# OpenAI key
openai.api.key=your open_AI key

# Security JWT Token key  # Encode to Base64  (HS256  RFC 7518)
application.security.jwt.secret-key=your security key with encode to Base64 
```

## API ##
[Swagger UI]  [http://localhost:5000/swagger-ui/index.html]()

[Request :]  
Content-Type: application/json
Authorization: Bearer {{acctoken}}

{
"firstname": "Kylie",
"lastname": "Rey",
"email": "Kylie.Rey@exaple.com",
"password": "#34pc3RyYXRvciJdfQ.f"
}

{
"email": "Kylie.Rey@exaple.com",
"roles":USER,
"access_token": "eyJhbGciOiJ9.iIxMjM0NTY3ODkwIiwibmFt.JV_adQssw5c",
"refresh_token":"eyJhbGciOiJ9.iIxMjM0NTY3ODkwIiwibmFt.5MDIssw5c",
}


![console](./doc/swagger.png)

## Database ###
[http://localhost:5000/h2-console]()

**URL:** [jdbc:h2:mem:testdb;OLD_INFORMATION_SCHEMA=TRUE;DATABASE_TO_UPPER=false;]()

**User**:sa

**pass**:sa

## To build and run the project
```
mvn clean install
```

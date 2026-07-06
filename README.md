# Hibernate Practical - Spring Security

## M9: Spring Security

### What was done

- Added `spring-boot-starter-security` to pom.xml
- Created `SecurityConfig` with a `SecurityFilterChain` bean
- Created `UserConfig` with two users (BCrypt encoded passwords):
    - `user` / `user` with role `USER`
    - `admin` / `admin` with role `ADMIN`
- Configured endpoint protection:
    - `GET /api/books/**` -> any authenticated user
    - `POST /api/books` -> ADMIN only
    - `PUT /api/books/**` -> ADMIN only
    - `DELETE /api/books/**` -> ADMIN only

### Testing

Behaviour was verified manually through Postman (Basic Auth), and also with automated requests in `src/test/java/bootcamp/hibernate_practical/httptest/requests.http` (IntelliJ HTTP Client).

Verified scenarios:
- GET without auth -> 401
- GET with user/user -> 200
- GET with admin/admin -> 200
- POST with user/user -> 403
- POST with admin/admin -> 201
- PUT with user/user -> 403
- DELETE with user/user -> 403
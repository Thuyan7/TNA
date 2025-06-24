#  TNA 

TNA là hệ thống quản lý được phát triển bằng **Spring Boot**. Đây là phần backend xử lý các API, xác thực, và kết nối cơ sở dữ liệu.

---

## Công nghệ sử dụng

- **Java 17+**
- **Spring Boot**
- **Spring Web**
- **Spring Security**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Maven**

---

##  Cấu trúc thư mục chính
TNA/
└── backend/
├── pom.xml
├── src/
│ ├── main/
│ │ ├── java/com/example/backend/
│ │ │ ├── controller/
│ │ │ ├── dto/
│ │ │ ├── entity/
│ │ │ ├── repository/
│ │ │ ├── security/
│ │ │ └── BackendApplication.java
│ │ └── resources/
│ │ └── application.properties
└── .mvn/ (wrapper)

---

## Cài đặt và chạy dự án

### 1. Yêu cầu hệ thống

- Java JDK 17 hoặc cao hơn
- Maven 3.6+
- MySQL 

### 2. Cấu hình CSDL

Sửa file application.properties (hoặc application.yml) để trỏ đúng đến CSDL của bạn:

spring.datasource.url=jdbc:mysql://localhost:3306/tna

spring.datasource.username=root

spring.datasource.password=1234

### 3. Chạy ứng dụng
./mvnw spring-boot:run
hoặc dùng IDE như IntelliJ để chạy BackendApplication.java.

### Tác giả: ThuyAn




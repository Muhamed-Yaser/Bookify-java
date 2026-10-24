# 📚 Bookify (Spring MVC Bookstore Application)

A feature-rich, web-based bookstore application built using **Java** and the **Spring MVC** framework, designed with a clean architectural pattern to manage book inventories, authors, categories, and user interactions seamlessly.

---

## 🚀 Key Features

* **MVC Architecture:** Strict separation of concerns across Models, Views, and Controllers for maintainable and scalable code.
* **Full CRUD Operations:** Comprehensive management capabilities for Books, Authors, Categories, and Book Details.
* **Relational Database Mapping:** Complex entity relationships using JPA and Hibernate (One-to-One, Many-to-One, and Many-to-Many).
* **Search & Filter:** Browse through a rich catalog of books sorted by categories and authors.
* **Server-Side Validation:** Ensures data integrity and secure inputs before processing requests.
* **Responsive UI:** Clean and intuitive user interface built with modern web styling.

---

## 🛠️ Tech Stack

* **Java**
* **Spring Framework (Spring MVC & Spring Data JPA)**
* **Hibernate ORM**
* **MySQL / Relational Database**
* **Maven** (Dependency Management)
* **JSP / Thymeleaf & Bootstrap** (Frontend Views)

---

## 📂 Project Structure

src/main/java/com/
│
├── controller/        # Spring MVC Controllers managing web requests and views
├── model/             # JPA Entities representing database tables
├── repository/        # Spring Data JPA interfaces for database operations
└── exception/         # Custom exception handlers
⚙️ Getting Started
Prerequisites
Java JDK 17+ installed.

Maven installed.

A relational database (e.g., MySQL) configured.

Installation & Running
Clone the repository:


git clone [https://github.com/Muhamed-Yaser/Bookify-java.git](https://github.com/Muhamed-Yaser/Bookify-java.git)
cd Bookify-java
Configure your database connection in src/main/resources/application.properties (or application.yml):

Properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookify_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
Build and run the project using Maven:


mvn clean spring-boot:run

👨‍💻 Author
Muhammad Yasser

Backend Software Developer

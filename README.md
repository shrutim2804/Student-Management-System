# Student Management System

A complete Student Management System built with Java Swing, JDBC, and MySQL.

## Features
- Admin Authentication with database validation
- Complete Student CRUD Operations
- Real-time Search by ID or Name
- Dashboard with Statistics
- CSV Export functionality
- Input Validation (Email, Phone, ID format)
- Professional Modern UI

## Technologies Used
- Java 11+
- Swing GUI framework
- JDBC for database connectivity
- MySQL 8.0
- MVC Architecture
- DAO Pattern

## Quick Setup

### Prerequisites
- Java JDK 11 or higher
- MySQL 8.0
- MySQL Connector/J (included in lib folder)

### Database Setup
1. Start MySQL service
2. Login to MySQL:
   mysql -u root -p
3. Create database:
   CREATE DATABASE student_management_system;
   USE student_management_system;
4. Import schema:
   SOURCE database/schema.sql;

### Running the Application
Compile and run using the commands in the project.

## Default Login
- Username: admin
- Password: admin123

## Author
Your Name

## License
Educational Purpose Only

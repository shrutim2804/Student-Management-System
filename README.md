# 🎓 Student Management System

A complete, production-ready Student Management System built with **Java Swing**, **JDBC**, and **MySQL**. Perfect for educational institutions to manage student records efficiently.

[![Java](https://img.shields.io/badge/Java-11%2B-blue.svg)](https://www.java.com/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)](https://www.mysql.com/)
[![Swing](https://img.shields.io/badge/GUI-Swing-green.svg)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![License](https://img.shields.io/badge/License-Educational-red.svg)](LICENSE)

---

## 📋 Table of Contents
- [Features](#-features)
- [Technologies Used](#-technologies-used)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
- [Database Setup](#-database-setup)
- [Running the Application](#-running-the-application)
- [Default Login](#-default-login)
- [Screenshots](#-screenshots)
- [Usage Guide](#-usage-guide)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)
- [Contact](#-contact)

---

## ✨ Features

### 🔐 Authentication Module
- Secure admin login with MySQL database validation
- Password protection and error handling
- Session management

### 📝 Student Management
- **Add Student** - Register new students with complete details
- **Update Student** - Modify existing student information
- **Delete Student** - Remove student records with confirmation dialog
- **View All Students** - Display students in sortable JTable
- **Search Students** - Search by ID, Name, or Email

### 📊 Dashboard
- Total students count
- Course-wise distribution
- Department statistics
- Recent student registrations
- Interactive navigation menu

### ✅ Validation Features
- Email format validation
- Phone number validation (10 digits, starts with 6-9)
- Empty field validation
- Duplicate Student ID prevention
- Semester range validation (1-8)

### 📎 Additional Features
- **CSV Export** - Export student data to CSV files
- **Sorting** - Sort students by name, course, or department
- **Search & Filter** - Real-time search functionality
- **Reports** - Generate student reports

### 🎨 User Interface
- Modern, professional Swing UI
- Consistent color theme
- Responsive layout using GridBagLayout
- Button hover effects
- Professional icons and styling

---

## 🛠 Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 11+ | Core programming language |
| Swing | - | GUI framework |
| JDBC | - | Database connectivity |
| MySQL | 8.0 | Database management |
| OOP | - | Object-oriented design patterns |

### Design Patterns Implemented
- **MVC Pattern** - Model-View-Controller architecture
- **DAO Pattern** - Data Access Objects for database operations
- **Singleton Pattern** - Database connection management
- **Service Layer Pattern** - Business logic separation

---

## 📁 Project Structure

```
Student-Management-System/
│
├── src/
│   ├── database/
│   │   └── DatabaseConnection.java      # Database connection handler
│   │
│   ├── model/
│   │   └── Student.java                  # Student entity/model
│   │
│   ├── dao/
│   │   ├── AdminDAO.java                 # Admin data access
│   │   └── StudentDAO.java               # Student CRUD operations
│   │
│   ├── service/
│   │   ├── StudentService.java           # Business logic
│   │   └── ValidationService.java        # Input validation
│   │
│   ├── ui/
│   │   ├── LoginUI.java                  # Login screen
│   │   ├── DashboardUI.java              # Main dashboard
│   │   ├── StudentManagementUI.java      # Student management interface
│   │   └── AddEditStudentUI.java         # Add/Edit student form
│   │
│   └── Main.java                         # Application entry point
│
├── database/
│   └── schema.sql                        # Database schema and sample data
│
├── lib/
│   └── mysql-connector-j-9.7.0.jar      # MySQL JDBC driver
│
├── README.md                              # Project documentation
└── .gitignore                             # Git ignore file
```

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- [Java JDK 11 or higher](https://www.oracle.com/java/technologies/downloads/)
- [MySQL 8.0 or higher](https://dev.mysql.com/downloads/mysql/)
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) (included in lib folder)

### Verify Installation
```bash
# Check Java version
java -version

# Check MySQL version
mysql --version

🚀 Installation & Setup
Step 1: Clone the Repository
bash
git clone https://github.com/YOUR_USERNAME/Student-Management-System.git
cd Student-Management-System
Step 2: Database Setup
Start MySQL Service

bash
# Windows
net start MySQL80

# Mac/Linux
sudo systemctl start mysql
Login to MySQL

bash
mysql -u root -p
Create Database and Import Schema

sql
CREATE DATABASE student_management_system;
USE student_management_system;
SOURCE database/schema.sql;
Verify Setup

sql
SHOW TABLES;
SELECT * FROM admin;
SELECT * FROM students;
Step 3: Configure Database Connection
Open src/database/DatabaseConnection.java and update the password:

java
private static final String USERNAME = "root";
private static final String PASSWORD = "YOUR_MYSQL_PASSWORD"; // Change this
Step 4: Add MySQL Connector
Ensure mysql-connector-j-9.7.0.jar is in the lib folder.

🏃 Running the Application
Compile the Project
bash
# Compile DatabaseConnection first
javac -cp "lib\mysql-connector-j-9.7.0.jar" -d bin src\database\DatabaseConnection.java

# Compile all remaining files
javac -cp "lib\mysql-connector-j-9.7.0.jar;bin" -d bin src\model\*.java src\dao\*.java src\service\*.java src\ui\*.java src\Main.java
Run the Application
bash
java -cp "bin;lib\mysql-connector-j-9.7.0.jar" Main
One-Line Command (Windows PowerShell)
powershell
$jar = "lib\mysql-connector-j-9.7.0.jar"; javac -cp $jar -d bin src\database\DatabaseConnection.java; javac -cp "$jar;bin" -d bin src\model\*.java src\dao\*.java src\service\*.java src\ui\*.java src\Main.java; java -cp "bin;$jar" Main
🔑 Default Login
After setup, use these credentials to login:

Field	Value
Username	admin
Password	admin123
Note: Change the default password after first login for security.

📸 Screenshots
Login Screen
https://screenshots/login.png

Dashboard
https://screenshots/dashboard.png

Student Management
https://screenshots/student-management.png

Add/Edit Student
https://screenshots/add-student.png

📖 Usage Guide
Adding a New Student
Login to the system

Click on "Student Management" in sidebar

Click "Add Student" button

Fill in all required fields:

Student ID (Format: STU001, STU002, etc.)

Full Name

Gender

Date of Birth

Email

Phone Number (10 digits)

Address

Course

Department

Semester (1-8)

Enrollment Date

Click "Save"

Searching for Students
Enter search term in the search box (ID, Name, or Email)

Results update in real-time

Editing a Student
Select a student from the table

Click "Edit Student"

Modify the information

Click "Save"

Deleting a Student
Select a student from the table

Click "Delete Student"

Confirm deletion

Exporting to CSV
Click "Export to CSV" button

File will be saved in the project directory

🔮 Future Enhancements
PDF report generation (iText library)

Student ID card printing

Email notifications (JavaMail API)

Charts and graphs for statistics (JFreeChart)

Backup and restore functionality

Multi-language support

Dark/Light theme toggle

Batch import from Excel

REST API layer (Spring Boot)

Cloud database deployment

Mobile app integration


📄 License
This project is for educational purposes only. Feel free to use it for learning and portfolio building.

📧 Contact
GitHub: https://github.com/shrutim2804

Project Link: https://github.com/shrutim2804/Student-Management-System

📝 Acknowledgments
Oracle for Java and Swing documentation

MySQL team for database support

All contributors and users of this project

Built with ❤️ for educational excellence


## 📸 Screenshots

### Login Screen
![Login Screen](images/login.png)

### Dashboard
![Dashboard](images/dashboard.png)

### Student Management
![Student Management](images/students.png)

### Add Student Form
![Add Student Form](images/add-student.png)



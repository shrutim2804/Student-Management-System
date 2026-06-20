# Student Management System

A complete, production-ready Student Management System built with **Java Swing**, **JDBC**, and **MySQL** — designed for educational institutions to manage student records efficiently.

<p align="center">
  <img src="https://img.shields.io/badge/Java-11%2B-blue.svg" alt="Java">
  <img src="https://img.shields.io/badge/MySQL-8.0-orange.svg" alt="MySQL">
  <img src="https://img.shields.io/badge/GUI-Swing-green.svg" alt="Swing">
  <img src="https://img.shields.io/badge/License-Educational-red.svg" alt="License">
</p>

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [Default Login](#default-login)
- [Screenshots](#screenshots)
- [Usage Guide](#usage-guide)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## Features

### Authentication
- Secure admin login with MySQL-backed validation
- Password protection and error handling
- Session management

### Student Management
- **Add** — register new students with complete details
- **Update** — modify existing student records
- **Delete** — remove records with confirmation dialog
- **View All** — display students in a sortable table
- **Search** — search by ID, name, or email

### Dashboard
- Total student count
- Course-wise and department-wise distribution
- Recent registrations
- Interactive navigation menu

### Validation
- Email format validation
- Phone number validation (10 digits, must start with 6–9)
- Empty field checks
- Duplicate Student ID prevention
- Semester range validation (1–8)

### Additional Features
- CSV export of student data
- Sorting by name, course, or department
- Real-time search and filtering
- Report generation

### User Interface
- Modern, professional Swing UI
- Consistent color theme
- Responsive layout (GridBagLayout)
- Button hover effects

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 11+ | Core programming language |
| Swing | — | GUI framework |
| JDBC | — | Database connectivity |
| MySQL | 8.0 | Database management |

### Design Patterns

- **MVC** — Model-View-Controller architecture
- **DAO** — Data Access Objects for database operations
- **Singleton** — database connection management
- **Service Layer** — business logic separation

---

## Project Structure

```
Student-Management-System/
│
├── src/
│   ├── database/
│   │   └── DatabaseConnection.java      # Database connection handler
│   │
│   ├── model/
│   │   └── Student.java                 # Student entity/model
│   │
│   ├── dao/
│   │   ├── AdminDAO.java                # Admin data access
│   │   └── StudentDAO.java              # Student CRUD operations
│   │
│   ├── service/
│   │   ├── StudentService.java          # Business logic
│   │   └── ValidationService.java       # Input validation
│   │
│   ├── ui/
│   │   ├── LoginUI.java                 # Login screen
│   │   ├── DashboardUI.java             # Main dashboard
│   │   ├── StudentManagementUI.java     # Student management interface
│   │   └── AddEditStudentUI.java        # Add/Edit student form
│   │
│   └── Main.java                        # Application entry point
│
├── database/
│   └── schema.sql                       # Database schema and sample data
│
├── lib/
│   └── mysql-connector-j-9.7.0.jar      # MySQL JDBC driver
│
├── README.md
└── .gitignore
```

---

## Prerequisites

- [Java JDK 11 or higher](https://www.oracle.com/java/technologies/downloads/)
- [MySQL 8.0 or higher](https://dev.mysql.com/downloads/mysql/)
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) (included in `lib/`)

Verify your installation:

```bash
java -version
mysql --version
```

---

## Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/shrutim2804/Student-Management-System.git
cd Student-Management-System
```

### 2. Set up the database

Start MySQL:

```bash
# Windows
net start MySQL80

# Mac/Linux
sudo systemctl start mysql
```

Log in to MySQL:

```bash
mysql -u root -p
```

Create the database and import the schema:

```sql
CREATE DATABASE student_management_system;
USE student_management_system;
SOURCE database/schema.sql;
```

Verify the setup:

```sql
SHOW TABLES;
SELECT * FROM admin;
SELECT * FROM students;
```

### 3. Configure the database connection

Open `src/database/DatabaseConnection.java` and update the password:

```java
private static final String USERNAME = "root";
private static final String PASSWORD = "YOUR_MYSQL_PASSWORD"; // change this
```

### 4. Add the MySQL connector

Make sure `mysql-connector-j-9.7.0.jar` is present in the `lib/` folder.

---

## Running the Application

### Compile

```bash
# Compile DatabaseConnection first
javac -cp "lib\mysql-connector-j-9.7.0.jar" -d bin src\database\DatabaseConnection.java

# Compile all remaining files
javac -cp "lib\mysql-connector-j-9.7.0.jar;bin" -d bin src\model\*.java src\dao\*.java src\service\*.java src\ui\*.java src\Main.java
```

### Run

```bash
java -cp "bin;lib\mysql-connector-j-9.7.0.jar" Main
```

### One-line build & run (Windows PowerShell)

```powershell
$jar = "lib\mysql-connector-j-9.7.0.jar"; javac -cp $jar -d bin src\database\DatabaseConnection.java; javac -cp "$jar;bin" -d bin src\model\*.java src\dao\*.java src\service\*.java src\ui\*.java src\Main.java; java -cp "bin;$jar" Main
```

---

## Default Login

| Field | Value |
|---|---|
| Username | `admin` |
| Password | `admin123` |

> **Note:** Change the default password after your first login.

---

## Screenshots

See the [images folder](https://github.com/shrutim2804/Student-Management-System/images/images) for application screenshots.

---

## Usage Guide

### Adding a student
1. Log in to the system
2. Click **Student Management** in the sidebar
3. Click **Add Student**
4. Fill in all required fields:
   - Student ID (format: `STU001`, `STU002`, etc.)
   - Full name
   - Gender
   - Date of birth
   - Email
   - Phone number (10 digits)
   - Address
   - Course
   - Department
   - Semester (1–8)
   - Enrollment date
5. Click **Save**

### Searching for students
Enter a search term (ID, name, or email) in the search box — results update in real time.

### Editing a student
1. Select a student from the table
2. Click **Edit Student**
3. Modify the information
4. Click **Save**

### Deleting a student
1. Select a student from the table
2. Click **Delete Student**
3. Confirm deletion

### Exporting to CSV
Click **Export to CSV** — the file is saved in the project directory.

---

## Future Enhancements

- [ ] PDF report generation (iText)
- [ ] Student ID card printing
- [ ] Email notifications (JavaMail API)
- [ ] Charts and statistics (JFreeChart)
- [ ] Backup and restore functionality
- [ ] Multi-language support
- [ ] Dark/light theme toggle
- [ ] Batch import from Excel
- [ ] REST API layer (Spring Boot)
- [ ] Cloud database deployment
- [ ] Mobile app integration

---

## Contributing

Contributions are welcome. Please open an issue first to discuss what you'd like to change.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes
4. Push to the branch
5. Open a pull request

---

## License

This project is for educational purposes only — feel free to use it for learning and portfolio building.

---

## Contact

- GitHub: [@shrutim2804](https://github.com/shrutim2804)
- Project: [Student-Management-System](https://github.com/shrutim2804/Student-Management-System)

---

## Acknowledgments

- Oracle — Java and Swing documentation
- MySQL team — database support
- All contributors and users of this project

<p align="center">Built with care for educational excellence.</p>

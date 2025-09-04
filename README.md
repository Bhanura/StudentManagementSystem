# Student Management System

A console-based Java application that manages student information in a MySQL database using JDBC. This application implements full CRUD operations (Create, Read, Update, Delete) and follows JDBC best practices.

## Repository Contents
- **Source Code**: Complete Java implementation
- **SQL Script**: Database creation script
- **Documentation**: Setup and usage instructions

## Features

### Student Management
- Add new students with validation
- View all students
- View student details by ID
- Search students by name
- Update student information
- Delete students

### Technical Features
- JDBC with PreparedStatements for SQL injection prevention
- Proper resource management
- Transaction handling
- Input validation
- Error handling
- Console-based user interface

## Requirements
- Java JDK 8 or later (Tested with OpenJDK 24)
- MySQL Database (Tested with XAMPP 3.3.0)
- MySQL Connector (com.mysql:mysql-connector-j:8.4.0)

## Setup Instructions

### 1. Database Setup

**Start XAMPP:**
1. Launch XAMPP Control Panel
2. Start Apache and MySQL services

**Create Database:**
1. Open phpMyAdmin (http://localhost/phpmyadmin/)
2. Create a new database named `student_management`
3. Select the new database and go to the SQL tab
4. Run the SQL script from `create_database.sql` or paste the following:

```sql
CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    age INT CHECK (age > 0 AND age <= 120),
    gpa DECIMAL(3,2) CHECK (gpa >= 0.0 AND gpa <= 4.0),
    enrollment_date DATE NOT NULL
);

-- Sample data (optional)
   INSERT INTO students (name, email, age, gpa, enrollment_date) VALUES
   ('Bhanura Waduge', 'bhanuwaduge@gmail.com', 20, 3.75, '2023-09-01'),
   ('Tashini Muthukumarana', 'tash.muthu@gmail.com', 22, 3.89, '2022-09-01'),
   ('Chamitha Madushanka', 'chamitha@gmail.com', 19, 3.45, '2024-01-15'),
   ('Udaya Namal', 'namal@gmail.com', 21, 3.22, '2023-01-10'),
   ('Thivanka Wimalasena', 'happy@gmail.com', 20, 3.95, '2023-09-01');
```

### 2. Project Setup in IntelliJ IDEA

**Clone or Download the Repository:**
```bash
git clone https://github.com/Bhanura/StudentManagementSystem.git
```

**Open in IntelliJ IDEA:**
1. Open IntelliJ IDEA
2. Select "Open" and navigate to the project folder
3. Once opened, right-click on the project and select "Open Module Settings" (F4)
4. Go to "Libraries" and add the MySQL connector:
   - Click "+" → "From Maven..."
   - Search for `com.mysql:mysql-connector-j:8.4.0`
   - Click "OK" and "Apply"

**Configure Database Connection:**
1. Open `DatabaseConnection.java`
2. Verify or update the database connection details:
```java
private static final String URL = "jdbc:mysql://localhost:3306/student_management";
private static final String USERNAME = "root"; // XAMPP default username
private static final String PASSWORD = ""; // XAMPP default has no password
```

### 3. Running the Application

**Compile and Run:**
- In IntelliJ, right-click on `StudentManagementApp.java`
- Select "Run 'StudentManagementApp.main()'"

## Usage Guide

Once running, the application presents a menu-driven interface:

```
===== STUDENT MANAGEMENT SYSTEM =====

===== MENU =====
1. Add New Student
2. View All Students
3. View Student by ID
4. Search Students by Name
5. Update Student
6. Delete Student
0. Exit
Enter your choice:
```

### Menu Options:
- **Add New Student:**
  - Enter student details with validation
  - System will assign an ID automatically

- **View All Students:**
  - Displays all students in the database

- **View Student by ID:**
  - Enter student ID to see complete details

- **Search Students by Name:**
  - Enter full or partial name to find matching students

- **Update Student:**
  - Enter student ID to update
  - For each field, press Enter to keep current value or input new value

- **Delete Student:**
  - Enter student ID to delete
  - Requires confirmation before deletion

- **Exit:**
  - Close the application

## Technical Documentation

### System Architecture
The application follows a simple layered architecture:

- **Presentation Layer**: `StudentManagementApp.java`
  - Handles user interaction
  - Input/output operations
  - Menu navigation

- **Data Access Layer**: `StudentDAO.java`
  - Implements CRUD operations
  - Manages database interactions
  - Uses JDBC with PreparedStatements

- **Model Layer**: `Student.java`
  - Represents student data
  - Provides getters/setters for student attributes

- **Database Connection Layer**: `DatabaseConnection.java`
  - Manages database connections
  - Implements singleton pattern for connection reuse

### Database Schema
```
students
├── id (INT, PRIMARY KEY, AUTO_INCREMENT)
├── name (VARCHAR(100), NOT NULL)
├── email (VARCHAR(100), UNIQUE, NOT NULL)
├── age (INT, CHECK 1-120)
├── gpa (DECIMAL(3,2), CHECK 0.0-4.0)
└── enrollment_date (DATE, NOT NULL)
```

### Assumptions and Special Notes
- **Database Configuration:**
  - The application assumes MySQL is running on localhost:3306
  - Default XAMPP credentials (username: root, password: empty) are used

- **Date Format:**
  - Enrollment dates should be in YYYY-MM-DD format

- **GPA Scale:**
  - GPA is on a 4.0 scale (0.0 to 4.0)

- **Input Validation:**
  - The application includes basic validation for all inputs
  - Email validation uses a simple regex pattern

- **Resource Management:**
  - All database connections and resources are properly closed

- **Error Handling:**
  - Database errors are caught and displayed to the user
  - Invalid inputs are handled gracefully

## License
This project is available for educational purposes.

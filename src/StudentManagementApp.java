import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the Student Management System
 */
public class StudentManagementApp {
    private static StudentDAO studentDAO;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Initialize resources
        studentDAO = new StudentDAO();
        scanner = new Scanner(System.in);
        
        boolean exit = false;
        
        System.out.println("===== STUDENT MANAGEMENT SYSTEM =====");
        
        while (!exit) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    viewStudentById();
                    break;
                case 4:
                    searchStudentsByName();
                    break;
                case 5:
                    updateStudent();
                    break;
                case 6:
                    deleteStudent();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (!exit) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        // Close resources
        scanner.close();
        DatabaseConnection.closeConnection();
    }
    
    /**
     * Display the main menu options
     */
    private static void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. View Student by ID");
        System.out.println("4. Search Students by Name");
        System.out.println("5. Update Student");
        System.out.println("6. Delete Student");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Get user choice with input validation
     */
    private static int getUserChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine();  // Consume invalid input
            return -1;  // Invalid choice
        }
    }
    
    /**
     * Add a new student
     */
    private static void addStudent() {
        System.out.println("\n===== ADD NEW STUDENT =====");
        
        // Get student details with validation
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.println("Name cannot be empty. Please try again.");
            System.out.print("Enter name: ");
            name = scanner.nextLine().trim();
        }
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        while (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please try again.");
            System.out.print("Enter email: ");
            email = scanner.nextLine().trim();
        }
        
        int age = getValidIntInput("Enter age: ", 1, 120);
        double gpa = getValidDoubleInput("Enter GPA: ", 0.0, 4.0);
        
        System.out.print("Enter enrollment date (YYYY-MM-DD): ");
        String enrollmentDate = scanner.nextLine().trim();
        while (!isValidDate(enrollmentDate)) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            System.out.print("Enter enrollment date (YYYY-MM-DD): ");
            enrollmentDate = scanner.nextLine().trim();
        }
        
        // Create and save the student
        Student student = new Student(name, email, age, gpa, enrollmentDate);
        boolean success = studentDAO.createStudent(student);
        
        if (success) {
            System.out.println("Student added successfully with ID: " + student.getId());
        } else {
            System.out.println("Failed to add student. Please try again.");
        }
    }
    
    /**
     * View all students
     */
    private static void viewAllStudents() {
        System.out.println("\n===== ALL STUDENTS =====");
        List<Student> students = studentDAO.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found in the database.");
        } else {
            System.out.println("Total students: " + students.size());
            for (Student student : students) {
                System.out.println("\n-------------------------");
                System.out.println(student);
            }
        }
    }
    
    /**
     * View student by ID
     */
    private static void viewStudentById() {
        System.out.println("\n===== VIEW STUDENT BY ID =====");
        int id = getValidIntInput("Enter student ID: ", 1, Integer.MAX_VALUE);
        
        Student student = studentDAO.getStudentById(id);
        
        if (student != null) {
            System.out.println("\nStudent found:");
            System.out.println(student);
        } else {
            System.out.println("No student found with ID: " + id);
        }
    }
    
    /**
     * Search students by name
     */
    private static void searchStudentsByName() {
        System.out.println("\n===== SEARCH STUDENTS BY NAME =====");
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().trim();
        
        List<Student> students = studentDAO.searchStudentsByName(name);
        
        if (students.isEmpty()) {
            System.out.println("No students found matching the name: " + name);
        } else {
            System.out.println("Found " + students.size() + " student(s):");
            for (Student student : students) {
                System.out.println("\n-------------------------");
                System.out.println(student);
            }
        }
    }
    
    /**
     * Update student information
     */
    private static void updateStudent() {
        System.out.println("\n===== UPDATE STUDENT =====");
        int id = getValidIntInput("Enter student ID to update: ", 1, Integer.MAX_VALUE);
        
        Student student = studentDAO.getStudentById(id);
        
        if (student == null) {
            System.out.println("No student found with ID: " + id);
            return;
        }
        
        System.out.println("\nCurrent student details:");
        System.out.println(student);
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("Name (" + student.getName() + "): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            student.setName(name);
        }
        
        System.out.print("Email (" + student.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            if (isValidEmail(email)) {
                student.setEmail(email);
            } else {
                System.out.println("Invalid email format. Email not updated.");
            }
        }
        
        System.out.print("Age (" + student.getAge() + "): ");
        String ageStr = scanner.nextLine().trim();
        if (!ageStr.isEmpty()) {
            try {
                int age = Integer.parseInt(ageStr);
                if (age > 0 && age <= 120) {
                    student.setAge(age);
                } else {
                    System.out.println("Invalid age. Age not updated.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid age format. Age not updated.");
            }
        }
        
        System.out.print("GPA (" + student.getGpa() + "): ");
        String gpaStr = scanner.nextLine().trim();
        if (!gpaStr.isEmpty()) {
            try {
                double gpa = Double.parseDouble(gpaStr);
                if (gpa >= 0.0 && gpa <= 4.0) {
                    student.setGpa(gpa);
                } else {
                    System.out.println("Invalid GPA. GPA not updated.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid GPA format. GPA not updated.");
            }
        }
        
        System.out.print("Enrollment Date (" + student.getEnrollmentDate() + "): ");
        String enrollmentDate = scanner.nextLine().trim();
        if (!enrollmentDate.isEmpty()) {
            if (isValidDate(enrollmentDate)) {
                student.setEnrollmentDate(enrollmentDate);
            } else {
                System.out.println("Invalid date format. Enrollment date not updated.");
            }
        }
        
        boolean success = studentDAO.updateStudent(student);
        
        if (success) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Failed to update student. Please try again.");
        }
    }
    
    /**
     * Delete a student
     */
    private static void deleteStudent() {
        System.out.println("\n===== DELETE STUDENT =====");
        int id = getValidIntInput("Enter student ID to delete: ", 1, Integer.MAX_VALUE);
        
        Student student = studentDAO.getStudentById(id);
        
        if (student == null) {
            System.out.println("No student found with ID: " + id);
            return;
        }
        
        System.out.println("\nStudent to delete:");
        System.out.println(student);
        
        System.out.print("\nAre you sure you want to delete this student? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("y")) {
            boolean success = studentDAO.deleteStudent(id);
            
            if (success) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Failed to delete student. Please try again.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    /**
     * Helper method to get valid integer input
     */
    private static int getValidIntInput(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                
                if (value >= min && value <= max) {
                    break;
                } else {
                    System.out.println("Please enter a value between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();  // Consume invalid input
            }
        }
        return value;
    }
    
    /**
     * Helper method to get valid double input
     */
    private static double getValidDoubleInput(String prompt, double min, double max) {
        double value;
        while (true) {
            System.out.print(prompt);
            try {
                value = scanner.nextDouble();
                scanner.nextLine();  // Consume newline
                
                if (value >= min && value <= max) {
                    break;
                } else {
                    System.out.println("Please enter a value between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();  // Consume invalid input
            }
        }
        return value;
    }
    
    /**
     * Validate email format
     */
    private static boolean isValidEmail(String email) {
        // Basic email validation
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    /**
     * Validate date format (YYYY-MM-DD)
     */
    private static boolean isValidDate(String date) {
        return date.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }
}
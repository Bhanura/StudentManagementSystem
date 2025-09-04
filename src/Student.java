/**
 * Student model class representing a student entity
 */
public class Student {
    private int id;
    private String name;
    private String email;
    private int age;
    private double gpa;
    private String enrollmentDate;

    // Default constructor
    public Student() {
    }

    // Constructor with all fields except id (for creating new students)
    public Student(String name, String email, int age, double gpa, String enrollmentDate) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gpa = gpa;
        this.enrollmentDate = enrollmentDate;
    }

    // Constructor with all fields (for retrieving existing students)
    public Student(int id, String name, String email, int age, double gpa, String enrollmentDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gpa = gpa;
        this.enrollmentDate = enrollmentDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               "\nName: " + name +
               "\nEmail: " + email +
               "\nAge: " + age +
               "\nGPA: " + gpa +
               "\nEnrollment Date: " + enrollmentDate;
    }
}
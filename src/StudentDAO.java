import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Student entity
 * Handles all database operations related to students
 */
public class StudentDAO {
    private Connection connection;
    
    public StudentDAO() {
        this.connection = DatabaseConnection.getConnection();
    }
    
    /**
     * Create a new student in the database
     * @param student The student to add
     * @return true if successful, false otherwise
     */
    public boolean createStudent(Student student) {
        String query = "INSERT INTO students (name, email, age, gpa, enrollment_date) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setInt(3, student.getAge());
            stmt.setDouble(4, student.getGpa());
            stmt.setString(5, student.getEnrollmentDate());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get the generated ID and set it to the student object
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    student.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error creating student: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get all students from the database
     * @return List of Student objects
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Student student = extractStudentFromResultSet(rs);
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
        
        return students;
    }
    
    /**
     * Get a student by ID
     * @param id The student ID to search for
     * @return Student object if found, null otherwise
     */
    public Student getStudentById(int id) {
        String query = "SELECT * FROM students WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractStudentFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving student: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Search for students by name (partial match)
     * @param name The name to search for
     * @return List of matching Student objects
     */
    public List<Student> searchStudentsByName(String name) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students WHERE name LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Student student = extractStudentFromResultSet(rs);
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching students: " + e.getMessage());
        }
        
        return students;
    }
    
    /**
     * Update an existing student
     * @param student The student with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateStudent(Student student) {
        String query = "UPDATE students SET name = ?, email = ?, age = ?, gpa = ?, enrollment_date = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setInt(3, student.getAge());
            stmt.setDouble(4, student.getGpa());
            stmt.setString(5, student.getEnrollmentDate());
            stmt.setInt(6, student.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete a student by ID
     * @param id The ID of the student to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteStudent(int id) {
        String query = "DELETE FROM students WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Helper method to extract student data from a ResultSet
     */
    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        int age = rs.getInt("age");
        double gpa = rs.getDouble("gpa");
        String enrollmentDate = rs.getString("enrollment_date");
        
        return new Student(id, name, email, age, gpa, enrollmentDate);
    }
}
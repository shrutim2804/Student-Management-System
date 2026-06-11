package dao;

import database.DatabaseConnection;
import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    
    // Create - Add Student
    public boolean addStudent(Student student) {
        String query = "INSERT INTO students (student_id, full_name, gender, date_of_birth, " +
                      "email, phone_number, address, course, department, semester, enrollment_date) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getFullName());
            pstmt.setString(3, student.getGender());
            pstmt.setDate(4, new java.sql.Date(student.getDateOfBirth().getTime()));
            pstmt.setString(5, student.getEmail());
            pstmt.setString(6, student.getPhoneNumber());
            pstmt.setString(7, student.getAddress());
            pstmt.setString(8, student.getCourse());
            pstmt.setString(9, student.getDepartment());
            pstmt.setInt(10, student.getSemester());
            pstmt.setDate(11, new java.sql.Date(student.getEnrollmentDate().getTime()));
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Read - Get All Students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students ORDER BY full_name";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Student student = extractStudentFromResultSet(rs);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    
    // Read - Search Student
    public List<Student> searchStudents(String searchTerm) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students WHERE student_id LIKE ? OR full_name LIKE ? OR email LIKE ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            String pattern = "%" + searchTerm + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            pstmt.setString(3, pattern);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    
    // Update Student
    public boolean updateStudent(Student student) {
        String query = "UPDATE students SET full_name=?, gender=?, date_of_birth=?, email=?, " +
                      "phone_number=?, address=?, course=?, department=?, semester=?, enrollment_date=? " +
                      "WHERE student_id=?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, student.getFullName());
            pstmt.setString(2, student.getGender());
            pstmt.setDate(3, new java.sql.Date(student.getDateOfBirth().getTime()));
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhoneNumber());
            pstmt.setString(6, student.getAddress());
            pstmt.setString(7, student.getCourse());
            pstmt.setString(8, student.getDepartment());
            pstmt.setInt(9, student.getSemester());
            pstmt.setDate(10, new java.sql.Date(student.getEnrollmentDate().getTime()));
            pstmt.setString(11, student.getStudentId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Delete Student
    public boolean deleteStudent(String studentId) {
        String query = "DELETE FROM students WHERE student_id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, studentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get Student by ID
    public Student getStudentById(String studentId) {
        String query = "SELECT * FROM students WHERE student_id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractStudentFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Check if Student ID exists
    public boolean isStudentIdExists(String studentId) {
        String query = "SELECT 1 FROM students WHERE student_id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get Total Students Count
    public int getTotalStudentsCount() {
        String query = "SELECT COUNT(*) FROM students";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Get Recent Students (last 5)
    public List<Student> getRecentStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students ORDER BY enrollment_date DESC LIMIT 5";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    
    // Get Statistics by Course - ADD THIS METHOD
    public List<Object[]> getCourseStatistics() {
        List<Object[]> stats = new ArrayList<>();
        String query = "SELECT course, COUNT(*) as count FROM students GROUP BY course";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Object[] stat = new Object[2];
                stat[0] = rs.getString("course");
                stat[1] = rs.getInt("count");
                stats.add(stat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }
    
    // Helper method to extract Student from ResultSet
    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getString("student_id"));
        student.setFullName(rs.getString("full_name"));
        student.setGender(rs.getString("gender"));
        student.setDateOfBirth(rs.getDate("date_of_birth"));
        student.setEmail(rs.getString("email"));
        student.setPhoneNumber(rs.getString("phone_number"));
        student.setAddress(rs.getString("address"));
        student.setCourse(rs.getString("course"));
        student.setDepartment(rs.getString("department"));
        student.setSemester(rs.getInt("semester"));
        student.setEnrollmentDate(rs.getDate("enrollment_date"));
        return student;
    }
}
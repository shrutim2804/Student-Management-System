package service;

import java.util.regex.Pattern;

public class ValidationService {
    
    // Email validation
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && Pattern.matches(emailRegex, email);
    }
    
    // Phone number validation (Indian format)
    public static boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^[6-9]\\d{9}$";
        return phone != null && Pattern.matches(phoneRegex, phone);
    }
    
    // Student ID validation
    public static boolean isValidStudentId(String studentId) {
        return studentId != null && studentId.matches("^STU\\d{3}$");
    }
    
    // Name validation
    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2 && name.matches("^[a-zA-Z\\s]+$");
    }
    
    // Check empty fields
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    // Semester validation
    public static boolean isValidSemester(int semester) {
        return semester >= 1 && semester <= 8;
    }
}
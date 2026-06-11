package ui;

import model.Student;
import service.StudentService;
import service.ValidationService;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEditStudentUI extends JDialog {
    private StudentService studentService;
    private Student editingStudent;
    private JFrame parent;
    
    private JTextField studentIdField, nameField, emailField, phoneField, addressField;
    private JComboBox<String> genderCombo, courseCombo, departmentCombo;
    private JSpinner semesterSpinner;
    private JSpinner dobSpinner, enrollmentSpinner;
    
    public AddEditStudentUI(JFrame parent, Student student) {
        super(parent, student == null ? "Add Student" : "Edit Student", true);
        this.parent = parent;
        this.editingStudent = student;
        this.studentService = new StudentService();
        initComponents();
        
        if (student != null) {
            loadStudentData();
        }
    }
    
    private void initComponents() {
        setSize(600, 700);
        setLocationRelativeTo(parent);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel(editingStudent == null ? "Add New Student" : "Edit Student");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(41, 128, 185));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        // Student ID
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Student ID *"), gbc);
        gbc.gridx = 1;
        studentIdField = new JTextField(15);
        studentIdField.setEnabled(editingStudent == null); // ID cannot be edited
        mainPanel.add(studentIdField, gbc);
        
        // Full Name
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Full Name *"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        mainPanel.add(nameField, gbc);
        
        // Gender
        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Gender *"), gbc);
        gbc.gridx = 1;
        genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        mainPanel.add(genderCombo, gbc);
        
        // Date of Birth
        gbc.gridy = 4;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Date of Birth *"), gbc);
        gbc.gridx = 1;
        SpinnerDateModel dobModel = new SpinnerDateModel();
        dobSpinner = new JSpinner(dobModel);
        JSpinner.DateEditor dobEditor = new JSpinner.DateEditor(dobSpinner, "yyyy-MM-dd");
        dobSpinner.setEditor(dobEditor);
        mainPanel.add(dobSpinner, gbc);
        
        // Email
        gbc.gridy = 5;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Email *"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(15);
        mainPanel.add(emailField, gbc);
        
        // Phone Number
        gbc.gridy = 6;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Phone Number *"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        mainPanel.add(phoneField, gbc);
        
        // Address
        gbc.gridy = 7;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Address"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField(15);
        mainPanel.add(addressField, gbc);
        
        // Course
        gbc.gridy = 8;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Course *"), gbc);
        gbc.gridx = 1;
        courseCombo = new JComboBox<>(new String[]{"Computer Science", "Information Technology", "Electronics", "Mechanical", "Civil"});
        mainPanel.add(courseCombo, gbc);
        
        // Department
        gbc.gridy = 9;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Department *"), gbc);
        gbc.gridx = 1;
        departmentCombo = new JComboBox<>(new String[]{"Software Engineering", "Web Development", "Data Science", "AI/ML", "Networking"});
        mainPanel.add(departmentCombo, gbc);
        
        // Semester
        gbc.gridy = 10;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Semester *"), gbc);
        gbc.gridx = 1;
        semesterSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
        mainPanel.add(semesterSpinner, gbc);
        
        // Enrollment Date
        gbc.gridy = 11;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Enrollment Date *"), gbc);
        gbc.gridx = 1;
        SpinnerDateModel enrollModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        enrollmentSpinner = new JSpinner(enrollModel);
        JSpinner.DateEditor enrollEditor = new JSpinner.DateEditor(enrollmentSpinner, "yyyy-MM-dd");
        enrollmentSpinner.setEditor(enrollEditor);
        mainPanel.add(enrollmentSpinner, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton saveButton = new JButton("💾 Save");
        JButton cancelButton = new JButton("❌ Cancel");
        
        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.BLACK);
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(100, 35));
        
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(100, 35));
        
        saveButton.addActionListener(e -> saveStudent());
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridy = 12;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }
    
    private void loadStudentData() {
        studentIdField.setText(editingStudent.getStudentId());
        nameField.setText(editingStudent.getFullName());
        genderCombo.setSelectedItem(editingStudent.getGender());
        dobSpinner.setValue(editingStudent.getDateOfBirth());
        emailField.setText(editingStudent.getEmail());
        phoneField.setText(editingStudent.getPhoneNumber());
        addressField.setText(editingStudent.getAddress());
        courseCombo.setSelectedItem(editingStudent.getCourse());
        departmentCombo.setSelectedItem(editingStudent.getDepartment());
        semesterSpinner.setValue(editingStudent.getSemester());
        enrollmentSpinner.setValue(editingStudent.getEnrollmentDate());
    }
    
    private void saveStudent() {
        // Validate fields
        if (ValidationService.isEmpty(studentIdField.getText())) {
            JOptionPane.showMessageDialog(this, "Student ID is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!ValidationService.isValidStudentId(studentIdField.getText()) && editingStudent == null) {
            JOptionPane.showMessageDialog(this, "Student ID must be in format: STU001", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (ValidationService.isEmpty(nameField.getText())) {
            JOptionPane.showMessageDialog(this, "Full Name is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!ValidationService.isValidName(nameField.getText())) {
            JOptionPane.showMessageDialog(this, "Name should contain only letters!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (ValidationService.isEmpty(emailField.getText())) {
            JOptionPane.showMessageDialog(this, "Email is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!ValidationService.isValidEmail(emailField.getText())) {
            JOptionPane.showMessageDialog(this, "Invalid email format!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (ValidationService.isEmpty(phoneField.getText())) {
            JOptionPane.showMessageDialog(this, "Phone number is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!ValidationService.isValidPhoneNumber(phoneField.getText())) {
            JOptionPane.showMessageDialog(this, "Phone number must be 10 digits starting with 6-9!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create Student object
        Student student = new Student();
        student.setStudentId(studentIdField.getText());
        student.setFullName(nameField.getText());
        student.setGender((String) genderCombo.getSelectedItem());
        student.setDateOfBirth((Date) dobSpinner.getValue());
        student.setEmail(emailField.getText());
        student.setPhoneNumber(phoneField.getText());
        student.setAddress(addressField.getText());
        student.setCourse((String) courseCombo.getSelectedItem());
        student.setDepartment((String) departmentCombo.getSelectedItem());
        student.setSemester((int) semesterSpinner.getValue());
        student.setEnrollmentDate((Date) enrollmentSpinner.getValue());
        
        boolean success;
        if (editingStudent == null) {
            success = studentService.addStudent(student);
            if (success) {
                JOptionPane.showMessageDialog(this, "Student added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student! Student ID may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            success = studentService.updateStudent(student);
            if (success) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update student!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        dispose();
    }
}
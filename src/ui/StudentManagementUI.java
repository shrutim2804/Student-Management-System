package ui;

import service.StudentService;
import model.Student;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentManagementUI extends JFrame {
    private StudentService studentService;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    
    public StudentManagementUI() {
        studentService = new StudentService();
        initComponents();
        loadStudentData();
    }
    
    private void initComponents() {
        setTitle("Student Management");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 242, 245));
        
        // Top Control Panel
        mainPanel.add(createControlPanel(), BorderLayout.NORTH);
        
        // Student Table
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        
        // Bottom Button Panel
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setBackground(new Color(240, 242, 245));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(240, 242, 245));
        
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                searchStudents();
            }
        });
        
        JButton searchButton = new JButton("🔍 Search");
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> searchStudents());
        
        JButton refreshButton = new JButton("⟳ Refresh");
        refreshButton.setBackground(new Color(46, 204, 113));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadStudentData());
        
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        
        // Sort Panel
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sortPanel.setBackground(new Color(240, 242, 245));
        
        String[] sortOptions = {"Sort by Name", "Sort by Course", "Sort by Department"};
        JComboBox<String> sortCombo = new JComboBox<>(sortOptions);
        sortCombo.addActionListener(e -> sortStudents((String) sortCombo.getSelectedItem()));
        
        sortPanel.add(new JLabel("Sort: "));
        sortPanel.add(sortCombo);
        
        controlPanel.add(searchPanel, BorderLayout.WEST);
        controlPanel.add(sortPanel, BorderLayout.EAST);
        
        return controlPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Student Records"));
        
        // Table Columns
        String[] columns = {"ID", "Full Name", "Gender", "Email", "Phone", "Course", "Department", "Semester"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        studentTable.setRowHeight(30);
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        studentTable.getTableHeader().setBackground(new Color(52, 73, 94));
        studentTable.getTableHeader().setForeground(Color.BLACK);
        studentTable.setSelectionBackground(new Color(187, 222, 251));
        
        // Set column widths
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        studentTable.getColumnModel().getColumn(6).setPreferredWidth(120);
        studentTable.getColumnModel().getColumn(7).setPreferredWidth(70);
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 242, 245));
        
        JButton addButton = createStyledButton("➕ Add Student", new Color(46, 204, 113));
        JButton editButton = createStyledButton("✏️ Edit Student", new Color(52, 152, 219));
        JButton deleteButton = createStyledButton("🗑️ Delete Student", new Color(231, 76, 60));
        JButton exportButton = createStyledButton("📎 Export to CSV", new Color(155, 89, 182));
        JButton backButton = createStyledButton("← Back to Dashboard", new Color(149, 165, 166));
        
        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        exportButton.addActionListener(e -> exportToCSV());
        backButton.addActionListener(e -> {
            new DashboardUI().setVisible(true);
            dispose();
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(backButton);
        
        return buttonPanel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 35));
        return button;
    }
    
    private void loadStudentData() {
        tableModel.setRowCount(0);
        List<Student> students = studentService.getAllStudents();
        
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                student.getStudentId(),
                student.getFullName(),
                student.getGender(),
                student.getEmail(),
                student.getPhoneNumber(),
                student.getCourse(),
                student.getDepartment(),
                student.getSemester()
            });
        }
    }
    
    private void searchStudents() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadStudentData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Student> students = studentService.searchStudents(searchTerm);
        
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                student.getStudentId(),
                student.getFullName(),
                student.getGender(),
                student.getEmail(),
                student.getPhoneNumber(),
                student.getCourse(),
                student.getDepartment(),
                student.getSemester()
            });
        }
        
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students found!", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void sortStudents(String sortBy) {
        // Simple sorting implementation
        tableModel.setRowCount(0);
        List<Student> students = studentService.getAllStudents();
        
        if (sortBy.equals("Sort by Name")) {
            students.sort((s1, s2) -> s1.getFullName().compareTo(s2.getFullName()));
        } else if (sortBy.equals("Sort by Course")) {
            students.sort((s1, s2) -> s1.getCourse().compareTo(s2.getCourse()));
        } else if (sortBy.equals("Sort by Department")) {
            students.sort((s1, s2) -> s1.getDepartment().compareTo(s2.getDepartment()));
        }
        
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                student.getStudentId(),
                student.getFullName(),
                student.getGender(),
                student.getEmail(),
                student.getPhoneNumber(),
                student.getCourse(),
                student.getDepartment(),
                student.getSemester()
            });
        }
    }
    
    private void addStudent() {
        new AddEditStudentUI(this, null).setVisible(true);
        loadStudentData();
    }
    
    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String studentId = (String) tableModel.getValueAt(selectedRow, 0);
        Student student = studentService.getStudentById(studentId);
        new AddEditStudentUI(this, student).setVisible(true);
        loadStudentData();
    }
    
    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this student?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String studentId = (String) tableModel.getValueAt(selectedRow, 0);
            if (studentService.deleteStudent(studentId)) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete student!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void exportToCSV() {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("students_export_" + System.currentTimeMillis() + ".csv");
            StringBuilder sb = new StringBuilder();
            
            // Header
            for (int i = 0; i < studentTable.getColumnCount(); i++) {
                sb.append(studentTable.getColumnName(i));
                if (i < studentTable.getColumnCount() - 1) sb.append(",");
            }
            sb.append("\n");
            
            // Data
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    sb.append(tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1) sb.append(",");
                }
                sb.append("\n");
            }
            
            fw.write(sb.toString());
            fw.close();
            
            JOptionPane.showMessageDialog(this, "Data exported successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error exporting data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
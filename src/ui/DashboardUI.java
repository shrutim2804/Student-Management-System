package ui;

import service.StudentService;
import model.Student;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class DashboardUI extends JFrame {
    private StudentService studentService;
    private JLabel totalStudentsLabel;
    private JPanel recentStudentsPanel;
    
    public DashboardUI() {
        studentService = new StudentService();
        initComponents();
        loadDashboardData();
    }
    
    private void initComponents() {
        setTitle("Student Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));
        
        // Top Header
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        
        // Sidebar
        mainPanel.add(createSidebar(), BorderLayout.WEST);
        
        // Center Content
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(41, 128, 185));
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        header.setPreferredSize(new Dimension(0, 70));
        
        JLabel titleLabel = new JLabel("Student Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel adminLabel = new JLabel("Welcome, Admin | Today: " + new java.util.Date());
        adminLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        adminLabel.setForeground(Color.WHITE);
        
        header.add(titleLabel, BorderLayout.WEST);
        header.add(adminLabel, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(52, 73, 94));
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        
        String[] menuItems = {"Dashboard", "Student Management", "Reports", "Logout"};
        String[] icons = {"📊", "👨‍🎓", "📈", "🚪"};
        
        for (int i = 0; i < menuItems.length; i++) {
            JButton menuButton = new JButton(icons[i] + " " + menuItems[i]);
            menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuButton.setMaximumSize(new Dimension(220, 45));
            menuButton.setBackground(new Color(52, 73, 94));
            menuButton.setForeground(Color.BLACK);
            menuButton.setFont(new Font("Arial", Font.PLAIN, 14));
            menuButton.setFocusPainted(false);
            menuButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            final int index = i;
            menuButton.addActionListener(e -> handleMenuClick(index));
            
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            sidebar.add(menuButton);
        }
        
        return sidebar;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(new Color(240, 242, 245));
        
        // Stats Cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(new Color(240, 242, 245));
        
        // Total Students Card
        totalStudentsLabel = new JLabel("0", SwingConstants.CENTER);
        statsPanel.add(createStatCard("Total Students", totalStudentsLabel, new Color(46, 204, 113)));
        
        // Courses Card
        JLabel coursesLabel = new JLabel("3", SwingConstants.CENTER);
        statsPanel.add(createStatCard("Active Courses", coursesLabel, new Color(52, 152, 219)));
        
        // Departments Card
        JLabel deptLabel = new JLabel("3", SwingConstants.CENTER);
        statsPanel.add(createStatCard("Departments", deptLabel, new Color(155, 89, 182)));
        
        centerPanel.add(statsPanel, BorderLayout.NORTH);
        
        // Recent Students Panel
        recentStudentsPanel = new JPanel();
        recentStudentsPanel.setLayout(new BoxLayout(recentStudentsPanel, BoxLayout.Y_AXIS));
        recentStudentsPanel.setBackground(Color.WHITE);
        recentStudentsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JScrollPane scrollPane = new JScrollPane(recentStudentsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Recent Student Registrations"));
        scrollPane.setPreferredSize(new Dimension(0, 400));
        
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        return centerPanel;
    }
    
    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);
        
        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valueLabel.setForeground(color);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void loadDashboardData() {
        // Load total students
        int totalStudents = studentService.getTotalStudentsCount();
        totalStudentsLabel.setText(String.valueOf(totalStudents));
        
        // Load recent students
        loadRecentStudents();
    }
    
    private void loadRecentStudents() {
        recentStudentsPanel.removeAll();
        List<Student> recentStudents = studentService.getRecentStudents();
        
        if (recentStudents.isEmpty()) {
            JLabel noDataLabel = new JLabel("No students registered yet.");
            noDataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            recentStudentsPanel.add(noDataLabel);
        } else {
            for (Student student : recentStudents) {
                recentStudentsPanel.add(createStudentRow(student));
                recentStudentsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        recentStudentsPanel.revalidate();
        recentStudentsPanel.repaint();
    }
    
    private JPanel createStudentRow(Student student) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(250, 250, 250));
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel nameLabel = new JLabel(student.getFullName() + " (" + student.getStudentId() + ")");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel courseLabel = new JLabel(student.getCourse() + " - Semester " + student.getSemester());
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        courseLabel.setForeground(Color.GRAY);
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(new Color(250, 250, 250));
        textPanel.add(nameLabel);
        textPanel.add(courseLabel);
        
        row.add(textPanel, BorderLayout.WEST);
        
        return row;
    }
    
    private void handleMenuClick(int menuIndex) {
        switch (menuIndex) {
            case 0: // Dashboard
                loadDashboardData();
                break;
            case 1: // Student Management
                new StudentManagementUI().setVisible(true);
                dispose();
                break;
            case 2: // Reports
                generateReport();
                break;
            case 3: // Logout
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new LoginUI().setVisible(true);
                    dispose();
                }
                break;
        }
    }
    
    private void generateReport() {
        List<Student> students = studentService.getAllStudents();
        StringBuilder report = new StringBuilder();
        report.append("STUDENT MANAGEMENT SYSTEM REPORT\n");
        report.append("Generated: ").append(new java.util.Date()).append("\n");
        report.append("=".repeat(50)).append("\n\n");
        report.append("Total Students: ").append(students.size()).append("\n\n");
        report.append("Student List:\n");
        report.append("-".repeat(50)).append("\n");
        
        for (Student s : students) {
            report.append(String.format("ID: %s | Name: %s | Course: %s | Semester: %d\n",
                s.getStudentId(), s.getFullName(), s.getCourse(), s.getSemester()));
        }
        
        JTextArea textArea = new JTextArea(report.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Student Report", JOptionPane.INFORMATION_MESSAGE);
    }
}
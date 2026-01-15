package gui;

import auth.AuthenticationService;
import service.ReportService;
import service.StudentService;
import javax.swing.*;
import java.awt.*;

/**
 * Main application frame with tabbed interface
 */
public class MainFrame extends JFrame {
    private AuthenticationService authService;
    private StudentService studentService;
    private ReportService reportService;
    private JTabbedPane tabbedPane;
    private JLabel userLabel;
    
    public MainFrame(AuthenticationService authService) {
        this.authService = authService;
        this.studentService = new StudentService();
        this.reportService = new ReportService(studentService);
        
        initializeComponents();
        setupLayout();
        setupMenuBar();
    }
    
    private void initializeComponents() {
        setTitle("Student Information System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        userLabel = new JLabel();
        updateUserLabel();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Create panels
        StudentEntryPanel entryPanel = new StudentEntryPanel(studentService);
        SearchPanel searchPanel = new SearchPanel(studentService);
        ReportPanel reportPanel = new ReportPanel(reportService);
        
        // Add tabs
        tabbedPane.addTab("Student Entry", entryPanel);
        tabbedPane.addTab("Search", searchPanel);
        tabbedPane.addTab("Reports", reportPanel);
        
        // Top panel with user info
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        topPanel.add(new JLabel("Student Information System"), BorderLayout.WEST);
        topPanel.add(userLabel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem logoutItem = new JMenuItem("Logout");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        logoutItem.addActionListener(e -> logout());
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(logoutItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void updateUserLabel() {
        if (authService.isLoggedIn()) {
            userLabel.setText("Logged in as: " + authService.getCurrentUser().getUsername());
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            authService.logout();
            dispose();
            showLogin();
        }
    }
    
    private void showLogin() {
        LoginFrame loginFrame = new LoginFrame(authService);
        loginFrame.setLoginListener(() -> {
            MainFrame mainFrame = new MainFrame(authService);
            mainFrame.setVisible(true);
        });
        loginFrame.setVisible(true);
    }
    
    private void showAbout() {
        JOptionPane.showMessageDialog(this,
                "Student Information System\n\n" +
                "A Java Swing application demonstrating OOP principles:\n" +
                "- Encapsulation\n" +
                "- Inheritance\n" +
                "- Polymorphism\n" +
                "- Abstraction\n\n" +
                "Features:\n" +
                "- Student CRUD operations\n" +
                "- Advanced search functionality\n" +
                "- Comprehensive reporting",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
}


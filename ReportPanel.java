package gui;

import service.ReportService;
import javax.swing.*;
import java.awt.*;

/**
 * Panel for generating and displaying reports
 */
public class ReportPanel extends JPanel {
    private ReportService reportService;
    private JTextArea reportArea;
    private JButton allStudentsButton, courseReportButton, yearReportButton, statisticsButton;
    private JTextField courseField, yearField;
    
    public ReportPanel(ReportService reportService) {
        this.reportService = reportService;
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        // Report display area
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        
        // Buttons
        allStudentsButton = new JButton("All Students Report");
        courseReportButton = new JButton("Course Report");
        yearReportButton = new JButton("Year Report");
        statisticsButton = new JButton("Statistics Report");
        
        // Input fields
        courseField = new JTextField(15);
        yearField = new JTextField(15);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Top panel - Buttons and inputs
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("Generate Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        controlPanel.add(titleLabel, gbc);
        
        // Buttons row 1
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        controlPanel.add(allStudentsButton, gbc);
        
        gbc.gridx = 1;
        controlPanel.add(statisticsButton, gbc);
        
        // Course report
        gbc.gridx = 2;
        controlPanel.add(new JLabel("Course:"), gbc);
        
        gbc.gridx = 3;
        controlPanel.add(courseField, gbc);
        
        gbc.gridx = 4;
        controlPanel.add(courseReportButton, gbc);
        
        // Year report
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Year:"), gbc);
        
        gbc.gridx = 1;
        controlPanel.add(yearField, gbc);
        
        gbc.gridx = 2;
        controlPanel.add(yearReportButton, gbc);
        
        // Center - Report area
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        // Layout
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        allStudentsButton.addActionListener(e -> generateAllStudentsReport());
        courseReportButton.addActionListener(e -> generateCourseReport());
        yearReportButton.addActionListener(e -> generateYearReport());
        statisticsButton.addActionListener(e -> generateStatisticsReport());
    }
    
    private void generateAllStudentsReport() {
        String report = reportService.generateAllStudentsReport();
        reportArea.setText(report);
    }
    
    private void generateCourseReport() {
        String course = courseField.getText().trim();
        if (course.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a course name.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String report = reportService.generateCourseReport(course);
        reportArea.setText(report);
    }
    
    private void generateYearReport() {
        String yearStr = yearField.getText().trim();
        if (yearStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a year.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int year = Integer.parseInt(yearStr);
            String report = reportService.generateYearReport(year);
            reportArea.setText(report);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Year must be a valid number.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generateStatisticsReport() {
        String report = reportService.generateStatisticsReport();
        reportArea.setText(report);
    }
}


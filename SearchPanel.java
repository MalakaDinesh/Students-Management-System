package gui;

import model.Student;
import service.StudentService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for searching students by various criteria
 */
public class SearchPanel extends JPanel {
    private StudentService studentService;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JTextField idField, nameField, courseField, yearField;
    private JButton searchButton, clearButton;
    
    public SearchPanel(StudentService studentService) {
        this.studentService = studentService;
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        // Table
        String[] columnNames = {"ID", "Name", "Course", "Year", "Email", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(tableModel);
        
        // Search fields
        idField = new JTextField(20);
        nameField = new JTextField(20);
        courseField = new JTextField(20);
        yearField = new JTextField(20);
        
        // Buttons
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Top panel - Search form
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("Search Students");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        searchPanel.add(titleLabel, gbc);
        
        // Search fields
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        int row = 1;
        addSearchField(searchPanel, gbc, "Search by ID:", idField, row++);
        addSearchField(searchPanel, gbc, "Search by Name:", nameField, row++);
        addSearchField(searchPanel, gbc, "Search by Course:", courseField, row++);
        addSearchField(searchPanel, gbc, "Search by Year:", yearField, row++);
        
        // Info label
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel infoLabel = new JLabel("<html><center>You can search by any combination of criteria.<br>Leave fields empty to search all.</center></html>");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        searchPanel.add(infoLabel, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(buttonPanel, gbc);
        
        // Center - Results table
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        
        // Layout
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void addSearchField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }
    
    private void setupListeners() {
        searchButton.addActionListener(e -> performSearch());
        clearButton.addActionListener(e -> clearSearch());
        
        // Enter key support
        idField.addActionListener(e -> performSearch());
        nameField.addActionListener(e -> performSearch());
        courseField.addActionListener(e -> performSearch());
        yearField.addActionListener(e -> performSearch());
    }
    
    private void performSearch() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String course = courseField.getText().trim();
        String yearStr = yearField.getText().trim();
        
        Integer year = null;
        if (!yearStr.isEmpty()) {
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Year must be a valid number.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // Convert empty strings to null
        id = id.isEmpty() ? null : id;
        name = name.isEmpty() ? null : name;
        course = course.isEmpty() ? null : course;
        
        List<Student> results = studentService.search(id, name, course, year);
        displayResults(results);
        
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No students found matching the search criteria.",
                    "No Results",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void clearSearch() {
        idField.setText("");
        nameField.setText("");
        courseField.setText("");
        yearField.setText("");
        tableModel.setRowCount(0);
    }
    
    private void displayResults(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                student.getId(),
                student.getName(),
                student.getCourse(),
                student.getYear(),
                student.getEmail(),
                student.getPhone()
            });
        }
    }
}


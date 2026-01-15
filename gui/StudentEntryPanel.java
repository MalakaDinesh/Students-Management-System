package gui;

import model.Student;
import service.StudentService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for Student CRUD operations
 */
public class StudentEntryPanel extends JPanel {
    private StudentService studentService;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField idField, nameField, courseField, yearField, emailField, phoneField;
    private JButton addButton, updateButton, deleteButton, clearButton;
    
    public StudentEntryPanel(StudentService studentService) {
        this.studentService = studentService;
        initializeComponents();
        setupLayout();
        setupListeners();
        loadStudents();
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
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedStudent();
            }
        });
        
        // Text fields
        idField = new JTextField(20);
        nameField = new JTextField(20);
        courseField = new JTextField(20);
        yearField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        
        // Buttons
        addButton = new JButton("Add Student");
        updateButton = new JButton("Update Student");
        deleteButton = new JButton("Delete Student");
        clearButton = new JButton("Clear");
        
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Top panel - Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Form fields
        int row = 0;
        addFormField(formPanel, gbc, "Student ID:", idField, row++);
        addFormField(formPanel, gbc, "Name:", nameField, row++);
        addFormField(formPanel, gbc, "Course:", courseField, row++);
        addFormField(formPanel, gbc, "Year:", yearField, row++);
        addFormField(formPanel, gbc, "Email:", emailField, row++);
        addFormField(formPanel, gbc, "Phone:", phoneField, row++);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);
        
        // Center - Table
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        
        // Layout
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int row) {
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
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearForm());
    }
    
    private void addStudent() {
        if (!validateInput()) {
            return;
        }
        
        Student student = createStudentFromForm();
        if (studentService.addStudent(student)) {
            JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadStudents();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Student with this ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateStudent() {
        if (!validateInput()) {
            return;
        }
        
        Student student = createStudentFromForm();
        if (studentService.updateStudent(student)) {
            JOptionPane.showMessageDialog(this, "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadStudents();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteStudent() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete student with ID: " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (studentService.deleteStudent(id)) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadStudents();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        courseField.setText("");
        yearField.setText("");
        emailField.setText("");
        phoneField.setText("");
        studentTable.clearSelection();
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    
    private boolean validateInput() {
        if (idField.getText().trim().isEmpty() ||
            nameField.getText().trim().isEmpty() ||
            courseField.getText().trim().isEmpty() ||
            yearField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            int year = Integer.parseInt(yearField.getText().trim());
            if (year < 1 || year > 10) {
                JOptionPane.showMessageDialog(this, "Year must be between 1 and 10.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Year must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private Student createStudentFromForm() {
        return new Student(
            idField.getText().trim(),
            nameField.getText().trim(),
            courseField.getText().trim(),
            Integer.parseInt(yearField.getText().trim()),
            emailField.getText().trim(),
            phoneField.getText().trim()
        );
    }
    
    private void loadSelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            courseField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            yearField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            emailField.setText(tableModel.getValueAt(selectedRow, 4).toString());
            phoneField.setText(tableModel.getValueAt(selectedRow, 5).toString());
            
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);
        }
    }
    
    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = studentService.getAllStudents();
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


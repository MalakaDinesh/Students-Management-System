package data;

import model.Student;
import java.util.List;

/**
 * Abstract class demonstrating Abstraction
 * Defines the contract for data operations without implementation details
 */
public abstract class DataHandler {
    
    /**
     * Load all students from data source
     */
    public abstract List<Student> loadStudents();
    
    /**
     * Save all students to data source
     */
    public abstract void saveStudents(List<Student> students);
    
    /**
     * Add a new student
     */
    public abstract boolean addStudent(Student student);
    
    /**
     * Update an existing student
     */
    public abstract boolean updateStudent(Student student);
    
    /**
     * Delete a student by ID
     */
    public abstract boolean deleteStudent(String id);
    
    /**
     * Find a student by ID
     */
    public abstract Student findStudentById(String id);
    
    /**
     * Get all students
     */
    public abstract List<Student> getAllStudents();
}


package service;

import data.DataHandler;
import data.FileDataHandler;
import model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for student operations
 * Demonstrates polymorphism through method overloading for search
 */
public class StudentService {
    private DataHandler dataHandler;
    
    public StudentService() {
        this.dataHandler = new FileDataHandler();
    }
    
    // CRUD Operations
    public boolean addStudent(Student student) {
        return dataHandler.addStudent(student);
    }
    
    public boolean updateStudent(Student student) {
        return dataHandler.updateStudent(student);
    }
    
    public boolean deleteStudent(String id) {
        return dataHandler.deleteStudent(id);
    }
    
    public Student getStudentById(String id) {
        return dataHandler.findStudentById(id);
    }
    
    public List<Student> getAllStudents() {
        return dataHandler.getAllStudents();
    }
    
    // Search operations demonstrating Polymorphism (method overloading)
    public List<Student> searchById(String id) {
        Student student = dataHandler.findStudentById(id);
        List<Student> results = new ArrayList<>();
        if (student != null) {
            results.add(student);
        }
        return results;
    }
    
    public List<Student> searchByName(String name) {
        return dataHandler.getAllStudents().stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public List<Student> searchByCourse(String course) {
        return dataHandler.getAllStudents().stream()
                .filter(s -> s.getCourse().toLowerCase().contains(course.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public List<Student> searchByYear(int year) {
        return dataHandler.getAllStudents().stream()
                .filter(s -> s.getYear() == year)
                .collect(Collectors.toList());
    }
    
    // Combined search
    public List<Student> search(String id, String name, String course, Integer year) {
        List<Student> allStudents = dataHandler.getAllStudents();
        
        return allStudents.stream()
                .filter(s -> id == null || id.isEmpty() || s.getId().toLowerCase().contains(id.toLowerCase()))
                .filter(s -> name == null || name.isEmpty() || s.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(s -> course == null || course.isEmpty() || s.getCourse().toLowerCase().contains(course.toLowerCase()))
                .filter(s -> year == null || s.getYear() == year)
                .collect(Collectors.toList());
    }
}


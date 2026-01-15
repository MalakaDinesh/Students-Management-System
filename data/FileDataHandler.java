package data;

import model.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * File-based data handler demonstrating Inheritance
 * Extends abstract DataHandler and provides concrete implementation
 */
public class FileDataHandler extends DataHandler {
    private static final String DATA_FILE = "students.dat";
    private List<Student> students;
    
    public FileDataHandler() {
        this.students = new ArrayList<>();
        loadStudents();
    }
    
    @Override
    public List<Student> loadStudents() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            @SuppressWarnings("unchecked")
            List<Student> loadedStudents = (List<Student>) ois.readObject();
            students = loadedStudents;
            return students;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading students: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @Override
    public void saveStudents(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
            this.students = students;
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }
    
    @Override
    public boolean addStudent(Student student) {
        if (findStudentById(student.getId()) != null) {
            return false; // Student with this ID already exists
        }
        students.add(student);
        saveStudents(students);
        return true;
    }
    
    @Override
    public boolean updateStudent(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.set(i, student);
                saveStudents(students);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean deleteStudent(String id) {
        Student student = findStudentById(id);
        if (student != null) {
            students.remove(student);
            saveStudents(students);
            return true;
        }
        return false;
    }
    
    @Override
    public Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }
    
    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
}


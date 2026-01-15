package model;

import java.io.Serializable;

/**
 * Student model class demonstrating Encapsulation
 * All fields are private and accessed through getters/setters
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String course;
    private int year;
    private String email;
    private String phone;
    
    // Constructor
    public Student(String id, String name, String course, int year, String email, String phone) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.year = year;
        this.email = email;
        this.phone = phone;
    }
    
    // Default constructor
    public Student() {
    }
    
    // Getters and Setters (Encapsulation)
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCourse() {
        return course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", year=" + year +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return id != null && id.equals(student.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}


package service;

import model.Student;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Report Service for generating various reports
 */
public class ReportService {
    private StudentService studentService;
    
    public ReportService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    /**
     * Generate report of all students
     */
    public String generateAllStudentsReport() {
        List<Student> students = studentService.getAllStudents();
        StringBuilder report = new StringBuilder();
        
        report.append("=".repeat(80)).append("\n");
        report.append("ALL STUDENTS REPORT\n");
        report.append("=".repeat(80)).append("\n");
        report.append(String.format("%-10s %-25s %-20s %-5s %-25s %-15s\n", 
                "ID", "Name", "Course", "Year", "Email", "Phone"));
        report.append("-".repeat(80)).append("\n");
        
        if (students.isEmpty()) {
            report.append("No students found.\n");
        } else {
            for (Student student : students) {
                report.append(String.format("%-10s %-25s %-20s %-5d %-25s %-15s\n",
                        student.getId(),
                        student.getName(),
                        student.getCourse(),
                        student.getYear(),
                        student.getEmail(),
                        student.getPhone()));
            }
        }
        
        report.append("=".repeat(80)).append("\n");
        report.append("Total Students: ").append(students.size()).append("\n");
        
        return report.toString();
    }
    
    /**
     * Generate report by course
     */
    public String generateCourseReport(String course) {
        List<Student> students = studentService.searchByCourse(course);
        StringBuilder report = new StringBuilder();
        
        report.append("=".repeat(80)).append("\n");
        report.append("STUDENTS BY COURSE: ").append(course.toUpperCase()).append("\n");
        report.append("=".repeat(80)).append("\n");
        report.append(String.format("%-10s %-25s %-5s %-25s %-15s\n", 
                "ID", "Name", "Year", "Email", "Phone"));
        report.append("-".repeat(80)).append("\n");
        
        if (students.isEmpty()) {
            report.append("No students found for course: ").append(course).append("\n");
        } else {
            for (Student student : students) {
                report.append(String.format("%-10s %-25s %-5d %-25s %-15s\n",
                        student.getId(),
                        student.getName(),
                        student.getYear(),
                        student.getEmail(),
                        student.getPhone()));
            }
        }
        
        report.append("=".repeat(80)).append("\n");
        report.append("Total Students: ").append(students.size()).append("\n");
        
        return report.toString();
    }
    
    /**
     * Generate report by year
     */
    public String generateYearReport(int year) {
        List<Student> students = studentService.searchByYear(year);
        StringBuilder report = new StringBuilder();
        
        report.append("=".repeat(80)).append("\n");
        report.append("STUDENTS BY YEAR: ").append(year).append("\n");
        report.append("=".repeat(80)).append("\n");
        report.append(String.format("%-10s %-25s %-20s %-25s %-15s\n", 
                "ID", "Name", "Course", "Email", "Phone"));
        report.append("-".repeat(80)).append("\n");
        
        if (students.isEmpty()) {
            report.append("No students found for year: ").append(year).append("\n");
        } else {
            for (Student student : students) {
                report.append(String.format("%-10s %-25s %-20s %-25s %-15s\n",
                        student.getId(),
                        student.getName(),
                        student.getCourse(),
                        student.getEmail(),
                        student.getPhone()));
            }
        }
        
        report.append("=".repeat(80)).append("\n");
        report.append("Total Students: ").append(students.size()).append("\n");
        
        return report.toString();
    }
    
    /**
     * Generate statistics report
     */
    public String generateStatisticsReport() {
        List<Student> students = studentService.getAllStudents();
        StringBuilder report = new StringBuilder();
        
        report.append("=".repeat(80)).append("\n");
        report.append("STATISTICS REPORT\n");
        report.append("=".repeat(80)).append("\n");
        
        report.append("Total Students: ").append(students.size()).append("\n\n");
        
        // Count by course
        Map<String, Long> byCourse = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.counting()));
        
        report.append("Students by Course:\n");
        report.append("-".repeat(40)).append("\n");
        byCourse.forEach((course, count) -> 
            report.append(String.format("%-30s: %d\n", course, count)));
        
        // Count by year
        Map<Integer, Long> byYear = students.stream()
                .collect(Collectors.groupingBy(Student::getYear, Collectors.counting()));
        
        report.append("\nStudents by Year:\n");
        report.append("-".repeat(40)).append("\n");
        byYear.forEach((year, count) -> 
            report.append(String.format("%-30s: %d\n", "Year " + year, count)));
        
        report.append("=".repeat(80)).append("\n");
        
        return report.toString();
    }
}


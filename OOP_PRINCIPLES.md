# OOP Principles Demonstration

This document explains how each Object-Oriented Programming principle is demonstrated in the Student Information System.

## 1. Encapsulation

**Definition**: Bundling data and methods that operate on that data within a single unit, and restricting access to some of the object's components.

### Examples in the Project:

#### Student.java
- All fields (`id`, `name`, `course`, `year`, `email`, `phone`) are declared as `private`
- Access to these fields is controlled through public getter and setter methods
- This ensures data integrity and prevents unauthorized modification

```java
private String id;
private String name;
// ... other private fields

public String getId() { return id; }
public void setId(String id) { this.id = id; }
```

#### User.java
- Similar encapsulation pattern with private fields and public accessors
- Password field is protected from direct access

## 2. Inheritance

**Definition**: Mechanism where a new class is derived from an existing class, inheriting its properties and methods.

### Examples in the Project:

#### DataHandler.java (Abstract Base Class)
```java
public abstract class DataHandler {
    public abstract List<Student> loadStudents();
    public abstract void saveStudents(List<Student> students);
    // ... other abstract methods
}
```

#### FileDataHandler.java (Derived Class)
```java
public class FileDataHandler extends DataHandler {
    // Implements all abstract methods from DataHandler
    @Override
    public List<Student> loadStudents() { /* implementation */ }
    @Override
    public void saveStudents(List<Student> students) { /* implementation */ }
}
```

**Benefits**:
- Code reusability
- Extensibility (can create DatabaseDataHandler, XMLDataHandler, etc.)
- Polymorphic behavior

## 3. Polymorphism

**Definition**: Ability of objects of different types to be accessed through the same interface, or ability to process objects differently based on their data type.

### Examples in the Project:

#### Method Overloading in StudentService.java
Multiple methods with the same name but different parameters:

```java
// Search by ID
public List<Student> searchById(String id) { /* ... */ }

// Search by Name
public List<Student> searchByName(String name) { /* ... */ }

// Search by Course
public List<Student> searchByCourse(String course) { /* ... */ }

// Search by Year
public List<Student> searchByYear(int year) { /* ... */ }
```

**Runtime Polymorphism**:
- `DataHandler` reference can point to `FileDataHandler` instance
- Method calls are resolved at runtime based on actual object type

```java
DataHandler handler = new FileDataHandler(); // Polymorphic reference
handler.loadStudents(); // Calls FileDataHandler's implementation
```

## 4. Abstraction

**Definition**: Hiding implementation details and showing only essential features of an object.

### Examples in the Project:

#### DataHandler.java (Abstract Class)
- Defines the contract (what operations are available) without implementation details
- Hides how data is actually stored (file, database, etc.)
- Provides a clean interface for data operations

```java
public abstract class DataHandler {
    // Abstract methods - no implementation, just contract
    public abstract List<Student> loadStudents();
    public abstract void saveStudents(List<Student> students);
    public abstract boolean addStudent(Student student);
    // ...
}
```

**Benefits**:
- Separation of concerns
- Easy to swap implementations (file-based to database-based)
- Client code doesn't need to know storage details
- Easier testing and maintenance

## Summary

| Principle | Location | Example |
|-----------|----------|---------|
| **Encapsulation** | `model/Student.java`, `model/User.java` | Private fields with public getters/setters |
| **Inheritance** | `data/FileDataHandler.java` extends `data/DataHandler.java` | FileDataHandler inherits from abstract DataHandler |
| **Polymorphism** | `service/StudentService.java` | Method overloading for search operations |
| **Abstraction** | `data/DataHandler.java` | Abstract class defining data operation contract |

## Real-World Application

These principles work together to create:
- **Maintainable code**: Changes to data storage don't affect GUI code
- **Extensible system**: Easy to add new search methods or data handlers
- **Secure data**: Encapsulation protects student information
- **Flexible design**: Can switch from file-based to database storage without changing other components


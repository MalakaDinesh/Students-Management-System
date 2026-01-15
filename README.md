# Student Information System

A comprehensive Java Swing desktop application demonstrating core Object-Oriented Programming (OOP) principles including Encapsulation, Inheritance, Polymorphism, and Abstraction.

## Features

### 1. Authentication System
- Secure login/logout functionality
- User session management
- Default credentials: `admin` / `admin123`

### 2. Student Data Entry (CRUD Operations)
- **Create**: Add new students with complete information
- **Read**: View all students in a table format
- **Update**: Modify existing student records
- **Delete**: Remove students from the system
- Fields: Student ID, Name, Course, Year, Email, Phone

### 3. Advanced Search Functionality
- Search by Student ID
- Search by Name (partial match supported)
- Search by Course
- Search by Year
- Combined search with multiple criteria
- Real-time results display

### 4. Comprehensive Reporting
- **All Students Report**: Complete list of all registered students
- **Course Report**: Students grouped by course
- **Year Report**: Students grouped by academic year
- **Statistics Report**: Summary statistics including:
  - Total student count
  - Distribution by course
  - Distribution by year

## OOP Principles Demonstrated

### 1. Encapsulation
- **Student.java**: All fields are private with public getters/setters
- **User.java**: Private fields accessed only through methods
- Data integrity maintained through controlled access

### 2. Inheritance
- **FileDataHandler.java**: Extends abstract `DataHandler` class
- Inherits method signatures and provides concrete implementation
- Demonstrates code reusability and extensibility

### 3. Polymorphism
- **StudentService.java**: Method overloading for search operations
  - `searchById(String id)`
  - `searchByName(String name)`
  - `searchByCourse(String course)`
  - `searchByYear(int year)`
- Same method name, different parameters
- Runtime method resolution based on arguments

### 4. Abstraction
- **DataHandler.java**: Abstract class defining contract for data operations
- Hides implementation details
- Provides interface for data persistence
- Allows for different implementations (file-based, database, etc.)

## Project Structure

```
src/
├── Main.java                    # Application entry point
├── model/
│   ├── Student.java            # Student model (Encapsulation)
│   └── User.java               # User model (Encapsulation)
├── data/
│   ├── DataHandler.java        # Abstract data handler (Abstraction)
│   └── FileDataHandler.java   # File-based implementation (Inheritance)
├── auth/
│   └── AuthenticationService.java  # Authentication logic
├── service/
│   ├── StudentService.java     # Student operations (Polymorphism)
│   └── ReportService.java      # Report generation
└── gui/
    ├── LoginFrame.java         # Login window
    ├── MainFrame.java          # Main application window
    ├── StudentEntryPanel.java  # CRUD operations panel
    ├── SearchPanel.java        # Search functionality panel
    └── ReportPanel.java        # Reports panel
```

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, NetBeans) or command line

### Compilation

#### Using Command Line:
```bash
# Navigate to project root directory
cd "Student management system"

# Compile all Java files
javac -d bin src/**/*.java src/*.java

# Run the application
java -cp bin Main
```

#### Using IDE:
1. Import the project into your IDE
2. Set `src` as source directory
3. Run `Main.java`

### Running the Application

1. Launch the application
2. Login with default credentials:
   - Username: `admin`
   - Password: `admin123`
3. Use the tabbed interface to navigate between:
   - **Student Entry**: Manage student records
   - **Search**: Find students by various criteria
   - **Reports**: Generate and view reports

## Data Persistence

- Student data is stored in `students.dat` (binary serialization)
- User data is stored in `users.dat` (binary serialization)
- Files are created automatically in the project root directory
- Data persists between application sessions

## Usage Examples

### Adding a Student
1. Go to "Student Entry" tab
2. Fill in all fields (ID, Name, Course, Year, Email, Phone)
3. Click "Add Student"
4. Student appears in the table below

### Searching Students
1. Go to "Search" tab
2. Enter search criteria (any combination)
3. Click "Search" or press Enter
4. Results display in the table

### Generating Reports
1. Go to "Reports" tab
2. Click desired report button:
   - "All Students Report" - Complete list
   - "Course Report" - Enter course name and click button
   - "Year Report" - Enter year and click button
   - "Statistics Report" - Summary statistics
3. Report displays in the text area

## Technical Details

- **GUI Framework**: Java Swing
- **Data Storage**: Java Serialization (binary files)
- **Architecture**: MVC-like pattern with separation of concerns
- **Design Patterns**: Service Layer, Data Access Layer

## Future Enhancements

- Database integration (MySQL, PostgreSQL)
- Export reports to PDF/Excel
- Advanced filtering options
- Student photo upload
- Academic records tracking
- Grade management

## Author

Student Information System - OOP Demonstration Project

## License

This project is created for educational purposes to demonstrate OOP principles in Java.


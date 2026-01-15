package auth;

import model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Authentication Service for login/logout functionality
 */
public class AuthenticationService {
    private static final String USERS_FILE = "users.dat";
    private User currentUser;
    private List<User> users;
    
    public AuthenticationService() {
        this.users = loadUsers();
        // Create default admin user if no users exist
        if (users.isEmpty()) {
            users.add(new User("admin", "admin123", "admin"));
            saveUsers(users);
        }
    }
    
    private List<User> loadUsers() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            @SuppressWarnings("unchecked")
            List<User> users = (List<User>) ois.readObject();
            return users;
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
    
    private void saveUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean registerUser(String username, String password, String role) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // Username already exists
            }
        }
        users.add(new User(username, password, role));
        saveUsers(users);
        return true;
    }
}


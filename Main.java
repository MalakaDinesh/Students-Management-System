import auth.AuthenticationService;
import gui.LoginFrame;
import gui.MainFrame;
import javax.swing.*;

/**
 * Main entry point for the Student Information System
 * Demonstrates OOP principles: Encapsulation, Inheritance, Polymorphism, Abstraction
 */
public class Main {
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create authentication service
        AuthenticationService authService = new AuthenticationService();
        
        // Show login frame
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(authService);
            loginFrame.setLoginListener(() -> {
                MainFrame mainFrame = new MainFrame(authService);
                mainFrame.setVisible(true);
            });
            loginFrame.setVisible(true);
        });
    }
}


package gui;

import auth.AuthenticationService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login Frame for user authentication
 */
public class LoginFrame extends JFrame {
    private AuthenticationService authService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private LoginListener loginListener;
    
    public interface LoginListener {
        void onLoginSuccess();
    }
    
    public LoginFrame(AuthenticationService authService) {
        this.authService = authService;
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        setTitle("Student Information System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("Student Information System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(passwordField, gbc);
        
        // Login button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton.setPreferredSize(new Dimension(150, 30));
        mainPanel.add(loginButton, gbc);
        
        // Default credentials label
        gbc.gridy = 4;
        JLabel defaultLabel = new JLabel("<html><center>Default: admin / admin123</center></html>");
        defaultLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        mainPanel.add(defaultLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Please enter both username and password.",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (authService.login(username, password)) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Login successful! Welcome " + username,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    if (loginListener != null) {
                        loginListener.onLoginSuccess();
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Invalid username or password.",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                }
            }
        });
        
        // Enter key support
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick();
            }
        });
    }
    
    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }
}


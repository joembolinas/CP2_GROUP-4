package com.motor.ph;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    public LoginPage() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        // Title
        JLabel titleLabel = new JLabel("Sign in");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        // Create account link
        JLabel createAccountLabel = new JLabel("MotorPH Employee Login");
        createAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(createAccountLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));


        // Email field
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setBorder(BorderFactory.createTitledBorder("Email or Employee ID"));
        mainPanel.add(emailField);

        // Password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        mainPanel.add(passwordField);

        // Remember me checkbox
        JCheckBox rememberMe = new JCheckBox("Remember me");
        rememberMe.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(rememberMe);

        mainPanel.add(Box.createRigidArea(new Dimension( 0, 10)));

        // Sign in button
        JButton signInButton = new JButton("Sign in");
        signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(signInButton);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Sign in with GitHub
        JButton githubButton = new JButton("Sign in with Github");
        githubButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(githubButton);

        // Forgot password
        JLabel forgotLabel = new JLabel("Forgot your password?");
        forgotLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        forgotLabel.setForeground(Color.RED);
        forgotLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(forgotLabel);

        add(mainPanel, BorderLayout.CENTER);       
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }

}
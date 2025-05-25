package com.motorph.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.motorph.repository.CredentialManager;

public class LogIn extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private boolean authenticated = false;

    public LogIn(Frame owner) {
        super(owner, "Login", true);
        setSize(500, 500);
        setLocationRelativeTo(owner);

        Font biggerFont = new Font("SansSerif", Font.PLAIN, 18);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Image label above username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        ImageIcon userIcon = new ImageIcon("C:\\Users\\Karen\\Pictures\\Screenshots\\motorphlogo.png");
        JLabel userImageLabel = new JLabel(userIcon);
        add(userImageLabel, gbc);

        gbc.gridwidth = 1;

        // Username label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(biggerFont);
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        usernameField = new JTextField(15);
        usernameField.setFont(biggerFont);
        add(usernameField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(biggerFont);
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(15);
        passwordField.setFont(biggerFont);
        add(passwordField, gbc);

        // Login button aligned right
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        JButton loginButton = new JButton("Login");
        loginButton.setFont(biggerFont);
        loginButton.addActionListener(this::handleLogin);
        add(loginButton, gbc);

        // Set the login button as the default button
        getRootPane().setDefaultButton(loginButton);

        // Add key listener to password field to trigger login on Enter key press
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin(null);
                }
            }
        });

        setVisible(true);
    }

private void handleLogin(ActionEvent e) {
    String username = usernameField.getText().trim();
    String password = new String(passwordField.getPassword()).trim();

    String[] employeeRecord = CredentialManager.authenticate(username, password);

    if (employeeRecord != null) {
        if (password.equals("1234")) {
            String newPassword = JOptionPane.showInputDialog(this,
                    "First-time login detected. Please enter a new password:");

            if (newPassword != null && !newPassword.trim().isEmpty()) {
                boolean success = CredentialManager.updatePassword(username, newPassword.trim());
                if (success) {
                    JOptionPane.showMessageDialog(this, "Password updated successfully. Please log in again.");
                    // Clear fields for re-login
                    usernameField.setText("");
                    passwordField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update password.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Password not updated. Try again.");
            }
        } else {
            authenticated = true;
            dispose(); // <--- THIS closes the dialog and continues
        }
    } else {
        JOptionPane.showMessageDialog(this, "Invalid username or password.");
    }
}



    public boolean isAuthenticated() {
        return authenticated;
    }
}

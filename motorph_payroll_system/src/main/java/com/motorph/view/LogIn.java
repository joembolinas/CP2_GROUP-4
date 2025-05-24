/*package com.motorph.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LogIn extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private boolean authenticated = false;

    public LogIn(Frame owner) {
        super(owner, "Login", true);
        setSize(300, 150);
        setLocationRelativeTo(owner);

        // Use GridBagLayout for precise control
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding around components

       // Image label above username
       // gbc.gridx = 0;
       // gbc.gridy = 0;
       // ImageIcon userIcon = new ImageIcon(getClass().getResource("/resources/motorphlogo.png"));
       // JLabel userImageLabel = new JLabel(userIcon);
       // add(userImageLabel, gbc);

        // Username label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        add(usernameField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        // Login button aligned to the right
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST; // Align to the right
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::handleLogin);
        add(loginButton, gbc);

        setVisible(true);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Simple authentication logic
        if ("admin".equals(username) && "admin".equals(password)) {
            authenticated = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials, please try again.");
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public class YourClass {
    public YourClass() {
        // Create a JLabel
        JLabel label = new JLabel("Your Label");

        // Load the image icon
        URL imgURL = getClass().getResource("/path/to/your/image.png");
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            label.setIcon(icon);
        } else {
            System.err.println("Couldn't find file: /path/to/your/image.png");
        }

        // Additional code to add the label to a container and display the GUI
    }
}




}*/

package com.motorph.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if ("admin".equals(username) && "0000".equals(password)) {
            authenticated = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials, please try again.");
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}

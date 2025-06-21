package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.motorph.controller.AuthenticationController;
import com.motorph.util.UIConstants;

/**
 * Login panel for the MotorPH Payroll System.
 * Implements MPHCR-04 requirement for user authentication.
 */
public class LoginPanel extends JPanel {
    private final AuthenticationController authController;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel statusLabel;
    private final LoginCallback callback;

    /**
     * Interface for login callback
     */
    public interface LoginCallback {
        void onLoginSuccess();

        void onExit();
    }

    /**
     * Constructor for LoginPanel
     * 
     * @param authController The authentication controller
     * @param callback       The callback interface
     */
    public LoginPanel(AuthenticationController authController, LoginCallback callback) {
        this.authController = authController;
        this.callback = callback;
        initPanel();
    }

    /**
     * Initialize the login panel
     */
    private void initPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND_COLOR);

        // Main content panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();

        // Logo panel
        JPanel logoPanel = createLogoPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(logoPanel, gbc);

        // Title label
        JLabel titleLabel = new JLabel("MotorPH Payroll System", SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(titleLabel, gbc);

        // Subtitle label
        JLabel subtitleLabel = new JLabel("Please login to continue", SwingConstants.CENTER);
        subtitleLabel.setFont(UIConstants.NORMAL_FONT);
        subtitleLabel.setForeground(UIConstants.TEXT_COLOR);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(subtitleLabel, gbc);

        // Login form panel
        JPanel formPanel = createFormPanel();
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(formPanel, gbc);

        // Status label
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(UIConstants.SMALL_FONT);
        statusLabel.setForeground(UIConstants.DELETE_BUTTON_COLOR);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(statusLabel, gbc);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Set focus to username field
        usernameField.requestFocusInWindow();
    }

    /**
     * Create the logo panel
     */
    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        try {
            // Try to load the logo image from different possible locations
            String[] logoPaths = {
                    "motorPH_logo.png", // Current directory
                    "../motorPH_logo.png", // Parent directory
                    "../../motorPH_logo.png", // Two levels up
                    "src/main/resources/motorPH_logo.png", // Resources directory
                    "motorph_payroll_system/motorPH_logo.png" // Alternative path
            };

            ImageIcon logoIcon = null;
            for (String logoPath : logoPaths) {
                File logoFile = new File(logoPath);
                if (logoFile.exists()) {
                    ImageIcon originalIcon = new ImageIcon(logoPath);
                    Image originalImage = originalIcon.getImage();

                    // Scale the image to appropriate size
                    Image scaledImage = originalImage.getScaledInstance(120, 80, Image.SCALE_SMOOTH);
                    logoIcon = new ImageIcon(scaledImage);
                    break;
                }
            }

            if (logoIcon != null) {
                JLabel logoLabel = new JLabel(logoIcon);
                logoPanel.add(logoLabel);
            } else {
                // Fallback if logo file is not found
                JLabel logoLabel = new JLabel("MotorPH");
                logoLabel.setFont(UIConstants.TITLE_FONT);
                logoLabel.setForeground(UIConstants.BUTTON_COLOR);
                logoPanel.add(logoLabel);
            }
        } catch (Exception e) {
            // Fallback if image loading fails
            JLabel logoLabel = new JLabel("MotorPH");
            logoLabel.setFont(UIConstants.TITLE_FONT);
            logoLabel.setForeground(UIConstants.BUTTON_COLOR);
            logoPanel.add(logoLabel);
        }

        return logoPanel;
    }

    /**
     * Create the login form panel
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UIConstants.PANEL_BACKGROUND);
        formPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();

        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(UIConstants.NORMAL_FONT);
        usernameLabel.setForeground(UIConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 10);
        formPanel.add(usernameLabel, gbc);

        // Username field
        usernameField = new JTextField(20);
        usernameField.setFont(UIConstants.NORMAL_FONT);
        usernameField.setPreferredSize(new Dimension(200, UIConstants.FIELD_HEIGHT));
        usernameField.addKeyListener(new EnterKeyListener());
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(usernameField, gbc);

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(UIConstants.NORMAL_FONT);
        passwordLabel.setForeground(UIConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(15, 5, 5, 10);
        formPanel.add(passwordLabel, gbc);

        // Password field
        passwordField = new JPasswordField(20);
        passwordField.setFont(UIConstants.NORMAL_FONT);
        passwordField.setPreferredSize(new Dimension(200, UIConstants.FIELD_HEIGHT));
        passwordField.addKeyListener(new EnterKeyListener());
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passwordField, gbc);

        return formPanel;
    }

    /**
     * Create the button panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(UIConstants.BUTTON_FONT);
        loginButton.setBackground(UIConstants.BUTTON_COLOR);
        loginButton.setForeground(UIConstants.BUTTON_TEXT_COLOR);
        loginButton.setPreferredSize(new Dimension(120, UIConstants.BUTTON_HEIGHT));
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> performLogin());

        // Add hover effect for login button
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(UIConstants.BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(UIConstants.BUTTON_COLOR);
            }
        });

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.setFont(UIConstants.BUTTON_FONT);
        exitButton.setBackground(UIConstants.SECONDARY_BUTTON_COLOR);
        exitButton.setForeground(UIConstants.BUTTON_TEXT_COLOR);
        exitButton.setPreferredSize(new Dimension(120, UIConstants.BUTTON_HEIGHT));
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.addActionListener(e -> performExit());

        // Add hover effect for exit button
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(UIConstants.SECONDARY_BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(UIConstants.SECONDARY_BUTTON_COLOR);
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    /**
     * Perform login action
     */
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Clear status label
        statusLabel.setText(" ");

        // Validate input
        if (username.isEmpty()) {
            showStatus("Please enter your username");
            usernameField.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            showStatus("Please enter your password");
            passwordField.requestFocus();
            return;
        }

        // Disable login button during authentication
        loginButton.setEnabled(false);
        loginButton.setText("Logging in...");

        try {
            // Attempt authentication
            if (authController.login(username, password)) {
                showStatus("Login successful!");
                // Clear password field for security
                passwordField.setText("");

                // Notify callback of successful login
                if (callback != null) {
                    callback.onLoginSuccess();
                }
            } else {
                showStatus("Invalid username or password");
                passwordField.setText("");
                passwordField.requestFocus();
            }
        } catch (Exception e) {
            showStatus("Login error: " + e.getMessage());
        } finally {
            // Re-enable login button
            loginButton.setEnabled(true);
            loginButton.setText("Login");
        }
    }

    /**
     * Perform exit action
     */
    private void performExit() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            if (callback != null) {
                callback.onExit();
            }
        }
    }

    /**
     * Show status message
     */
    private void showStatus(String message) {
        statusLabel.setText(message);
    }

    /**
     * Clear the login form
     */
    public void clearForm() {
        usernameField.setText("");
        passwordField.setText("");
        statusLabel.setText(" ");
        usernameField.requestFocus();
    }

    /**
     * Set focus to username field
     */
    public void setFocusToUsername() {
        usernameField.requestFocusInWindow();
    }

    /**
     * Key listener for Enter key on form fields
     */
    private class EnterKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                performLogin();
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // Not used
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Not used
        }
    }
}

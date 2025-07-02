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
import com.motorph.util.AppConstants;

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
        setBackground(AppConstants.BACKGROUND_COLOR);

        // Main content panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        // Logo panel
        JPanel logoPanel = createLogoPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(logoPanel, gbc);

        // Title label
        JLabel titleLabel = new JLabel("MotorPH Payroll System", SwingConstants.CENTER);
        titleLabel.setFont(AppConstants.TITLE_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(titleLabel, gbc);

        // Subtitle label
        JLabel subtitleLabel = new JLabel("Please login to continue", SwingConstants.CENTER);
        subtitleLabel.setFont(AppConstants.NORMAL_FONT);
        subtitleLabel.setForeground(AppConstants.TEXT_SECONDARY);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(subtitleLabel, gbc);

        // Login form panel
        JPanel formPanel = createFormPanel();
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        mainPanel.add(formPanel, gbc);

        // Status label
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(AppConstants.SMALL_FONT);
        statusLabel.setForeground(AppConstants.DELETE_BUTTON_COLOR);
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(statusLabel, gbc);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Set focus to username field
        javax.swing.SwingUtilities.invokeLater(() -> {
            usernameField.requestFocusInWindow();
        });
    }

    /**
     * Create the logo panel
     */
    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(AppConstants.BACKGROUND_COLOR);

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
                logoLabel.setFont(AppConstants.TITLE_FONT);
                logoLabel.setForeground(AppConstants.BUTTON_COLOR);
                logoPanel.add(logoLabel);
            }
        } catch (Exception e) {
            // Fallback if image loading fails
            JLabel logoLabel = new JLabel("MotorPH");
            logoLabel.setFont(AppConstants.TITLE_FONT);
            logoLabel.setForeground(AppConstants.BUTTON_COLOR);
            logoPanel.add(logoLabel);
        }

        return logoPanel;
    }

    /**
     * Create the login form panel
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(AppConstants.PANEL_BACKGROUND);
        formPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        // Remove fixed size constraint to allow natural sizing

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;

        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(AppConstants.NORMAL_FONT);
        usernameLabel.setForeground(AppConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        formPanel.add(usernameLabel, gbc);

        // Username field
        usernameField = new JTextField(20); // Set columns for proper sizing
        usernameField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        usernameField.setPreferredSize(new Dimension(350, 45));
        usernameField.setMinimumSize(new Dimension(350, 45));
        usernameField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        usernameField.setBackground(java.awt.Color.WHITE);
        usernameField.setForeground(AppConstants.TEXT_COLOR);
        usernameField.setOpaque(true);
        usernameField.setEditable(true);
        usernameField.setEnabled(true);
        usernameField.addKeyListener(new EnterKeyListener());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        formPanel.add(usernameField, gbc);

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(AppConstants.NORMAL_FONT);
        passwordLabel.setForeground(AppConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        formPanel.add(passwordLabel, gbc);

        // Password field
        passwordField = new JPasswordField(20); // Set columns for proper sizing
        passwordField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(350, 45));
        passwordField.setMinimumSize(new Dimension(350, 45));
        passwordField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        passwordField.setBackground(java.awt.Color.WHITE);
        passwordField.setForeground(AppConstants.TEXT_COLOR);
        passwordField.setOpaque(true);
        passwordField.setEditable(true);
        passwordField.setEnabled(true);
        passwordField.addKeyListener(new EnterKeyListener());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        formPanel.add(passwordField, gbc);

        return formPanel;
    }

    /**
     * Create the button panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(AppConstants.BUTTON_FONT);
        loginButton.setBackground(AppConstants.BUTTON_COLOR);
        loginButton.setForeground(AppConstants.BUTTON_TEXT_COLOR);
        loginButton.setPreferredSize(new Dimension(120, AppConstants.BUTTON_HEIGHT));
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> performLogin());

        // Add hover effect for login button
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(AppConstants.BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(AppConstants.BUTTON_COLOR);
            }
        });

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.setFont(AppConstants.BUTTON_FONT);
        exitButton.setBackground(AppConstants.SECONDARY_BUTTON_COLOR);
        exitButton.setForeground(AppConstants.BUTTON_TEXT_COLOR);
        exitButton.setPreferredSize(new Dimension(120, AppConstants.BUTTON_HEIGHT));
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.addActionListener(e -> performExit());

        // Add hover effect for exit button
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(AppConstants.SECONDARY_BUTTON_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(AppConstants.SECONDARY_BUTTON_COLOR);
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

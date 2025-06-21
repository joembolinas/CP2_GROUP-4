package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.motorph.controller.AuthenticationController;

/**
 * Login frame for the MotorPH Payroll System.
 * Implements MPHCR-04 requirement for user authentication.
 */
public class LoginFrame extends JFrame implements LoginPanel.LoginCallback {
    private static final Logger logger = Logger.getLogger(LoginFrame.class.getName());

    private final AuthenticationController authController;
    private LoginPanel loginPanel;
    private Runnable onLoginSuccessCallback;

    /**
     * Constructor for LoginFrame
     */
    public LoginFrame() {
        this.authController = new AuthenticationController();
        initFrame();
    }

    /**
     * Constructor with success callback
     * 
     * @param onLoginSuccessCallback Callback to execute on successful login
     */
    public LoginFrame(Runnable onLoginSuccessCallback) {
        this.authController = new AuthenticationController();
        this.onLoginSuccessCallback = onLoginSuccessCallback;
        initFrame();
    }

    /**
     * Initialize the login frame
     */
    private void initFrame() {
        setTitle("MotorPH Payroll System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and add login panel
        loginPanel = new LoginPanel(authController, this);
        add(loginPanel, BorderLayout.CENTER);

        // Set frame properties
        setSize(500, 600);
        setMinimumSize(new Dimension(450, 550));
        setLocationRelativeTo(null);
        setResizable(false);

        // Set icon if available
        try {
            setIconImage(new javax.swing.ImageIcon("motorPH_logo.png").getImage());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Could not load application icon", e);
        }

        logger.log(Level.INFO, "Login frame initialized");
    }

    /**
     * Callback for successful login
     */
    @Override
    public void onLoginSuccess() {
        logger.log(Level.INFO, "Login successful, transitioning to main application");

        // Hide login frame
        setVisible(false);

        // Execute success callback if provided
        if (onLoginSuccessCallback != null) {
            SwingUtilities.invokeLater(onLoginSuccessCallback);
        }

        // Dispose of login frame
        dispose();
    }

    /**
     * Callback for exit action
     */
    @Override
    public void onExit() {
        logger.log(Level.INFO, "User chose to exit application");
        System.exit(0);
    }

    /**
     * Show the login frame
     */
    public void showLogin() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            loginPanel.setFocusToUsername();
        });
    }

    /**
     * Get the authentication controller
     * 
     * @return The authentication controller
     */
    public AuthenticationController getAuthenticationController() {
        return authController;
    }

    /**
     * Clear the login form
     */
    public void clearLoginForm() {
        if (loginPanel != null) {
            loginPanel.clearForm();
        }
    }
}

package com.motorph.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.motorph.model.User;
import com.motorph.service.AuthenticationService;
import com.motorph.util.SessionManager;

/**
 * Controller for handling authentication operations.
 * Implements MPHCR-04 requirement for login functionality.
 */
public class AuthenticationController {
    private static final Logger logger = Logger.getLogger(AuthenticationController.class.getName());

    private final AuthenticationService authService;
    private final SessionManager sessionManager;

    /**
     * Constructor for AuthenticationController
     */
    public AuthenticationController() {
        this.authService = new AuthenticationService();
        this.sessionManager = SessionManager.getInstance();
    }

    /**
     * Constructor with custom authentication service (for testing)
     * 
     * @param authService The authentication service to use
     */
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
        this.sessionManager = SessionManager.getInstance();
    }

    /**
     * Perform user login
     * 
     * @param username The username to authenticate
     * @param password The password to authenticate
     * @return true if login successful, false otherwise
     */
    public boolean login(String username, String password) {
        try {
            logger.log(Level.INFO, "Login attempt for username: {0}", username);

            // Validate input
            if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
                logger.log(Level.WARNING, "Login attempt with empty credentials");
                return false;
            }

            // Attempt authentication
            User user = authService.authenticateUser(username, password);

            if (user != null) {
                // Set the current user in session
                sessionManager.setCurrentUser(user);
                logger.log(Level.INFO, "Login successful for user: {0}", username);
                return true;
            } else {
                logger.log(Level.WARNING, "Login failed for user: {0}", username);
                return false;
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Login error for user: " + username, e);
            return false;
        }
    }

    /**
     * Perform user logout
     * 
     * @return true if logout successful
     */
    public boolean logout() {
        try {
            User currentUser = sessionManager.getCurrentUser();
            if (currentUser != null) {
                logger.log(Level.INFO, "User {0} logging out", currentUser.getUsername());
                sessionManager.clearSession();
                logger.log(Level.INFO, "Logout successful for user: {0}", currentUser.getUsername());
                return true;
            } else {
                logger.log(Level.WARNING, "Logout attempted but no user was logged in");
                return false;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during logout", e);
            return false;
        }
    }

    /**
     * Check if a user is currently logged in
     * 
     * @return true if user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return sessionManager.isLoggedIn();
    }

    /**
     * Get the current logged-in user
     * 
     * @return Current user or null if no user is logged in
     */
    public User getCurrentUser() {
        return sessionManager.getCurrentUser();
    }

    /**
     * Get the current username
     * 
     * @return Username or "Guest" if no user is logged in
     */
    public String getCurrentUsername() {
        return sessionManager.getCurrentUsername();
    }

    /**
     * Check if the current user is an admin
     * 
     * @return true if current user is admin, false otherwise
     */
    public boolean isCurrentUserAdmin() {
        return sessionManager.isCurrentUserAdmin();
    }

    /**
     * Get session information
     * 
     * @return Session information string
     */
    public String getSessionInfo() {
        return sessionManager.getSessionInfo();
    }

    /**
     * Validate user credentials without logging in
     * 
     * @param username The username to validate
     * @param password The password to validate
     * @return true if credentials are valid, false otherwise
     */
    public boolean validateCredentials(String username, String password) {
        try {
            return authService.validateLogin(username, password);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error validating credentials for user: " + username, e);
            return false;
        }
    }

    /**
     * Get the authentication service (for testing purposes)
     * 
     * @return The authentication service
     */
    public AuthenticationService getAuthenticationService() {
        return authService;
    }

    /**
     * Check if the credentials file is available
     * 
     * @return true if credentials file is available, false otherwise
     */
    public boolean isCredentialsFileAvailable() {
        return authService.isCredentialsFileAvailable();
    }

    /**
     * Get the number of loaded users
     * 
     * @return Number of users
     */
    public int getUserCount() {
        return authService.getUserCount();
    }
}

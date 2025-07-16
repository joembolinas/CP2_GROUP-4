package com.motorph.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.motorph.model.User;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

/**
 * Service class for handling user authentication.
 * Implements MPHCR-04 requirement for login functionality.
 */
public class AuthenticationService {
    private static final Logger logger = Logger.getLogger(AuthenticationService.class.getName());
    private static final String CREDENTIALS_FILE_PATH = "data/userCredentials-backup.csv"; // If running from
                                                                                    // motorph_payroll_system directory
    // If you run from the parent directory, use:
    // private static final String CREDENTIALS_FILE_PATH =
    // "motorph_payroll_system/userCredentials-backup.csv";

    private final List<User> users;

    /**
     * Constructor that loads user credentials from CSV file
     */
    public AuthenticationService() {
        users = new ArrayList<>();
        loadCredentials();
    }

    /**
     * Loads user credentials from the CSV file
     */
    private void loadCredentials() {
        try (CSVReader reader = new CSVReader(new FileReader(CREDENTIALS_FILE_PATH))) {
            List<String[]> records = reader.readAll();


            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);

                if (record.length >= 5) {
                    try {
                        String username = record[0].trim();
                        String password = record[1].trim();
                        int employeeId = Integer.parseInt(record[2].trim());
                        String role = record[3].trim();
                        boolean isActive = Boolean.parseBoolean(record[4].trim());

                        User user = new User(username, password, employeeId, role, isActive);
                        users.add(user);

                        logger.log(Level.INFO, "Loaded user: {0}", username);
                    } catch (NumberFormatException e) {
                        logger.log(Level.WARNING, "Invalid employee ID in credentials file at line {0}", i + 1);
                    }
                } else {
                    logger.log(Level.WARNING, "Invalid record format in credentials file at line {0}", i + 1);
                }
            }

            logger.log(Level.INFO, "Successfully loaded {0} user credentials", users.size());

        } catch (IOException | CsvException e) {
            logger.log(Level.SEVERE, "Failed to load user credentials from file: " + CREDENTIALS_FILE_PATH, e);
            // Create default admin user if file loading fails
            createDefaultAdminUser();
        }
    }

    /**
     * Creates a default admin user if credentials file cannot be loaded
     */
    private void createDefaultAdminUser() {
        logger.log(Level.INFO, "Creating default admin user");
        User defaultAdmin = new User("admin", "admin123", 1, "ADMIN", true);
        users.add(defaultAdmin);
    }

    /**
     * Authenticates a user with username and password
     * 
     * @param username The username to authenticate
     * @param password The password to authenticate
     * @return User object if authentication successful, null otherwise
     */
    public User authenticateUser(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            logger.log(Level.WARNING, "Authentication attempt with empty username or password");
            return null;
        }

        String trimmedUsername = username.trim();
        String trimmedPassword = password.trim();

        for (User user : users) {
            if (user.getUsername().equals(trimmedUsername) &&
                    user.getPassword().equals(trimmedPassword) &&
                    user.isActive()) {

                logger.log(Level.INFO, "Successful authentication for user: {0}", trimmedUsername);
                return user;
            }
        }

        logger.log(Level.WARNING, "Failed authentication attempt for username: {0}", trimmedUsername);
        return null;
    }

    /**
     * Validates login credentials
     * 
     * @param username The username to validate
     * @param password The password to validate
     * @return true if credentials are valid, false otherwise
     */
    public boolean validateLogin(String username, String password) {
        return authenticateUser(username, password) != null;
    }

    /**
     * Gets a user by username
     * 
     * @param username The username to search for
     * @return User object if found, null otherwise
     */
    public User getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        String trimmedUsername = username.trim();
        for (User user : users) {
            if (user.getUsername().equals(trimmedUsername)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets all active users
     * 
     * @return List of active users
     */
    public List<User> getActiveUsers() {
        List<User> activeUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isActive()) {
                activeUsers.add(user);
            }
        }
        return activeUsers;
    }

    /**
     * Gets the total number of loaded users
     * 
     * @return Number of users
     */
    public int getUserCount() {
        return users.size();
    }

    /**
     * Checks if the credentials file exists and is readable
     * 
     * @return true if file exists and is readable
     */
    public boolean isCredentialsFileAvailable() {
        try {
            java.io.File file = new java.io.File(CREDENTIALS_FILE_PATH);
            return file.exists() && file.canRead();
        } catch (Exception e) {
            return false;
        }
    }
}

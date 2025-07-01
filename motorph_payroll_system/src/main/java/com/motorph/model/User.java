package com.motorph.model;

/**
 * Model class representing a user with login credentials.
 * Implements MPHCR-04 requirement for user authentication.
 */
public class User {
    private String username;
    private String password;
    private int employeeId;
    private String role;
    private boolean isActive;

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * Constructor with all parameters
     * 
     * @param username   The username for login
     * @param password   The password for login
     * @param employeeId The associated employee ID
     * @param role       The user role (ADMIN, EMPLOYEE, etc.)
     * @param isActive   Whether the account is active
     */
    public User(String username, String password, int employeeId, String role, boolean isActive) {
        this.username = username;
        this.password = password;
        this.employeeId = employeeId;
        this.role = role;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Validates if the user object has valid data
     * 
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return username != null && !username.trim().isEmpty() &&
                password != null && !password.trim().isEmpty() &&
                employeeId > 0 &&
                role != null && !role.trim().isEmpty();
    }

    /**
     * Checks if the user has admin privileges
     * 
     * @return true if user is an admin
     */
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", employeeId=" + employeeId +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User user = (User) obj;
        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}

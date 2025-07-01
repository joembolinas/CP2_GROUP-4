package com.motorph.util;

import com.motorph.model.User;

/**
 * Utility class for managing user sessions.
 * Implements MPHCR-04 requirement for session management.
 */
public class SessionManager {
    private static SessionManager instance;
    private User currentUser;
    private long loginTime;

    /**
     * Private constructor for singleton pattern
     */
    private SessionManager() {
        this.currentUser = null;
        this.loginTime = 0;
    }

    /**
     * Gets the singleton instance of SessionManager
     * 
     * @return SessionManager instance
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Sets the current logged-in user
     * 
     * @param user The user who logged in
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
        this.loginTime = System.currentTimeMillis();
    }

    /**
     * Gets the current logged-in user
     * 
     * @return Current user or null if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks if a user is currently logged in
     * 
     * @return true if user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Checks if the current user is an admin
     * 
     * @return true if current user is admin, false otherwise
     */
    public boolean isCurrentUserAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    /**
     * Gets the current user's username
     * 
     * @return Username or "Guest" if no user is logged in
     */
    public String getCurrentUsername() {
        return currentUser != null ? currentUser.getUsername() : "Guest";
    }

    /**
     * Gets the current user's role
     * 
     * @return Role or "GUEST" if no user is logged in
     */
    public String getCurrentUserRole() {
        return currentUser != null ? currentUser.getRole() : "GUEST";
    }

    /**
     * Gets the current user's employee ID
     * 
     * @return Employee ID or 0 if no user is logged in
     */
    public int getCurrentUserEmployeeId() {
        return currentUser != null ? currentUser.getEmployeeId() : 0;
    }

    /**
     * Gets the login time
     * 
     * @return Login time in milliseconds
     */
    public long getLoginTime() {
        return loginTime;
    }

    /**
     * Gets the session duration in minutes
     * 
     * @return Session duration in minutes
     */
    public long getSessionDurationMinutes() {
        if (loginTime == 0) {
            return 0;
        }
        return (System.currentTimeMillis() - loginTime) / (1000 * 60);
    }

    /**
     * Clears the current session (logout)
     */
    public void clearSession() {
        this.currentUser = null;
        this.loginTime = 0;
    }

    /**
     * Gets session information as a string
     * 
     * @return Session information
     */
    public String getSessionInfo() {
        if (currentUser == null) {
            return "No active session";
        }
        return String.format("User: %s, Role: %s, Session Duration: %d minutes",
                getCurrentUsername(), getCurrentUserRole(), getSessionDurationMinutes());
    }
}

package com.motorph.test;

import com.motorph.controller.AuthenticationController;
import com.motorph.model.User;
import com.motorph.service.AuthenticationService;

/**
 * Simple test for the authentication system
 */
public class LoginTest {
    public static void main(String[] args) {
        System.out.println("=== MotorPH Login System Test ===");

        // Test authentication service
        AuthenticationService authService = new AuthenticationService();
        System.out.println("User count loaded: " + authService.getUserCount());
        System.out.println("Credentials file available: " + authService.isCredentialsFileAvailable());

        // Test authentication controller
        AuthenticationController authController = new AuthenticationController();

        // Test login with admin credentials
        System.out.println("\nTesting admin login...");
        boolean loginResult = authController.login("admin", "admin123");
        System.out.println("Login result: " + loginResult);

        if (loginResult) {
            User currentUser = authController.getCurrentUser();
            System.out.println("Current user: " + currentUser.getUsername());
            System.out.println("User role: " + currentUser.getRole());
            System.out.println("Is admin: " + currentUser.isAdmin());
            System.out.println("Session info: " + authController.getSessionInfo());
        }

        // Test invalid login
        System.out.println("\nTesting invalid login...");
        boolean invalidLogin = authController.login("invalid", "wrong");
        System.out.println("Invalid login result: " + invalidLogin);

        // Test logout
        System.out.println("\nTesting logout...");
        authController.logout();
        System.out.println("Is logged in after logout: " + authController.isLoggedIn());

        System.out.println("\n=== Test Complete ===");
    }
}

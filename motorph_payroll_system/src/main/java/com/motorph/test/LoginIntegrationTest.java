package com.motorph.test;

import com.motorph.controller.AuthenticationController;
import com.motorph.service.AuthenticationService;
import com.motorph.util.SessionManager;

/**
 * Integration test for login functionality
 * Tests MPHCR-04 implementation
 */
public class LoginIntegrationTest {

    public static void main(String[] args) {
        System.out.println("=== MotorPH Login Integration Test ===");

        try {
            // Test authentication service initialization
            System.out.println("1. Testing AuthenticationService initialization...");
            AuthenticationService authService = new AuthenticationService();
            System.out.println("   ✓ AuthenticationService created successfully");
            System.out.println("   ✓ Users loaded: " + authService.getUserCount());
            System.out.println("   ✓ Credentials file available: " + authService.isCredentialsFileAvailable());

            // Test authentication controller
            System.out.println("\n2. Testing AuthenticationController...");
            AuthenticationController authController = new AuthenticationController();
            System.out.println("   ✓ AuthenticationController created successfully");

            // Test login with valid credentials
            System.out.println("\n3. Testing login with valid credentials...");
            boolean loginResult = authController.login("admin", "admin123");
            System.out.println("   ✓ Login result: " + loginResult);

            if (loginResult) {
                // Check session
                SessionManager sessionManager = SessionManager.getInstance();
                System.out.println("   ✓ Current user: " + sessionManager.getCurrentUsername());
                System.out.println("   ✓ User role: " + sessionManager.getCurrentUserRole());
                System.out.println("   ✓ Is logged in: " + sessionManager.isLoggedIn());
                System.out.println("   ✓ Session info: " + sessionManager.getSessionInfo());

                // Test logout
                System.out.println("\n4. Testing logout...");
                boolean logoutResult = authController.logout();
                System.out.println("   ✓ Logout result: " + logoutResult);
                System.out.println("   ✓ Is logged in after logout: " + sessionManager.isLoggedIn());
            }

            // Test login with invalid credentials
            System.out.println("\n5. Testing login with invalid credentials...");
            boolean invalidLogin = authController.login("invalid", "wrong");
            System.out.println("   ✓ Invalid login result: " + invalidLogin);

            System.out.println("\n=== All tests completed successfully! ===");

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

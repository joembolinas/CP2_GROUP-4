package com.motorph.test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.motorph.controller.AuthenticationController;
import com.motorph.view.LoginPanel;

/**
 * Visual test for the login panel to verify button colors and logo display
 */
public class LoginVisualTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create test frame
            JFrame testFrame = new JFrame("Login Visual Test");
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testFrame.setSize(600, 500);
            testFrame.setLocationRelativeTo(null);

            // Create authentication controller
            AuthenticationController authController = new AuthenticationController();

            // Create login panel with test callback
            LoginPanel loginPanel = new LoginPanel(authController, new LoginPanel.LoginCallback() {
                @Override
                public void onLoginSuccess() {
                    System.out.println("Login successful! Visual test complete.");
                    System.out.println("Button colors and logo should be visible.");
                    System.exit(0);
                }

                @Override
                public void onExit() {
                    System.out.println("Exit button clicked. Visual test complete.");
                    System.exit(0);
                }
            });

            // Add panel to frame and show
            testFrame.add(loginPanel);
            testFrame.setVisible(true);

            // Print instructions
            System.out.println("=== LOGIN VISUAL TEST ===");
            System.out.println("Check the following:");
            System.out.println("1. MotorPH logo should be displayed at the top");
            System.out.println("2. Login button should be blue (#3498DB)");
            System.out.println("3. Exit button should be gray (#95A5A6)");
            System.out.println("4. Buttons should change color when you hover over them");
            System.out.println("5. Button text should be white");
            System.out.println();
            System.out.println("Test accounts:");
            System.out.println("- admin / admin123");
            System.out.println("- demo / demo123");
            System.out.println();
            System.out.println("Click Exit or login successfully to end the test.");
        });
    }
}

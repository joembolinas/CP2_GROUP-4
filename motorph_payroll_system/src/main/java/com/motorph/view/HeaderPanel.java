package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.motorph.util.SessionManager;
import com.motorph.util.UIConstants;

/**
 * Header panel for the MotorPH Payroll System.
 * Shows the current user information and system branding.
 */
public class HeaderPanel extends JPanel {

    public HeaderPanel() {
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.BORDER_COLOR),
                BorderFactory.createEmptyBorder(15, 25, 15, 25)));

        // Left side - could show current page title (optional)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);

        // Right side - user info
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);

        // User greeting - get from session
        SessionManager sessionManager = SessionManager.getInstance();
        String currentUsername = sessionManager.getCurrentUsername();
        String displayName = formatDisplayName(currentUsername);

        JLabel userLabel = new JLabel(displayName);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(UIConstants.TEXT_COLOR);

        // User initials circle - derive from username
        String initials = generateInitials(currentUsername);
        JLabel initialsLabel = new JLabel(initials) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIConstants.PRIMARY_BUTTON_COLOR);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        initialsLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        initialsLabel.setForeground(Color.WHITE);
        initialsLabel.setOpaque(false);
        initialsLabel.setHorizontalAlignment(JLabel.CENTER);
        initialsLabel.setVerticalAlignment(JLabel.CENTER);
        initialsLabel.setPreferredSize(new java.awt.Dimension(32, 32));

        rightPanel.add(userLabel);
        rightPanel.add(javax.swing.Box.createHorizontalStrut(12));
        rightPanel.add(initialsLabel);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Format the display name from username
     * Maps common usernames to display names
     */
    private String formatDisplayName(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "Guest User";
        }

        // Map usernames to display names
        switch (username.toLowerCase()) {
            case "admin":
                return "Administrator";
            case "jdoe":
                return "John Doe";
            case "msmith":
                return "Maria Smith";
            case "jbolinas":
                return "Joem Bolinas";
            default:
                // Capitalize first letter and add spaces before capitals
                return username.substring(0, 1).toUpperCase() +
                        username.substring(1).replaceAll("([A-Z])", " $1");
        }
    }

    /**
     * Generate initials from username
     */
    private String generateInitials(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "GU";
        }

        // Special cases for known usernames
        switch (username.toLowerCase()) {
            case "admin":
                return "AD";
            case "jdoe":
                return "JD";
            case "msmith":
                return "MS";
            case "jbolinas":
                return "JB";
            default:
                // Generate from username - take first letter and first capital or second letter
                String upper = username.toUpperCase();
                if (upper.length() == 1) {
                    return upper + "U";
                } else if (upper.length() >= 2) {
                    return upper.substring(0, 1) + upper.substring(1, 2);
                }
                return "US";
        }
    }
}

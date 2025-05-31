package com.motorph.view;

import java.awt.*;
import javax.swing.*;
import com.motorph.util.UIConstants;

/**
 * Main menu panel with fixed-size navigation buttons.
 */
public class MainMenuPanel extends JPanel {

    private final MainFrame mainFrame;

    public MainMenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initPanel();
    }

    private void initPanel() {
        setLayout(new BorderLayout());

        // Navigation panel on the left
        JPanel navPanel = new JPanel();
        navPanel.setBackground(UIConstants.NAVIGATION_BACKGROUND);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        navPanel.setPreferredSize(new Dimension(260, getHeight())); // fixed width panel

        JLabel titleLabel = new JLabel(UIConstants.APP_TITLE, SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create buttons
        JButton employeeManagementButton = createStyledButton("Employee Management");
        JButton payrollManagementButton = createStyledButton("Payroll Management");
        JButton reportsButton = createStyledButton("Reports");
        JButton exitButton = createStyledButton("Exit", UIConstants.DELETE_BUTTON_COLOR);

        // Add components
        navPanel.add(titleLabel);
        navPanel.add(Box.createVerticalStrut(30));
        navPanel.add(employeeManagementButton);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(payrollManagementButton);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(reportsButton);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(exitButton);

        add(navPanel, BorderLayout.WEST);

        // Add listeners
        employeeManagementButton.addActionListener(e -> mainFrame.showEmployeeManagement());
        payrollManagementButton.addActionListener(e -> mainFrame.showPayrollManagement());
        reportsButton.addActionListener(e -> mainFrame.showReports());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private JButton createStyledButton(String text) {
        return createStyledButton(text, UIConstants.BUTTON_COLOR);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setBackground(bgColor);
        button.setForeground(UIConstants.BUTTON_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Inner padding

        // Fixed size for all buttons
        Dimension fixedSize = new Dimension(200, UIConstants.BUTTON_HEIGHT);
        button.setPreferredSize(fixedSize);
        button.setMaximumSize(fixedSize);
        button.setMinimumSize(fixedSize);

        return button;
    }
}

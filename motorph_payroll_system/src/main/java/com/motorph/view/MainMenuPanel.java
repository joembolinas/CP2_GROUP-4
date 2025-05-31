package com.motorph.view;

import java.awt.*;
import javax.swing.*;
import com.motorph.util.UIConstants;

/**
 * Main menu panel with full title above buttons and consistent button sizing.
 */
public class MainMenuPanel extends JPanel {

    private final MainFrame mainFrame;

    public MainMenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initPanel();
    }

    private void initPanel() {
        setLayout(new BorderLayout());

        // ========== Title Panel ==========
        JLabel titleLabel = new JLabel(UIConstants.APP_TITLE, SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(UIConstants.NAVIGATION_BACKGROUND);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Fills the rest of the screen with a placeholder panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE); // or UIConstants.PANEL_BACKGROUND
        add(contentPanel, BorderLayout.CENTER);


        // ========== Navigation Panel (Buttons) ==========
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(UIConstants.NAVIGATION_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        // Buttons
        JButton employeeManagementButton = createStyledButton("Employee Management");
        JButton payrollManagementButton = createStyledButton("Payroll Management");
        JButton reportsButton = createStyledButton("Reports");
        JButton exitButton = createStyledButton("Exit", UIConstants.DELETE_BUTTON_COLOR);

        // Add buttons with spacing
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(employeeManagementButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(payrollManagementButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(reportsButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(exitButton);

        // ========== Left Navigation Container ==========
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        navPanel.setBackground(UIConstants.NAVIGATION_BACKGROUND);
        navPanel.setPreferredSize(new Dimension(260, getHeight()));

        navPanel.add(titlePanel, BorderLayout.NORTH);
        navPanel.add(buttonPanel, BorderLayout.CENTER);

        add(navPanel, BorderLayout.WEST);

        // ========== Action Listeners ==========
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

        // Uniform button size
        Dimension size = new Dimension(200, 40); // Wider and taller to fit long text
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }
}

package com.motorph.view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.motorph.util.UIConstants;

/**
 * Main menu panel for the application.
 */
public class MainMenuPanel extends JPanel {
    
    private final MainFrame mainFrame;
    
    /**
     * Constructor for the main menu panel
     */
    public MainMenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initPanel();
    }
    
    /**
     * Initialize the panel
     */
    private void initPanel() {
        setLayout(new GridLayout(5, 1, 10, 10));
        setBackground(UIConstants.BACKGROUND_COLOR);
        
        JLabel titleLabel = new JLabel("MotorPH Payroll System", SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.TITLE_FONT);
        add(titleLabel);
        
        // Create buttons
        JButton employeeManagementButton = createStyledButton("Employee Management");
        JButton payrollManagementButton = createStyledButton("Payroll Management");
        JButton reportsButton = createStyledButton("Reports");
        JButton exitButton = createStyledButton("Exit");
        
        add(employeeManagementButton);
        add(payrollManagementButton);
        add(reportsButton);
        add(exitButton);
        
        // Add action listeners for buttons
        employeeManagementButton.addActionListener(e -> mainFrame.showEmployeeManagement());
        payrollManagementButton.addActionListener(e -> mainFrame.showPayrollManagement());
        reportsButton.addActionListener(e -> mainFrame.showReports());
        exitButton.addActionListener(e -> System.exit(0));
    }
    
    /**
     * Create a styled button with consistent look and feel
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(UIConstants.BUTTON_COLOR);
        button.setFont(UIConstants.NORMAL_FONT);
        button.setFocusPainted(false);
        return button;
    }
}

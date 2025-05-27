package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.motorph.model.Employee;
import com.motorph.util.UIConstants;

/**
 * Frame for displaying complete employee details as required by MPHCR-02.
 * Shows all employee information in a formatted view.
 */
public class EmployeeDetailsFrame extends JFrame {
    
    private final Employee employee;
    
    /**
     * Constructor for employee details frame
     */
    public EmployeeDetailsFrame(JFrame parent, Employee employee) {
        super("Employee Details - " + employee.getFullName());
        this.employee = employee;
        initFrame(parent);
    }
    
    /**
     * Initialize the frame
     */
    private void initFrame(JFrame parent) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        // Personal Information Panel
        JPanel personalPanel = createPersonalInfoPanel();
        personalPanel.setBorder(new TitledBorder("Personal Information"));
        
        // Employment Information Panel
        JPanel employmentPanel = createEmploymentInfoPanel();
        employmentPanel.setBorder(new TitledBorder("Employment Information"));
        
        // Government IDs Panel
        JPanel governmentIdsPanel = createGovernmentIdsPanel();
        governmentIdsPanel.setBorder(new TitledBorder("Government IDs"));
        
        // Salary Information Panel
        JPanel salaryPanel = createSalaryInfoPanel();
        salaryPanel.setBorder(new TitledBorder("Salary Information"));
        
        // Top panel containing personal and employment info
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        topPanel.add(personalPanel);
        topPanel.add(employmentPanel);
        
        // Bottom panel containing government IDs and salary info
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        bottomPanel.add(governmentIdsPanel);
        bottomPanel.add(salaryPanel);
        
        // Combine panels
        JPanel contentPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        contentPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        contentPanel.add(topPanel);
        contentPanel.add(bottomPanel);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(UIConstants.BUTTON_COLOR);
        closeButton.setFont(UIConstants.NORMAL_FONT);        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
        
        // Set frame properties
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    /**
     * Create personal information panel
     */
    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        panel.add(new JLabel("Employee ID:"));
        panel.add(new JLabel(String.valueOf(employee.getEmployeeId())));
        
        panel.add(new JLabel("First Name:"));
        panel.add(new JLabel(employee.getFirstName() != null ? employee.getFirstName() : "N/A"));
        
        panel.add(new JLabel("Last Name:"));
        panel.add(new JLabel(employee.getLastName() != null ? employee.getLastName() : "N/A"));
        
        panel.add(new JLabel("Birthday:"));
        panel.add(new JLabel(employee.getBirthday() != null ? employee.getBirthday().toString() : "N/A"));
        
        panel.add(new JLabel("Address:"));
        JLabel addressLabel = new JLabel(employee.getAddress() != null ? employee.getAddress() : "N/A");
        addressLabel.setToolTipText(employee.getAddress()); // Show full address on hover
        panel.add(addressLabel);
        
        return panel;
    }
    
    /**
     * Create employment information panel
     */
    private JPanel createEmploymentInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        panel.add(new JLabel("Position:"));
        panel.add(new JLabel(employee.getPosition() != null ? employee.getPosition() : "N/A"));
        
        panel.add(new JLabel("Status:"));
        panel.add(new JLabel(employee.getStatus() != null ? employee.getStatus() : "N/A"));
        
        panel.add(new JLabel("Supervisor:"));
        panel.add(new JLabel(employee.getSupervisor() != null ? employee.getSupervisor() : "N/A"));
        
        panel.add(new JLabel("Phone Number:"));
        panel.add(new JLabel(employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "N/A"));
        
        panel.add(new JLabel(""));
        panel.add(new JLabel("")); // Empty row for spacing
        
        return panel;
    }
    
    /**
     * Create government IDs panel
     */
    private JPanel createGovernmentIdsPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        panel.add(new JLabel("SSS Number:"));
        panel.add(new JLabel(employee.getSssNumber() != null ? employee.getSssNumber() : "N/A"));
        
        panel.add(new JLabel("PhilHealth Number:"));
        panel.add(new JLabel(employee.getPhilhealthNumber() != null ? employee.getPhilhealthNumber() : "N/A"));
        
        panel.add(new JLabel("TIN:"));
        panel.add(new JLabel(employee.getTinNumber() != null ? employee.getTinNumber() : "N/A"));
        
        panel.add(new JLabel("Pag-IBIG Number:"));
        panel.add(new JLabel(employee.getPagibigNumber() != null ? employee.getPagibigNumber() : "N/A"));
        
        panel.add(new JLabel(""));
        panel.add(new JLabel("")); // Empty row for spacing
        
        return panel;
    }
    
    /**
     * Create salary information panel
     */
    private JPanel createSalaryInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        panel.add(new JLabel("Basic Salary:"));
        panel.add(new JLabel(String.format("₱%,.2f", employee.getBasicSalary())));
        
        panel.add(new JLabel("Rice Subsidy:"));
        panel.add(new JLabel(String.format("₱%,.2f", employee.getRiceSubsidy())));
        
        panel.add(new JLabel("Phone Allowance:"));
        panel.add(new JLabel(String.format("₱%,.2f", employee.getPhoneAllowance())));
        
        panel.add(new JLabel("Clothing Allowance:"));
        panel.add(new JLabel(String.format("₱%,.2f", employee.getClothingAllowance())));
        
        panel.add(new JLabel("Hourly Rate:"));
        panel.add(new JLabel(String.format("₱%.2f", employee.getHourlyRate())));
        
        return panel;
    }
}

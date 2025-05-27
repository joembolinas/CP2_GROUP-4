package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.motorph.controller.EmployeeController;
import com.motorph.model.Employee;
import com.motorph.util.UIConstants;

/**
 * Dialog for adding new employees as required by MPHCR-02.
 * Provides form fields for all employee information and saves to CSV.
 */
public class NewEmployeeDialog extends JDialog {
    
    private final EmployeeController employeeController;
    private boolean employeeAdded = false;
    
    // Form fields
    private JTextField employeeIdField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField birthdayField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JTextField sssNumberField;
    private JTextField philhealthNumberField;
    private JTextField tinNumberField;
    private JTextField pagibigNumberField;
    private JTextField positionField;
    private JTextField statusField;
    private JTextField supervisorField;
    private JTextField basicSalaryField;
    private JTextField riceSubsidyField;
    private JTextField phoneAllowanceField;
    private JTextField clothingAllowanceField;
    
    /**
     * Constructor for new employee dialog
     */
    public NewEmployeeDialog(JFrame parent, EmployeeController employeeController) {
        super(parent, "Add New Employee", true);
        this.employeeController = employeeController;
        initDialog();
    }
    
    /**
     * Initialize the dialog
     */
    private void initDialog() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        // Form panels
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
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
        
        formPanel.add(personalPanel);
        formPanel.add(employmentPanel);
        formPanel.add(governmentIdsPanel);
        formPanel.add(salaryPanel);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        JButton saveButton = new JButton("Save Employee");
        saveButton.setBackground(UIConstants.BUTTON_COLOR);
        saveButton.setFont(UIConstants.NORMAL_FONT);        saveButton.addActionListener(e -> saveEmployee());
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(UIConstants.BUTTON_COLOR);
        cancelButton.setFont(UIConstants.NORMAL_FONT);        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
        
        // Set dialog properties
        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        // Set focus to first field
        employeeIdField.requestFocus();
    }
    
    /**
     * Create personal information panel
     */
    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        panel.add(new JLabel("Employee ID:*"));
        employeeIdField = new JTextField();
        panel.add(employeeIdField);
        
        panel.add(new JLabel("Last Name:*"));
        lastNameField = new JTextField();
        panel.add(lastNameField);
        
        panel.add(new JLabel("First Name:*"));
        firstNameField = new JTextField();
        panel.add(firstNameField);
        
        panel.add(new JLabel("Birthday (MM/DD/YYYY):"));
        birthdayField = new JTextField();
        panel.add(birthdayField);
        
        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);
        
        panel.add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField();
        panel.add(phoneNumberField);
        
        return panel;
    }
    
    /**
     * Create employment information panel
     */
    private JPanel createEmploymentInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        panel.add(new JLabel("Position:*"));
        positionField = new JTextField();
        panel.add(positionField);
        
        panel.add(new JLabel("Status:*"));
        statusField = new JTextField("Regular");
        panel.add(statusField);
        
        panel.add(new JLabel("Supervisor:"));
        supervisorField = new JTextField();
        panel.add(supervisorField);
        
        // Empty rows for alignment
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        
        return panel;
    }
    
    /**
     * Create government IDs panel
     */
    private JPanel createGovernmentIdsPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        panel.add(new JLabel("SSS Number:"));
        sssNumberField = new JTextField();
        panel.add(sssNumberField);
        
        panel.add(new JLabel("PhilHealth Number:"));
        philhealthNumberField = new JTextField();
        panel.add(philhealthNumberField);
        
        panel.add(new JLabel("TIN:"));
        tinNumberField = new JTextField();
        panel.add(tinNumberField);
        
        panel.add(new JLabel("Pag-IBIG Number:"));
        pagibigNumberField = new JTextField();
        panel.add(pagibigNumberField);
        
        // Empty rows for alignment
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        
        return panel;
    }
    
    /**
     * Create salary information panel
     */
    private JPanel createSalaryInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        panel.add(new JLabel("Basic Salary:*"));
        basicSalaryField = new JTextField();
        panel.add(basicSalaryField);
        
        panel.add(new JLabel("Rice Subsidy:"));
        riceSubsidyField = new JTextField("1500");
        panel.add(riceSubsidyField);
        
        panel.add(new JLabel("Phone Allowance:"));
        phoneAllowanceField = new JTextField("500");
        panel.add(phoneAllowanceField);
        
        panel.add(new JLabel("Clothing Allowance:"));
        clothingAllowanceField = new JTextField("500");
        panel.add(clothingAllowanceField);
        
        // Empty rows for alignment
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        
        return panel;
    }
    
    /**
     * Save the new employee
     */
    private void saveEmployee() {
        try {
            // Validate required fields
            if (employeeIdField.getText().trim().isEmpty() ||
                lastNameField.getText().trim().isEmpty() ||
                firstNameField.getText().trim().isEmpty() ||
                positionField.getText().trim().isEmpty() ||
                statusField.getText().trim().isEmpty() ||
                basicSalaryField.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(this,
                        "Please fill in all required fields (marked with *).",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Parse and validate data
            int employeeId = Integer.parseInt(employeeIdField.getText().trim());
            String lastName = lastNameField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String position = positionField.getText().trim();
            String status = statusField.getText().trim();
            double basicSalary = Double.parseDouble(basicSalaryField.getText().trim());
            
            // Optional fields
            LocalDate birthday = null;
            if (!birthdayField.getText().trim().isEmpty()) {
                try {
                    birthday = LocalDate.parse(birthdayField.getText().trim(), 
                            DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter birthday in MM/DD/YYYY format.",
                            "Date Format Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            String address = birthdayField.getText().trim().isEmpty() ? null : addressField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim().isEmpty() ? null : phoneNumberField.getText().trim();
            String sssNumber = sssNumberField.getText().trim().isEmpty() ? null : sssNumberField.getText().trim();
            String philhealthNumber = philhealthNumberField.getText().trim().isEmpty() ? null : philhealthNumberField.getText().trim();
            String tinNumber = tinNumberField.getText().trim().isEmpty() ? null : tinNumberField.getText().trim();
            String pagibigNumber = pagibigNumberField.getText().trim().isEmpty() ? null : pagibigNumberField.getText().trim();
            String supervisor = supervisorField.getText().trim().isEmpty() ? null : supervisorField.getText().trim();
            
            double riceSubsidy = riceSubsidyField.getText().trim().isEmpty() ? 0.0 : 
                    Double.parseDouble(riceSubsidyField.getText().trim());
            double phoneAllowance = phoneAllowanceField.getText().trim().isEmpty() ? 0.0 : 
                    Double.parseDouble(phoneAllowanceField.getText().trim());
            double clothingAllowance = clothingAllowanceField.getText().trim().isEmpty() ? 0.0 : 
                    Double.parseDouble(clothingAllowanceField.getText().trim());
            
            // Calculate gross semi-monthly rate (basic salary / 2)
            double grossSemiMonthlyRate = basicSalary / 2;
            
            // Create new employee
            Employee newEmployee = new Employee(
                    employeeId, lastName, firstName, birthday, address, phoneNumber,
                    sssNumber, philhealthNumber, tinNumber, pagibigNumber, status,
                    position, supervisor, basicSalary, riceSubsidy, phoneAllowance,
                    clothingAllowance, grossSemiMonthlyRate
            );
            
            // Add employee through controller (this should handle CSV writing)
            boolean success = addEmployeeToSystem(newEmployee);
            
            if (success) {
                employeeAdded = true;
                JOptionPane.showMessageDialog(this,
                        "Employee added successfully and saved to CSV file!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to save employee. Employee ID might already exist.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numeric values for Employee ID, Basic Salary, and allowances.",
                    "Number Format Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
      /**
     * Add employee to system and save to CSV
     */
    private boolean addEmployeeToSystem(Employee employee) {
        try {
            return employeeController.addEmployee(employee);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error saving employee: " + e.getMessage(),
                "Save Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Check if employee was successfully added
     */
    public boolean isEmployeeAdded() {
        return employeeAdded;
    }
}

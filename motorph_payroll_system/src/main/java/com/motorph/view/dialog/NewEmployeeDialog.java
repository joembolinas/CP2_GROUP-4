package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.motorph.controller.EmployeeController;
import com.motorph.model.Employee;
import com.motorph.util.UIConstants;

/**
 * Dialog for adding new employees as required by MPHCR-02.
 * Provides a comprehensive form for entering all employee information
 * and saves the new employee to the CSV file.
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
    private JTextField statusField;
    private JTextField positionField;
    private JTextField supervisorField;
    private JTextField basicSalaryField;
    private JTextField riceSubsidyField;
    private JTextField phoneAllowanceField;
    private JTextField clothingAllowanceField;

    /**
     * Constructor for the new employee dialog
     */
    public NewEmployeeDialog(JFrame parent, EmployeeController employeeController) {
        super(parent, "New Employee - MPHCR-02", true);
        this.employeeController = employeeController;

        initDialog();
        setupForm();
        setDefaultValues();
    }

    /**
     * Initialize the dialog properties
     */
    private void initDialog() {
        setSize(UIConstants.EMPLOYEE_DIALOG_SIZE);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    /**
     * Setup the form layout and components
     */
    private void setupForm() {
        setLayout(new BorderLayout(UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING));
        getContentPane().setBackground(UIConstants.BACKGROUND_COLOR);

        // Title panel
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Create the title panel
     */
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(UIConstants.BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Add New Employee");
        titleLabel.setFont(UIConstants.HEADING_FONT);
        titleLabel.setForeground(UIConstants.HEADING_COLOR);
        titlePanel.add(titleLabel);

        return titlePanel;
    }

    /**
     * Create the form panel with all input fields
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Employee ID (auto-generated)
        addFormField(formPanel, gbc, row++, "Employee ID:", employeeIdField = new JTextField(20));
        employeeIdField.setEditable(false);
        employeeIdField.setBackground(UIConstants.LIGHT_GRAY);

        // Personal Information
        addFormField(formPanel, gbc, row++, "Last Name:", lastNameField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "First Name:", firstNameField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Birthday (MM/DD/YYYY):", birthdayField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Address:", addressField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Phone Number:", phoneNumberField = new JTextField(20));

        // Government IDs
        addFormField(formPanel, gbc, row++, "SSS Number:", sssNumberField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "PhilHealth Number:", philhealthNumberField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "TIN Number:", tinNumberField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Pag-IBIG Number:", pagibigNumberField = new JTextField(20));

        // Employment Information
        addFormField(formPanel, gbc, row++, "Status:", statusField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Position:", positionField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Immediate Supervisor:", supervisorField = new JTextField(20));

        // Salary Information
        addFormField(formPanel, gbc, row++, "Basic Salary:", basicSalaryField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Rice Subsidy:", riceSubsidyField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Phone Allowance:", phoneAllowanceField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Clothing Allowance:", clothingAllowanceField = new JTextField(20));

        return formPanel;
    }

    /**
     * Helper method to add form fields
     */
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;

        JLabel label = new JLabel(labelText);
        label.setFont(UIConstants.NORMAL_FONT);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        field.setFont(UIConstants.NORMAL_FONT);
        panel.add(field, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
    }

    /**
     * Create the button panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING));
        buttonPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        JButton saveButton = createStyledButton("Save Employee", UIConstants.SUCCESS_GREEN);
        JButton cancelButton = createStyledButton("Cancel", UIConstants.DANGER_RED);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add action listeners
        saveButton.addActionListener(e -> saveEmployee());
        cancelButton.addActionListener(e -> dispose());

        return buttonPanel;
    }

    /**
     * Create a styled button
     */
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(UIConstants.MEDIUM_BUTTON_SIZE);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Set default values for the form
     */
    private void setDefaultValues() {
        // Auto-generate employee ID
        int nextId = employeeController.generateNextEmployeeId();
        employeeIdField.setText(String.valueOf(nextId));

        // Set default values
        statusField.setText("Regular");
        riceSubsidyField.setText("1500");
        phoneAllowanceField.setText("2000");
        clothingAllowanceField.setText("1000");
    }

    /**
     * Validate the input data
     */
    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();

        // Required fields validation
        if (lastNameField.getText().trim().isEmpty()) {
            errors.append("- Last Name is required\n");
        }

        if (firstNameField.getText().trim().isEmpty()) {
            errors.append("- First Name is required\n");
        }

        if (positionField.getText().trim().isEmpty()) {
            errors.append("- Position is required\n");
        }

        if (basicSalaryField.getText().trim().isEmpty()) {
            errors.append("- Basic Salary is required\n");
        } else {
            try {
                double salary = Double.parseDouble(basicSalaryField.getText().trim().replace(",", ""));
                if (salary <= 0) {
                    errors.append("- Basic Salary must be greater than 0\n");
                }
            } catch (NumberFormatException e) {
                errors.append("- Basic Salary must be a valid number\n");
            }
        }

        // Validate numeric fields
        validateNumericField(riceSubsidyField, "Rice Subsidy", errors);
        validateNumericField(phoneAllowanceField, "Phone Allowance", errors);
        validateNumericField(clothingAllowanceField, "Clothing Allowance", errors);

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Please correct the following errors:\n\n" + errors.toString(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Helper method to validate numeric fields
     */
    private void validateNumericField(JTextField field, String fieldName, StringBuilder errors) {
        String value = field.getText().trim();
        if (!value.isEmpty()) {
            try {
                Double.parseDouble(value.replace(",", ""));
            } catch (NumberFormatException e) {
                errors.append("- ").append(fieldName).append(" must be a valid number\n");
            }
        }
    }

    /**
     * Save the employee
     */
    private void saveEmployee() {
        if (!validateInput()) {
            return;
        }

        try {
            // Parse values
            int employeeId = Integer.parseInt(employeeIdField.getText().trim());
            String lastName = lastNameField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String birthday = birthdayField.getText().trim();
            String address = addressField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();
            String sssNumber = sssNumberField.getText().trim();
            String philhealthNumber = philhealthNumberField.getText().trim();
            String tinNumber = tinNumberField.getText().trim();
            String pagibigNumber = pagibigNumberField.getText().trim();
            String status = statusField.getText().trim();
            String position = positionField.getText().trim();
            String supervisor = supervisorField.getText().trim();

            double basicSalary = Double.parseDouble(basicSalaryField.getText().trim().replace(",", ""));
            double riceSubsidy = parseDoubleOrDefault(riceSubsidyField.getText().trim(), 0.0);
            double phoneAllowance = parseDoubleOrDefault(phoneAllowanceField.getText().trim(), 0.0);
            double clothingAllowance = parseDoubleOrDefault(clothingAllowanceField.getText().trim(), 0.0);

            // Calculate gross semi-monthly rate and hourly rate
            double grossSemimonthlyRate = (basicSalary + riceSubsidy + phoneAllowance + clothingAllowance) / 2;
            double hourlyRate = basicSalary / (21 * 8); // 21 working days, 8 hours per day

            // Create employee object
            Employee employee = new Employee(
                    employeeId, lastName, firstName, birthday, address, phoneNumber,
                    sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, position,
                    supervisor, basicSalary, riceSubsidy, phoneAllowance, clothingAllowance,
                    grossSemimonthlyRate, hourlyRate);

            // Save employee
            employeeController.addEmployee(employee);
            employeeAdded = true;
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Helper method to parse double with default value
     */
    private double parseDoubleOrDefault(String value, double defaultValue) {
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Check if an employee was added
     */
    public boolean isEmployeeAdded() {
        return employeeAdded;
    }
}

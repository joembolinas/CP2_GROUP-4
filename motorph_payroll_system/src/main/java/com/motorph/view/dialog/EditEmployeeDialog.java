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
import com.motorph.util.UIUtils;

/**
 * Dialog for editing existing employees.
 * Provides form fields for all employee information and saves changes to CSV.
 */
public class EditEmployeeDialog extends JDialog {

    private final EmployeeController employeeController;
    private final Employee employee;
    private boolean employeeUpdated = false;

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
     * Constructor for edit employee dialog
     */
    public EditEmployeeDialog(JFrame parent, EmployeeController employeeController, Employee employee) {
        super(parent, "Edit Employee - " + employee.getFullName(), true);
        this.employeeController = employeeController;
        this.employee = employee;
        initDialog();
        populateFields();
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

        JButton updateButton = UIUtils.createPrimaryButton("Update Employee");
        updateButton.addActionListener(e -> updateEmployee());

        JButton cancelButton = UIUtils.createSecondaryButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Set dialog properties
        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    /**
     * Create personal information panel
     */
    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBackground(UIConstants.PANEL_BACKGROUND);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Employee ID (read-only for edit)
        panel.add(new JLabel("Employee ID:"));
        employeeIdField = new JTextField();
        employeeIdField.setEditable(false); // Cannot change employee ID
        employeeIdField.setBackground(UIConstants.BACKGROUND_COLOR);
        panel.add(employeeIdField);

        // Last Name
        panel.add(new JLabel("Last Name:*"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        // First Name
        panel.add(new JLabel("First Name:*"));
        firstNameField = new JTextField();
        panel.add(firstNameField);

        // Birthday
        panel.add(new JLabel("Birthday (YYYY-MM-DD):"));
        birthdayField = new JTextField();
        panel.add(birthdayField);

        // Address
        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        return panel;
    }

    /**
     * Create employment information panel
     */
    private JPanel createEmploymentInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBackground(UIConstants.PANEL_BACKGROUND);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Position
        panel.add(new JLabel("Position:*"));
        positionField = new JTextField();
        panel.add(positionField);

        // Status
        panel.add(new JLabel("Status:"));
        statusField = new JTextField();
        panel.add(statusField);

        // Supervisor
        panel.add(new JLabel("Supervisor:"));
        supervisorField = new JTextField();
        panel.add(supervisorField);

        // Phone Number
        panel.add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField();
        panel.add(phoneNumberField);

        // Empty row for spacing
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        return panel;
    }

    /**
     * Create government IDs panel
     */
    private JPanel createGovernmentIdsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBackground(UIConstants.PANEL_BACKGROUND);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // SSS Number
        panel.add(new JLabel("SSS Number:"));
        sssNumberField = new JTextField();
        panel.add(sssNumberField);

        // PhilHealth Number
        panel.add(new JLabel("PhilHealth Number:"));
        philhealthNumberField = new JTextField();
        panel.add(philhealthNumberField);

        // TIN Number
        panel.add(new JLabel("TIN Number:"));
        tinNumberField = new JTextField();
        panel.add(tinNumberField);

        // Pagibig Number
        panel.add(new JLabel("Pagibig Number:"));
        pagibigNumberField = new JTextField();
        panel.add(pagibigNumberField);

        return panel;
    }

    /**
     * Create salary information panel
     */
    private JPanel createSalaryInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBackground(UIConstants.PANEL_BACKGROUND);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Basic Salary
        panel.add(new JLabel("Basic Salary:*"));
        basicSalaryField = new JTextField();
        panel.add(basicSalaryField);

        // Rice Subsidy
        panel.add(new JLabel("Rice Subsidy:"));
        riceSubsidyField = new JTextField();
        panel.add(riceSubsidyField);

        // Phone Allowance
        panel.add(new JLabel("Phone Allowance:"));
        phoneAllowanceField = new JTextField();
        panel.add(phoneAllowanceField);

        // Clothing Allowance
        panel.add(new JLabel("Clothing Allowance:"));
        clothingAllowanceField = new JTextField();
        panel.add(clothingAllowanceField);

        return panel;
    }

    /**
     * Populate fields with existing employee data
     */
    private void populateFields() {
        employeeIdField.setText(String.valueOf(employee.getEmployeeId()));
        lastNameField.setText(employee.getLastName());
        firstNameField.setText(employee.getFirstName());

        if (employee.getBirthday() != null) {
            birthdayField.setText(employee.getBirthday().toString());
        }

        addressField.setText(employee.getAddress() != null ? employee.getAddress() : "");
        phoneNumberField.setText(employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "");
        sssNumberField.setText(employee.getSssNumber() != null ? employee.getSssNumber() : "");
        philhealthNumberField.setText(employee.getPhilhealthNumber() != null ? employee.getPhilhealthNumber() : "");
        tinNumberField.setText(employee.getTinNumber() != null ? employee.getTinNumber() : "");
        pagibigNumberField.setText(employee.getPagibigNumber() != null ? employee.getPagibigNumber() : "");
        positionField.setText(employee.getPosition());
        statusField.setText(employee.getStatus());
        supervisorField.setText(employee.getSupervisor() != null ? employee.getSupervisor() : "");
        basicSalaryField.setText(String.valueOf(employee.getBasicSalary()));
        riceSubsidyField.setText(String.valueOf(employee.getRiceSubsidy()));
        phoneAllowanceField.setText(String.valueOf(employee.getPhoneAllowance()));
        clothingAllowanceField.setText(String.valueOf(employee.getClothingAllowance()));
    }

    /**
     * Update employee with form data
     */
    private void updateEmployee() {
        try {
            // Validate required fields
            if (lastNameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Last name is required", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (firstNameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "First name is required", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (positionField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Position is required", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse and validate salary
            double basicSalary;
            try {
                basicSalary = Double.parseDouble(basicSalaryField.getText().trim());
                if (basicSalary <= 0) {
                    JOptionPane.showMessageDialog(this, "Basic salary must be greater than zero", "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid basic salary", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse birthday if provided
            LocalDate birthday = null;
            if (!birthdayField.getText().trim().isEmpty()) {
                try {
                    birthday = LocalDate.parse(birthdayField.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this, "Please enter birthday in YYYY-MM-DD format",
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Update employee object
            employee.setLastName(lastNameField.getText().trim());
            employee.setFirstName(firstNameField.getText().trim());
            employee.setBirthday(birthday);
            employee.setAddress(addressField.getText().trim());
            employee.setPhoneNumber(phoneNumberField.getText().trim());
            employee.setSssNumber(sssNumberField.getText().trim());
            employee.setPhilhealthNumber(philhealthNumberField.getText().trim());
            employee.setTinNumber(tinNumberField.getText().trim());
            employee.setPagibigNumber(pagibigNumberField.getText().trim());
            employee.setPosition(positionField.getText().trim());
            employee.setStatus(statusField.getText().trim());
            employee.setSupervisor(supervisorField.getText().trim());
            employee.setBasicSalary(basicSalary);

            // Parse allowances
            try {
                if (!riceSubsidyField.getText().trim().isEmpty()) {
                    employee.setRiceSubsidy(Double.parseDouble(riceSubsidyField.getText().trim()));
                }
                if (!phoneAllowanceField.getText().trim().isEmpty()) {
                    employee.setPhoneAllowance(Double.parseDouble(phoneAllowanceField.getText().trim()));
                }
                if (!clothingAllowanceField.getText().trim().isEmpty()) {
                    employee.setClothingAllowance(Double.parseDouble(clothingAllowanceField.getText().trim()));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for allowances", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update employee through controller
            boolean success = employeeController.updateEmployee(employee);

            if (success) {
                employeeUpdated = true;
                JOptionPane.showMessageDialog(this,
                        "Employee updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to update employee. Employee may not exist.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                    "Error updating employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Check if employee was updated
     */
    public boolean isEmployeeUpdated() {
        return employeeUpdated;
    }
}

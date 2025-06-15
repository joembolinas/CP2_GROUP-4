package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.motorph.controller.EmployeeController;
import com.motorph.model.Employee;
import com.motorph.util.UIConstants;

/**
 * Dialog for viewing employee details and computing salary as required by
 * MPHCR-02.
 * Shows full employee information and allows month selection for salary
 * computation.
 */
public class EmployeeDetailsDialog extends JDialog {

    private final Employee employee;
    private final EmployeeController employeeController;

    // Display fields
    private JTextField employeeIdField;
    private JTextField nameField;
    private JTextField positionField;
    private JTextField statusField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField sssField;
    private JTextField philhealthField;
    private JTextField tinField;
    private JTextField pagibigField;
    private JTextField basicSalaryField;
    private JTextField allowancesField;

    // Salary computation
    private JComboBox<String> monthComboBox;
    private JTextArea salaryDetailsArea;

    /**
     * Constructor for the employee details dialog
     */
    public EmployeeDetailsDialog(JFrame parent, Employee employee, EmployeeController employeeController) {
        super(parent, "Employee Details - " + employee.getFullName(), true);
        this.employee = employee;
        this.employeeController = employeeController;

        initDialog();
        setupLayout();
        populateFields();
    }

    /**
     * Initialize the dialog properties
     */
    private void initDialog() {
        setSize(700, 600);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(true);
    }

    /**
     * Setup the layout and components
     */
    private void setupLayout() {
        setLayout(new BorderLayout(UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING));
        getContentPane().setBackground(UIConstants.BACKGROUND_COLOR);

        // Title panel
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        // Main content panel
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

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

        JLabel titleLabel = new JLabel("Employee Details - MPHCR-02");
        titleLabel.setFont(UIConstants.HEADING_FONT);
        titleLabel.setForeground(UIConstants.HEADING_COLOR);
        titlePanel.add(titleLabel);

        return titlePanel;
    }

    /**
     * Create the main content panel
     */
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING));
        mainPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        // Employee information panel
        JPanel employeeInfoPanel = createEmployeeInfoPanel();
        mainPanel.add(employeeInfoPanel, BorderLayout.WEST);

        // Salary computation panel
        JPanel salaryPanel = createSalaryPanel();
        mainPanel.add(salaryPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    /**
     * Create the employee information panel
     */
    private JPanel createEmployeeInfoPanel() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Section header
        addSectionHeader(infoPanel, gbc, row++, "Personal Information");

        // Personal details
        addDisplayField(infoPanel, gbc, row++, "Employee ID:", employeeIdField = new JTextField(15));
        addDisplayField(infoPanel, gbc, row++, "Full Name:", nameField = new JTextField(20));
        addDisplayField(infoPanel, gbc, row++, "Position:", positionField = new JTextField(15));
        addDisplayField(infoPanel, gbc, row++, "Status:", statusField = new JTextField(15));
        addDisplayField(infoPanel, gbc, row++, "Phone:", phoneField = new JTextField(15));

        // Address field (multi-line)
        gbc.gridx = 0;
        gbc.gridy = row++;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(UIConstants.NORMAL_FONT);
        infoPanel.add(addressLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        addressField = new JTextField(20);
        addressField.setEditable(false);
        addressField.setBackground(UIConstants.LIGHT_GRAY);
        infoPanel.add(addressField, gbc);
        gbc.fill = GridBagConstraints.NONE;

        row++; // Add spacing
        addSectionHeader(infoPanel, gbc, row++, "Government IDs");

        // Government IDs
        addDisplayField(infoPanel, gbc, row++, "SSS Number:", sssField = new JTextField(15));
        addDisplayField(infoPanel, gbc, row++, "PhilHealth:", philhealthField = new JTextField(15));
        addDisplayField(infoPanel, gbc, row++, "TIN:", tinField = new JTextField(15));
        addDisplayField(infoPanel, gbc, row++, "Pag-IBIG:", pagibigField = new JTextField(15));

        row++; // Add spacing
        addSectionHeader(infoPanel, gbc, row++, "Salary Information");

        // Salary details
        addDisplayField(infoPanel, gbc, row++, "Basic Salary:", basicSalaryField = new JTextField(15));
        addDisplayField(infoPanel, gbc, row++, "Total Allowances:", allowancesField = new JTextField(15));

        return infoPanel;
    }

    /**
     * Create the salary computation panel
     */
    private JPanel createSalaryPanel() {
        JPanel salaryPanel = new JPanel(new BorderLayout(UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING));
        salaryPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        // Month selection panel
        JPanel monthPanel = createMonthSelectionPanel();
        salaryPanel.add(monthPanel, BorderLayout.NORTH);

        // Salary details panel
        JPanel detailsPanel = createSalaryDetailsPanel();
        salaryPanel.add(detailsPanel, BorderLayout.CENTER);

        return salaryPanel;
    }

    /**
     * Create the month selection panel
     */
    private JPanel createMonthSelectionPanel() {
        JPanel monthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        monthPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        JLabel monthLabel = new JLabel("Select Month for Salary Computation:");
        monthLabel.setFont(UIConstants.NORMAL_FONT);
        monthPanel.add(monthLabel);

        monthComboBox = new JComboBox<>(UIConstants.MONTHS);
        monthComboBox.setFont(UIConstants.NORMAL_FONT);
        monthPanel.add(monthComboBox);

        JButton computeButton = new JButton("Compute Salary");
        computeButton.setFont(UIConstants.BUTTON_FONT);
        computeButton.setBackground(UIConstants.PRIMARY_BLUE);
        computeButton.setForeground(Color.WHITE);
        computeButton.setFocusPainted(false);
        computeButton.addActionListener(e -> computeSalary());
        monthPanel.add(computeButton);

        return monthPanel;
    }

    /**
     * Create the salary details panel
     */
    private JPanel createSalaryDetailsPanel() {
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        JLabel detailsLabel = new JLabel("Salary Computation Details:");
        detailsLabel.setFont(UIConstants.HEADING_FONT);
        detailsPanel.add(detailsLabel, BorderLayout.NORTH);

        salaryDetailsArea = new JTextArea(15, 30);
        salaryDetailsArea.setFont(UIConstants.NORMAL_FONT);
        salaryDetailsArea.setEditable(false);
        salaryDetailsArea.setBackground(Color.WHITE);
        salaryDetailsArea.setText("Select a month and click 'Compute Salary' to view detailed salary breakdown.");

        JScrollPane scrollPane = new JScrollPane(salaryDetailsArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);

        return detailsPanel;
    }

    /**
     * Create the button panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING));
        buttonPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(UIConstants.BUTTON_FONT);
        closeButton.setBackground(UIConstants.DARK_GRAY);
        closeButton.setForeground(Color.WHITE);
        closeButton.setPreferredSize(UIConstants.MEDIUM_BUTTON_SIZE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);

        return buttonPanel;
    }

    /**
     * Helper method to add section headers
     */
    private void addSectionHeader(JPanel panel, GridBagConstraints gbc, int row, String text) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;

        JLabel headerLabel = new JLabel(text);
        headerLabel.setFont(UIConstants.HEADING_FONT);
        headerLabel.setForeground(UIConstants.HEADING_COLOR);
        panel.add(headerLabel, gbc);

        gbc.gridwidth = 1;
    }

    /**
     * Helper method to add display fields
     */
    private void addDisplayField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;

        JLabel label = new JLabel(labelText);
        label.setFont(UIConstants.NORMAL_FONT);
        panel.add(label, gbc);

        gbc.gridx = 1;
        field.setFont(UIConstants.NORMAL_FONT);
        field.setEditable(false);
        field.setBackground(UIConstants.LIGHT_GRAY);
        panel.add(field, gbc);
    }

    /**
     * Populate the fields with employee data
     */
    private void populateFields() {
        employeeIdField.setText(String.valueOf(employee.getEmployeeId()));
        nameField.setText(employee.getFullName());
        positionField.setText(employee.getPosition() != null ? employee.getPosition() : "N/A");
        statusField.setText(employee.getStatus() != null ? employee.getStatus() : "N/A");
        phoneField.setText(employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "N/A");
        addressField.setText(employee.getAddress() != null ? employee.getAddress() : "N/A");
        sssField.setText(employee.getSssNumber() != null ? employee.getSssNumber() : "N/A");
        philhealthField.setText(employee.getPhilhealthNumber() != null ? employee.getPhilhealthNumber() : "N/A");
        tinField.setText(employee.getTinNumber() != null ? employee.getTinNumber() : "N/A");
        pagibigField.setText(employee.getPagibigNumber() != null ? employee.getPagibigNumber() : "N/A");
        basicSalaryField.setText(String.format("₱%.2f", employee.getBasicSalary()));

        double totalAllowances = employee.getRiceSubsidy() + employee.getPhoneAllowance()
                + employee.getClothingAllowance();
        allowancesField.setText(String.format("₱%.2f", totalAllowances));
    }

    /**
     * Compute and display salary details for the selected month
     */
    private void computeSalary() {
        String selectedMonth = (String) monthComboBox.getSelectedItem();
        if (selectedMonth == null) {
            JOptionPane.showMessageDialog(this, "Please select a month first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Calculate salary details
        double basicSalary = employee.getBasicSalary();
        double riceSubsidy = employee.getRiceSubsidy();
        double phoneAllowance = employee.getPhoneAllowance();
        double clothingAllowance = employee.getClothingAllowance();
        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
        double grossPay = basicSalary + totalAllowances;
        double hourlyRate = employee.getHourlyRate();

        // Format the salary breakdown
        StringBuilder details = new StringBuilder();
        details.append("SALARY COMPUTATION DETAILS\n");
        details.append("Employee: ").append(employee.getFullName()).append("\n");
        details.append("Month: ").append(selectedMonth).append("\n");
        details.append("=".repeat(50)).append("\n\n");

        details.append("BASIC COMPENSATION:\n");
        details.append(String.format("Basic Salary: ₱%,.2f\n", basicSalary));
        details.append(String.format("Hourly Rate: ₱%.2f\n\n", hourlyRate));

        details.append("ALLOWANCES:\n");
        details.append(String.format("Rice Subsidy: ₱%,.2f\n", riceSubsidy));
        details.append(String.format("Phone Allowance: ₱%,.2f\n", phoneAllowance));
        details.append(String.format("Clothing Allowance: ₱%,.2f\n", clothingAllowance));
        details.append(String.format("Total Allowances: ₱%,.2f\n\n", totalAllowances));

        details.append("GROSS PAY CALCULATION:\n");
        details.append(String.format("Basic Salary: ₱%,.2f\n", basicSalary));
        details.append(String.format("Total Allowances: ₱%,.2f\n", totalAllowances));
        details.append("-".repeat(30)).append("\n");
        details.append(String.format("GROSS PAY: ₱%,.2f\n\n", grossPay));

        details.append("SEMI-MONTHLY BREAKDOWN:\n");
        details.append(String.format("Semi-monthly Gross: ₱%,.2f\n", grossPay / 2));

        // Note about deductions
        details.append("\n").append("*".repeat(50)).append("\n");
        details.append("NOTE: This shows gross pay calculation.\n");
        details.append("Actual take-home pay may differ due to:\n");
        details.append("- SSS contributions\n");
        details.append("- PhilHealth contributions\n");
        details.append("- Pag-IBIG contributions\n");
        details.append("- Withholding tax\n");
        details.append("- Other deductions\n");

        salaryDetailsArea.setText(details.toString());
        salaryDetailsArea.setCaretPosition(0); // Scroll to top
    }
}

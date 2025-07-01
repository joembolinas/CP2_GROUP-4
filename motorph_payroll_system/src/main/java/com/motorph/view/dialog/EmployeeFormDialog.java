package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.motorph.model.Employee;
import com.motorph.util.InputValidator;
import com.motorph.util.UIConstants;

/**
 * Dialog for adding or editing employees.
 * Provides form fields for all employee information.
 * Can be used for both adding and editing employees.
 */
public class EmployeeFormDialog extends JDialog {

    private boolean confirmed = false;
    private Employee employee;

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
    private JComboBox<String> statusComboBox;
    private JTextField positionField;
    private JTextField supervisorField;
    private JTextField basicSalaryField;
    private JTextField riceSubsidyField;
    private JTextField phoneAllowanceField;
    private JTextField clothingAllowanceField;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final String[] STATUS_OPTIONS = {"Regular", "Probationary", "Contract", "Project-based"};

    /**
     * Constructor for EmployeeFormDialog
     * @param parent The parent frame
     * @param title The dialog title
     * @param employee Existing employee to edit, or null for adding a new employee
     */
    public EmployeeFormDialog(JFrame parent, String title, Employee employee) {
        super(parent, title, true);
        this.employee = employee;
        initComponents();
        populateFields();
        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Initialize the dialog components
     */
    private void initComponents() {
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create form panel
        JPanel formPanel = createFormPanel();
        contentPanel.add(formPanel, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveEmployee());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
    }

    /**
     * Create the form panel with all input fields
     * @return The form panel
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout());

        // Personal Info Panel
        JPanel personalInfoPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        personalInfoPanel.setBorder(new TitledBorder("Personal Information"));

        personalInfoPanel.add(new JLabel("Employee ID:"));
        employeeIdField = new JTextField(10);
        personalInfoPanel.add(employeeIdField);

        personalInfoPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(20);
        personalInfoPanel.add(lastNameField);

        personalInfoPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(20);
        personalInfoPanel.add(firstNameField);

        personalInfoPanel.add(new JLabel("Birthday (MM/DD/YYYY):"));
        birthdayField = new JTextField(10);
        personalInfoPanel.add(birthdayField);

        personalInfoPanel.add(new JLabel("Address:"));
        addressField = new JTextField(30);
        personalInfoPanel.add(addressField);

        personalInfoPanel.add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField(15);
        personalInfoPanel.add(phoneNumberField);

        // Government IDs Panel
        JPanel governmentPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        governmentPanel.setBorder(new TitledBorder("Government IDs"));

        governmentPanel.add(new JLabel("SSS Number:"));
        sssNumberField = new JTextField(15);
        governmentPanel.add(sssNumberField);

        governmentPanel.add(new JLabel("PhilHealth Number:"));
        philhealthNumberField = new JTextField(15);
        governmentPanel.add(philhealthNumberField);

        governmentPanel.add(new JLabel("TIN Number:"));
        tinNumberField = new JTextField(15);
        governmentPanel.add(tinNumberField);

        governmentPanel.add(new JLabel("Pag-IBIG Number:"));
        pagibigNumberField = new JTextField(15);
        governmentPanel.add(pagibigNumberField);

        // Employment Details Panel
        JPanel employmentPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        employmentPanel.setBorder(new TitledBorder("Employment Details"));

        employmentPanel.add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(STATUS_OPTIONS);
        employmentPanel.add(statusComboBox);

        employmentPanel.add(new JLabel("Position:"));
        positionField = new JTextField(20);
        employmentPanel.add(positionField);

        employmentPanel.add(new JLabel("Supervisor:"));
        supervisorField = new JTextField(20);
        employmentPanel.add(supervisorField);

        // Compensation Panel
        JPanel compensationPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        compensationPanel.setBorder(new TitledBorder("Compensation Details"));

        compensationPanel.add(new JLabel("Basic Salary:"));
        basicSalaryField = new JTextField(10);
        compensationPanel.add(basicSalaryField);

        compensationPanel.add(new JLabel("Rice Subsidy:"));
        riceSubsidyField = new JTextField(10);
        compensationPanel.add(riceSubsidyField);

        compensationPanel.add(new JLabel("Phone Allowance:"));
        phoneAllowanceField = new JTextField(10);
        compensationPanel.add(phoneAllowanceField);

        compensationPanel.add(new JLabel("Clothing Allowance:"));
        clothingAllowanceField = new JTextField(10);
        compensationPanel.add(clothingAllowanceField);

        // Add all panels to main form
        JPanel mainFormPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        mainFormPanel.add(personalInfoPanel);
        mainFormPanel.add(governmentPanel);
        mainFormPanel.add(employmentPanel);
        mainFormPanel.add(compensationPanel);

        formPanel.add(mainFormPanel, BorderLayout.CENTER);
        return formPanel;
    }

    /**
     * Populate the form fields with data from the existing employee (if editing)
     */
    private void populateFields() {
        // Default values
        riceSubsidyField.setText("1500");
        phoneAllowanceField.setText("500");
        clothingAllowanceField.setText("500");

        // If editing an existing employee, populate form with their data
        if (employee != null) {
            employeeIdField.setText(String.valueOf(employee.getEmployeeId()));
            employeeIdField.setEditable(false); // Can't change employee ID when editing

            lastNameField.setText(employee.getLastName());
            firstNameField.setText(employee.getFirstName());

            if (employee.getBirthday() != null) {
                birthdayField.setText(employee.getBirthday().format(DATE_FORMATTER));
            }

            addressField.setText(employee.getAddress());
            phoneNumberField.setText(employee.getPhoneNumber());
            sssNumberField.setText(employee.getSssNumber());
            philhealthNumberField.setText(employee.getPhilhealthNumber());
            tinNumberField.setText(employee.getTinNumber());
            pagibigNumberField.setText(employee.getPagibigNumber());

            // Set selected status
            for (int i = 0; i < STATUS_OPTIONS.length; i++) {
                if (STATUS_OPTIONS[i].equals(employee.getStatus())) {
                    statusComboBox.setSelectedIndex(i);
                    break;
                }
            }

            positionField.setText(employee.getPosition());
            supervisorField.setText(employee.getSupervisor());
            basicSalaryField.setText(String.valueOf(employee.getBasicSalary()));
            riceSubsidyField.setText(String.valueOf(employee.getRiceSubsidy()));
            phoneAllowanceField.setText(String.valueOf(employee.getPhoneAllowance()));
            clothingAllowanceField.setText(String.valueOf(employee.getClothingAllowance()));
        }
    }

    /**
     * Validate form input and save employee
     */
    private void saveEmployee() {
        try {
            // Validate employee ID
            int employeeId;
            try {
                employeeId = Integer.parseInt(employeeIdField.getText().trim());
                if (employeeId <= 0) {
                    throw new NumberFormatException("Employee ID must be a positive number");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid Employee ID. Please enter a valid number.");
            }

            // Validate required fields
            String lastName = lastNameField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String position = positionField.getText().trim();

            if (lastName.isEmpty()) {
                throw new IllegalArgumentException("Last Name is required");
            }
            if (firstName.isEmpty()) {
                throw new IllegalArgumentException("First Name is required");
            }
            if (position.isEmpty()) {
                throw new IllegalArgumentException("Position is required");
            }

            // Parse birthday if provided
            LocalDate birthday = null;
            String birthdayText = birthdayField.getText().trim();
            if (!birthdayText.isEmpty()) {
                try {
                    birthday = LocalDate.parse(birthdayText, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid date format. Please use MM/DD/YYYY");
                }
            }

            // Parse salary and allowances
            double basicSalary;
            try {
                basicSalary = Double.parseDouble(basicSalaryField.getText().trim());
                if (basicSalary <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Basic Salary must be a positive number");
            }

            double riceSubsidy = parseDoubleWithDefault(riceSubsidyField.getText(), 1500);
            double phoneAllowance = parseDoubleWithDefault(phoneAllowanceField.getText(), 500);
            double clothingAllowance = parseDoubleWithDefault(clothingAllowanceField.getText(), 500);

            // Calculate semi-monthly rate
            double grossSemiMonthlyRate = basicSalary / 2;

            // Calculate hourly rate (based on 168 hours per month)
            double hourlyRate = (grossSemiMonthlyRate / 84);

            // Get selected status
            String status = (String) statusComboBox.getSelectedItem();

            // Create or update employee object
            if (employee == null) {
                // Creating a new employee
                employee = new Employee(
                    employeeId, lastName, firstName, birthday,
                    addressField.getText().trim(), phoneNumberField.getText().trim(),
                    sssNumberField.getText().trim(), philhealthNumberField.getText().trim(),
                    tinNumberField.getText().trim(), pagibigNumberField.getText().trim(),
                    status, position, supervisorField.getText().trim(),
                    basicSalary, riceSubsidy, phoneAllowance, clothingAllowance, grossSemiMonthlyRate
                );
            } else {
                // Updating an existing employee
                employee.setLastName(lastName);
                employee.setFirstName(firstName);
                employee.setBirthday(birthday);
                employee.setAddress(addressField.getText().trim());
                employee.setPhoneNumber(phoneNumberField.getText().trim());
                employee.setSssNumber(sssNumberField.getText().trim());
                employee.setPhilhealthNumber(philhealthNumberField.getText().trim());
                employee.setTinNumber(tinNumberField.getText().trim());
                employee.setPagibigNumber(pagibigNumberField.getText().trim());
                employee.setStatus(status);
                employee.setPosition(position);
                employee.setSupervisor(supervisorField.getText().trim());
                employee.setBasicSalary(basicSalary);
                employee.setRiceSubsidy(riceSubsidy);
                employee.setPhoneAllowance(phoneAllowance);
                employee.setClothingAllowance(clothingAllowance);
                employee.setGrossSemiMonthlyRate(grossSemiMonthlyRate);
                // Hourly rate is calculated automatically when setting basic salary
            }

            confirmed = true;
            dispose();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Parse double with default value if empty or invalid
     * @param value The string value to parse
     * @param defaultValue The default value to use if parsing fails
     * @return The parsed double value or default
     */
    private double parseDoubleWithDefault(String value, double defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Check if user confirmed the dialog
     * @return true if user confirmed, false otherwise
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Get the employee created or edited in the dialog
     * @return The employee object
     */
    public Employee getEmployee() {
        return employee;
    }
}

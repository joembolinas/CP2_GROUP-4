package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.motorph.controller.EmployeeController;
import com.motorph.model.Employee;
import com.motorph.util.AppConstants;
import com.motorph.util.AppUtils;

/**
 * Universal employee dialog for both adding new employees and editing existing
 * ones.
 * Consolidated from NewEmployeeDialog and EditEmployeeDialog for better
 * maintainability.
 */
public class EmployeeDialog extends JDialog {

    private final EmployeeController employeeController;
    private final Employee existingEmployee; // null for new employee
    private final boolean isEditMode;
    private boolean operationSuccessful = false;

    // Form fields
    private JTextField employeeIdField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField birthdayField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField sssField;
    private JTextField philhealthField;
    private JTextField tinField;
    private JTextField pagibigField;
    private JTextField statusField;
    private JTextField positionField;
    private JTextField supervisorField;
    private JTextField basicSalaryField;
    private JTextField riceSubsidyField;
    private JTextField phoneAllowanceField;
    private JTextField clothingAllowanceField;

    /**
     * Constructor for adding new employee
     */
    public EmployeeDialog(JFrame parent, EmployeeController employeeController) {
        this(parent, employeeController, null);
    }

    /**
     * Constructor for editing existing employee
     */
    public EmployeeDialog(JFrame parent, EmployeeController employeeController, Employee employee) {
        super(parent, employee == null ? "Add New Employee" : "Edit Employee", true);
        this.employeeController = employeeController;
        this.existingEmployee = employee;
        this.isEditMode = (employee != null);

        initializeDialog();
        setupComponents();
        if (isEditMode) {
            populateFields();
        }
    }

    private void initializeDialog() {
        setSize(600, 700);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void setupComponents() {
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout());

        // Basic Information Panel
        JPanel basicInfoPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        basicInfoPanel.setBorder(new TitledBorder("Basic Information"));

        basicInfoPanel.add(new JLabel("Employee ID:"));
        employeeIdField = new JTextField();
        employeeIdField.setEnabled(!isEditMode); // Disable for edit mode
        basicInfoPanel.add(employeeIdField);

        basicInfoPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        basicInfoPanel.add(lastNameField);

        basicInfoPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        basicInfoPanel.add(firstNameField);

        basicInfoPanel.add(new JLabel("Birthday (MM/dd/yyyy):"));
        birthdayField = new JTextField();
        basicInfoPanel.add(birthdayField);

        basicInfoPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        basicInfoPanel.add(addressField);

        basicInfoPanel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        basicInfoPanel.add(phoneField);

        // Government IDs Panel
        JPanel idsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        idsPanel.setBorder(new TitledBorder("Government IDs"));

        idsPanel.add(new JLabel("SSS Number:"));
        sssField = new JTextField();
        idsPanel.add(sssField);

        idsPanel.add(new JLabel("PhilHealth Number:"));
        philhealthField = new JTextField();
        idsPanel.add(philhealthField);

        idsPanel.add(new JLabel("TIN Number:"));
        tinField = new JTextField();
        idsPanel.add(tinField);

        idsPanel.add(new JLabel("Pag-IBIG Number:"));
        pagibigField = new JTextField();
        idsPanel.add(pagibigField);

        // Employment Details Panel
        JPanel employmentPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        employmentPanel.setBorder(new TitledBorder("Employment Details"));

        employmentPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        employmentPanel.add(statusField);

        employmentPanel.add(new JLabel("Position:"));
        positionField = new JTextField();
        employmentPanel.add(positionField);

        employmentPanel.add(new JLabel("Immediate Supervisor:"));
        supervisorField = new JTextField();
        employmentPanel.add(supervisorField);

        // Compensation Panel
        JPanel compensationPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        compensationPanel.setBorder(new TitledBorder("Compensation"));

        compensationPanel.add(new JLabel("Basic Salary:"));
        basicSalaryField = new JTextField();
        compensationPanel.add(basicSalaryField);

        compensationPanel.add(new JLabel("Rice Subsidy:"));
        riceSubsidyField = new JTextField();
        compensationPanel.add(riceSubsidyField);

        compensationPanel.add(new JLabel("Phone Allowance:"));
        phoneAllowanceField = new JTextField();
        compensationPanel.add(phoneAllowanceField);

        compensationPanel.add(new JLabel("Clothing Allowance:"));
        clothingAllowanceField = new JTextField();
        compensationPanel.add(clothingAllowanceField);

        // Combine all panels
        JPanel combinedPanel = new JPanel(new BorderLayout(0, 15));
        combinedPanel.add(basicInfoPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel(new BorderLayout(0, 15));
        middlePanel.add(idsPanel, BorderLayout.NORTH);
        middlePanel.add(employmentPanel, BorderLayout.CENTER);
        combinedPanel.add(middlePanel, BorderLayout.CENTER);

        combinedPanel.add(compensationPanel, BorderLayout.SOUTH);

        formPanel.add(combinedPanel, BorderLayout.CENTER);
        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton saveButton = AppUtils.createPrimaryButton(isEditMode ? "Update Employee" : "Add Employee");
        saveButton.addActionListener(e -> saveEmployee());

        JButton cancelButton = AppUtils.createSecondaryButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void populateFields() {
        if (existingEmployee != null) {
            employeeIdField.setText(String.valueOf(existingEmployee.getEmployeeId()));
            lastNameField.setText(existingEmployee.getLastName());
            firstNameField.setText(existingEmployee.getFirstName());

            if (existingEmployee.getBirthday() != null) {
                birthdayField.setText(AppUtils.formatDate(existingEmployee.getBirthday()));
            }

            addressField.setText(existingEmployee.getAddress() != null ? existingEmployee.getAddress() : "");
            phoneField.setText(existingEmployee.getPhoneNumber() != null ? existingEmployee.getPhoneNumber() : "");
            sssField.setText(existingEmployee.getSssNumber() != null ? existingEmployee.getSssNumber() : "");
            philhealthField.setText(
                    existingEmployee.getPhilhealthNumber() != null ? existingEmployee.getPhilhealthNumber() : "");
            tinField.setText(existingEmployee.getTinNumber() != null ? existingEmployee.getTinNumber() : "");
            pagibigField
                    .setText(existingEmployee.getPagibigNumber() != null ? existingEmployee.getPagibigNumber() : "");
            statusField.setText(existingEmployee.getStatus() != null ? existingEmployee.getStatus() : "");
            positionField.setText(existingEmployee.getPosition() != null ? existingEmployee.getPosition() : "");
            supervisorField.setText(existingEmployee.getSupervisor() != null ? existingEmployee.getSupervisor() : "");
            basicSalaryField.setText(String.valueOf(existingEmployee.getBasicSalary()));
            riceSubsidyField.setText(String.valueOf(existingEmployee.getRiceSubsidy()));
            phoneAllowanceField.setText(String.valueOf(existingEmployee.getPhoneAllowance()));
            clothingAllowanceField.setText(String.valueOf(existingEmployee.getClothingAllowance()));
        }
    }

    private void saveEmployee() {
        try {
            // Validate and collect form data
            int employeeId = AppUtils.validateEmployeeNumber(employeeIdField.getText());
            String lastName = AppUtils.validateName(lastNameField.getText(), "Last Name");
            String firstName = AppUtils.validateName(firstNameField.getText(), "First Name");

            LocalDate birthday = null;
            if (!birthdayField.getText().trim().isEmpty()) {
                birthday = AppUtils.validateDate(birthdayField.getText());
            }

            String address = addressField.getText().trim();
            String phone = AppUtils.validatePhoneNumber(phoneField.getText());
            String sss = sssField.getText().trim();
            String philhealth = philhealthField.getText().trim();
            String tin = tinField.getText().trim();
            String pagibig = pagibigField.getText().trim();
            String status = statusField.getText().trim();
            String position = positionField.getText().trim();
            String supervisor = supervisorField.getText().trim();

            double basicSalary = AppUtils.validateSalary(basicSalaryField.getText(), "Basic Salary");
            double riceSubsidy = AppUtils.validateSalary(riceSubsidyField.getText(), "Rice Subsidy");
            double phoneAllowance = AppUtils.validateSalary(phoneAllowanceField.getText(), "Phone Allowance");
            double clothingAllowance = AppUtils.validateSalary(clothingAllowanceField.getText(), "Clothing Allowance");

            // Create or update employee
            Employee employee = new Employee(
                    employeeId, lastName, firstName, birthday, address, phone,
                    sss, philhealth, tin, pagibig, status, position, supervisor,
                    basicSalary, riceSubsidy, phoneAllowance, clothingAllowance, 0.0);

            // Save to database
            boolean success;
            if (isEditMode) {
                success = employeeController.updateEmployee(employee);
                if (success) {
                    AppUtils.showSuccess(this, AppConstants.EMPLOYEE_UPDATED_SUCCESS);
                }
            } else {
                success = employeeController.addEmployee(employee);
                if (success) {
                    AppUtils.showSuccess(this, AppConstants.EMPLOYEE_ADDED_SUCCESS);
                } else {
                    AppUtils.showError(this, AppConstants.DUPLICATE_EMPLOYEE_ID);
                    return;
                }
            }

            if (success) {
                operationSuccessful = true;
                dispose();
            }

        } catch (IllegalArgumentException ex) {
            AppUtils.showError(this, ex.getMessage());
        } catch (Exception ex) {
            AppUtils.handleException("saving employee", ex, "Failed to save employee. Please try again.");
        }
    }

    /**
     * Check if the operation (add/edit) was successful
     */
    public boolean isOperationSuccessful() {
        return operationSuccessful;
    }

    /**
     * Show dialog for adding new employee
     */
    public static boolean showAddDialog(JFrame parent, EmployeeController employeeController) {
        EmployeeDialog dialog = new EmployeeDialog(parent, employeeController);
        dialog.setVisible(true);
        return dialog.isOperationSuccessful();
    }

    /**
     * Show dialog for editing existing employee
     */
    public static boolean showEditDialog(JFrame parent, EmployeeController employeeController, Employee employee) {
        EmployeeDialog dialog = new EmployeeDialog(parent, employeeController, employee);
        dialog.setVisible(true);
        return dialog.isOperationSuccessful();
    }

    /**
     *  create a label with required asterisk
     * @param text The label text
     * @return JLabel with asterisk for required fields
     *
     *
     *
     */



}



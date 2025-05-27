package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.motorph.controller.EmployeeController;
import com.motorph.controller.PayrollController;
import com.motorph.model.Employee;
import com.motorph.util.UIConstants;
import com.motorph.view.dialog.EmployeeDetailsFrame;
import com.motorph.view.dialog.NewEmployeeDialog;

/**
 * Panel for displaying employee list with management functions as required by MPHCR-02.
 * Displays employees in JTable with Employee Number, Last Name, First Name, 
 * SSS Number, PhilHealth Number, TIN, and Pag-IBIG Number.
 */
public class EmployeeListPanel extends JPanel {
    
    private final MainFrame mainFrame;
    private final EmployeeController employeeController;
    private final PayrollController payrollController;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> monthComboBox;
    
    private static final String[] COLUMN_NAMES = {
        "Employee #", "Last Name", "First Name", "SSS Number", 
        "PhilHealth Number", "TIN", "Pag-IBIG Number"
    };
    
    private static final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    /**
     * Constructor for the employee list panel
     */
    public EmployeeListPanel(MainFrame mainFrame, EmployeeController employeeController, 
                           PayrollController payrollController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        this.payrollController = payrollController;
        initPanel();
        loadEmployeeData();
    }
    
    /**
     * Initialize the panel
     */
    private void initPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIConstants.BACKGROUND_COLOR);
        
        // North panel with title and controls
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        JLabel titleLabel = new JLabel("Employee Management System", SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.TITLE_FONT);
        northPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Control panel with month selection and buttons
        JPanel controlPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        controlPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        // Month selection row
        controlPanel.add(new JLabel("Select Month for Salary Computation:"));
        monthComboBox = new JComboBox<>(MONTHS);
        monthComboBox.setSelectedIndex(LocalDate.now().getMonthValue() - 1); // Current month
        controlPanel.add(monthComboBox);
        
        JButton computeButton = createStyledButton("Compute Salary");
        controlPanel.add(computeButton);
        controlPanel.add(new JLabel("")); // Spacer
        
        // Button row
        JButton viewEmployeeButton = createStyledButton("View Employee Details");
        JButton newEmployeeButton = createStyledButton("New Employee");
        JButton refreshButton = createStyledButton("Refresh");
        JButton backButton = createStyledButton("Back to Main Menu");
        
        controlPanel.add(viewEmployeeButton);
        controlPanel.add(newEmployeeButton);
        controlPanel.add(refreshButton);
        controlPanel.add(backButton);
        
        northPanel.add(controlPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);
        
        // Center panel with employee table
        createEmployeeTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBackground(UIConstants.BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add action listeners
        viewEmployeeButton.addActionListener(e -> viewSelectedEmployee());
        newEmployeeButton.addActionListener(e -> openNewEmployeeDialog());
        computeButton.addActionListener(e -> computeSalaryForSelectedEmployee());
        refreshButton.addActionListener(e -> {
            loadEmployeeData();
            JOptionPane.showMessageDialog(this, "Employee data refreshed successfully!", 
                    "Refresh", JOptionPane.INFORMATION_MESSAGE);
        });
        backButton.addActionListener(e -> mainFrame.showMainMenu());
    }
    
    /**
     * Create the employee table
     */
    private void createEmployeeTable() {
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        
        // Set column widths
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Employee #
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Last Name
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(150); // First Name
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(120); // SSS
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(120); // PhilHealth
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(120); // TIN
        employeeTable.getColumnModel().getColumn(6).setPreferredWidth(120); // Pag-IBIG
    }
    
    /**
     * Load employee data into the table
     */
    private void loadEmployeeData() {
        try {
            List<Employee> employees = employeeController.getAllEmployees();
            
            // Clear existing data
            tableModel.setRowCount(0);
            
            // Add employee data to table
            for (Employee employee : employees) {
                Object[] row = {
                    employee.getEmployeeId(),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getSssNumber() != null ? employee.getSssNumber() : "N/A",
                    employee.getPhilhealthNumber() != null ? employee.getPhilhealthNumber() : "N/A",
                    employee.getTinNumber() != null ? employee.getTinNumber() : "N/A",
                    employee.getPagibigNumber() != null ? employee.getPagibigNumber() : "N/A"
                };
                tableModel.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Error loading employee data: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * View details of the selected employee
     */
    private void viewSelectedEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Please select an employee to view details.",
                    "No Selection", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int employeeId = (Integer) tableModel.getValueAt(selectedRow, 0);
            Employee employee = employeeController.findEmployeeById(employeeId);
            
            // Open employee details frame
            new EmployeeDetailsFrame(mainFrame, employee).setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Error viewing employee details: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Open new employee dialog
     */
    private void openNewEmployeeDialog() {
        NewEmployeeDialog dialog = new NewEmployeeDialog(mainFrame, employeeController);
        dialog.setVisible(true);
        
        if (dialog.isEmployeeAdded()) {
            loadEmployeeData(); // Refresh the table
            JOptionPane.showMessageDialog(this, 
                    "New employee added successfully!",
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Compute salary for selected employee for the selected month
     */
    private void computeSalaryForSelectedEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Please select an employee to compute salary.",
                    "No Selection", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int employeeId = (Integer) tableModel.getValueAt(selectedRow, 0);
            Employee employee = employeeController.findEmployeeById(employeeId);
            
            // Get selected month
            int selectedMonthIndex = monthComboBox.getSelectedIndex();
            Month selectedMonth = Month.of(selectedMonthIndex + 1);
            int currentYear = LocalDate.now().getYear();
            
            // Calculate date range for the selected month
            LocalDate startDate = LocalDate.of(currentYear, selectedMonth, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            
            // Generate payslip for the month
            var payslip = payrollController.generatePayslip(employeeId, startDate, endDate);
            
            // Create a detailed view showing employee details and computed salary
            StringBuilder details = new StringBuilder();
            details.append("=== EMPLOYEE DETAILS ===\n");
            details.append(String.format("Employee ID: %d\n", employee.getEmployeeId()));
            details.append(String.format("Name: %s\n", employee.getFullName()));
            details.append(String.format("Position: %s\n", employee.getPosition()));
            details.append(String.format("Basic Salary: ₱%,.2f\n", employee.getBasicSalary()));
            details.append(String.format("Hourly Rate: ₱%.2f\n", employee.getHourlyRate()));
            details.append("\n=== COMPUTED SALARY FOR ").append(selectedMonth.name()).append(" ").append(currentYear).append(" ===\n");
            details.append(String.format("Regular Hours: %.2f\n", payslip.getRegularHours()));
            details.append(String.format("Overtime Hours: %.2f\n", payslip.getOvertimeHours()));
            details.append(String.format("Gross Pay: ₱%,.2f\n", payslip.getGrossPay()));
            details.append(String.format("Net Pay: ₱%,.2f\n", payslip.getNetPay()));
            
            JOptionPane.showMessageDialog(this, 
                    details.toString(),
                    "Salary Computation for " + employee.getFullName(), 
                    JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Error computing salary: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
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

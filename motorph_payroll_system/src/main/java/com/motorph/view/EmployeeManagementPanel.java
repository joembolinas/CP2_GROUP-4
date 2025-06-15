package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.motorph.controller.EmployeeController;
import com.motorph.util.UIConstants;
import com.motorph.view.dialog.DateRangeDialog;
import com.motorph.view.dialog.EmployeeNumberInputDialog;
import com.motorph.view.dialog.SearchResultDialog;

/**
 * Panel for employee management functions.
 */
public class EmployeeManagementPanel extends JPanel {
    
    private final MainFrame mainFrame;
    private final EmployeeController employeeController;
    
    /**
     * Constructor for the employee management panel
     */
    public EmployeeManagementPanel(MainFrame mainFrame, EmployeeController employeeController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        initPanel();
    }
    
    /**
     * Initialize the panel
     */
    private void initPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIConstants.BACKGROUND_COLOR);
        
        // North panel with title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(UIConstants.BACKGROUND_COLOR);
        JLabel titleLabel = new JLabel("Employee Management", SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Center panel with buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        JButton searchEmployeeButton = createStyledButton("Search Employee");
        JButton listAllEmployeesButton = createStyledButton("List All Employees");
        JButton attendanceButton = createStyledButton("Attendance");
        JButton backButton = createStyledButton("Back to Main Menu");
        
        buttonPanel.add(searchEmployeeButton);
        buttonPanel.add(listAllEmployeesButton);
        buttonPanel.add(attendanceButton);
        buttonPanel.add(backButton);
        
        // Add to a wrapper panel with margins
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        
        // Add action listeners for buttons
        searchEmployeeButton.addActionListener(e -> searchEmployee());
        listAllEmployeesButton.addActionListener(e -> listAllEmployees());
        attendanceButton.addActionListener(e -> viewAttendance());
        backButton.addActionListener(e -> mainFrame.showMainMenu());
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
    
    /**
     * Search for an employee
     */
    private void searchEmployee() {
        String searchTerm = JOptionPane.showInputDialog(mainFrame, 
                "Enter search term (name or employee number):");
                
        if (searchTerm == null || searchTerm.isEmpty()) {
            return; // User canceled
        }
        
        try {
            var matchingEmployees = employeeController.searchEmployees(searchTerm);
            
            if (matchingEmployees.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, 
                        "No employees found matching your search criteria.",
                        "Search Results", 
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Display results in a dialog
            new SearchResultDialog(mainFrame, matchingEmployees, "Search Results").setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, 
                    "Error searching for employees: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * List all employees
     */
    private void listAllEmployees() {
        try {
            var employees = employeeController.getAllEmployees();
            
            if (employees.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, 
                        "No employees found in the system.",
                        "All Employees", 
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Display results in a dialog
            new SearchResultDialog(mainFrame, employees, "All Employees").setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, 
                    "Error listing employees: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * View attendance records for an employee
     */
    private void viewAttendance() {
        // Show employee number input dialog
        EmployeeNumberInputDialog employeeDialog = new EmployeeNumberInputDialog(mainFrame, "Attendance Records");
        employeeDialog.setVisible(true);
        
        if (!employeeDialog.isConfirmed()) {
            return; // User canceled
        }
        
        int employeeId = employeeDialog.getEmployeeNumber();
        
        // Show date range dialog
        DateRangeDialog dateDialog = new DateRangeDialog(mainFrame, "Select Date Range");
        dateDialog.setVisible(true);
        
        if (!dateDialog.isConfirmed()) {
            return; // User canceled
        }
        
        LocalDate startDate = dateDialog.getStartDate();
        LocalDate endDate = dateDialog.getEndDate();
        
        try {
            // Find employee first
            var employee = employeeController.findEmployeeById(employeeId);
            
            // Get attendance records
            var records = employeeController.getAttendanceRecords(employeeId, startDate, endDate);
            
            if (records.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, 
                        "No attendance records found for the specified period.",
                        "Attendance Records", 
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Prepare data for the table
            String[] columnNames = {"Date", "Time In", "Time Out", "Duration (hrs)", "Remarks"};
            Object[][] data = new Object[records.size()][5];
            
            for (int i = 0; i < records.size(); i++) {
                var record = records.get(i);
                data[i][0] = record.getDate();
                data[i][1] = record.getTimeIn();
                data[i][2] = record.getTimeOut();
                data[i][3] = String.format("%.2f", record.getTotalHours());
                data[i][4] = record.isLate() ? "Late" : "On Time";
            }
            
            // Create the table
            JTable table = new JTable(data, columnNames);
            table.setFillsViewportHeight(true);
            
            // Show in a dialog
            JOptionPane.showMessageDialog(mainFrame, 
                    new JScrollPane(table), 
                    "Attendance Records for " + employee.getFullName(), 
                    JOptionPane.INFORMATION_MESSAGE);
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(mainFrame, 
                    e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, 
                    "Error retrieving attendance records: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

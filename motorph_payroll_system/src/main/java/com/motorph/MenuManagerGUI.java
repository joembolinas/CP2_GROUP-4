package com.motorph;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MenuManagerGUI extends JFrame {
    private List<Employee> employees;
    private List<AttendanceRecord> attendanceRecords;
    private PayrollCalculator payrollCalculator;

    public MenuManagerGUI(String employeesFilePath, String attendanceFilePath) {
        // Initialize the CSVLoader
        CSVLoader csvLoader = new CSVLoader(employeesFilePath, attendanceFilePath);

        try {
            // Load employees and attendance records
            this.employees = csvLoader.loadEmployees();
            this.attendanceRecords = csvLoader.loadAttendanceRecords();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.employees = List.of(); // Fallback to empty list
            this.attendanceRecords = List.of(); // Fallback to empty list
        }

        this.payrollCalculator = new PayrollCalculator();

        // Set up the main JFrame
        setTitle("MotorPH Payroll System");
        setSize(800, 600); // Adjusted size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the main menu
        initMainMenu();
    }

    private void initMainMenu() {
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(5, 1, 10, 10));
        mainMenuPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        JLabel titleLabel = new JLabel("MotorPH Payroll System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainMenuPanel.add(titleLabel);

        // Create buttons with white background and smaller size
        JButton employeeManagementButton = createStyledButton("Employee Management");
        JButton payrollManagementButton = createStyledButton("Payroll Management");
        JButton reportsButton = createStyledButton("Reports");
        JButton exitButton = createStyledButton("Exit");

        mainMenuPanel.add(employeeManagementButton);
        mainMenuPanel.add(payrollManagementButton);
        mainMenuPanel.add(reportsButton);
        mainMenuPanel.add(exitButton);

        // Add action listeners for buttons
        employeeManagementButton.addActionListener(e -> showEmployeeManagementMenu());
        payrollManagementButton.addActionListener(e -> showPayrollManagementMenu());
        reportsButton.addActionListener(e -> showReportsMenu());
        exitButton.addActionListener(e -> System.exit(0));

        // Add the panel to the frame
        setContentPane(mainMenuPanel);
        revalidate();
    }

    private void showEmployeeManagementMenu() {
        JPanel employeeMenuPanel = new JPanel();
        employeeMenuPanel.setLayout(new GridLayout(5, 1, 10, 10));
        employeeMenuPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        JLabel titleLabel = new JLabel("Employee Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        employeeMenuPanel.add(titleLabel);

        JButton searchEmployeeButton = createStyledButton("Search Employee");
        JButton listAllEmployeesButton = createStyledButton("List All Employees");
        JButton attendanceButton = createStyledButton("Attendance");
        JButton backButton = createStyledButton("Back to Main Menu");

        employeeMenuPanel.add(searchEmployeeButton);
        employeeMenuPanel.add(listAllEmployeesButton);
        employeeMenuPanel.add(attendanceButton);
        employeeMenuPanel.add(backButton);

        // Add action listeners for buttons
        searchEmployeeButton.addActionListener(e -> searchEmployee());
        listAllEmployeesButton.addActionListener(e -> listAllEmployees());
        attendanceButton.addActionListener(e -> viewAttendance());
        backButton.addActionListener(e -> initMainMenu());

        // Add the panel to the frame
        setContentPane(employeeMenuPanel);
        revalidate();
    }

    private void showPayrollManagementMenu() {
        JPanel payrollMenuPanel = new JPanel();
        payrollMenuPanel.setLayout(new GridLayout(4, 1, 10, 10));
        payrollMenuPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        JLabel titleLabel = new JLabel("Payroll Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        payrollMenuPanel.add(titleLabel);

        JButton generatePayrollButton = createStyledButton("Generate Payroll");
        JButton customPayrollButton = createStyledButton("Custom Payroll");
        JButton backButton = createStyledButton("Back to Main Menu");

        payrollMenuPanel.add(generatePayrollButton);
        payrollMenuPanel.add(customPayrollButton);
        payrollMenuPanel.add(backButton);

        // Add action listeners for buttons
        generatePayrollButton.addActionListener(e -> generatePayroll());
        customPayrollButton.addActionListener(e -> generateEmployeePayslip());
        backButton.addActionListener(e -> initMainMenu());

        // Add the panel to the frame
        setContentPane(payrollMenuPanel);
        revalidate();
    }

    private void showReportsMenu() {
        JPanel reportsMenuPanel = new JPanel();
        reportsMenuPanel.setLayout(new GridLayout(5, 1, 10, 10));
        reportsMenuPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        JLabel titleLabel = new JLabel("Reports", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        reportsMenuPanel.add(titleLabel);

        JButton payslipButton = createStyledButton("Payslip");
        JButton weeklySummaryButton = createStyledButton("Weekly Summary");
        JButton monthlySummaryButton = createStyledButton("Monthly Summary");
        JButton backButton = createStyledButton("Back to Main Menu");

        reportsMenuPanel.add(payslipButton);
        reportsMenuPanel.add(weeklySummaryButton);
        reportsMenuPanel.add(monthlySummaryButton);
        reportsMenuPanel.add(backButton);

        // Add action listeners for buttons
        payslipButton.addActionListener(e -> generateEmployeePayslip("PAYSLIP REPORT"));
        weeklySummaryButton.addActionListener(e -> generateSummaryReport("Weekly"));
        monthlySummaryButton.addActionListener(e -> generateSummaryReport("Monthly"));
        backButton.addActionListener(e -> initMainMenu());

        // Add the panel to the frame
        setContentPane(reportsMenuPanel);
        revalidate();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 30)); // Smaller button size
        return button;
    }

    private void searchEmployee() {
        // Prompt the user for a search term
        String searchTerm = JOptionPane.showInputDialog(this, "Enter search term (name or employee number):");
        if (searchTerm == null || searchTerm.isEmpty()) {
            return; // Exit if the user cancels or enters nothing
        }
        searchTerm = searchTerm.toLowerCase();

        // Prepare the result header
        StringBuilder result = new StringBuilder();
        result.append(String.format("%-10s %-20s %-20s %-15s %-15s%n",
                "Emp#", "Name", "Position", "Status", "Hourly Rate"));

        boolean found = false;
        for (Employee employee : employees) {
            String empId = String.valueOf(employee.getEmployeeId()).toLowerCase();
            String lastName = employee.getLastName().toLowerCase();
            String firstName = employee.getFirstName().toLowerCase();
            if (empId.contains(searchTerm) || lastName.contains(searchTerm) || firstName.contains(searchTerm)) {
                found = true;
                result.append(String.format("%-10d %-20s %-20s %-15s %-15.2f%n",
                        employee.getEmployeeId(), employee.getFullName(),
                        employee.getPosition(), employee.getStatus(), employee.getHourlyRate()));
            }
        }

        // Display the results
        if (!found) {
            JOptionPane.showMessageDialog(this, "No employees found matching your search criteria.");
        } else {
            JTextArea textArea = new JTextArea(result.toString());
            textArea.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void listAllEmployees() {
        // Define column names for the table
        String[] columnNames = {"Emp#", "Name", "Position", "Status", "Hourly Rate"};

        // Prepare data for the table
        Object[][] data = new Object[employees.size()][5];
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            String name = employee.getFullName();
            String position = employee.getPosition().length() > 18
                    ? employee.getPosition().substring(0, 15) + "..."
                    : employee.getPosition();
            data[i][0] = employee.getEmployeeId();
            data[i][1] = name;
            data[i][2] = position;
            data[i][3] = employee.getStatus();
            data[i][4] = employee.getHourlyRate();
        }

        // Create the table with the data and column names
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Display the table in a dialog
        JOptionPane.showMessageDialog(this, scrollPane, "All Employees", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewAttendance() {
        // Prompt the user for the employee ID
        String empNumberStr = JOptionPane.showInputDialog(this, "Enter Employee No:");
        if (empNumberStr == null || empNumberStr.isEmpty()) return;

        int empNumber;
        try {
            empNumber = Integer.parseInt(empNumberStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid employee number. Please enter a numeric value.");
            return;
        }

        // Prompt the user for the date range
        LocalDate startDate = getDateInput("Date From (MM/DD/YYYY):");
        LocalDate endDate = getDateInput("Date To (MM/DD/YYYY):");

        // Prepare data for the table
        String[] columnNames = {"Date", "Time In", "Time Out", "Duration (hrs)", "Remarks"};
        List<AttendanceRecord> filteredRecords = new ArrayList<>();
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getEmployeeId() == empNumber) {
                LocalDate recordDate = record.getDate();
                if (recordDate != null && !recordDate.isBefore(startDate) && !recordDate.isAfter(endDate)) {
                    filteredRecords.add(record);
                }
            }
        }

        if (filteredRecords.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No attendance records found for the specified period.");
            return;
        }

        Object[][] data = new Object[filteredRecords.size()][5];
        for (int i = 0; i < filteredRecords.size(); i++) {
            AttendanceRecord record = filteredRecords.get(i);
            data[i][0] = record.getDate();
            data[i][1] = record.getTimeIn();
            data[i][2] = record.getTimeOut();
            data[i][3] = String.format("%.2f", record.getTotalHours());
            data[i][4] = record.isLate() ? "Late" : "On Time";
        }

        // Create the table with the data and column names
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Display the table in a dialog
        JOptionPane.showMessageDialog(this, scrollPane, "Attendance Records", JOptionPane.INFORMATION_MESSAGE);
    }

    private LocalDate getDateInput(String prompt) {
        while (true) {
            String input = JOptionPane.showInputDialog(this, prompt);
            if (input == null || input.isEmpty()) return null;
            try {
                return LocalDate.parse(input, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use MM/DD/YYYY format.");
            }
        }
    }

    private void generatePayroll() {
        // Prompt the user for the date range
        LocalDate startDate = getDateInput("Date From (MM/DD/YYYY):");
        LocalDate endDate = getDateInput("Date To (MM/DD/YYYY):");

        // Prepare data for the table
        String[] columnNames = {"Emp#", "Name", "Reg Hours", "OT Hours", "Hourly Rate", "Gross Pay", "Net Pay"};
        Object[][] data = new Object[employees.size()][7];

        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);

            // Calculate total hours worked and overtime hours
            double regularHours = 0;
            double overtimeHours = 0;
            for (AttendanceRecord record : attendanceRecords) {
                if (record.getEmployeeId() == employee.getEmployeeId()) {
                    LocalDate recordDate = record.getDate();
                    if (recordDate != null && !recordDate.isBefore(startDate) && !recordDate.isAfter(endDate)) {
                        double totalHours = record.getTotalHours();
                        if (totalHours > 8) {
                            regularHours += 8;
                            overtimeHours += totalHours - 8;
                        } else {
                            regularHours += totalHours;
                        }
                    }
                }
            }

            // Calculate gross pay and net pay
            double hourlyRate = employee.getHourlyRate();
            double grossPay = payrollCalculator.calculateGrossPay(regularHours + overtimeHours, hourlyRate);
            double netPay = payrollCalculator.calculateNetPay(grossPay);

            // Populate the table data
            data[i][0] = employee.getEmployeeId();
            data[i][1] = employee.getFullName();
            data[i][2] = String.format("%.2f", regularHours);
            data[i][3] = String.format("%.2f", overtimeHours);
            data[i][4] = String.format("%.2f", hourlyRate);
            data[i][5] = String.format("%.2f", grossPay);
            data[i][6] = String.format("%.2f", netPay);
        }

        // Create the table with the data and column names
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Display the table in a dialog
        JOptionPane.showMessageDialog(this, scrollPane, "Payroll Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private void generateEmployeePayslip() {
        // Prompt the user for the employee ID
        String empNumberStr = JOptionPane.showInputDialog(this, "Enter Employee No:");
        if (empNumberStr == null || empNumberStr.isEmpty()) return;

        int empNumber;
        try {
            empNumber = Integer.parseInt(empNumberStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid employee number. Please enter a numeric value.");
            return;
        }

        // Find the employee
        Employee employee = employees.stream()
                .filter(emp -> emp.getEmployeeId() == empNumber)
                .findFirst()
                .orElse(null);

        if (employee == null) {
            JOptionPane.showMessageDialog(this, "Employee not found.");
            return;
        }

        // Prompt the user for the date range
        LocalDate startDate = getDateInput("Date From (MM/DD/YYYY):");
        LocalDate endDate = getDateInput("Date To (MM/DD/YYYY):");

        // Generate the payslip
        PaySlip paySlip = new PaySlip(employee, startDate, endDate);
        paySlip.generate(attendanceRecords, payrollCalculator);

        // Display the payslip in a dialog
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        StringBuilder payslipDetails = new StringBuilder();
        payslipDetails.append("═══════════════════════════════════════════\n");
        payslipDetails.append("           EMPLOYEE PAYSLIP\n");
        payslipDetails.append("═══════════════════════════════════════════\n");
        payslipDetails.append("Employee No: ").append(employee.getEmployeeId()).append("\n");
        payslipDetails.append("Name: ").append(employee.getFullName()).append("\n");
        payslipDetails.append("Position: ").append(employee.getPosition()).append("\n");
        payslipDetails.append("Period: ").append(startDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))
                .append(" to ").append(endDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))).append("\n");
        payslipDetails.append("───────────────────────────────────────────\n");
        payslipDetails.append("HOURS WORKED:\n");
        payslipDetails.append(String.format("Regular Hours: %.2f\n", paySlip.getRegularHours()));
        payslipDetails.append(String.format("Overtime Hours: %.2f\n", paySlip.getOvertimeHours()));
        payslipDetails.append(String.format("Total Hours: %.2f\n", paySlip.getRegularHours() + paySlip.getOvertimeHours()));
        payslipDetails.append("───────────────────────────────────────────\n");
        payslipDetails.append("PAY DETAILS:\n");
        payslipDetails.append(String.format("Hourly Rate: ₱%,.2f\n", employee.getHourlyRate()));
        payslipDetails.append(String.format("Gross Pay: ₱%,.2f\n", paySlip.getGrossPay()));
        payslipDetails.append("───────────────────────────────────────────\n");
        payslipDetails.append("DEDUCTIONS:\n");
        payslipDetails.append(String.format("SSS: ₱%,.2f\n", paySlip.getDeductions().get("sss")));
        payslipDetails.append(String.format("PhilHealth: ₱%,.2f\n", paySlip.getDeductions().get("philhealth")));
        payslipDetails.append(String.format("Pag-IBIG: ₱%,.2f\n", paySlip.getDeductions().get("pagibig")));
        payslipDetails.append(String.format("Withholding Tax: ₱%,.2f\n", paySlip.getDeductions().get("withholdingTax")));
        payslipDetails.append("───────────────────────────────────────────\n");
        payslipDetails.append("ALLOWANCES:\n");
        payslipDetails.append(String.format("Rice Subsidy: ₱%,.2f\n", paySlip.getAllowances().get("rice")));
        payslipDetails.append(String.format("Phone Allowance: ₱%,.2f\n", paySlip.getAllowances().get("phone")));
        payslipDetails.append(String.format("Clothing Allowance: ₱%,.2f\n", paySlip.getAllowances().get("clothing")));
        payslipDetails.append("───────────────────────────────────────────\n");
        payslipDetails.append(String.format("FINAL NET PAY: ₱%,.2f\n", paySlip.getNetPay()));
        payslipDetails.append("═══════════════════════════════════════════\n");

        textArea.setText(payslipDetails.toString());
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Payslip", JOptionPane.INFORMATION_MESSAGE);
    }

    private void generateEmployeePayslip(String title) {
        JOptionPane.showMessageDialog(this, title + " functionality not implemented yet.");
    }

    private void generateSummaryReport(String reportType) {
        JOptionPane.showMessageDialog(this, reportType + " Summary Report functionality not implemented yet.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Provide the file paths for employees and attendance records
            String employeesFilePath = "motorph_payroll_system/employeeDetails.csv";
            String attendanceFilePath = "motorph_payroll_system/attendanceRecord.csv";

            MenuManagerGUI gui = new MenuManagerGUI(employeesFilePath, attendanceFilePath);
            gui.setVisible(true);
        });
    }
}

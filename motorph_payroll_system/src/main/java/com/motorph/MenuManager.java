package com.motorph;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Manages all menus, navigation and program flow.
 * Handles user interactions and coordinates with other classes.
 */
public class MenuManager {
    private static final double OVERTIME_RATE = 1.25;
    private static final int REGULAR_HOURS_PER_DAY = 8;
    private static final int WORK_DAYS_PER_MONTH = 21;
    private static final String DATE_FORMAT_PATTERN = "MM/dd/yyyy";

    private List<Employee> employees;
    private List<AttendanceRecord> attendanceRecords;
    private Scanner scanner;
    private PayrollCalculator payrollCalculator;

    public MenuManager(List<Employee> employees, List<AttendanceRecord> attendanceRecords) {
        this.employees = employees;
        this.attendanceRecords = attendanceRecords;
        this.scanner = new Scanner(System.in);
        this.payrollCalculator = new PayrollCalculator();
    }

    public void startApplication() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== MotorPH Payroll System ===");
            System.out.println("1. Employee Management");
            System.out.println("2. Payroll Management");
            System.out.println("3. Reports");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        employeeManagementMenu();
                        break;
                    case 2:
                        payrollManagementMenu();
                        break;
                    case 3:
                        reportsMenu();
                        break;
                    case 4:
                        System.out.println("Exiting system...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1-4.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void employeeManagementMenu() {
        while (true) {
            System.out.println("\nEmployee Management:");
            System.out.println("1. Search Employee");
            System.out.println("2. List All Employees");
            System.out.println("3. View Attendance");
            System.out.println("4. Return to Main Menu");
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        searchEmployee();
                        break;
                    case 2:
                        listAllEmployees();
                        break;
                    case 3:
                        viewAttendance();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter 1-4.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void payrollManagementMenu() {
        while (true) {
            System.out.println("\nPayroll Management:");
            System.out.println("1. Generate Payroll (Calculate All Employees)");
            System.out.println("2. Custom Payroll");
            System.out.println("3. Return to Main Menu");
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        generatePayroll();
                        break;
                    case 2:
                        generateEmployeePayslip();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter 1-3.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void reportsMenu() {
        while (true) {
            System.out.println("\nReports:");
            System.out.println("1. Payslip");
            System.out.println("2. Weekly Summary");
            System.out.println("3. Monthly Summary");
            System.out.println("4. Return to Main Menu");
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        generateEmployeePayslip();
                        break;
                    case 2:
                        generateSummaryReport("Weekly");
                        break;
                    case 3:
                        generateSummaryReport("Monthly");
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter 1-4.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void generatePayroll() {
        System.out.println("\nGenerate Payroll:");
        LocalDate startDate = getDateInput("Date From (MM/DD/YYYY): ");
        LocalDate endDate = getDateInput("Date To (MM/DD/YYYY): ");
        System.out.println("═════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.printf("%-7s %-25s %-10s %-10s %-12s %-15s %-15s %-15s%n",
                "Emp#", "Name", "Reg Hours", "OT Hours", "Hourly Rate", "Gross Pay", "Allowances", "Net Pay");
        System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        for (Employee employee : employees) {
            PaySlip paySlip = new PaySlip(employee, startDate, endDate);
            paySlip.generate(attendanceRecords, payrollCalculator);
            System.out.printf("%-7d %-25s %10.2f %10.2f %12.2f %15s %15s %15s%n",
                    employee.getEmployeeId(),
                    employee.getFullName(),
                    paySlip.getRegularHours(),
                    paySlip.getOvertimeHours(),
                    employee.getHourlyRate(),
                    String.format("%,.2f", paySlip.getGrossPay()),
                    String.format("%,.2f", paySlip.getAllowances().get("rice") + 
                                  paySlip.getAllowances().get("phone") + 
                                  paySlip.getAllowances().get("clothing")),
                    String.format("%,.2f", paySlip.getNetPay()));
        }
        System.out.println("═════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("\nPress Enter to return to menu...");
        scanner.nextLine();
    }

    private void generateEmployeePayslip() {
        System.out.print("\nEnter Employee No: ");
        int empNumber;
        try {
            empNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid employee number. Please enter a numeric value.");
            return;
        }
        Employee employee = findEmployeeById(empNumber);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }
        LocalDate startDate = getDateInput("Date From (MM/DD/YYYY): ");
        LocalDate endDate = getDateInput("Date To (MM/DD/YYYY): ");
        PaySlip paySlip = new PaySlip(employee, startDate, endDate);
        paySlip.generate(attendanceRecords, payrollCalculator);
        paySlip.display();
        System.out.println("\nPress Enter to return to menu...");
        scanner.nextLine();
    }

    private void generateSummaryReport(String reportType) {
        System.out.println("\n" + reportType + " Summary Report:");
        LocalDate startDate = getDateInput("Date From (MM/DD/YYYY): ");
        LocalDate endDate = getDateInput("Date To (MM/DD/YYYY): ");
        System.out.printf("%-10s %-25s %-15s %-15s %-15s%n",
                "Emp#", "Name", "Total Work Hours", "Net Pay", "Gross Pay");
        System.out.println("-".repeat(85));
        for (Employee employee : employees) {
            PaySlip paySlip = new PaySlip(employee, startDate, endDate);
            paySlip.generate(attendanceRecords, payrollCalculator);
            System.out.printf("%-10d %-25s %-15.2f %-15.2f %-15.2f%n",
                    employee.getEmployeeId(), employee.getFullName(), 
                    paySlip.getRegularHours() + paySlip.getOvertimeHours(), 
                    paySlip.getNetPay(), paySlip.getGrossPay());
        }
    }

    private void viewAttendance() {
        System.out.print("\nEnter Employee No: ");
        int empNumber;
        try {
            empNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid employee number. Please enter a numeric value.");
            return;
        }
        LocalDate startDate = getDateInput("Date From (MM/DD/YYYY): ");
        LocalDate endDate = getDateInput("Date To (MM/DD/YYYY): ");
        System.out.printf("%-10s | %-6s | %-6s | %-9s | %-10s%n",
                "Date", "In", "Out", "Duration", "Remarks");
        boolean found = false;
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getEmployeeId() == empNumber) {
                LocalDate recordDate = record.getDate();
                if (recordDate != null && !recordDate.isBefore(startDate) && !recordDate.isAfter(endDate)) {
                    found = true;
                    double duration = record.getTotalHours();
                    String remarks = record.getTimeIn().isBefore(record.getTimeIn().withHour(8).withMinute(10)) ? 
                                    "On Time" : "Late";
                    System.out.printf("%-10s | %-6s | %-6s | %-9.2f | %-10s%n",
                            record.getDate(),
                            record.getTimeIn(),
                            record.getTimeOut(),
                            duration,
                            remarks);
                }
            }
        }
        if (!found) {
            System.out.println("No attendance records found for the specified period.");
        }
    }

    private LocalDate getDateInput(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                String input = scanner.nextLine();
                return LocalDate.parse(input, java.time.format.DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
            } catch (Exception e) {
                System.err.println("Invalid date format. Please use MM/DD/YYYY format.");
                System.out.print(prompt);
            }
        }
    }

    private void searchEmployee() {
        System.out.print("\nEnter search term (name or employee number): ");
        String searchTerm = scanner.nextLine().toLowerCase();
        System.out.printf("%-10s %-20s %-20s %-15s %-15s%n",
                "Emp#", "Name", "Position", "Status", "Hourly Rate");
        boolean found = false;
        for (Employee employee : employees) {
            String empId = String.valueOf(employee.getEmployeeId()).toLowerCase();
            String lastName = employee.getLastName().toLowerCase();
            String firstName = employee.getFirstName().toLowerCase();
            if (empId.contains(searchTerm) || lastName.contains(searchTerm) || firstName.contains(searchTerm)) {
                found = true;
                System.out.printf("%-10d %-20s %-20s %-15s %-15.2f%n",
                        employee.getEmployeeId(), employee.getFullName(), 
                        employee.getPosition(), employee.getStatus(), employee.getHourlyRate());
            }
        }
        if (!found) {
            System.out.println("No employees found matching your search criteria.");
        }
    }

    private void listAllEmployees() {
        System.out.printf("%-10s %-25s %-20s %-15s %-15s%n",
                "Emp#", "Name", "Position", "Status", "Hourly Rate");
        System.out.println("-".repeat(85));
        for (Employee employee : employees) {
            String name = employee.getFullName();
            String position = employee.getPosition().length() > 18 ? 
                employee.getPosition().substring(0, 15) + "..." : 
                employee.getPosition();
            System.out.printf("%-10d %-25s %-20s %-15s %-15.2f%n",
                    employee.getEmployeeId(), name, position, employee.getStatus(), employee.getHourlyRate());
        }
    }

    private Employee findEmployeeById(int empNumber) {
        for (Employee employee : employees) {
            if (employee.getEmployeeId() == empNumber) {
                return employee;
            }
        }
        return null;
    }
}

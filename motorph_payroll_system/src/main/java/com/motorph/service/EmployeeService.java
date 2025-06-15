package com.motorph.service;

import java.io.FileWriter;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.opencsv.CSVWriter;

/**
 * Service for employee-related operations.
 * Enhanced for MPHCR-02 Feature 2 implementation with CSV writing capabilities.
 */
public class EmployeeService {
    private final List<Employee> employees;
    private final List<AttendanceRecord> attendanceRecords;
    private static final String CSV_FILE_PATH = "motorph_payroll_system/employeeDetails.csv";

    public EmployeeService(List<Employee> employees, List<AttendanceRecord> attendanceRecords) {
        this.employees = employees;
        this.attendanceRecords = attendanceRecords;
    }

    /**
     * Find an employee by ID
     * 
     * @param employeeId The employee ID to find
     * @return Employee or null if not found
     */
    public Employee findEmployeeById(int employeeId) {
        return employees.stream()
                .filter(emp -> emp.getEmployeeId() == employeeId)
                .findFirst()
                .orElse(null);
    }

    /**
     * Search for employees by name or ID
     * 
     * @param searchTerm The search term (name or ID)
     * @return List of matching employees
     */
    public List<Employee> searchEmployees(String searchTerm) {
        String term = searchTerm.toLowerCase();

        return employees.stream()
                .filter(employee -> {
                    String empId = String.valueOf(employee.getEmployeeId()).toLowerCase();
                    String lastName = employee.getLastName().toLowerCase();
                    String firstName = employee.getFirstName().toLowerCase();
                    return empId.contains(term) || lastName.contains(term) || firstName.contains(term);
                })
                .collect(Collectors.toList());
    }

    /**
     * Get all employees
     * 
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    /**
     * Get attendance records for an employee within a date range
     * 
     * @param employeeId The employee ID
     * @param startDate  Start date for the range
     * @param endDate    End date for the range
     * @return List of matching attendance records
     */
    public List<AttendanceRecord> getAttendanceRecords(int employeeId, LocalDate startDate, LocalDate endDate) {
        return attendanceRecords.stream()
                .filter(record -> record.getEmployeeId() == employeeId)
                .filter(record -> {
                    LocalDate recordDate = record.getDate();
                    return recordDate != null &&
                            !recordDate.isBefore(startDate) &&
                            !recordDate.isAfter(endDate);
                })
                .collect(Collectors.toList());
    }

    /**
     * Add a new employee and save to CSV file (MPHCR-02 Feature 2)
     * 
     * @param employee The employee to add
     * @return true if successful, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        try {
            // Validate employee data
            if (employee == null || employee.getEmployeeId() <= 0) {
                throw new IllegalArgumentException("Invalid employee data");
            }

            // Check for duplicate employee ID
            if (findEmployeeById(employee.getEmployeeId()) != null) {
                throw new IllegalArgumentException("Employee ID already exists: " + employee.getEmployeeId());
            }

            // Add to memory
            employees.add(employee);

            // Save to CSV file
            appendEmployeeToCSV(employee);

            return true;
        } catch (Exception e) {
            // Rollback if CSV writing fails
            employees.remove(employee);
            throw new RuntimeException("Failed to save employee: " + e.getMessage(), e);
        }
    }

    /**
     * Append a single employee to the CSV file
     * 
     * @param employee The employee to append
     * @throws Exception if writing fails
     */
    private void appendEmployeeToCSV(Employee employee) throws Exception {
        try (FileWriter fileWriter = new FileWriter(CSV_FILE_PATH, true);
                CSVWriter writer = new CSVWriter(fileWriter)) {

            String[] data = {
                    String.valueOf(employee.getEmployeeId()),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getBirthday() != null ? employee.getBirthday() : "",
                    employee.getAddress() != null ? employee.getAddress() : "",
                    employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "",
                    employee.getSssNumber() != null ? employee.getSssNumber() : "",
                    employee.getPhilhealthNumber() != null ? employee.getPhilhealthNumber() : "",
                    employee.getTinNumber() != null ? employee.getTinNumber() : "",
                    employee.getPagibigNumber() != null ? employee.getPagibigNumber() : "",
                    employee.getStatus() != null ? employee.getStatus() : "Regular",
                    employee.getPosition() != null ? employee.getPosition() : "",
                    employee.getImmediateSupervisor() != null ? employee.getImmediateSupervisor() : "",
                    formatMoneyForCSV(employee.getBasicSalary()),
                    formatMoneyForCSV(employee.getRiceSubsidy()),
                    formatMoneyForCSV(employee.getPhoneAllowance()),
                    formatMoneyForCSV(employee.getClothingAllowance()),
                    formatMoneyForCSV(employee.getGrossSemimonthlyRate()),
                    String.format("%.2f", employee.getHourlyRate())
            };

            writer.writeNext(data);
        }
    }

    /**
     * Format money values to match the CSV format with quotes and commas
     * 
     * @param amount The amount to format
     * @return Formatted string for CSV
     */
    private String formatMoneyForCSV(double amount) {
        if (amount >= 1000) {
            NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
            return "\"" + formatter.format(amount) + "\"";
        }
        return "\"" + (int) amount + "\"";
    }

    /**
     * Generate next available employee ID
     * 
     * @return Next available employee ID
     */
    public int generateNextEmployeeId() {
        return employees.stream()
                .mapToInt(Employee::getEmployeeId)
                .max()
                .orElse(10000) + 1;
    }

    /**
     * Get employees suitable for table display (MPHCR-02 requirement)
     * Returns data in the format needed for the employee list table
     * 
     * @return List of Object arrays for table model
     */
    public List<Object[]> getEmployeesForTable() {
        return employees.stream()
                .map(emp -> new Object[] {
                        emp.getEmployeeId(),
                        emp.getLastName(),
                        emp.getFirstName(),
                        emp.getSssNumber() != null ? emp.getSssNumber() : "N/A",
                        emp.getPhilhealthNumber() != null ? emp.getPhilhealthNumber() : "N/A",
                        emp.getTinNumber() != null ? emp.getTinNumber() : "N/A",
                        emp.getPagibigNumber() != null ? emp.getPagibigNumber() : "N/A"
                })
                .collect(Collectors.toList());
    }
}

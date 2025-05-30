package com.motorph.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;

/**
 * Service for employee-related operations.
 */
public class EmployeeService {
    private final List<Employee> employees;
    private final List<AttendanceRecord> attendanceRecords;

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
     * Add a new employee to the system and save to CSV
     * 
     * @param employee The employee to add
     * @return true if employee was added successfully, false if employee ID already
     *         exists
     */
    public boolean addEmployee(Employee employee) {
        // Check if employee ID already exists
        if (findEmployeeById(employee.getEmployeeId()) != null) {
            return false; // Employee ID already exists
        }

        // Add employee to in-memory list
        employees.add(employee);

        // Save to CSV file
        try {
            appendEmployeeToCSV(employee);
            return true;
        } catch (Exception e) {
            // If CSV write fails, remove from memory and return false
            employees.remove(employee);
            throw new RuntimeException("Failed to save employee to CSV: " + e.getMessage(), e);
        }
    }

    /**
     * Append a single employee to the CSV file
     */
    private void appendEmployeeToCSV(Employee employee) throws Exception {
        String csvFile = "employeeDetails.csv";
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(csvFile, true);
                com.opencsv.CSVWriter writer = new com.opencsv.CSVWriter(fileWriter)) {

            // Create CSV record for the employee
            String[] data = {
                    String.valueOf(employee.getEmployeeId()),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getBirthday() != null ? employee.getBirthday().toString() : "",
                    employee.getAddress() != null ? employee.getAddress() : "",
                    employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "",
                    employee.getSssNumber() != null ? employee.getSssNumber() : "",
                    employee.getPhilhealthNumber() != null ? employee.getPhilhealthNumber() : "",
                    employee.getTinNumber() != null ? employee.getTinNumber() : "",
                    employee.getPagibigNumber() != null ? employee.getPagibigNumber() : "",
                    employee.getStatus(),
                    employee.getPosition(),
                    employee.getSupervisor() != null ? employee.getSupervisor() : "",
                    String.valueOf(employee.getBasicSalary()),
                    String.valueOf(employee.getRiceSubsidy()),
                    String.valueOf(employee.getPhoneAllowance()),
                    String.valueOf(employee.getClothingAllowance()),
                    String.valueOf(employee.getGrossSemiMonthlyRate()),
                    String.valueOf(employee.getHourlyRate())
            };
            writer.writeNext(data);
        }
    }

    /**
     * Update an existing employee in the system and save to CSV
     * 
     * @param employee The employee with updated information
     * @return true if employee was updated successfully, false if employee ID
     *         doesn't exist
     */
    public boolean updateEmployee(Employee employee) {
        // Find the existing employee
        Employee existingEmployee = findEmployeeById(employee.getEmployeeId());
        if (existingEmployee == null) {
            return false; // Employee doesn't exist
        }

        // Update the employee in the list
        int index = employees.indexOf(existingEmployee);
        employees.set(index, employee);

        // Save all employees to CSV file
        try {
            saveAllEmployeesToCSV();
            return true;
        } catch (Exception e) {
            // If CSV write fails, revert the change
            employees.set(index, existingEmployee);
            throw new RuntimeException("Failed to update employee in CSV: " + e.getMessage(), e);
        }
    }

    /**
     * Delete an employee from the system and update CSV
     * 
     * @param employeeId The ID of the employee to delete
     * @return true if employee was deleted successfully, false if employee ID
     *         doesn't exist
     */
    public boolean deleteEmployee(int employeeId) {
        // Find the existing employee
        Employee existingEmployee = findEmployeeById(employeeId);
        if (existingEmployee == null) {
            return false; // Employee doesn't exist
        }

        // Remove the employee from the list
        employees.remove(existingEmployee);

        // Save all employees to CSV file
        try {
            saveAllEmployeesToCSV();
            return true;
        } catch (Exception e) {
            // If CSV write fails, add the employee back
            employees.add(existingEmployee);
            throw new RuntimeException("Failed to delete employee from CSV: " + e.getMessage(), e);
        }
    }

    /**
     * Save all employees to the CSV file (used for update and delete operations)
     */
    private void saveAllEmployeesToCSV() throws Exception {
        String csvFile = "employeeDetails.csv";
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(csvFile);
                com.opencsv.CSVWriter writer = new com.opencsv.CSVWriter(fileWriter)) {

            // Write header
            String[] header = {
                    "Employee Number", "Last Name", "First Name", "Birthday", "Address",
                    "Phone Number", "SSS Number", "Philhealth Number", "TIN Number",
                    "Pagibig Number", "Status", "Position", "Immediate Supervisor",
                    "Basic Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance",
                    "Gross Semi-monthly Rate", "Hourly Rate"
            };
            writer.writeNext(header);

            // Write all employee data
            for (Employee employee : employees) {
                String[] data = {
                        String.valueOf(employee.getEmployeeId()),
                        employee.getLastName(),
                        employee.getFirstName(),
                        employee.getBirthday() != null ? employee.getBirthday().toString() : "",
                        employee.getAddress() != null ? employee.getAddress() : "",
                        employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "",
                        employee.getSssNumber() != null ? employee.getSssNumber() : "",
                        employee.getPhilhealthNumber() != null ? employee.getPhilhealthNumber() : "",
                        employee.getTinNumber() != null ? employee.getTinNumber() : "",
                        employee.getPagibigNumber() != null ? employee.getPagibigNumber() : "",
                        employee.getStatus(),
                        employee.getPosition(),
                        employee.getSupervisor() != null ? employee.getSupervisor() : "",
                        String.valueOf(employee.getBasicSalary()),
                        String.valueOf(employee.getRiceSubsidy()),
                        String.valueOf(employee.getPhoneAllowance()),
                        String.valueOf(employee.getClothingAllowance()),
                        String.valueOf(employee.getGrossSemiMonthlyRate()),
                        String.valueOf(employee.getHourlyRate())
                };
                writer.writeNext(data);
            }
        }
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
}

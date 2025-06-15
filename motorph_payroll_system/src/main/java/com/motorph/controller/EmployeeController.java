package com.motorph.controller;

import java.time.LocalDate;
import java.util.List;

import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.motorph.service.EmployeeService;

/**
 * Controller for employee-related operations.
 * Implements proper error handling as required by MPHCR01.
 */
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Find an employee by ID
     * 
     * @param employeeId The employee ID to find
     * @return Employee or null if not found
     * @throws IllegalArgumentException if employee ID is invalid
     */
    public Employee findEmployeeById(int employeeId) throws IllegalArgumentException {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        Employee employee = employeeService.findEmployeeById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " not found");
        }

        return employee;
    }

    /**
     * Search for employees by name or ID
     * 
     * @param searchTerm The search term (name or ID)
     * @return List of matching employees
     */
    public List<Employee> searchEmployees(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }

        return employeeService.searchEmployees(searchTerm);
    }

    /**
     * Get all employees
     * 
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     * Add a new employee to the system and save to CSV
     * 
     * @param employee The employee to add
     * @return true if employee was added successfully, false if employee ID already
     *         exists
     * @throws IllegalArgumentException if employee data is invalid
     */
    public boolean addEmployee(Employee employee) throws IllegalArgumentException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        if (employee.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }

        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }

        if (employee.getPosition() == null || employee.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("Position is required");
        }

        if (employee.getBasicSalary() <= 0) {
            throw new IllegalArgumentException("Basic salary must be greater than zero");
        }
        return employeeService.addEmployee(employee);
    }

    /**
     * Update an existing employee in the system and save to CSV
     * 
     * @param employee The employee with updated information
     * @return true if employee was updated successfully, false if employee ID
     *         doesn't exist
     * @throws IllegalArgumentException if employee data is invalid
     */
    public boolean updateEmployee(Employee employee) throws IllegalArgumentException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        if (employee.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }

        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }

        if (employee.getPosition() == null || employee.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("Position is required");
        }

        if (employee.getBasicSalary() <= 0) {
            throw new IllegalArgumentException("Basic salary must be greater than zero");
        }

        return employeeService.updateEmployee(employee);
    }

    /**
     * Delete an employee from the system and update CSV
     * 
     * @param employeeId The ID of the employee to delete
     * @return true if employee was deleted successfully, false if employee ID
     *         doesn't exist
     * @throws IllegalArgumentException if employee ID is invalid
     */
    public boolean deleteEmployee(int employeeId) throws IllegalArgumentException {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        return employeeService.deleteEmployee(employeeId);
    }

    /**
     * Get attendance records for an employee within a date range
     * 
     * @param employeeId The employee ID
     * @param startDate  Start date for the range
     * @param endDate    End date for the range
     * @return List of matching attendance records
     * @throws IllegalArgumentException if parameters are invalid
     */
    public List<AttendanceRecord> getAttendanceRecords(int employeeId, LocalDate startDate, LocalDate endDate)
            throws IllegalArgumentException {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        return employeeService.getAttendanceRecords(employeeId, startDate, endDate);
    }
}

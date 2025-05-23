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
     * Get attendance records for an employee within a date range
     * 
     * @param employeeId The employee ID
     * @param startDate Start date for the range
     * @param endDate End date for the range
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

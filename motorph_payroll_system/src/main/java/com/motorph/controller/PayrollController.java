package com.motorph.controller;

import java.time.LocalDate;
import java.util.List;

import com.motorph.model.PaySlip;
import com.motorph.service.PayrollService;

/**
 * Controller for payroll-related operations.
 * Implements proper error handling as required by MPHCR01.
 */
public class PayrollController {
    private final PayrollService payrollService;
    
    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }
    
    /**
     * Generates a payslip for an employee with proper error handling
     * 
     * @param employeeId The employee ID
     * @param startDate Start date for pay period
     * @param endDate End date for pay period
     * @return PaySlip object or null if an error occurred
     * @throws IllegalArgumentException if parameters are invalid
     */
    public PaySlip generatePayslip(int employeeId, LocalDate startDate, LocalDate endDate) throws IllegalArgumentException {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
        
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
        
        try {
            return payrollService.generatePayslip(employeeId, startDate, endDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error generating payslip: " + e.getMessage());
        }
    }
    
    /**
     * Generates payroll for all employees for a given date range
     * 
     * @param startDate Start date for pay period
     * @param endDate End date for pay period
     * @return List of PaySlip objects
     * @throws IllegalArgumentException if parameters are invalid
     */
    public List<PaySlip> generatePayroll(LocalDate startDate, LocalDate endDate) throws IllegalArgumentException {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
        
        try {
            return payrollService.generatePayroll(startDate, endDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error generating payroll: " + e.getMessage());
        }
    }
}

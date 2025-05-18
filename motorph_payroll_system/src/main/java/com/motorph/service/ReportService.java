package com.motorph.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.motorph.model.Employee;
import com.motorph.model.PaySlip;

/**
 * Service for report generation operations.
 */
public class ReportService {
    private final EmployeeService employeeService;
    private final PayrollService payrollService;
    
    public ReportService(EmployeeService employeeService, PayrollService payrollService) {
        this.employeeService = employeeService;
        this.payrollService = payrollService;
    }
    
    /**
     * Generate a payslip report for a specific employee
     * 
     * @param employeeId The employee ID
     * @param startDate Start date for the report period
     * @param endDate End date for the report period
     * @return PaySlip object with report data
     * @throws IllegalArgumentException if employee not found
     */
    public PaySlip generatePayslipReport(int employeeId, LocalDate startDate, LocalDate endDate) 
            throws IllegalArgumentException {
        return payrollService.generatePayslip(employeeId, startDate, endDate);
    }
    
    /**
     * Generate a summary report for all employees for a given period
     * 
     * @param reportType The type of report ("Weekly" or "Monthly")
     * @param startDate Start date for the report period
     * @param endDate End date for the report period
     * @return List of summary data for each employee
     */
    public List<Map<String, Object>> generateSummaryReport(String reportType, LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> summaryData = new ArrayList<>();
        List<Employee> employees = employeeService.getAllEmployees();
        
        for (Employee employee : employees) {
            try {
                // Generate payslip for this employee
                PaySlip paySlip = payrollService.generatePayslip(
                        employee.getEmployeeId(), startDate, endDate);
                
                // Create summary data entry
                Map<String, Object> employeeSummary = new HashMap<>();
                employeeSummary.put("employeeId", employee.getEmployeeId());
                employeeSummary.put("name", employee.getFullName());
                employeeSummary.put("totalHours", paySlip.getRegularHours() + paySlip.getOvertimeHours());
                employeeSummary.put("grossPay", paySlip.getGrossPay());
                employeeSummary.put("netPay", paySlip.getNetPay());
                
                summaryData.add(employeeSummary);
            } catch (Exception e) {
                // Log error and continue with next employee
                System.err.println("Error generating summary for employee " + 
                        employee.getEmployeeId() + ": " + e.getMessage());
            }
        }
        
        return summaryData;
    }
}

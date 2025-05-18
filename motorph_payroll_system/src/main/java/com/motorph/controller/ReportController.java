package com.motorph.controller;

import com.motorph.model.PaySlip;
import com.motorph.service.ReportService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controller for report generation operations.
 */
public class ReportController {
    private final ReportService reportService;
    
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    
    /**
     * Generate a payslip report for a specific employee
     * 
     * @param employeeId The employee ID
     * @param startDate Start date for the report period
     * @param endDate End date for the report period
     * @return PaySlip object with report data
     * @throws IllegalArgumentException if parameters are invalid
     */
    public PaySlip generatePayslipReport(int employeeId, LocalDate startDate, LocalDate endDate) 
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
        
        return reportService.generatePayslipReport(employeeId, startDate, endDate);
    }
    
    /**
     * Generate a summary report for all employees for a given period
     * 
     * @param reportType The type of report ("Weekly" or "Monthly")
     * @param startDate Start date for the report period
     * @param endDate End date for the report period
     * @return List of summary data for each employee
     * @throws IllegalArgumentException if parameters are invalid
     */
    public List<Map<String, Object>> generateSummaryReport(String reportType, LocalDate startDate, LocalDate endDate) 
            throws IllegalArgumentException {
        if (reportType == null || (!reportType.equals("Weekly") && !reportType.equals("Monthly"))) {
            throw new IllegalArgumentException("Report type must be 'Weekly' or 'Monthly'");
        }
        
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
        
        return reportService.generateSummaryReport(reportType, startDate, endDate);
    }
}

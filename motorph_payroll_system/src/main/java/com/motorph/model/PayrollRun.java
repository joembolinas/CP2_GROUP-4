package com.motorph.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Represents a complete payroll run for a specific pay period
 */
public class PayrollRun {
    private final String runId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private PayrollStatus status;
    private List<PaySlip> paySlips;
    private double totalGrossPay;
    private double totalNetPay;
    private double totalDeductions;
    private int employeeCount;
    private String processedBy;
    private LocalDateTime approvedAt;
    private String approvedBy;
    private String notes;
    private String dataFile;

    // Default constructor for creating new payroll runs
    public PayrollRun() {
        this.runId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.createdAt = LocalDateTime.now();
        this.status = PayrollStatus.DRAFT;
    }

    public PayrollRun(LocalDate startDate, LocalDate endDate, String processedBy) {
        this.runId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.startDate = startDate;
        this.endDate = endDate;
        this.processedBy = processedBy;
        this.createdAt = LocalDateTime.now();
        this.status = PayrollStatus.DRAFT;
    }

    // Getters and setters
    public String getRunId() {
        return runId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public PayrollStatus getStatus() {
        return status;
    }

    public void setStatus(PayrollStatus status) {
        this.status = status;
    }

    public List<PaySlip> getPaySlips() {
        return paySlips;
    }

    public void setPaySlips(List<PaySlip> paySlips) {
        this.paySlips = paySlips;
        if (paySlips != null) {
            calculateTotals();
        }
    }

    public double getTotalGrossPay() {
        return totalGrossPay;
    }

    public double getTotalNetPay() {
        return totalNetPay;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCreatedDate(LocalDate createdDate) {
        // For compatibility - convert LocalDate to LocalDateTime
        this.createdAt = createdDate.atStartOfDay();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public String getDataFile() {
        return dataFile;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedAt = approvedDate.atStartOfDay();
    }

    public void setPostedDate(LocalDate postedDate) {
        // Add postedAt field if needed for tracking
        this.notes = (this.notes != null ? this.notes + "; " : "") + "Posted on: " + postedDate;
    }

    /**
     * Calculate totals from payslips
     */
    private void calculateTotals() {
        if (paySlips == null || paySlips.isEmpty()) {
            totalGrossPay = 0;
            totalNetPay = 0;
            totalDeductions = 0;
            employeeCount = 0;
            return;
        }

        totalGrossPay = paySlips.stream().mapToDouble(PaySlip::getGrossPay).sum();
        totalNetPay = paySlips.stream().mapToDouble(PaySlip::getNetPay).sum();
        totalDeductions = totalGrossPay - totalNetPay;
        employeeCount = paySlips.size();
    }

    /**
     * Get formatted pay period string
     */
    public String getPayPeriodString() {
        return startDate.toString() + " to " + endDate.toString();
    }
}

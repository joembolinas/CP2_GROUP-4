package com.motorph.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.motorph.service.PayrollProcessor;

/**
 * Represents a payslip for an employee for a specific period.
 * Calculates and displays payslip information including allowances and deductions.
 */
public class PaySlip {
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private double regularHours;
    private double overtimeHours;
    private double grossPay;
    private double netPay;
    private Map<String, Double> deductions;
    private Map<String, Double> allowances;
    private static final String CURRENCY_FORMAT = "₱%,15.2f";

    public PaySlip(Employee employee, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deductions = new HashMap<>();
        this.allowances = new HashMap<>();
    }

    public void generate(List<AttendanceRecord> attendanceRecords, PayrollProcessor calculator) {
        // Calculate working hours
        Map<String, Double> payDetails = getGrossPayDetails(attendanceRecords, calculator);
        this.regularHours = payDetails.get("regularHours");
        this.overtimeHours = payDetails.get("overtimeHours");
        this.grossPay = payDetails.get("totalPay");
        // Calculate allowances
        Map<String, Double> allowanceDetails = getProRatedAllowanceDetails();
        this.allowances.put("rice", allowanceDetails.get("riceSubsidy"));
        this.allowances.put("phone", allowanceDetails.get("phoneAllowance"));
        this.allowances.put("clothing", allowanceDetails.get("clothingAllowance"));
        this.allowances.put("workingDays", allowanceDetails.get("workingDays") != null ? 
            allowanceDetails.get("workingDays") : 21.0);
        double totalAllowances = allowanceDetails.get("totalAllowances");
        // Calculate deductions
        this.deductions.put("sss", calculator.calculateSSSContribution(grossPay));
        this.deductions.put("philhealth", calculator.calculatePhilHealthContribution(grossPay));
        this.deductions.put("pagibig", calculator.calculatePagIbigContribution(grossPay));
        double taxableIncome = grossPay - (deductions.get("sss") + deductions.get("philhealth") + deductions.get("pagibig"));
        this.deductions.put("withholdingTax", calculator.calculateWithholdingTax(taxableIncome));
        double totalDeductions = deductions.get("sss") + deductions.get("philhealth") + 
            deductions.get("pagibig") + deductions.get("withholdingTax");
        // Calculate net pay
        this.netPay = grossPay - totalDeductions + totalAllowances;
    }

    private Map<String, Double> getGrossPayDetails(List<AttendanceRecord> attendanceRecords, PayrollProcessor calculator) {
        double regularHours = 0;
        double overtimeHours = 0;
        double regularPay = 0;
        double overtimePay = 0;
        
        // Filter attendance records for the employee within the date range
        List<AttendanceRecord> employeeRecords = new ArrayList<>();
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getEmployeeId() == employee.getEmployeeId() && 
                    !record.getDate().isBefore(startDate) && 
                    !record.getDate().isAfter(endDate)) {
                employeeRecords.add(record);
            }
        }
        
        // Calculate total hours worked
        for (AttendanceRecord record : employeeRecords) {
            double hoursWorked = record.getTotalHours();
            
            // Assuming over 8 hours is overtime
            if (hoursWorked <= 8) {
                regularHours += hoursWorked;
            } else {
                regularHours += 8;
                overtimeHours += (hoursWorked - 8);
            }
        }
        
        // Calculate regular pay and overtime pay
        regularPay = regularHours * employee.getHourlyRate();
        overtimePay = overtimeHours * employee.getHourlyRate() * 1.25; // 25% overtime premium
        
        Map<String, Double> result = new HashMap<>();
        result.put("regularHours", regularHours);
        result.put("overtimeHours", overtimeHours);
        result.put("regularPay", regularPay);
        result.put("overtimePay", overtimePay);
        result.put("totalPay", regularPay + overtimePay);
        
        return result;
    }

    private Map<String, Double> getProRatedAllowanceDetails() {
        // Calculate number of working days in the date range
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        double workingDays = totalDays * 5 / 7; // assuming 5-day work week
        
        // Pro-rate the allowances based on the number of working days
        double riceSubsidyPerDay = employee.getRiceSubsidy() / 21; // 21 working days per month
        double phoneAllowancePerDay = employee.getPhoneAllowance() / 21;
        double clothingAllowancePerDay = employee.getClothingAllowance() / 21;
        
        double proRatedRiceSubsidy = riceSubsidyPerDay * workingDays;
        double proRatedPhoneAllowance = phoneAllowancePerDay * workingDays;
        double proRatedClothingAllowance = clothingAllowancePerDay * workingDays;
        
        Map<String, Double> result = new HashMap<>();
        result.put("riceSubsidy", proRatedRiceSubsidy);
        result.put("phoneAllowance", proRatedPhoneAllowance);
        result.put("clothingAllowance", proRatedClothingAllowance);
        result.put("totalAllowances", proRatedRiceSubsidy + proRatedPhoneAllowance + proRatedClothingAllowance);
        result.put("workingDays", workingDays);
        
        return result;
    }
    
    // Getters
    public Employee getEmployee() { return employee; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public double getRegularHours() { return regularHours; }
    public double getOvertimeHours() { return overtimeHours; }
    public double getGrossPay() { return grossPay; }
    public double getNetPay() { return netPay; }
    public Map<String, Double> getDeductions() { return deductions; }
    public Map<String, Double> getAllowances() { return allowances; }
}

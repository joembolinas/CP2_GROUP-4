package com.motorph;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public PaySlip(Employee employee, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deductions = new HashMap<>();
        this.allowances = new HashMap<>();
    }

    public void generate(List<AttendanceRecord> attendanceRecords, PayrollCalculator calculator) {
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
        double totalAllowances = allowanceDetails.get("totalAllowances");
        // Calculate deductions
        this.deductions.put("sss", calculator.calculateSSSContribution(grossPay));
        this.deductions.put("philhealth", calculator.calculatePhilHealthContribution(grossPay));
        this.deductions.put("pagibig", calculator.calculatePagIbigContribution(grossPay));
        double taxableIncome = grossPay - (deductions.get("sss") + deductions.get("philhealth") + deductions.get("pagibig"));
        this.deductions.put("withholdingTax", calculator.calculateWithholdingTax(taxableIncome));
        double totalDeductions = deductions.get("sss") + deductions.get("philhealth") + deductions.get("pagibig") + deductions.get("withholdingTax");
        // Calculate net pay
        this.netPay = calculator.calculateNetPay(grossPay) + totalAllowances;
    }

    private Map<String, Double> getGrossPayDetails(List<AttendanceRecord> attendanceRecords, PayrollCalculator calculator) {
        Map<String, Double> payDetails = new HashMap<>();
        double totalRegularHours = 0.0;
        double totalOvertimeHours = 0.0;
        double totalRegularPay = 0.0;
        double totalOvertimePay = 0.0;
        // Group records by date to handle overtime correctly
        Map<LocalDate, List<AttendanceRecord>> recordsByDate = new HashMap<>();
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getEmployeeId() == employee.getEmployeeId() &&
                !record.getDate().isBefore(startDate) &&
                !record.getDate().isAfter(endDate)) {
                recordsByDate.computeIfAbsent(record.getDate(), k -> new ArrayList<>()).add(record);
            }
        }
        // Process each day separately for proper overtime calculation
        for (Map.Entry<LocalDate, List<AttendanceRecord>> entry : recordsByDate.entrySet()) {
            double dailyHours = 0.0;
            for (AttendanceRecord record : entry.getValue()) {
                dailyHours += record.getTotalHours();
            }
            double regularHours = Math.min(dailyHours, 8);
            double overtimeHours = Math.max(0, dailyHours - 8);
            double regularPay = regularHours * employee.getHourlyRate();
            double overtimePay = overtimeHours * employee.getHourlyRate() * 1.25;
            totalRegularHours += regularHours;
            totalOvertimeHours += overtimeHours;
            totalRegularPay += regularPay;
            totalOvertimePay += overtimePay;
        }
        payDetails.put("regularHours", totalRegularHours);
        payDetails.put("overtimeHours", totalOvertimeHours);
        payDetails.put("regularPay", totalRegularPay);
        payDetails.put("overtimePay", totalOvertimePay);
        payDetails.put("totalPay", totalRegularPay + totalOvertimePay);
        return payDetails;
    }

    private Map<String, Double> getProRatedAllowanceDetails() {
        Map<String, Double> allowances = new HashMap<>();
        // Extract full monthly allowances
        double riceSubsidy = employee.getRiceSubsidy();
        double phoneAllowance = employee.getPhoneAllowance();
        double clothingAllowance = employee.getClothingAllowance();
        // Calculate working days in the period (excluding weekends)
        long totalDays = 0;
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            if (currentDate.getDayOfWeek().getValue() <= 5) {
                totalDays++;
            }
            currentDate = currentDate.plusDays(1);
        }
        // Cap the working days at 21
        long effectiveDays = Math.min(totalDays, 21);
        double proRateFactor = (double) effectiveDays / 21.0;
        allowances.put("riceSubsidy", riceSubsidy * proRateFactor);
        allowances.put("phoneAllowance", phoneAllowance * proRateFactor);
        allowances.put("clothingAllowance", clothingAllowance * proRateFactor);
        allowances.put("totalAllowances", (riceSubsidy + phoneAllowance + clothingAllowance) * proRateFactor);
        allowances.put("workingDays", (double) totalDays);
        return allowances;
    }

    public void display() {
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("           EMPLOYEE PAYSLIP");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("Employee No: " + employee.getEmployeeId());
        System.out.println("Name: " + employee.getFullName());
        System.out.println("Position: " + employee.getPosition());
        System.out.println("Period: " + startDate + " to " + endDate);
        System.out.println("Working Days: " + allowances.get("workingDays") + " of 21 days");
        System.out.println("───────────────────────────────────────────");
        System.out.println("HOURS WORKED:");
        System.out.printf("Regular Hours: %.2f\n", regularHours);
        System.out.printf("Overtime Hours: %.2f\n", overtimeHours);
        System.out.printf("Total Hours: %.2f\n", regularHours + overtimeHours);
        System.out.println("───────────────────────────────────────────");
        System.out.println("PAY DETAILS:");
        System.out.printf("Hourly Rate: ₱%.2f\n", employee.getHourlyRate());
        System.out.printf("Regular Pay: ₱%.2f\n", grossPay - (overtimeHours * employee.getHourlyRate() * 1.25));
        System.out.printf("Overtime Pay: ₱%.2f\n", overtimeHours * employee.getHourlyRate() * 1.25);
        System.out.printf("Gross Pay: ₱%.2f\n", grossPay);
        System.out.println("───────────────────────────────────────────");
        System.out.println("DEDUCTIONS:");
        System.out.printf("SSS: ₱%.2f\n", deductions.get("sss"));
        System.out.printf("PhilHealth: ₱%.2f\n", deductions.get("philhealth"));
        System.out.printf("Pag-IBIG: ₱%.2f\n", deductions.get("pagibig"));
        System.out.printf("Withholding Tax: ₱%.2f\n", deductions.get("withholdingTax"));
        System.out.printf("Total Deductions: ₱%.2f\n", deductions.get("sss") + deductions.get("philhealth") + deductions.get("pagibig") + deductions.get("withholdingTax"));
        System.out.println("───────────────────────────────────────────");
        System.out.println("ALLOWANCES:");
        System.out.printf("Rice Subsidy: ₱%.2f\n", allowances.get("rice"));
        System.out.printf("Phone Allowance: ₱%.2f\n", allowances.get("phone"));
        System.out.printf("Clothing Allowance: ₱%.2f\n", allowances.get("clothing"));
        System.out.printf("Total Allowances: ₱%.2f\n", allowances.get("rice") + allowances.get("phone") + allowances.get("clothing"));
        System.out.println("───────────────────────────────────────────");
        System.out.printf("FINAL NET PAY: ₱%.2f\n", netPay);
        System.out.println("═══════════════════════════════════════════");
    }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public double getRegularHours() { return regularHours; }
    public void setRegularHours(double regularHours) { this.regularHours = regularHours; }
    public double getOvertimeHours() { return overtimeHours; }
    public void setOvertimeHours(double overtimeHours) { this.overtimeHours = overtimeHours; }
    public double getGrossPay() { return grossPay; }
    public void setGrossPay(double grossPay) { this.grossPay = grossPay; }
    public double getNetPay() { return netPay; }
    public void setNetPay(double netPay) { this.netPay = netPay; }
    public Map<String, Double> getDeductions() { return deductions; }
    public void setDeductions(Map<String, Double> deductions) { this.deductions = deductions; }
    public Map<String, Double> getAllowances() { return allowances; }
    public void setAllowances(Map<String, Double> allowances) { this.allowances = allowances; }
}

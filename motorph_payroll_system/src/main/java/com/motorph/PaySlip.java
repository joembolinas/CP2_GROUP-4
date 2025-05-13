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
    private static final String CURRENCY_FORMAT = "₱%,15.2f";

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
        this.allowances.put("workingDays", allowanceDetails.get("workingDays") != null ? 
            allowanceDetails.get("workingDays") : 21.0);
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
        // This ensures overtime is calculated per day, not cumulatively
        Map<LocalDate, List<AttendanceRecord>> recordsByDate = new HashMap<>();
        
        // Group each attendance record by its date for the specified employee
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getEmployeeId() == employee.getEmployeeId() &&
                !record.getDate().isBefore(startDate) &&
                !record.getDate().isAfter(endDate) &&
                // Only consider weekdays (Monday-Friday)
                record.getDate().getDayOfWeek().getValue() <= 5) {
                recordsByDate.computeIfAbsent(record.getDate(), k -> new ArrayList<>()).add(record);
            }
        }
        
        // Process each day separately for proper overtime calculation
        // Overtime is calculated only for hours exceeding REGULAR_HOURS_PER_DAY (8) on a single day
        for (Map.Entry<LocalDate, List<AttendanceRecord>> entry : recordsByDate.entrySet()) {
            double dailyHours = 0.0;
            
            // Sum up hours for this day
            for (AttendanceRecord record : entry.getValue()) {
                dailyHours += record.getTotalHours();
            }
            
            // Calculate pay with overtime
            double regularHours = Math.min(dailyHours, 8); // Cap regular hours at 8 per day
            double overtimeHours = Math.max(0, dailyHours - 8); // Hours beyond 8 are overtime
            
            double regularPay = regularHours * employee.getHourlyRate();
            double overtimePay = overtimeHours * employee.getHourlyRate() * 1.25; // Overtime at 1.25x
            
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
        Map<String, Double> allowanceMap = new HashMap<>();
        // Extract full monthly allowances
        double riceSubsidy = employee.getRiceSubsidy();
        double phoneAllowance = employee.getPhoneAllowance();
        double clothingAllowance = employee.getClothingAllowance();
        
        // Calculate working days in the period (excluding weekends)
        long totalDays = 0;
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            // Only count weekdays (Monday-Friday)
            if (currentDate.getDayOfWeek().getValue() <= 5) {
                totalDays++;
            }
            currentDate = currentDate.plusDays(1);
        }
        
        // Cap the working days at standard working days (21)
        long effectiveDays = Math.min(totalDays, 21);
        double proRateFactor = (double) effectiveDays / 21.0;
        
        // Calculate pro-rated allowances
        allowanceMap.put("riceSubsidy", riceSubsidy * proRateFactor);
        allowanceMap.put("phoneAllowance", phoneAllowance * proRateFactor);
        allowanceMap.put("clothingAllowance", clothingAllowance * proRateFactor);
        allowanceMap.put("totalAllowances", (riceSubsidy + phoneAllowance + clothingAllowance) * proRateFactor);
        allowanceMap.put("workingDays", (double) totalDays);
        
        return allowanceMap;
    }

    public void display() {
        display("EMPLOYEE PAYSLIP");
    }

    public void display(String title) {
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("           " + title);
        System.out.println("═══════════════════════════════════════════");
        System.out.println("Employee No: " + employee.getEmployeeId());
        System.out.println("Name: " + employee.getFullName());
        System.out.println("Position: " + employee.getPosition());
        System.out.println("Period: " + startDate.format(java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy")) + 
                         " to " + endDate.format(java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                         
        // Safe check for workingDays
        int workingDays = 21;
        if (allowances.containsKey("workingDays")) {
            workingDays = (int)Math.round(allowances.get("workingDays"));
        }
        
        System.out.println("Working Days: " + workingDays + " of 21 days");
        System.out.println("───────────────────────────────────────────");
        System.out.println("HOURS WORKED:");
        System.out.printf("Regular Hours: %.2f\n", regularHours);
        System.out.printf("Overtime Hours: %.2f\n", overtimeHours);
        System.out.printf("Total Hours: %.2f\n", regularHours + overtimeHours);
        System.out.println("───────────────────────────────────────────");
        
        // Calculate regular pay and overtime pay
        double regularPay = regularHours * employee.getHourlyRate();
        double overtimePay = overtimeHours * employee.getHourlyRate() * 1.25;
        
        System.out.println("PAY DETAILS:");
        System.out.printf("Hourly Rate: " + CURRENCY_FORMAT + "\n", employee.getHourlyRate());
        System.out.printf("Regular Pay: " + CURRENCY_FORMAT + "\n", regularPay);
        System.out.printf("Overtime Pay: " + CURRENCY_FORMAT + "\n", overtimePay);
        System.out.printf("Gross Pay: " + CURRENCY_FORMAT + "\n", grossPay);
        System.out.println("───────────────────────────────────────────");
        System.out.println("DEDUCTIONS:");
        System.out.printf("SSS: " + CURRENCY_FORMAT + "\n", deductions.get("sss"));
        System.out.printf("PhilHealth: " + CURRENCY_FORMAT + "\n", deductions.get("philhealth"));
        System.out.printf("Pag-IBIG: " + CURRENCY_FORMAT + "\n", deductions.get("pagibig"));
        System.out.printf("Withholding Tax: " + CURRENCY_FORMAT + "\n", deductions.get("withholdingTax"));
        double totalDeductions = deductions.get("sss") + deductions.get("philhealth") + 
                               deductions.get("pagibig") + deductions.get("withholdingTax");
        System.out.printf("Total Deductions: " + CURRENCY_FORMAT + "\n", totalDeductions);
        System.out.println("───────────────────────────────────────────");
        System.out.println("ALLOWANCES (Pro-rated for " + (int)Math.round(allowances.get("workingDays")) + " days):");
        System.out.printf("Rice Subsidy: " + CURRENCY_FORMAT + "\n", allowances.get("rice"));
        System.out.printf("Phone Allowance: " + CURRENCY_FORMAT + "\n", allowances.get("phone"));
        System.out.printf("Clothing Allowance: " + CURRENCY_FORMAT + "\n", allowances.get("clothing"));
        double totalAllowances = allowances.get("rice") + allowances.get("phone") + allowances.get("clothing");
        System.out.printf("Total Allowances: " + CURRENCY_FORMAT + "\n", totalAllowances);
        System.out.println("───────────────────────────────────────────");
        System.out.printf("FINAL NET PAY: " + CURRENCY_FORMAT + "\n", netPay);
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

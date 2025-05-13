package com.motorph;

public class PayrollConstants {

    // General Payroll Constants
    public static final int STANDARD_WORK_DAYS_PER_MONTH = 21;
    public static final double REGULAR_HOURS_PER_DAY = 8.0;
    public static final double OVERTIME_RATE_MULTIPLIER = 1.25;

    // Government Contribution Rates & Caps (Examples - these should be up-to-date)
    // PhilHealth
    public static final double PHILHEALTH_EMPLOYEE_CONTRIBUTION_RATE = 0.015; // Employee's share (e.g., 1.5% of 3% total)
    public static final double PHILHEALTH_TOTAL_CONTRIBUTION_RATE = 0.03; // Total PhilHealth contribution rate

    // Pag-IBIG
    public static final double PAGIBIG_EMPLOYEE_CONTRIBUTION_RATE = 0.02; // Can vary, 2% is common for employee
    public static final double PAGIBIG_MAX_CONTRIBUTION_SALARY_CAP = 5000.00; // Salary cap for 2% calculation
    public static final double PAGIBIG_FIXED_RATE_ABOVE_CAP = 100.00; // Fixed contribution if salary is above cap

    // SSS - SSS contributions are typically based on salary brackets.
    // Representing the full SSS table here might be too complex for simple constants.
    // For now, let's define caps if they were percentage based, though SSS is usually a table.
    // These are illustrative and might not reflect actual SSS computation which uses a table.
    public static final double SSS_EMPLOYEE_CONTRIBUTION_CAP_PERCENT = 0.045; // Example if it were a simple percentage cap

    // Tax calculation constants (illustrative, real tax tables are more complex)
    public static final double WITHHOLDING_TAX_EXEMPTION_THRESHOLD = 20833.00; // Example monthly non-taxable income

    // Date/Time Formatting
    public static final String DATE_FORMAT_PATTERN = "MM/dd/yyyy";
    public static final String TIME_FORMAT_PATTERN = "H:mm";
    public static final String STANDARD_LOGIN_TIME_STRING = "08:00";

    // Private constructor to prevent instantiation
    private PayrollConstants() {
    }
}

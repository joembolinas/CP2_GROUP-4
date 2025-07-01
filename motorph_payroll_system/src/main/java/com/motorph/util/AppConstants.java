package com.motorph.util;

import java.awt.Color;
import java.awt.Font;

/**
 * All application constants consolidated into one file.
 * Includes UI styling, payroll calculations, and system configurations.
 */
public final class AppConstants {

    // ========== UI CONSTANTS ==========

    // Colors - Modern Slate/Tailwind-inspired Color Scheme
    public static final Color BACKGROUND_COLOR = new Color(241, 245, 249); // #F1F5F9 - slate-100
    public static final Color PANEL_BACKGROUND = Color.WHITE; // #FFFFFF - White panels
    public static final Color CARD_BACKGROUND = Color.WHITE; // #FFFFFF - Card backgrounds
    public static final Color NAVIGATION_BACKGROUND = new Color(248, 250, 252); // #F8FAFC - slate-50
    public static final Color TABLE_HEADER_BACKGROUND = new Color(248, 250, 252); // #F8FAFC - slate-50
    public static final Color TABLE_ROW_HOVER = new Color(248, 250, 252); // #F8FAFC - slate-50
    public static final Color TABLE_ALT_ROW = new Color(248, 250, 252); // #F8FAFC - slate-50

    // Button Colors
    public static final Color PRIMARY_BUTTON_COLOR = new Color(99, 102, 241); // #6366F1 - indigo-600
    public static final Color PRIMARY_BUTTON_HOVER = new Color(79, 70, 229); // #4F46E5 - indigo-700
    public static final Color SECONDARY_BUTTON_COLOR = new Color(107, 114, 128); // #6B7280 - gray-500
    public static final Color SECONDARY_BUTTON_HOVER = new Color(75, 85, 99); // #4B5563 - gray-600
    public static final Color BUTTON_TEXT_COLOR = Color.WHITE; // #FFFFFF
    public static final Color DELETE_BUTTON_COLOR = new Color(239, 68, 68); // #EF4444 - red-500
    public static final Color DELETE_BUTTON_HOVER = new Color(220, 38, 38); // #DC2626 - red-600
    public static final Color SUCCESS_COLOR = new Color(34, 197, 94); // #22C55E - green-500
    public static final Color WARNING_COLOR = new Color(245, 158, 11); // #F59E0B - amber-500

    // Legacy button colors for compatibility
    public static final Color BUTTON_COLOR = PRIMARY_BUTTON_COLOR;
    public static final Color BUTTON_HOVER_COLOR = PRIMARY_BUTTON_HOVER;

    // Text and Border Colors
    public static final Color TEXT_COLOR = new Color(51, 65, 85); // #334155 - slate-700
    public static final Color TEXT_SECONDARY = new Color(100, 116, 139); // #64748B - slate-500
    public static final Color TEXT_MUTED = new Color(148, 163, 184); // #94A3B8 - slate-400
    public static final Color BORDER_COLOR = new Color(226, 232, 240); // #E2E8F0 - slate-200
    public static final Color TABLE_BORDER_COLOR = new Color(226, 232, 240); // #E2E8F0 - slate-200

    // Status Colors
    public static final Color STATUS_ACTIVE_BG = new Color(220, 252, 231); // #DCFCE7 - green-100
    public static final Color STATUS_ACTIVE_TEXT = new Color(21, 128, 61); // #15803D - green-700
    public static final Color STATUS_INACTIVE_BG = new Color(241, 245, 249); // #F1F5F9 - slate-100
    public static final Color STATUS_INACTIVE_TEXT = new Color(51, 65, 85); // #334155 - slate-700

    // Action Button Colors
    public static final Color ACTION_VIEW_COLOR = new Color(14, 165, 233); // #0EA5E9 - sky-500
    public static final Color ACTION_EDIT_COLOR = new Color(34, 197, 94); // #22C55E - green-500
    public static final Color ACTION_DELETE_COLOR = new Color(239, 68, 68); // #EF4444 - red-500

    // Component Sizing
    public static final int FRAME_WIDTH = 1200;
    public static final int FRAME_HEIGHT = 800;
    public static final int DIALOG_WIDTH = 600;
    public static final int DIALOG_HEIGHT = 400;
    public static final int BUTTON_HEIGHT = 36;
    public static final int INPUT_HEIGHT = 32;
    public static final int TABLE_ROW_HEIGHT = 40;
    public static final int CARD_BORDER_RADIUS = 8;
    public static final int BUTTON_BORDER_RADIUS = 6;

    // Fonts
    public static final Font HEADING_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBHEADING_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font TABLE_HEADER_FONT = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    // ========== PAYROLL CONSTANTS ==========

    // General Payroll Constants
    public static final int STANDARD_WORK_DAYS_PER_MONTH = 21;
    public static final double REGULAR_HOURS_PER_DAY = 8.0;
    public static final double OVERTIME_RATE_MULTIPLIER = 1.25;

    // Government Contribution Rates & Caps
    public static final double PHILHEALTH_EMPLOYEE_CONTRIBUTION_RATE = 0.015; // Employee's share (e.g., 1.5% of 3%
                                                                              // total)
    public static final double PHILHEALTH_TOTAL_CONTRIBUTION_RATE = 0.03; // Total PhilHealth contribution rate

    // Pag-IBIG
    public static final double PAGIBIG_EMPLOYEE_CONTRIBUTION_RATE = 0.02; // Can vary, 2% is common for employee
    public static final double PAGIBIG_MAX_CONTRIBUTION_SALARY_CAP = 5000.00; // Salary cap for 2% calculation
    public static final double PAGIBIG_FIXED_RATE_ABOVE_CAP = 100.00; // Fixed contribution if salary is above cap

    // SSS - SSS contributions are typically based on salary brackets
    public static final double SSS_EMPLOYEE_CONTRIBUTION_CAP_PERCENT = 0.045; // Example if it were a simple percentage
                                                                              // cap

    // Tax calculation constants
    public static final double WITHHOLDING_TAX_EXEMPTION_THRESHOLD = 20833.00; // Example monthly non-taxable income

    // ========== DATE/TIME FORMATS ==========

    public static final String DATE_FORMAT_PATTERN = "MM/dd/yyyy";
    public static final String TIME_FORMAT_PATTERN = "H:mm";
    public static final String STANDARD_LOGIN_TIME_STRING = "08:00";

    // ========== VALIDATION CONSTANTS ==========

    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MIN_EMPLOYEE_ID = 1;
    public static final int MAX_EMPLOYEE_ID = 999999;
    public static final double MIN_SALARY = 0.0;
    public static final double MAX_SALARY = 10000000.0;

    // Phone number validation
    public static final String PHONE_PATTERN = "^[0-9]{3}-[0-9]{3}-[0-9]{3,4}$";

    // SSS, PhilHealth, TIN, Pag-IBIG patterns
    public static final String SSS_PATTERN = "^[0-9]{2}-[0-9]{7}-[0-9]$";
    public static final String PHILHEALTH_PATTERN = "^[0-9]{12}$";
    public static final String TIN_PATTERN = "^[0-9]{3}-[0-9]{3}-[0-9]{3}-[0-9]{3}$";
    public static final String PAGIBIG_PATTERN = "^[0-9]{12}$";

    // ========== APPLICATION MESSAGES ==========

    public static final String APP_TITLE = "MotorPH Payroll System";
    public static final String LOGIN_TITLE = "Login - MotorPH Payroll System";
    public static final String DASHBOARD_TITLE = "Dashboard - MotorPH Payroll System";

    // Success Messages
    public static final String EMPLOYEE_ADDED_SUCCESS = "Employee added successfully!";
    public static final String EMPLOYEE_UPDATED_SUCCESS = "Employee updated successfully!";
    public static final String EMPLOYEE_DELETED_SUCCESS = "Employee deleted successfully!";

    // Error Messages
    public static final String LOGIN_FAILED = "Invalid username or password.";
    public static final String EMPLOYEE_NOT_FOUND = "Employee not found.";
    public static final String DUPLICATE_EMPLOYEE_ID = "Employee ID already exists.";
    public static final String INVALID_INPUT = "Please check your input and try again.";
    public static final String FILE_ERROR = "Error accessing file. Please check file permissions.";
    public static final String NETWORK_ERROR = "Network connection error. Please try again.";

    // Confirmation Messages
    public static final String CONFIRM_DELETE_EMPLOYEE = "Are you sure you want to delete this employee?";
    public static final String CONFIRM_LOGOUT = "Are you sure you want to logout?";

    // Private constructor to prevent instantiation
    private AppConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

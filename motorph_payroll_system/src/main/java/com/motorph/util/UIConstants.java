package com.motorph.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Constants for UI components to maintain consistent styling across the
 * application.
 * Enhanced for MPHCR-02 Feature 2 implementation.
 */
public class UIConstants {
    // Colors - Professional theme matching modern UI standards
    public static final Color BACKGROUND_COLOR = new Color(173, 216, 230); // Light blue
    public static final Color BUTTON_COLOR = Color.WHITE;
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Color HEADING_COLOR = new Color(0, 51, 102); // Dark blue

    // MPHCR-02 Enhanced Color Palette
    public static final Color PRIMARY_BLUE = new Color(0, 123, 255); // Bootstrap primary
    public static final Color SUCCESS_GREEN = new Color(40, 167, 69); // Bootstrap success
    public static final Color DANGER_RED = new Color(220, 53, 69); // Bootstrap danger
    public static final Color WARNING_YELLOW = new Color(255, 193, 7); // Bootstrap warning
    public static final Color INFO_CYAN = new Color(23, 162, 184); // Bootstrap info
    public static final Color LIGHT_GRAY = new Color(248, 249, 250); // Bootstrap light
    public static final Color DARK_GRAY = new Color(52, 58, 64); // Bootstrap dark

    // Table Colors
    public static final Color TABLE_HEADER_COLOR = new Color(52, 58, 64);
    public static final Color TABLE_ROW_EVEN = Color.WHITE;
    public static final Color TABLE_ROW_ODD = new Color(248, 249, 250);
    public static final Color TABLE_SELECTION = new Color(0, 123, 255, 50);

    // Fonts
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    public static final Font HEADING_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 12);
    public static final Font TABLE_HEADER_FONT = new Font("Arial", Font.BOLD, 12);
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 11);

    // Dimensions
    public static final int DEFAULT_PADDING = 10;
    public static final int SMALL_PADDING = 5;
    public static final int LARGE_PADDING = 16;
    public static final int BUTTON_HEIGHT = 30;
    public static final int FIELD_HEIGHT = 25;

    // Button Dimensions for MPHCR-02
    public static final Dimension SMALL_BUTTON_SIZE = new Dimension(60, 25);
    public static final Dimension MEDIUM_BUTTON_SIZE = new Dimension(80, 30);
    public static final Dimension LARGE_BUTTON_SIZE = new Dimension(120, 35);
    public static final Dimension EXTRA_LARGE_BUTTON_SIZE = new Dimension(150, 40);

    // Dialog Dimensions
    public static final Dimension EMPLOYEE_DIALOG_SIZE = new Dimension(600, 500);
    public static final Dimension PAYROLL_DIALOG_SIZE = new Dimension(500, 400);
    public static final Dimension LIST_DIALOG_SIZE = new Dimension(900, 600);

    // Application constants
    public static final String APP_TITLE = "MotorPH Payroll System";
    public static final String APP_VERSION = "v1.0";

    // MPHCR-02 Feature constants
    public static final String FEATURE_2_TITLE = "Employee Management - Feature 2";
    public static final String[] MONTHS = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    // Table column names for employee list
    public static final String[] EMPLOYEE_TABLE_COLUMNS = {
            "Employee Number", "Last Name", "First Name", "SSS Number",
            "PhilHealth Number", "TIN", "Pag-IBIG Number", "Actions"
    };
}

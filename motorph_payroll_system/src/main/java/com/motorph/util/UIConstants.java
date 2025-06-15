package com.motorph.util;

import java.awt.Color;
import java.awt.Font;

/**
 * Constants for UI components to maintain consistent styling.
 * Updated to match modern prototype design.
 */
public class UIConstants {
    // Colors
    public static final Color BACKGROUND_COLOR = new Color(240, 240, 240); // #f0f0f0 - Light gray
    public static final Color PANEL_BACKGROUND = Color.WHITE; // #FFFFFF - White panels
    public static final Color NAVIGATION_BACKGROUND = new Color(233, 233, 233); // #E9E9E9 - Light gray navigation
    public static final Color TABLE_HEADER_BACKGROUND = new Color(240, 240, 240); // #F0F0F0 - Very light gray
    public static final Color BUTTON_COLOR = new Color(0, 123, 255); // #007BFF - Bootstrap blue
    public static final Color SECONDARY_BUTTON_COLOR = new Color(108, 117, 125); // #6C757D - Bootstrap gray
    public static final Color BUTTON_TEXT_COLOR = Color.WHITE; // #FFFFFF - White text on buttons
    public static final Color DELETE_BUTTON_COLOR = new Color(220, 53, 69); // #DC3545 - Bootstrap red

    public static final Color TEXT_COLOR = new Color(51, 51, 51); // #333333 - Dark gray text
    public static final Color BORDER_COLOR = new Color(224, 224, 224); // #e0e0e0 - Light gray border
    public static final Color TABLE_BORDER_COLOR = new Color(221, 221, 221); // #DDD - Table border

    // Fonts
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    public static final Font HEADING_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 12);
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);

    // Dimensions
    public static final int DEFAULT_PADDING = 15; // Updated to match prototype
    public static final int BUTTON_HEIGHT = 35;
    public static final int FIELD_HEIGHT = 25;
    public static final int BORDER_RADIUS = 4; // For rounded corners

    // Application constants
    public static final String APP_TITLE = "MotorPH Payroll System";
    public static final String APP_VERSION = "v1.0";
}

package com.motorph.util;

import java.awt.Color;
import java.awt.Font;

/**
 * Constants for UI components to maintain consistent styling.
 * Updated to match in wireframe
 */
public class UIConstants {

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
    public static final Color ACTION_VIEW_HOVER = new Color(219, 234, 254); // #DBEAFE - sky-100
    public static final Color ACTION_EDIT_COLOR = new Color(245, 158, 11); // #F59E0B - amber-500
    public static final Color ACTION_EDIT_HOVER = new Color(254, 243, 199); // #FEF3C7 - amber-100
    public static final Color ACTION_DELETE_COLOR = new Color(239, 68, 68); // #EF4444 - red-500
    public static final Color ACTION_DELETE_HOVER = new Color(254, 226, 226); // #FEE2E2 - red-100

    // Fonts - Inter-inspired font family with better spacing
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24); // Larger, more prominent
    public static final Font HEADING_FONT = new Font("Segoe UI", Font.BOLD, 18); // Better hierarchy
    public static final Font SUBHEADING_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font TABLE_HEADER_FONT = new Font("Segoe UI", Font.BOLD, 13);

    // Dimensions - Better spacing and modern proportions
    public static final int DEFAULT_PADDING = 20; // More generous padding
    public static final int CARD_PADDING = 24; // Card internal padding
    public static final int BUTTON_HEIGHT = 40; // Taller buttons
    public static final int FIELD_HEIGHT = 36; // Taller input fields
    public static final int BORDER_RADIUS = 8; // More rounded corners
    public static final int SMALL_BORDER_RADIUS = 6; // For smaller components
    public static final int TABLE_ROW_HEIGHT = 44; // Comfortable row height
    public static final int ICON_SIZE = 20; // Standard icon size
    public static final int SMALL_ICON_SIZE = 16; // Small icon size

    // Shadows and effects
    public static final Color SHADOW_COLOR = new Color(0, 0, 0, 10); // Subtle shadow
    public static final Color CARD_SHADOW = new Color(0, 0, 0, 8); // Card shadow

    // Application constants
    public static final String APP_TITLE = "MotorPH Payroll System";
    public static final String APP_VERSION = "v1.0";
}

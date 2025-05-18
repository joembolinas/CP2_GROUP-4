package com.motorph.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date-related operations.
 */
public class DateUtils {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    /**
     * Format a LocalDate to a string using the default format (MM/dd/yyyy)
     * 
     * @param date The date to format
     * @return Formatted date string
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DEFAULT_FORMATTER);
    }
    
    /**
     * Create a human-readable date range string
     * 
     * @param startDate Start date
     * @param endDate End date
     * @return Formatted date range string
     */
    public static String formatDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return "";
        }
        return formatDate(startDate) + " to " + formatDate(endDate);
    }
}

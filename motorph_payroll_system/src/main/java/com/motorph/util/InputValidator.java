package com.motorph.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Validates user inputs and provides standardized error messages.
 * Addresses MPHCR01 requirement for handling errors in user inputs.
 */
public class InputValidator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    /**
     * Validates employee number input
     * 
     * @param employeeNumberStr The string to validate as an employee number
     * @return Validated employee number as integer
     * @throws IllegalArgumentException If input is invalid
     */
    public static int validateEmployeeNumber(String employeeNumberStr) throws IllegalArgumentException {
        if (employeeNumberStr == null || employeeNumberStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee number cannot be empty");
        }
        
        try {
            int employeeNumber = Integer.parseInt(employeeNumberStr);
            if (employeeNumber <= 0) {
                throw new IllegalArgumentException("Employee number must be a positive number");
            }
            return employeeNumber;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Employee number must be a valid number");
        }
    }
    
    /**
     * Validates and parses a date string in MM/dd/yyyy format
     * 
     * @param dateStr The date string to validate and parse
     * @return The parsed LocalDate object
     * @throws IllegalArgumentException If date format is invalid
     */
    public static LocalDate validateDate(String dateStr) throws IllegalArgumentException {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Date cannot be empty");
        }
        
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use MM/DD/YYYY format");
        }
    }
    
    /**
     * Validates that start date is before or equal to end date
     * 
     * @param startDate Start date to validate
     * @param endDate End date to validate
     * @throws IllegalArgumentException If date range is invalid
     */
    public static void validateDateRange(LocalDate startDate, LocalDate endDate) throws IllegalArgumentException {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Both start and end dates must be provided");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
    }
}

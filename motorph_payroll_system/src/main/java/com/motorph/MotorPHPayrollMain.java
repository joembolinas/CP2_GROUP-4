package com.motorph;

import java.io.IOException;
import java.util.List;

/**
 * Entry point of the application that initializes all components and starts the application.
 */
public class MotorPHPayrollMain {
    public static void main(String[] args) {
        try {
            // CSVLoader with URLs
            CSVLoader csvLoader = new CSVLoader(
                "https://docs.google.com/spreadsheets/d/e/2PACX-1vRe4-w2yYtOZpBxFZGP1UZqyKWk053QkCmVxwq9Hiu2LfHU2nVIvCkTTg8rtWQsP-sp31jG6OleREqM/pub?output=csv",
                "https://docs.google.com/spreadsheets/d/e/2PACX-1vTqBrLETQHcACenfV0_VSgV_uEGH5Cne2Vuw-oN2yDGRH5wWS8x8CcAXAV8iSNugtwWB_oVCuOlcFYT/pub?output=csv"
            );
            // Load data from CSV files
            List<Employee> employees = csvLoader.loadEmployees();
            List<AttendanceRecord> attendanceRecords = csvLoader.loadAttendanceRecords();
            // Initialize and start the application
            MenuManager menuManager = new MenuManager(employees, attendanceRecords);
            menuManager.startApplication();
        } catch (IOException e) {
            System.err.println("Error initializing system: " + e.getMessage());
            System.exit(1);
        }
    }
}

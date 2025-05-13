package com.motorph;

import java.io.IOException;
import java.util.List;

/**
 * Entry point of the application that initializes all components and starts the application.
 */
public class MotorPHPayrollMain {
    public static void main(String[] args) {
        try {            // CSVLoader with local file paths
            CSVLoader csvLoader = new CSVLoader(
                "employeeDetails.csv",
                "attendanceRecord.csv"
            );// Load data from CSV files
            System.out.println("Loading employee data...");
            List<Employee> employees = csvLoader.loadEmployees();
            System.out.println("Loaded " + employees.size() + " employees.");
            
            System.out.println("Loading attendance records...");
            List<AttendanceRecord> attendanceRecords = csvLoader.loadAttendanceRecords();
            System.out.println("Loaded " + attendanceRecords.size() + " attendance records.");
            
            // Initialize and start the application
            MenuManager menuManager = new MenuManager(employees, attendanceRecords);
            menuManager.startApplication();
        } catch (IOException e) {
            System.err.println("Error initializing system: " + e.getMessage());
            System.exit(1);
        }
    }
}

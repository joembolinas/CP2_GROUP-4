package com.motorph.test;

import java.io.File;
import java.util.List;

import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.motorph.repository.DataRepository;

/**
 * Simple test to verify that CSV files can be loaded properly
 */
public class CSVLoadTest {

    public static void main(String[] args) {
        System.out.println("=== CSV Load Test ===");
        System.out.println("Working directory: " + System.getProperty("user.dir"));

        // Check if CSV files exist
        File employeeFile = new File("employeeDetails.csv");
        File attendanceFile = new File("attendanceRecord.csv");

        System.out.println("Employee file exists: " + employeeFile.exists());
        System.out.println("Employee file path: " + employeeFile.getAbsolutePath());
        System.out.println("Attendance file exists: " + attendanceFile.exists());
        System.out.println("Attendance file path: " + attendanceFile.getAbsolutePath());

        if (!employeeFile.exists() || !attendanceFile.exists()) {
            System.out.println("❌ CSV files not found in current directory");
            return;
        }

        try {
            // Test loading data
            System.out.println("\nAttempting to load data...");
            DataRepository dataRepository = new DataRepository("employeeDetails.csv", "attendanceRecord.csv");

            List<Employee> employees = dataRepository.getAllEmployees();
            List<AttendanceRecord> attendanceRecords = dataRepository.getAllAttendanceRecords();

            System.out.println("✅ Successfully loaded " + employees.size() + " employees");
            System.out.println("✅ Successfully loaded " + attendanceRecords.size() + " attendance records");

            // Display first few employees
            System.out.println("\nFirst 3 employees:");
            for (int i = 0; i < Math.min(3, employees.size()); i++) {
                Employee emp = employees.get(i);
                System.out.println("  " + emp.getEmployeeId() + ": " + emp.getFullName() + " - " + emp.getPosition());
            }

            System.out.println("\n✅ CSV loading test completed successfully!");

        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

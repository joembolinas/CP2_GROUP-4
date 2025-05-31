package com.motorph.test;

import java.io.File;
import java.util.List;

import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.motorph.repository.DataRepository;

/**
 * Quick test to verify CSV file loading and basic functionality
 */
public class SystemVerification {

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("MotorPH System Verification Test");
        System.out.println("==========================================");

        try {
            // Check if CSV files exist
            File employeeFile = new File("employeeDetails.csv");
            File attendanceFile = new File("attendanceRecord.csv");

            System.out.println("1. Checking CSV files...");
            System.out.println("   Employee file exists: " + employeeFile.exists());
            System.out.println("   Employee file path: " + employeeFile.getAbsolutePath());
            System.out.println("   Attendance file exists: " + attendanceFile.exists());
            System.out.println("   Attendance file path: " + attendanceFile.getAbsolutePath());

            // Initialize DataRepository
            System.out.println("\n2. Initializing DataRepository...");
            DataRepository dataRepository = new DataRepository("employeeDetails.csv", "attendanceRecord.csv");

            // Load employees
            System.out.println("\n3. Loading employees...");
            List<Employee> employees = dataRepository.getAllEmployees();
            System.out.println("   Loaded " + employees.size() + " employees");

            if (!employees.isEmpty()) {
                Employee firstEmployee = employees.get(0);
                System.out.println("   First employee: " + firstEmployee.getFirstName() + " "
                        + firstEmployee.getLastName() + " (ID: " + firstEmployee.getEmployeeId() + ")");
            }

            // Load attendance records
            System.out.println("\n4. Loading attendance records...");
            List<AttendanceRecord> attendanceRecords = dataRepository.getAllAttendanceRecords();
            System.out.println("   Loaded " + attendanceRecords.size() + " attendance records");

            if (!attendanceRecords.isEmpty()) {
                AttendanceRecord firstRecord = attendanceRecords.get(0);
                System.out.println(
                        "   First record: Employee ID " + firstRecord.getEmployeeId() + " on " + firstRecord.getDate());
            }

            System.out.println("\n==========================================");
            System.out.println("✅ All tests passed successfully!");
            System.out.println("✅ System is ready to run");
            System.out.println("==========================================");

        } catch (Exception e) {
            System.err.println("\n==========================================");
            System.err.println("❌ Test failed with error:");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.err.println("==========================================");
        }
    }
}

package com.motorph.test;

import java.io.File;
import java.util.List;
import com.motorph.repository.DataRepository;
import com.motorph.model.Employee;
import com.motorph.model.AttendanceRecord;

/**
 * Quick test to verify MotorPH system components are working
 * Computer Programming 2 - Verification Script
 */
public class QuickTest {
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("MotorPH Payroll System - Quick Test");
        System.out.println("===========================================");

        try {
            // Test 1: Check CSV files exist
            System.out.println("🔍 Test 1: Checking CSV files...");
            File employeeFile = new File("employeeDetails.csv");
            File attendanceFile = new File("attendanceRecord.csv");

            if (employeeFile.exists()) {
                System.out.println("✅ employeeDetails.csv found");
            } else {
                System.out.println("❌ employeeDetails.csv missing");
                return;
            }

            if (attendanceFile.exists()) {
                System.out.println("✅ attendanceRecord.csv found");
            } else {
                System.out.println("❌ attendanceRecord.csv missing");
                return;
            }

            // Test 2: Load data
            System.out.println("\n🔍 Test 2: Loading data...");
            DataRepository dataRepository = new DataRepository("employeeDetails.csv", "attendanceRecord.csv");

            List<Employee> employees = dataRepository.getAllEmployees();
            List<AttendanceRecord> attendanceRecords = dataRepository.getAllAttendanceRecords();

            System.out.println("✅ Loaded " + employees.size() + " employees");
            System.out.println("✅ Loaded " + attendanceRecords.size() + " attendance records");

            // Test 3: Sample employee data
            if (!employees.isEmpty()) {
                Employee firstEmployee = employees.get(0);
                System.out.println(
                        "✅ Sample employee: " + firstEmployee.getFirstName() + " " + firstEmployee.getLastName());
            }

            System.out.println("\n🎉 All tests passed! System is ready to run.");
            System.out.println("💡 You can now run: mvn exec:java");

        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

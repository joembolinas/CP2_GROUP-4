package com.motorph;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.motorph.model.Employee;
import com.motorph.service.EmployeeService;

/**
 * Test for EmployeeService CSV functionality without external CSV dependencies
 */
public class EmployeeServiceTest {

    public static void main(String[] args) {
        System.out.println("=== EmployeeService CSV Test ===");

        try {
            // Test file path
            String csvFilePath = "c:\\Users\\ADMIN\\Desktop\\developerFiles\\CP2_GROUP-4\\motorph_payroll_system\\service_test.csv";

            // Create initial employee list
            List<Employee> employees = new ArrayList<>();
            Employee existingEmployee = new Employee(
                    1, "Dela Cruz", "Juan", LocalDate.of(1990, 1, 1),
                    "123 Main St", "123-456-7890", "11-1111111-1",
                    "111111111111", "111-111-111-000", "111111111111",
                    "Regular", "Developer", "Manager", 50000, 1500, 1000, 1000, 25000);
            employees.add(existingEmployee);

            // Create empty attendance list
            List<Object> attendanceRecords = new ArrayList<>();

            // Create EmployeeService
            EmployeeService employeeService = new EmployeeService(
                    employees,
                    attendanceRecords,
                    csvFilePath);

            System.out.println("Initial employee count: " + employees.size());

            // First, save all employees to create the initial CSV file
            System.out.println("Creating initial CSV file with existing employees...");
            try {
                // Use reflection to call the private method for testing
                java.lang.reflect.Method saveMethod = EmployeeService.class.getDeclaredMethod("saveAllEmployeesToCSV");
                saveMethod.setAccessible(true);
                saveMethod.invoke(employeeService);
                System.out.println("✅ Initial CSV created successfully");
            } catch (Exception e) {
                System.out.println("❌ Failed to create initial CSV: " + e.getMessage());
                return;
            }

            // Create a test employee
            Employee testEmployee = new Employee(
                    99999, // Employee ID
                    "TestLastName", // Last Name
                    "TestFirstName", // First Name
                    LocalDate.of(1995, 6, 15), // Birthday
                    "456 Test Avenue, Test City, Test Province", // Address
                    "987-654-3210", // Phone
                    "22-2222222-2", // SSS
                    "222222222222", // PhilHealth
                    "222-222-222-000", // TIN
                    "222222222222", // Pag-IBIG
                    "Regular", // Status
                    "Test Engineer", // Position
                    "Test Manager", // Supervisor
                    55000, // Basic Salary
                    1500, // Rice Subsidy
                    1000, // Phone Allowance
                    1000, // Clothing Allowance
                    27500 // Gross Semi-monthly Rate
            );

            System.out.println("Adding test employee with ID: " + testEmployee.getEmployeeId());

            // Attempt to add the employee using the service
            boolean success = employeeService.addEmployee(testEmployee);

            if (success) {
                System.out.println("✅ Employee added successfully!");
                System.out.println("New employee count: " + employees.size());

                // Verify the employee was added to memory
                Employee found = employeeService.findEmployeeById(99999);
                if (found != null) {
                    System.out
                            .println("✅ Employee found in memory: " + found.getFirstName() + " " + found.getLastName());
                } else {
                    System.out.println("❌ Employee not found in memory!");
                }

                // Check if CSV file exists and has content
                File csvFile = new File(csvFilePath);
                if (csvFile.exists()) {
                    System.out.println("✅ CSV file exists at: " + csvFilePath);
                    System.out.println("File size: " + csvFile.length() + " bytes");

                    // Try to count lines in the file
                    try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(csvFile))) {
                        long lineCount = reader.lines().count();
                        System.out.println("CSV file has " + lineCount + " lines (1 header + " + (lineCount - 1)
                                + " employee records)");
                    }
                } else {
                    System.out.println("❌ CSV file does not exist!");
                }

                System.out.println("✅ Test completed successfully!");
            } else {
                System.out.println("❌ Failed to add employee!");
            }

        } catch (Exception e) {
            System.err.println("❌ Test failed with error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

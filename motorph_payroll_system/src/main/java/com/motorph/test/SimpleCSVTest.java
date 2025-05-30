package com.motorph.test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.motorph.model.Employee;
import com.motorph.service.EmployeeService;

/**
 * Simple test to verify CSV writing functionality without full DataRepository
 */
public class SimpleCSVTest {
    public static void main(String[] args) {
        try {
            System.out.println("=== Simple CSV Write Test ===");

            // Use absolute path for CSV file
            String csvFilePath = "c:\\Users\\ADMIN\\Desktop\\developerFiles\\CP2_GROUP-4\\motorph_payroll_system\\test_employees.csv";

            // Create a simple list with one existing employee for testing
            List<Employee> employees = new ArrayList<>();
            Employee existingEmployee = new Employee(
                    1, "Dela Cruz", "Juan", LocalDate.of(1990, 1, 1),
                    "123 Main St", "123-456-7890", "11-1111111-1",
                    "111111111111", "111-111-111-000", "111111111111",
                    "Regular", "Developer", "Manager", 50000, 1500, 1000, 1000, 25000);
            employees.add(existingEmployee);

            // Create empty attendance list
            List<Object> attendanceRecords = new ArrayList<>();

            // Create EmployeeService with CSV file path
            EmployeeService employeeService = new EmployeeService(
                    employees,
                    attendanceRecords,
                    csvFilePath);

            System.out.println("Initial employee count: " + employees.size());

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

            System.out.println("Adding test employee: " + testEmployee.getEmployeeId());

            // First, manually save all employees to create the initial CSV
            System.out.println("Creating initial CSV file...");
            employeeService.saveAllEmployeesToCSV();

            // Now attempt to add the new employee
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
                } else {
                    System.out.println("❌ CSV file does not exist!");
                }

                System.out.println("✅ Test completed. Check the CSV file to verify the new employee was written.");
            } else {
                System.out.println("❌ Failed to add employee!");
            }

        } catch (Exception e) {
            System.err.println("❌ Test failed with error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

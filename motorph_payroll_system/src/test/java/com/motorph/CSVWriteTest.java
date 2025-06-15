package com.motorph;

import java.time.LocalDate;
import java.util.List;

import com.motorph.model.Employee;
import com.motorph.repository.DataRepository;
import com.motorph.service.EmployeeService;

/**
 * Test class to verify CSV writing functionality for employee addition
 */
public class CSVWriteTest {
    public static void main(String[] args) {
        try {
            System.out.println("=== MotorPH CSV Write Test ===");

            // File paths (absolute paths to ensure we can find the files)
            String employeesPath = "c:\\Users\\ADMIN\\Desktop\\developerFiles\\CP2_GROUP-4\\motorph_payroll_system\\employeeDetails.csv";
            String attendancePath = "c:\\Users\\ADMIN\\Desktop\\developerFiles\\CP2_GROUP-4\\motorph_payroll_system\\attendanceRecord.csv";

            // Initialize repository and load existing data
            DataRepository repository = new DataRepository(employeesPath, attendancePath);
            List<Employee> employees = repository.getAllEmployees();

            System.out.println("Initial employee count: " + employees.size());

            // Create EmployeeService with correct file path
            EmployeeService employeeService = new EmployeeService(
                    employees,
                    repository.getAllAttendanceRecords(),
                    employeesPath);

            // Create a test employee
            Employee testEmployee = new Employee(
                    99998, // Employee ID
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

            // Attempt to add the employee
            boolean success = employeeService.addEmployee(testEmployee);

            if (success) {
                System.out.println("✅ Employee added successfully!");
                System.out.println("New employee count: " + employees.size());

                // Verify the employee was added to memory
                Employee found = employeeService.findEmployeeById(99998);
                if (found != null) {
                    System.out
                            .println("✅ Employee found in memory: " + found.getFirstName() + " " + found.getLastName());
                } else {
                    System.out.println("❌ Employee not found in memory!");
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

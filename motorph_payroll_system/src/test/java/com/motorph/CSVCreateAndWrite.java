package com.motorph.repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVWriter;
import com.motorph.model.Employee;

/**
 * CSV utility for creating and writing employee data to CSV files.
 * This class provides functionality to collect employee information
 * and export it to CSV format for the MotorPH payroll system.
 */
public class CSVCreateAndWrite {
    public static void main(String[] args) throws IOException {
        // Create a list to store the employee data
        List<Employee> employees = new ArrayList<>();

        // Prompt the user to enter the employee details
        try (Scanner scanner = new Scanner(System.in)) {
            boolean done = false;
            System.out.println("=== MotorPH Employee Data Entry System ===");
            System.out.println("Enter employee details (type 'done' for Employee ID to finish):\n");
            
            while (!done) {
                System.out.print("Enter Employee ID (or type 'done' to finish): ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("done")) {
                    done = true;
                    continue;
                }
                
                int employeeId;
                try {
                    employeeId = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Employee ID. Please enter a valid number.");
                    continue;
                }
                
                System.out.print("Enter Last Name: ");
                String lastName = scanner.nextLine().trim();
                
                System.out.print("Enter First Name: ");
                String firstName = scanner.nextLine().trim();
                
                System.out.print("Enter Position: ");
                String position = scanner.nextLine().trim();
                
                System.out.print("Enter Status (e.g., Regular, Probationary, Part-time): ");
                String status = scanner.nextLine().trim();
                
                double basicSalary = getValidDoubleInput(scanner, "Enter Basic Salary: ");
                double riceSubsidy = getValidDoubleInput(scanner, "Enter Rice Subsidy: ");
                double phoneAllowance = getValidDoubleInput(scanner, "Enter Phone Allowance: ");
                double clothingAllowance = getValidDoubleInput(scanner, "Enter Clothing Allowance: ");
                
                Employee employee = new Employee(employeeId, lastName, firstName, position, 
                                               status, basicSalary, riceSubsidy, 
                                               phoneAllowance, clothingAllowance);
                employees.add(employee);
                
                System.out.println("Employee " + employee.getFullName() + " added successfully!\n");
            }
        }

        // Write the employee data to a CSV file
        String csvFile = "employees.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            String[] headers = {"employee_id", "last_name", "first_name", "position", 
                              "status", "basic_salary", "rice_subsidy", "phone_allowance", 
                              "clothing_allowance", "hourly_rate"};
            writer.writeNext(headers);
            
            for (Employee employee : employees) {
                String[] data = {
                    String.valueOf(employee.getEmployeeId()),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getPosition(),
                    employee.getStatus(),
                    String.valueOf(employee.getBasicSalary()),
                    String.valueOf(employee.getRiceSubsidy()),
                    String.valueOf(employee.getPhoneAllowance()),
                    String.valueOf(employee.getClothingAllowance()),
                    String.valueOf(employee.getHourlyRate())
                };
                writer.writeNext(data);
            }
        }
        
        System.out.println("\n=== Export Complete ===");
        System.out.println("Employee data has been written to " + csvFile);
        System.out.println("Total employees added: " + employees.size());
    }

    /**
     * Helper method to get valid double input from user with error handling
     */
    private static double getValidDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
package com.motorph;

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.motorph.model.Employee;

/**
 * Manual CSV test without external dependencies
 */
public class ManualCSVTest {

    public static void main(String[] args) {
        System.out.println("=== Manual CSV Write Test ===");

        try {
            // Test file path
            String csvFilePath = "c:\\Users\\ADMIN\\Desktop\\developerFiles\\CP2_GROUP-4\\motorph_payroll_system\\manual_test.csv";

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

            // Write CSV manually
            writeEmployeeToCSV(testEmployee, csvFilePath);

            System.out.println("✅ Test completed. Check manual_test.csv file.");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeEmployeeToCSV(Employee employee, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.write(
                    "Employee Number,Last Name,First Name,Birthday,Address,Phone Number,SSS Number,Philhealth Number,TIN Number,Pagibig Number,Status,Position,Immediate Supervisor,Basic Salary,Rice Subsidy,Phone Allowance,Clothing Allowance,Gross Semi-monthly Rate,Hourly Rate\n");

            // Write employee data
            writer.write(String.valueOf(employee.getEmployeeId()) + ",");
            writer.write(escapeCSV(employee.getLastName()) + ",");
            writer.write(escapeCSV(employee.getFirstName()) + ",");
            writer.write(formatDateForCSV(employee.getBirthday()) + ",");
            writer.write(escapeCSV(employee.getAddress()) + ",");
            writer.write(escapeCSV(employee.getPhoneNumber()) + ",");
            writer.write(escapeCSV(employee.getSssNumber()) + ",");
            writer.write(escapeCSV(employee.getPhilhealthNumber()) + ",");
            writer.write(escapeCSV(employee.getTinNumber()) + ",");
            writer.write(escapeCSV(employee.getPagibigNumber()) + ",");
            writer.write(escapeCSV(employee.getStatus()) + ",");
            writer.write(escapeCSV(employee.getPosition()) + ",");
            writer.write(escapeCSV(employee.getSupervisor()) + ",");
            writer.write(formatMoneyForCSV(employee.getBasicSalary()) + ",");
            writer.write(formatMoneyForCSV(employee.getRiceSubsidy()) + ",");
            writer.write(formatMoneyForCSV(employee.getPhoneAllowance()) + ",");
            writer.write(formatMoneyForCSV(employee.getClothingAllowance()) + ",");
            writer.write(formatMoneyForCSV(employee.getGrossSemiMonthlyRate()) + ",");
            writer.write(String.valueOf(employee.getHourlyRate()));
            writer.write("\n");
        }
    }

    private static String formatDateForCSV(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date.format(formatter);
    }

    private static String formatMoneyForCSV(double amount) {
        if (amount >= 1000) {
            NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
            return "\"" + formatter.format(amount) + "\"";
        } else {
            return "\"" + (int) amount + "\"";
        }
    }

    private static String escapeCSV(String value) {
        if (value == null)
            return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}

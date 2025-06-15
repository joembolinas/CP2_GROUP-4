package com.motorph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.motorph.model.Employee;
import com.opencsv.CSVWriter;

/**
 * Test class to demonstrate the CSV creation functionality
 * This shows that the CSV integration is working properly with OpenCSV
 */
public class CSVTest {
    public static void main(String[] args) throws IOException {
        System.out.println("=== MotorPH CSV Creation Test ===");

        // Create sample employees
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(99001, "Test", "John", "Software Developer", "Regular", 45000, 1500, 1000, 1000));
        employees.add(new Employee(99002, "Demo", "Jane", "HR Manager", "Regular", 50000, 1500, 1000, 1000));

        // Write to CSV file
        String csvFile = "test_employees.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            // Write headers
            String[] headers = { "employee_id", "last_name", "first_name", "position",
                    "status", "basic_salary", "rice_subsidy", "phone_allowance",
                    "clothing_allowance", "hourly_rate" };
            writer.writeNext(headers);

            // Write employee data
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

        System.out.println("✅ CSV file created successfully: " + csvFile);
        System.out.println("✅ Total employees written: " + employees.size());
        System.out.println("✅ OpenCSV integration working properly");
        System.out.println("\nCSV Creation Test completed successfully!");
    }
}

package com.motorph.test;

import java.util.List;

import com.motorph.model.Employee;
import com.motorph.repository.DataRepository;

/**
 * Test class to verify that employee details from CSV are correctly loaded
 */
public class EmployeeDetailsTest {

    public static void main(String[] args) {
        try {
            // Load employee data
            DataRepository repository = new DataRepository("employeeDetails.csv", "attendanceRecord.csv");

            // First get all employees
            List<Employee> allEmployees = repository.getAllEmployees();

            // Check if we have any employees
            System.out.println("Total employees loaded: " + allEmployees.size());

            // Find employee with ID 10001
            Employee employee = null;
            for (Employee emp : allEmployees) {
                if (emp.getEmployeeId() == 10001) {
                    employee = emp;
                    break;
                }
            }

            // Print employee details
            if (employee != null) {
                System.out.println("\nEmployee Details:");
                System.out.println("ID: " + employee.getEmployeeId());
                System.out.println("Name: " + employee.getFirstName() + " " + employee.getLastName());
                System.out.println("Birthday: " + employee.getBirthday());
                System.out.println("Address: " + employee.getAddress());
                System.out.println("Phone: " + employee.getPhoneNumber());

                System.out.println("\nGovernment IDs:");
                System.out.println("SSS: " + employee.getSssNumber());
                System.out.println("PhilHealth: " + employee.getPhilhealthNumber());
                System.out.println("TIN: " + employee.getTinNumber());
                System.out.println("Pag-IBIG: " + employee.getPagibigNumber());

                System.out.println("\nEmployment Info:");
                System.out.println("Position: " + employee.getPosition());
                System.out.println("Status: " + employee.getStatus());
                System.out.println("Supervisor: " + employee.getSupervisor());

                System.out.println("\nSalary Info:");
                System.out.println("Basic Salary: " + employee.getBasicSalary());
                System.out.println("Rice Subsidy: " + employee.getRiceSubsidy());
                System.out.println("Phone Allowance: " + employee.getPhoneAllowance());
                System.out.println("Clothing Allowance: " + employee.getClothingAllowance());
            } else {
                System.out.println("Employee with ID 10001 not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

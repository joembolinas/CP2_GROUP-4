package com.motorph.test;

import java.util.List;

import com.motorph.controller.EmployeeController;
import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.motorph.repository.DataRepository;
import com.motorph.service.EmployeeService;
import com.motorph.service.PayrollProcessor;
import com.motorph.service.PayrollService;

/**
 * Quick system verification test without GUI
 */
public class QuickSystemTest {

    public static void main(String[] args) {
        System.out.println("=== MotorPH System Quick Test ===");
        System.out.println("Working directory: " + System.getProperty("user.dir"));

        try {
            // Test 1: Data loading
            System.out.println("\nTest 1: Loading data...");
            DataRepository dataRepository = new DataRepository("employeeDetails.csv", "attendanceRecord.csv");
            List<Employee> employees = dataRepository.getAllEmployees();
            List<AttendanceRecord> attendanceRecords = dataRepository.getAllAttendanceRecords();

            System.out.println("✅ Loaded " + employees.size() + " employees");
            System.out.println("✅ Loaded " + attendanceRecords.size() + " attendance records");

            // Test 2: Services
            System.out.println("\nTest 2: Initializing services...");
            PayrollProcessor payrollCalculator = new PayrollProcessor();
            EmployeeService employeeService = new EmployeeService(employees, attendanceRecords, "employeeDetails.csv");
            PayrollService payrollService = new PayrollService(employees, attendanceRecords, payrollCalculator);

            System.out.println("✅ Services initialized successfully");

            // Test 3: Controllers
            System.out.println("\nTest 3: Initializing controllers...");
            EmployeeController employeeController = new EmployeeController(employeeService);

            System.out.println("✅ Controllers initialized successfully");

            // Test 4: Basic functionality
            System.out.println("\nTest 4: Testing basic functionality...");
            List<Employee> allEmployees = employeeController.getAllEmployees();
            System.out.println("✅ Found " + allEmployees.size() + " employees via controller");

            if (!allEmployees.isEmpty()) {
                Employee firstEmployee = allEmployees.get(0);
                System.out.println("✅ First employee: " + firstEmployee.getFullName() + " (ID: "
                        + firstEmployee.getEmployeeId() + ")");
            }

            System.out.println("\n=== All Tests Passed! ===");
            System.out.println("The MotorPH system is ready to run.");
            System.out.println("You can now start the GUI application.");

        } catch (Exception e) {
            System.out.println("❌ System Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

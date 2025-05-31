package com.motorph;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.motorph.controller.EmployeeController;
import com.motorph.controller.PayrollController;
import com.motorph.controller.ReportController;
import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.motorph.repository.DataRepository;
import com.motorph.service.EmployeeService;
import com.motorph.service.PayrollProcessor;
import com.motorph.service.PayrollService;
import com.motorph.service.ReportService;
import com.motorph.view.MainFrame;

/**
 * Entry point for the MotorPH Payroll System application.
 * This class is responsible for initializing the application components
 * and starting the user interface.
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName()); // File paths for data sources
    private static final String EMPLOYEES_FILE_PATH = "employeeDetails.csv";
    private static final String ATTENDANCE_FILE_PATH = "attendanceRecord.csv";

    /**
     * Main entry point for the application
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Set the look and feel to the system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Initialize and start the application on the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                try {
                    // Initialize the application components
                    initializeApplication();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Failed to initialize application", e);
                    System.exit(1);
                }
            });
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            logger.log(Level.WARNING, "Failed to set system look and feel", e);
            // Continue with default look and feel
            SwingUtilities.invokeLater(() -> {
                try {
                    initializeApplication();
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Failed to initialize application", ex);
                    System.exit(1);
                }
            });
        }
    }

    /**
     * Initialize the application components and start the UI
     * 
     * @throws IOException If there's an error loading data
     */
    private static void initializeApplication() throws IOException {
        // Initialize the data repository
        DataRepository dataRepository = new DataRepository(EMPLOYEES_FILE_PATH, ATTENDANCE_FILE_PATH);

        // Get employees and attendance records
        List<Employee> employees = dataRepository.getAllEmployees();
        List<AttendanceRecord> attendanceRecords = dataRepository.getAllAttendanceRecords();

        // Initialize the payroll calculator
        PayrollProcessor payrollCalculator = new PayrollProcessor(); // Initialize services
        EmployeeService employeeService = new EmployeeService(employees, attendanceRecords, EMPLOYEES_FILE_PATH);
        PayrollService payrollService = new PayrollService(employees, attendanceRecords, payrollCalculator);
        ReportService reportService = new ReportService(employeeService, payrollService);

        // Initialize controllers
        EmployeeController employeeController = new EmployeeController(employeeService);
        PayrollController payrollController = new PayrollController(payrollService);
        ReportController reportController = new ReportController(reportService); // Initialize and show the main frame
        MainFrame mainFrame = new MainFrame(employeeController, payrollController, reportController);
        mainFrame.setVisible(true);

        logger.info("MotorPH Payroll System started successfully");
    }
}

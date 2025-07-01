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
 * Test entry point for the MotorPH Payroll System application without login.
 * This bypasses the login screen for testing the modernized UI.
 */
public class TestMain {
    private static final Logger logger = Logger.getLogger(TestMain.class.getName());

    // File paths for data sources
    private static final String EMPLOYEES_FILE_PATH = "data/employeeDetails.csv";
    private static final String ATTENDANCE_FILE_PATH = "data/attendanceRecord.csv";

    /**
     * Main entry point for testing
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Set the look and feel to the system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            SwingUtilities.invokeLater(() -> {
                try {
                    // Skip login and go directly to main application
                    initializeApplication();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Failed to initialize application", e);
                    System.exit(1);
                }
            });
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            logger.log(Level.WARNING, "Failed to set system look and feel", e);
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
    public static void initializeApplication() throws IOException {
        // Log working directory and file paths for debugging
        String workingDir = System.getProperty("user.dir");
        logger.log(Level.INFO, "Working directory: {0}", workingDir);
        logger.log(Level.INFO, "Looking for employee file: {0}", EMPLOYEES_FILE_PATH);
        logger.log(Level.INFO, "Looking for attendance file: {0}", ATTENDANCE_FILE_PATH);

        // Check if files exist and log their status
        java.io.File employeeFile = new java.io.File(EMPLOYEES_FILE_PATH);
        java.io.File attendanceFile = new java.io.File(ATTENDANCE_FILE_PATH);

        logger.log(Level.INFO, "Employee file exists: {0} at {1}",
                new Object[] { employeeFile.exists(), employeeFile.getAbsolutePath() });
        logger.log(Level.INFO, "Attendance file exists: {0} at {1}",
                new Object[] { attendanceFile.exists(), attendanceFile.getAbsolutePath() });

        // Initialize the data repository
        DataRepository dataRepository = new DataRepository(EMPLOYEES_FILE_PATH, ATTENDANCE_FILE_PATH);

        // Get employees and attendance records
        List<Employee> employees = dataRepository.getAllEmployees();
        List<AttendanceRecord> attendanceRecords = dataRepository.getAllAttendanceRecords();

        // Initialize the payroll calculator
        PayrollProcessor payrollCalculator = new PayrollProcessor();

        // Initialize services
        EmployeeService employeeService = new EmployeeService(employees, attendanceRecords, EMPLOYEES_FILE_PATH);
        PayrollService payrollService = new PayrollService(employees, attendanceRecords, payrollCalculator);
        ReportService reportService = new ReportService(employeeService, payrollService);

        // Initialize controllers
        EmployeeController employeeController = new EmployeeController(employeeService);
        PayrollController payrollController = new PayrollController(payrollService);
        ReportController reportController = new ReportController(reportService);

        // Initialize and show the main frame
        MainFrame mainFrame = new MainFrame(employeeController, payrollController, reportController);
        mainFrame.setVisible(true);

        logger.info("MotorPH Payroll System (Test Mode) started successfully");
    }
}

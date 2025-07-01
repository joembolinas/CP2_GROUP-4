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
import com.motorph.view.LoginFrame;
import com.motorph.view.MainFrame;

/**
 * Entry point for the MotorPH Payroll System application.
 * This class is responsible for initializing the application components
 * and starting the user interface.
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName()); // File paths for data sources
    private static final String EMPLOYEES_FILE_PATH = "data/employeeDetails.csv";
    private static final String ATTENDANCE_FILE_PATH = "data/attendanceRecord.csv";

    /**
     * Main entry point for the application
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Set the look and feel to the system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Initialize and start the application
                                                                                 // on the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                try {
                    showLoginScreen();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Failed to initialize application", e);
                    System.exit(1);
                }
            });
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            logger.log(Level.WARNING, "Failed to set system look and feel", e); // Continue with default look and feel
            SwingUtilities.invokeLater(() -> {
                try {
                    showLoginScreen();
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Failed to initialize application", ex);
                    System.exit(1);
                }
            });
        }
    }

    /**
     * Initialize a default session for bypassed login
     * This allows the dashboard to display user information during testing
     */
    private static void initializeDefaultSession() {
        try {
            // Create a default admin user for testing
            com.motorph.model.User defaultUser = new com.motorph.model.User("admin", "password", 1, "ADMIN", true);
            com.motorph.util.SessionManager sessionManager = com.motorph.util.SessionManager.getInstance();
            sessionManager.setCurrentUser(defaultUser);
            logger.log(Level.INFO, "Default session initialized for user: {0}", defaultUser.getUsername());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to initialize default session", e);
        }
    }

    /**
     * Show the login screen
     */
    private static void showLoginScreen() {
        showLoginScreen(null);
    }

    /**
     * Show the login screen with a callback for successful login
     * 
     * @param onLoginSuccess Callback to run after successful login
     */
    public static void showLoginScreen(Runnable onLoginSuccess) {
        logger.log(Level.INFO, "Starting MotorPH Payroll System with login screen");

        LoginFrame loginFrame = new LoginFrame(() -> {
            try {
                if (onLoginSuccess != null) {
                    onLoginSuccess.run();
                } else {
                    initializeApplication();
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to initialize main application after login", e);
                System.exit(1);
            }
        });

        loginFrame.showLogin();
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

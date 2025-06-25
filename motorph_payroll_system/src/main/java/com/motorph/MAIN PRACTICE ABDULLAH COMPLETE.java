package com.motorph;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    // File paths for data sources
    private static final String EMPLOYEES_FILE_PATH = "motorph_payroll_system/data/employeeDetails.csv";
    private static final String ATTENDANCE_FILE_PATH = "motorph_payroll_system/data/attendanceRecord.csv";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Startup failed", e);
        }
    }

    // Initializes the application by loading data and setting up controllers
    // This method is called after successful login
    public static void initializeApplication() throws IOException {
        DataRepository dataRepository = new DataRepository(EMPLOYEES_FILE_PATH, ATTENDANCE_FILE_PATH);
        List<Employee> employees = dataRepository.getAllEmployees();
        List<AttendanceRecord> attendanceRecords = dataRepository.getAllAttendanceRecords();

        PayrollProcessor payrollCalculator = new PayrollProcessor();
        EmployeeService employeeService = new EmployeeService(employees, attendanceRecords, EMPLOYEES_FILE_PATH);
        PayrollService payrollService = new PayrollService(employees, attendanceRecords, payrollCalculator);
        ReportService reportService = new ReportService(employeeService, payrollService);

        EmployeeController employeeController = new EmployeeController(employeeService);
        PayrollController payrollController = new PayrollController(payrollService);
        ReportController reportController = new ReportController(reportService);

        MainFrame mainFrame = new MainFrame(employeeController, payrollController, reportController);
        mainFrame.setVisible(true);

        logger.info("MotorPH Payroll System started successfully");
    }
}

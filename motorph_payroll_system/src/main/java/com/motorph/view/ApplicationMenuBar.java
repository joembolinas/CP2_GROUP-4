package com.motorph.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.motorph.controller.EmployeeController;
import com.motorph.controller.PayrollController;
import com.motorph.controller.ReportController;
import com.motorph.util.UIConstants;

/**
 * Menu bar for the application.
 */
public class ApplicationMenuBar extends JMenuBar {

    private final MainFrame mainFrame;
    private final EmployeeController employeeController;
    private final PayrollController payrollController;
    private final ReportController reportController;

    /**
     * Constructor for the application menu bar
     */
    public ApplicationMenuBar(MainFrame mainFrame,
            EmployeeController employeeController,
            PayrollController payrollController,
            ReportController reportController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        this.payrollController = payrollController;
        this.reportController = reportController;

        initMenuBar();
    }

    /**
     * Initialize the menu bar
     */
    private void initMenuBar() {
        // Create menus
        JMenu fileMenu = new JMenu("File");
        JMenu employeeMenu = new JMenu("Employee");
        JMenu payrollMenu = new JMenu("Payroll");
        JMenu reportsMenu = new JMenu("Reports");
        JMenu helpMenu = new JMenu("Help");
        // Add menu items to "File" menu
        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        logoutMenuItem.addActionListener(e -> mainFrame.logout());
        fileMenu.add(logoutMenuItem);

        fileMenu.addSeparator();

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        // Add menu items to "Employee" menu
        JMenuItem searchEmployeeMenuItem = new JMenuItem("Search Employee");
        JMenuItem listAllEmployeesMenuItem = new JMenuItem("List All Employees");
        JMenuItem attendanceMenuItem = new JMenuItem("Attendance");

        searchEmployeeMenuItem.addActionListener(e -> mainFrame.showEmployeeManagement());
        listAllEmployeesMenuItem.addActionListener(e -> mainFrame.showEmployeeManagement());
        attendanceMenuItem.addActionListener(e -> mainFrame.showEmployeeManagement());

        employeeMenu.add(searchEmployeeMenuItem);
        employeeMenu.add(listAllEmployeesMenuItem);
        employeeMenu.add(attendanceMenuItem);

        // Add menu items to "Payroll" menu
        JMenuItem generatePayrollMenuItem = new JMenuItem("Generate Payroll");
        JMenuItem customPayrollMenuItem = new JMenuItem("Custom Payroll");

        generatePayrollMenuItem.addActionListener(e -> mainFrame.showPayrollManagement());
        customPayrollMenuItem.addActionListener(e -> mainFrame.showPayrollManagement());

        payrollMenu.add(generatePayrollMenuItem);
        payrollMenu.add(customPayrollMenuItem);

        // Add menu items to "Reports" menu
        JMenuItem payslipMenuItem = new JMenuItem("Payslip");
        JMenuItem weeklySummaryMenuItem = new JMenuItem("Weekly Summary");
        JMenuItem monthlySummaryMenuItem = new JMenuItem("Monthly Summary");

        payslipMenuItem.addActionListener(e -> mainFrame.showReports());
        weeklySummaryMenuItem.addActionListener(e -> mainFrame.showReports());
        monthlySummaryMenuItem.addActionListener(e -> mainFrame.showReports());

        reportsMenu.add(payslipMenuItem);
        reportsMenu.add(weeklySummaryMenuItem);
        reportsMenu.add(monthlySummaryMenuItem);

        // Add menu items to "Help" menu
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(
                mainFrame,
                "MotorPH Payroll System " + UIConstants.APP_VERSION + "\nDeveloped by Group 4",
                "About",
                JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutMenuItem);

        // Add menus to the menu bar
        add(fileMenu);
        add(employeeMenu);
        add(payrollMenu);
        add(reportsMenu);
        add(helpMenu);
    }
}

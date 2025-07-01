package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.motorph.controller.EmployeeController;
import com.motorph.controller.PayrollController;
import com.motorph.controller.ReportController;
import com.motorph.util.UIConstants;

/**
 * Main frame for the MotorPH Payroll System.
 * This serves as the container for all panels in the application.
 */
public class MainFrame extends JFrame {
    private final EmployeeController employeeController;
    private final PayrollController payrollController;
    private final ReportController reportController;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private SideNavigationPanel sideNavPanel;
    private HeaderPanel headerPanel; // Panels
    private DashboardPanel dashboardPanel;
    private EmployeeManagementPanel employeePanel;
    private ModernEmployeeListPanel employeeListPanel;
    private PayrollPanel payrollPanel;
    private ReportsPanel reportsPanel;

    /**
     * Constructor for the main frame
     */
    public MainFrame(EmployeeController employeeController,
            PayrollController payrollController,
            ReportController reportController) {
        super(UIConstants.APP_TITLE);

        this.employeeController = employeeController;
        this.payrollController = payrollController;
        this.reportController = reportController;

        initUI();
    }

    /**
     * Initialize the UI components
     */
    private void initUI() {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }

        // Set up the JFrame
        setSize(1200, 800); // Increased size for side navigation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Initialize side navigation and header
        sideNavPanel = new SideNavigationPanel(this);
        headerPanel = new HeaderPanel();

        // Set up the card layout for main content
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(UIConstants.BACKGROUND_COLOR); // Initialize panels
        dashboardPanel = new DashboardPanel(this, employeeController);
        employeePanel = new EmployeeManagementPanel(this, employeeController);
        employeeListPanel = new ModernEmployeeListPanel(this, employeeController);
        payrollPanel = new PayrollPanel(this, payrollController);
        reportsPanel = new ReportsPanel(this, reportController);

        // Add panels to card layout
        cardPanel.add(dashboardPanel, "MainMenu");
        cardPanel.add(employeePanel, "EmployeeManagement");
        cardPanel.add(employeeListPanel, "EmployeeList");
        cardPanel.add(payrollPanel, "PayrollManagement");
        cardPanel.add(reportsPanel, "Reports");// Show the main menu panel initially
        cardLayout.show(cardPanel, "MainMenu");

        // Create main content area with header and content
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(headerPanel, BorderLayout.NORTH);
        mainContentPanel.add(cardPanel, BorderLayout.CENTER);

        // Add components to main frame
        add(sideNavPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    /**
     * Show a specific panel by name
     * 
     * @param panelName The name of the panel to show
     */
    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    /**
     * Show the main menu panel
     */
    public void showMainMenu() {
        cardLayout.show(cardPanel, "MainMenu");
    }

    /**
     * Show the employee management panel
     */
    public void showEmployeeManagement() {
        cardLayout.show(cardPanel, "EmployeeList");
    }

    /**
     * Show the employee list panel (MPHCR-02)
     */
    public void showEmployeeList() {
        cardLayout.show(cardPanel, "EmployeeList");
    }

    /**
     * Show the payroll management panel
     */
    public void showPayrollManagement() {
        cardLayout.show(cardPanel, "PayrollManagement");
    }

    /**
     * Show the reports panel
     */
    public void showReports() {
        cardLayout.show(cardPanel, "Reports");
    }

    /**
     * Handle user logout
     */
    public void logout() {
        // Create authentication controller
        com.motorph.controller.AuthenticationController authController = new com.motorph.controller.AuthenticationController();

        // Perform logout
        boolean logoutSuccess = authController.logout();

        if (logoutSuccess) {
            // Close current window
            this.dispose();

            // Exit application since login is bypassed
            System.exit(0);
        }
    }
}

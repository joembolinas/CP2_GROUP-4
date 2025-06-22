package com.motorph.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
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
    private JPanel cardPanel; // Panels
    private MainMenuPanel mainMenuPanel;
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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the card layout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout); // Initialize panels
        mainMenuPanel = new MainMenuPanel(this);
        employeePanel = new EmployeeManagementPanel(this, employeeController);
        employeeListPanel = new ModernEmployeeListPanel(this, employeeController);
        payrollPanel = new PayrollPanel(this, payrollController);
        reportsPanel = new ReportsPanel(this, reportController);

        // Add panels to card layout
        cardPanel.add(mainMenuPanel, "MainMenu");
        cardPanel.add(employeePanel, "EmployeeManagement");
        cardPanel.add(employeeListPanel, "EmployeeList");
        cardPanel.add(payrollPanel, "PayrollManagement");
        cardPanel.add(reportsPanel, "Reports");

        // Show the main menu panel initially
        cardLayout.show(cardPanel, "MainMenu");

        // Add the card panel to the frame
        add(cardPanel);

        // Initialize menu bar
        setJMenuBar(createMenuBar());
    }

    /**
     * Create the application menu bar
     */
    private JMenuBar createMenuBar() {
        return new ApplicationMenuBar(this, employeeController, payrollController, reportController);
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

            // Show login screen again
            javax.swing.SwingUtilities.invokeLater(() -> {
                com.motorph.Main.showLoginScreen(() -> {
                    try {
                        com.motorph.Main.initializeApplication();
                    } catch (Exception e) {
                        java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
                                java.util.logging.Level.SEVERE, "Failed to restart application after logout", e);
                        System.exit(1);
                    }
                });
            });
        }
    }
}

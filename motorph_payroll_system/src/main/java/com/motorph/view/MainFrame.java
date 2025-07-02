package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.motorph.controller.EmployeeController;
import com.motorph.controller.PayrollController;
import com.motorph.controller.ReportController;
import com.motorph.util.AppConstants;
import com.motorph.util.AppUtils;

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
    private HeaderPanel headerPanel;
    private JPanel sideNavPanel; // Panels
    private Dashboard dashboardPanel;
    private EmployeePanel employeePanel;
    private Payroll payrollPanel;
    private Reports reportsPanel;

    /**
     * Constructor for the main frame
     */
    public MainFrame(EmployeeController employeeController,
            PayrollController payrollController,
            ReportController reportController) {
        super(AppConstants.APP_TITLE);

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
        setSize(1400, 900); // Increased size for side navigation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize header
        headerPanel = new HeaderPanel();

        // Initialize side navigation
        sideNavPanel = createSideNavigationPanel();

        // Set up the card layout for main content
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        // Initialize panels
        dashboardPanel = new Dashboard(this, employeeController);
        employeePanel = new EmployeePanel(this, employeeController);
        payrollPanel = new Payroll(this, payrollController);
        reportsPanel = new Reports(this, reportController);

        // Add panels to card layout
        cardPanel.add(dashboardPanel, "MainMenu");
        cardPanel.add(employeePanel, "EmployeeList");
        cardPanel.add(payrollPanel, "PayrollManagement");
        cardPanel.add(reportsPanel, "Reports");

        // Show the main menu panel initially
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
     * Create the side navigation panel
     */
    private JPanel createSideNavigationPanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(AppConstants.SIDEBAR_BACKGROUND);
        sidePanel.setPreferredSize(new Dimension(280, 0));
        sidePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(148, 163, 184))); // lighter border
                                                                                                    // for dark sidebar

        // Logo/Brand section
        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        brandPanel.setBackground(AppConstants.SIDEBAR_BACKGROUND);
        brandPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel brandLabel = new JLabel("MotorPH");
        brandLabel.setFont(AppConstants.TITLE_FONT);
        brandLabel.setForeground(Color.WHITE); // White text on dark sidebar
        brandPanel.add(brandLabel);

        sidePanel.add(brandPanel);
        sidePanel.add(Box.createVerticalStrut(20));

        // Navigation buttons
        addNavButton(sidePanel, "🏠 Dashboard", "MainMenu");
        addNavButton(sidePanel, "👥 Employee Management", "EmployeeList");
        addNavButton(sidePanel, "💰 Payroll Management", "PayrollManagement");
        addNavButton(sidePanel, "📊 Reports", "Reports");

        // Add flexible space to push logout button to bottom
        sidePanel.add(Box.createVerticalGlue());

        // Logout button
        JButton logoutBtn = AppUtils.createDangerButton("🚪 Logout");
        logoutBtn.addActionListener(e -> logout());
        JPanel logoutPanel = new JPanel(new FlowLayout());
        logoutPanel.setBackground(AppConstants.SIDEBAR_BACKGROUND);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
        logoutPanel.add(logoutBtn);
        sidePanel.add(logoutPanel);

        return sidePanel;
    }

    /**
     * Add a navigation button to the side panel
     */
    private void addNavButton(JPanel parent, String text, String panelName) {
        JButton button = AppUtils.createNavigationButton(text);
        if (panelName != null) {
            button.addActionListener(e -> showPanel(panelName));
        }

        // Customize button for navigation
        button.setPreferredSize(new Dimension(240, 45));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(AppConstants.SIDEBAR_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(button);
        parent.add(buttonPanel);
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

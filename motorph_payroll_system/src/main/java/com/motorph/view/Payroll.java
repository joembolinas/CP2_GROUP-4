package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.motorph.controller.PayrollController;
import com.motorph.util.AppConstants;
import com.motorph.view.dialog.DateRangeDialog;
import com.motorph.view.dialog.EmployeeNumberInputDialog;
import com.motorph.view.dialog.PayslipDialog;

/**
 * Panel for payroll management functions.
 */
public class Payroll extends JPanel {

    private final MainFrame mainFrame;
    private final PayrollController payrollController;

    /**
     * Constructor for the payroll panel
     */
    public Payroll(MainFrame mainFrame, PayrollController payrollController) {
        this.mainFrame = mainFrame;
        this.payrollController = payrollController;
        initPanel();
    }

    /**
     * Initialize the panel
     */
    private void initPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(AppConstants.BACKGROUND_COLOR);

        // North panel with title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(AppConstants.BACKGROUND_COLOR);
        JLabel titleLabel = new JLabel("Payroll Management", SwingConstants.CENTER);
        titleLabel.setFont(AppConstants.TITLE_FONT);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Center panel with buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        JButton generatePayrollButton = createStyledButton("Generate Payroll");
        JButton customPayrollButton = createStyledButton("Custom Payroll");
        JButton backButton = createStyledButton("Back to Main Menu");

        buttonPanel.add(generatePayrollButton);
        buttonPanel.add(customPayrollButton);
        buttonPanel.add(backButton);

        // Add to a wrapper panel with margins
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Add action listeners for buttons
        generatePayrollButton.addActionListener(e -> generatePayroll());
        customPayrollButton.addActionListener(e -> generateEmployeePayslip());
        backButton.addActionListener(e -> mainFrame.showMainMenu());
    }

    /**
     * Create a styled button with consistent look and feel
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(AppConstants.BUTTON_COLOR);
        button.setFont(AppConstants.NORMAL_FONT);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Generate payroll for all employees
     */
    private void generatePayroll() {
        // Show date range dialog
        DateRangeDialog dateDialog = new DateRangeDialog(mainFrame, "Select Pay Period");
        dateDialog.setVisible(true);

        if (!dateDialog.isConfirmed()) {
            return; // User canceled
        }

        LocalDate startDate = dateDialog.getStartDate();
        LocalDate endDate = dateDialog.getEndDate();

        try {
            // Generate payroll
            var payslips = payrollController.generatePayroll(startDate, endDate);

            if (payslips.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame,
                        "No payslips generated for the specified period.",
                        "Payroll Report",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Prepare data for the table
            String[] columnNames = { "Emp#", "Name", "Reg Hours", "OT Hours", "Hourly Rate", "Gross Pay", "Net Pay" };
            Object[][] data = new Object[payslips.size()][7];

            for (int i = 0; i < payslips.size(); i++) {
                var payslip = payslips.get(i);
                var employee = payslip.getEmployee();

                data[i][0] = employee.getEmployeeId();
                data[i][1] = employee.getFullName();
                data[i][2] = String.format("%.2f", payslip.getRegularHours());
                data[i][3] = String.format("%.2f", payslip.getOvertimeHours());
                data[i][4] = String.format("%.2f", employee.getHourlyRate());
                data[i][5] = String.format("%.2f", payslip.getGrossPay());
                data[i][6] = String.format("%.2f", payslip.getNetPay());
            }

            // Create the table
            JTable table = new JTable(data, columnNames);
            table.setFillsViewportHeight(true);

            // Show in a dialog
            JOptionPane.showMessageDialog(mainFrame,
                    new JScrollPane(table),
                    "Payroll Report",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(mainFrame,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Error generating payroll: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Generate payslip for a specific employee
     */
    private void generateEmployeePayslip() {
        // Show employee number input dialog
        EmployeeNumberInputDialog employeeDialog = new EmployeeNumberInputDialog(mainFrame, "Generate Payslip");
        employeeDialog.setVisible(true);

        if (!employeeDialog.isConfirmed()) {
            return; // User canceled
        }

        int employeeId = employeeDialog.getEmployeeNumber();

        // Show date range dialog
        DateRangeDialog dateDialog = new DateRangeDialog(mainFrame, "Select Pay Period");
        dateDialog.setVisible(true);

        if (!dateDialog.isConfirmed()) {
            return; // User canceled
        }

        LocalDate startDate = dateDialog.getStartDate();
        LocalDate endDate = dateDialog.getEndDate();

        try {
            // Generate payslip
            var payslip = payrollController.generatePayslip(employeeId, startDate, endDate);

            // Display the payslip
            new PayslipDialog(mainFrame, payslip, "EMPLOYEE PAYSLIP").setVisible(true);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(mainFrame,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Error generating payslip: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.motorph.controller.ReportController;
import com.motorph.util.AppConstants;
import com.motorph.view.dialog.DateRangeDialog;
import com.motorph.view.dialog.EmployeeNumberInputDialog;
import com.motorph.view.dialog.PayslipDialog;

/**
 * Panel for report generation functions.
 */
public class Reports extends JPanel {

    private final MainFrame mainFrame;
    private final ReportController reportController;

    /**
     * Constructor for the reports panel
     */
    public Reports(MainFrame mainFrame, ReportController reportController) {
        this.mainFrame = mainFrame;
        this.reportController = reportController;
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
        JLabel titleLabel = new JLabel("Reports", SwingConstants.CENTER);
        titleLabel.setFont(AppConstants.TITLE_FONT);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Center panel with buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        JButton payslipButton = createStyledButton("Payslip");
        JButton weeklySummaryButton = createStyledButton("Weekly Summary");
        JButton monthlySummaryButton = createStyledButton("Monthly Summary");
        JButton backButton = createStyledButton("Back to Main Menu");

        buttonPanel.add(payslipButton);
        buttonPanel.add(weeklySummaryButton);
        buttonPanel.add(monthlySummaryButton);
        buttonPanel.add(backButton);

        // Add to a wrapper panel with margins
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Add action listeners for buttons
        payslipButton.addActionListener(e -> generatePayslipReport());
        weeklySummaryButton.addActionListener(e -> generateSummaryReport("Weekly"));
        monthlySummaryButton.addActionListener(e -> generateSummaryReport("Monthly"));
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
     * Generate a payslip report for a specific employee
     */
    private void generatePayslipReport() {
        // Show employee number input dialog
        EmployeeNumberInputDialog employeeDialog = new EmployeeNumberInputDialog(mainFrame, "Payslip Report");
        employeeDialog.setVisible(true);

        if (!employeeDialog.isConfirmed()) {
            return; // User canceled
        }

        int employeeId = employeeDialog.getEmployeeNumber();

        // Show date range dialog
        DateRangeDialog dateDialog = new DateRangeDialog(mainFrame, "Select Report Period");
        dateDialog.setVisible(true);

        if (!dateDialog.isConfirmed()) {
            return; // User canceled
        }

        LocalDate startDate = dateDialog.getStartDate();
        LocalDate endDate = dateDialog.getEndDate();

        try {
            // Generate payslip report
            var payslip = reportController.generatePayslipReport(employeeId, startDate, endDate);

            // Display the payslip
            new PayslipDialog(mainFrame, payslip, "PAYSLIP REPORT").setVisible(true);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(mainFrame,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Error generating payslip report: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Generate a summary report
     * 
     * @param reportType The type of report ("Weekly" or "Monthly")
     */
    private void generateSummaryReport(String reportType) {
        // Show date range dialog
        DateRangeDialog dateDialog = new DateRangeDialog(mainFrame, "Select " + reportType + " Report Period");
        dateDialog.setVisible(true);

        if (!dateDialog.isConfirmed()) {
            return; // User canceled
        }

        LocalDate startDate = dateDialog.getStartDate();
        LocalDate endDate = dateDialog.getEndDate();

        try {
            // Generate summary report
            var summaryData = reportController.generateSummaryReport(reportType, startDate, endDate);

            if (summaryData.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame,
                        "No data available for the specified period.",
                        reportType + " Summary Report",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Prepare data for the table
            String[] columnNames = { "Emp#", "Name", "Total Hours", "Gross Pay", "Net Pay" };
            Object[][] data = new Object[summaryData.size()][5];

            int i = 0;
            for (Map<String, Object> row : summaryData) {
                data[i][0] = row.get("employeeId");
                data[i][1] = row.get("name");
                data[i][2] = String.format("%.2f", row.get("totalHours"));
                data[i][3] = String.format("%.2f", row.get("grossPay"));
                data[i][4] = String.format("%.2f", row.get("netPay"));
                i++;
            }

            // Create the table
            JTable table = new JTable(data, columnNames);
            table.setFillsViewportHeight(true);

            // Show in a dialog
            JOptionPane.showMessageDialog(mainFrame,
                    new JScrollPane(table),
                    reportType + " Summary Report",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(mainFrame,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Error generating " + reportType.toLowerCase() + " summary report: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

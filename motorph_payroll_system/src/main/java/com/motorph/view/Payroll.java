package com.motorph.view;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.motorph.controller.PayrollController;
import com.motorph.model.PaySlip;
import com.motorph.model.PayrollRun;
import com.motorph.model.PayrollStatus;
import com.motorph.util.AppConstants;
import com.motorph.view.dialog.DateRangeDialog;

/**
 * 🎯 Professional Payroll Management Panel
 * 
 * Features:
 * - Modern workflow-based UI
 * - CSV upload and validation
 * - Professional payroll table with review capabilities
 * - Approval workflow with POST to accounting
 * - Comprehensive reporting
 * - Real-time status tracking
 */
public class Payroll extends JPanel {

    private final MainFrame mainFrame;
    private final PayrollController payrollController;
    private final NumberFormat currencyFormat;

    // UI Components
    private JPanel headerPanel;
    private JPanel workflowPanel;
    private JPanel tablePanel;
    private JPanel summaryPanel;
    private JPanel controlPanel;

    private JLabel statusLabel;
    private JLabel periodLabel;
    private JLabel totalsLabel;
    private JProgressBar progressBar;

    private JTable payrollTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;

    // Workflow buttons
    private JButton uploadCSVButton;
    private JButton calculateButton;
    private JButton reviewButton;
    private JButton approveButton;
    private JButton postToAccountingButton;
    private JButton generateReportButton;
    private JButton backButton;
    private JButton refreshButton;

    // Current payroll run state
    private PayrollRun currentPayrollRun;
    private List<PaySlip> currentPayslips;

    // Table columns
    private static final String[] COLUMN_NAMES = {
            "Emp ID", "Employee Name", "Regular Hours", "OT Hours",
            "Gross Pay", "SSS", "PhilHealth", "Pag-IBIG", "Tax",
            "Other Deductions", "Net Pay", "Status"
    };

    public Payroll(MainFrame mainFrame, PayrollController payrollController) {
        this.mainFrame = mainFrame;
        this.payrollController = payrollController;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        this.currentPayslips = new ArrayList<>();

        initializePayrollRun();
        initComponents();
        layoutComponents();
        setupEventHandlers();
        updateUIState();
    }

    private void initializePayrollRun() {
        this.currentPayrollRun = new PayrollRun();
        this.currentPayrollRun.setStatus(PayrollStatus.DRAFT);
        this.currentPayrollRun.setCreatedDate(LocalDate.now());
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Initialize panels
        createHeaderPanel();
        createWorkflowPanel();
        createTablePanel();
        createSummaryPanel();
        createControlPanel();
    }

    private void createHeaderPanel() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR),
                new EmptyBorder(15, 20, 15, 20)));

        // Title and status
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(AppConstants.BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("💼 Payroll Management System");
        titleLabel.setFont(AppConstants.TITLE_FONT);
        titleLabel.setForeground(AppConstants.PRIMARY_COLOR);

        statusLabel = new JLabel("Status: " + currentPayrollRun.getStatus().getDisplayName());
        statusLabel.setFont(AppConstants.NORMAL_FONT);
        statusLabel.setForeground(getStatusColor(currentPayrollRun.getStatus()));

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createHorizontalStrut(20));
        titlePanel.add(statusLabel);

        // Period and progress
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        periodLabel = new JLabel("Period: Not Selected");
        periodLabel.setFont(AppConstants.SMALL_FONT);
        periodLabel.setForeground(AppConstants.TEXT_COLOR);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Ready");
        progressBar.setPreferredSize(new Dimension(200, 25));

        infoPanel.add(periodLabel);
        infoPanel.add(Box.createHorizontalStrut(15));
        infoPanel.add(progressBar);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(infoPanel, BorderLayout.EAST);
    }

    private void createWorkflowPanel() {
        workflowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        workflowPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        workflowPanel.setBorder(BorderFactory.createTitledBorder("Payroll Workflow"));

        uploadCSVButton = createWorkflowButton("📁 Upload CSV", "Upload attendance data");
        calculateButton = createWorkflowButton("🧮 Calculate", "Calculate payroll");
        reviewButton = createWorkflowButton("👁 Review", "Review calculations");
        approveButton = createWorkflowButton("✅ Approve", "Approve payroll");
        postToAccountingButton = createWorkflowButton("📊 POST", "Post to accounting");
        generateReportButton = createWorkflowButton("📄 Report", "Generate reports");
        refreshButton = createWorkflowButton("🔄 Refresh", "Refresh data");

        workflowPanel.add(uploadCSVButton);
        workflowPanel.add(createSeparator());
        workflowPanel.add(calculateButton);
        workflowPanel.add(createSeparator());
        workflowPanel.add(reviewButton);
        workflowPanel.add(createSeparator());
        workflowPanel.add(approveButton);
        workflowPanel.add(createSeparator());
        workflowPanel.add(postToAccountingButton);
        workflowPanel.add(createSeparator());
        workflowPanel.add(generateReportButton);
        workflowPanel.add(Box.createHorizontalStrut(20));
        workflowPanel.add(refreshButton);
    }

    private JButton createWorkflowButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(AppConstants.SMALL_FONT);
        button.setToolTipText(tooltip);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(110, 35));
        return button;
    }

    private Component createSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(2, 25));
        return separator;
    }

    private void createTablePanel() {
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(AppConstants.BACKGROUND_COLOR);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Payroll Data"));

        // Create table model
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only allow editing of deduction columns when in review status
                return currentPayrollRun.getStatus() == PayrollStatus.REVIEW &&
                        (column >= 5 && column <= 9); // Deduction columns
            }

            @Override
            public Class<?> getColumnClass(int column) {
                if (column >= 2 && column <= 10)
                    return Double.class;
                return String.class;
            }
        };

        payrollTable = new JTable(tableModel);
        setupTable();

        tableScrollPane = new JScrollPane(payrollTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 400));
        tableScrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
    }

    private void setupTable() {
        payrollTable.setFont(AppConstants.SMALL_FONT);
        payrollTable.setRowHeight(25);
        payrollTable.setGridColor(AppConstants.BORDER_COLOR);
        payrollTable.setSelectionBackground(AppConstants.SELECTION_COLOR);
        payrollTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Set column widths
        int[] columnWidths = { 60, 150, 80, 80, 100, 80, 80, 80, 80, 100, 100, 80 };
        for (int i = 0; i < columnWidths.length && i < payrollTable.getColumnCount(); i++) {
            TableColumn column = payrollTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        // Custom renderers
        payrollTable.setDefaultRenderer(Double.class, new CurrencyRenderer());
        payrollTable.getColumnModel().getColumn(11).setCellRenderer(new StatusRenderer());

        // Add change listener for editing
        tableModel.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                updateSummary();
            }
        });
    }

    private void createSummaryPanel() {
        summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR),
                new EmptyBorder(10, 15, 10, 15)));

        totalsLabel = new JLabel("No data loaded");
        totalsLabel.setFont(AppConstants.NORMAL_FONT);
        totalsLabel.setForeground(AppConstants.TEXT_COLOR);

        summaryPanel.add(totalsLabel, BorderLayout.WEST);
    }

    private void createControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        controlPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        backButton = new JButton("← Back to Menu");
        backButton.setFont(AppConstants.NORMAL_FONT);
        backButton.setFocusPainted(false);

        controlPanel.add(backButton);
    }

    private void layoutComponents() {
        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        centerPanel.add(workflowPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        uploadCSVButton.addActionListener(e -> uploadCSVData());
        calculateButton.addActionListener(e -> calculatePayroll());
        reviewButton.addActionListener(e -> enterReviewMode());
        approveButton.addActionListener(e -> approvePayroll());
        postToAccountingButton.addActionListener(e -> postToAccounting());
        generateReportButton.addActionListener(e -> generateReports());
        refreshButton.addActionListener(e -> refreshData());
        backButton.addActionListener(e -> mainFrame.showMainMenu());
    }

    private void uploadCSVData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files", "csv"));
        fileChooser.setDialogTitle("Select Attendance CSV File");

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            SwingUtilities.invokeLater(() -> {
                progressBar.setString("Uploading...");
                progressBar.setIndeterminate(true);

                try {
                    // Validate and load CSV
                    validateAndLoadCSV(selectedFile);
                    currentPayrollRun.setStatus(PayrollStatus.DATA_LOADED);
                    updateProgress(20, "Data uploaded successfully");
                    updateUIState();

                    JOptionPane.showMessageDialog(this,
                            "CSV data uploaded successfully!\nReady for payroll calculation.",
                            "Upload Complete",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error uploading CSV: " + ex.getMessage(),
                            "Upload Error",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    progressBar.setIndeterminate(false);
                }
            });
        }
    }

    private void validateAndLoadCSV(File csvFile) throws IOException {
        List<String[]> csvData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] values = line.split(",");
                if (values.length >= 3) { // Minimum required columns
                    csvData.add(values);
                }
            }
        }

        if (csvData.isEmpty()) {
            throw new IOException("No valid data found in CSV file");
        }

        currentPayrollRun.setEmployeeCount(csvData.size());
        currentPayrollRun.setDataFile(csvFile.getName());
    }

    private void calculatePayroll() {
        if (currentPayrollRun.getStatus() != PayrollStatus.DATA_LOADED) {
            JOptionPane.showMessageDialog(this,
                    "Please upload CSV data first.",
                    "Data Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Show date range dialog
        DateRangeDialog dateDialog = new DateRangeDialog(mainFrame, "Select Pay Period");
        dateDialog.setVisible(true);

        if (!dateDialog.isConfirmed()) {
            return;
        }

        LocalDate startDate = dateDialog.getStartDate();
        LocalDate endDate = dateDialog.getEndDate();

        SwingUtilities.invokeLater(() -> {
            progressBar.setString("Calculating payroll...");
            progressBar.setIndeterminate(true);

            try {
                // Generate payroll
                currentPayslips = payrollController.generatePayroll(startDate, endDate);

                if (currentPayslips.isEmpty()) {
                    throw new RuntimeException("No payroll data generated");
                }

                // Update UI
                populateTable();
                updateSummary();
                updatePeriodInfo(startDate, endDate);

                currentPayrollRun.setStatus(PayrollStatus.CALCULATED);
                currentPayrollRun.setStartDate(startDate);
                currentPayrollRun.setEndDate(endDate);

                updateProgress(50, "Payroll calculated");
                updateUIState();

                JOptionPane.showMessageDialog(this,
                        String.format("Payroll calculated for %d employees\nPeriod: %s to %s",
                                currentPayslips.size(),
                                startDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                                endDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))),
                        "Calculation Complete",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error calculating payroll: " + ex.getMessage(),
                        "Calculation Error",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                progressBar.setIndeterminate(false);
            }
        });
    }

    private void populateTable() {
        tableModel.setRowCount(0);

        for (PaySlip paySlip : currentPayslips) {
            Object[] row = new Object[COLUMN_NAMES.length];
            row[0] = paySlip.getEmployee().getEmployeeId();
            row[1] = paySlip.getEmployee().getFullName();
            row[2] = paySlip.getRegularHours();
            row[3] = paySlip.getOvertimeHours();
            row[4] = paySlip.getGrossPay();
            row[5] = getDeduction(paySlip, "SSS");
            row[6] = getDeduction(paySlip, "PhilHealth");
            row[7] = getDeduction(paySlip, "Pag-IBIG");
            row[8] = getDeduction(paySlip, "Tax");
            row[9] = getDeduction(paySlip, "Other");
            row[10] = paySlip.getNetPay();
            row[11] = "Calculated";

            tableModel.addRow(row);
        }
    }

    private double getDeduction(PaySlip paySlip, String type) {
        // Get deduction from payslip based on type
        // This would need to be implemented based on your PaySlip structure
        return paySlip.getTotalDeductions() / 5; // Simplified for now
    }

    private void enterReviewMode() {
        if (currentPayrollRun.getStatus() != PayrollStatus.CALCULATED) {
            JOptionPane.showMessageDialog(this,
                    "Please calculate payroll first.",
                    "Calculation Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        currentPayrollRun.setStatus(PayrollStatus.REVIEW);
        updateProgress(70, "In review mode");
        updateUIState();

        JOptionPane.showMessageDialog(this,
                "Review mode activated.\nYou can now edit deduction values in the table.\nClick 'Approve' when ready.",
                "Review Mode",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void approvePayroll() {
        if (currentPayrollRun.getStatus() != PayrollStatus.REVIEW) {
            JOptionPane.showMessageDialog(this,
                    "Please review the payroll first.",
                    "Review Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to approve this payroll?\nThis action cannot be undone.",
                "Confirm Approval",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            currentPayrollRun.setStatus(PayrollStatus.APPROVED);
            currentPayrollRun.setApprovedDate(LocalDate.now());
            updateProgress(85, "Payroll approved");
            updateUIState();

            JOptionPane.showMessageDialog(this,
                    "Payroll approved successfully!\nReady for posting to accounting.",
                    "Approval Complete",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void postToAccounting() {
        if (currentPayrollRun.getStatus() != PayrollStatus.APPROVED) {
            JOptionPane.showMessageDialog(this,
                    "Please approve the payroll first.",
                    "Approval Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Post payroll to accounting system?\nThis will finalize the payroll run.",
                "Confirm POST",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> {
                progressBar.setString("Posting to accounting...");
                progressBar.setIndeterminate(true);

                try {
                    // Simulate posting process
                    Thread.sleep(2000);

                    // Generate accounting entries
                    generateAccountingEntries();

                    currentPayrollRun.setStatus(PayrollStatus.POSTED);
                    currentPayrollRun.setPostedDate(LocalDate.now());
                    updateProgress(100, "Posted to accounting");
                    updateUIState();

                    JOptionPane.showMessageDialog(this,
                            "Payroll posted to accounting successfully!\n" +
                                    "Accounting entries have been generated.\n" +
                                    "Payroll run is now complete.",
                            "POST Complete",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error posting to accounting: " + ex.getMessage(),
                            "POST Error",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    progressBar.setIndeterminate(false);
                }
            });
        }
    }

    private void generateAccountingEntries() throws IOException {
        String fileName = String.format("accounting_entries_%s.txt",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("=== MOTORPH PAYROLL ACCOUNTING ENTRIES ===\n\n");
            writer.write("Period: " + currentPayrollRun.getStartDate() + " to " +
                    currentPayrollRun.getEndDate() + "\n");
            writer.write("Posted: " + LocalDate.now() + "\n");
            writer.write("Employees: " + currentPayslips.size() + "\n\n");

            // Calculate totals for journal entries
            double totalGross = currentPayslips.stream().mapToDouble(PaySlip::getGrossPay).sum();
            double totalDeductions = currentPayslips.stream().mapToDouble(PaySlip::getTotalDeductions).sum();
            double totalNet = currentPayslips.stream().mapToDouble(PaySlip::getNetPay).sum();

            writer.write("JOURNAL ENTRIES:\n");
            writer.write("DR Payroll Expense: " + currencyFormat.format(totalGross) + "\n");
            writer.write("CR Employee Payable: " + currencyFormat.format(totalNet) + "\n");
            writer.write("CR Payroll Deductions Payable: " + currencyFormat.format(totalDeductions) + "\n\n");

            writer.write("DETAILED BREAKDOWN:\n");
            for (PaySlip paySlip : currentPayslips) {
                writer.write(String.format("Emp %d - %s: Gross=%s, Deductions=%s, Net=%s\n",
                        paySlip.getEmployee().getEmployeeId(),
                        paySlip.getEmployee().getFullName(),
                        currencyFormat.format(paySlip.getGrossPay()),
                        currencyFormat.format(paySlip.getTotalDeductions()),
                        currencyFormat.format(paySlip.getNetPay())));
            }
        }
    }

    private void generateReports() {
        if (currentPayrollRun.getStatus() == PayrollStatus.DRAFT) {
            JOptionPane.showMessageDialog(this,
                    "No payroll data available for reporting.",
                    "No Data",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String reportFileName = String.format("payroll_report_%s.txt",
                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));

            try (FileWriter writer = new FileWriter(reportFileName)) {
                writer.write("=== MOTORPH PAYROLL REPORT ===\n\n");
                writer.write("Generated: " + LocalDate.now() + "\n");
                writer.write("Status: " + currentPayrollRun.getStatus().getDisplayName() + "\n");

                if (currentPayrollRun.getStartDate() != null) {
                    writer.write("Period: " + currentPayrollRun.getStartDate() + " to " +
                            currentPayrollRun.getEndDate() + "\n");
                }

                writer.write("Employees: " + currentPayslips.size() + "\n\n");

                if (!currentPayslips.isEmpty()) {
                    double totalGross = currentPayslips.stream().mapToDouble(PaySlip::getGrossPay).sum();
                    double totalNet = currentPayslips.stream().mapToDouble(PaySlip::getNetPay).sum();
                    double totalDeductions = currentPayslips.stream().mapToDouble(PaySlip::getTotalDeductions).sum();

                    writer.write("SUMMARY:\n");
                    writer.write("Total Gross Pay: " + currencyFormat.format(totalGross) + "\n");
                    writer.write("Total Deductions: " + currencyFormat.format(totalDeductions) + "\n");
                    writer.write("Total Net Pay: " + currencyFormat.format(totalNet) + "\n\n");

                    writer.write("EMPLOYEE DETAILS:\n");
                    writer.write(String.format("%-6s %-25s %-12s %-12s %-12s %-12s\n",
                            "EmpID", "Name", "Regular Hrs", "OT Hrs", "Gross Pay", "Net Pay"));
                    writer.write("=".repeat(85) + "\n");

                    for (PaySlip paySlip : currentPayslips) {
                        writer.write(String.format("%-6d %-25s %-12.2f %-12.2f %-12s %-12s\n",
                                paySlip.getEmployee().getEmployeeId(),
                                paySlip.getEmployee().getFullName(),
                                paySlip.getRegularHours(),
                                paySlip.getOvertimeHours(),
                                currencyFormat.format(paySlip.getGrossPay()),
                                currencyFormat.format(paySlip.getNetPay())));
                    }
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Report generated successfully!\nFile: " + reportFileName,
                    "Report Generated",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error generating report: " + ex.getMessage(),
                    "Report Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshData() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "This will reload all data and reset the current session.\nContinue?",
                "Confirm Refresh",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Reset everything
            tableModel.setRowCount(0);
            currentPayslips.clear();
            initializePayrollRun();
            updateProgress(0, "Ready");
            updateUIState();
            updateSummary();
            periodLabel.setText("Period: Not Selected");
        }
    }

    private void updateUIState() {
        PayrollStatus status = currentPayrollRun.getStatus();

        // Update status label
        statusLabel.setText("Status: " + status.getDisplayName());
        statusLabel.setForeground(getStatusColor(status));

        // Enable/disable buttons based on status
        uploadCSVButton.setEnabled(status == PayrollStatus.DRAFT);
        calculateButton.setEnabled(status == PayrollStatus.DATA_LOADED);
        reviewButton.setEnabled(status == PayrollStatus.CALCULATED);
        approveButton.setEnabled(status == PayrollStatus.REVIEW);
        postToAccountingButton.setEnabled(status == PayrollStatus.APPROVED);
        generateReportButton.setEnabled(status != PayrollStatus.DRAFT);

        // Update table editability
        payrollTable.repaint();
    }

    private Color getStatusColor(PayrollStatus status) {
        switch (status) {
            case DRAFT:
                return AppConstants.PAYROLL_DRAFT_COLOR;
            case DATA_LOADED:
                return AppConstants.PAYROLL_LOADED_COLOR;
            case CALCULATED:
                return AppConstants.PAYROLL_CALCULATED_COLOR;
            case REVIEW:
                return AppConstants.PAYROLL_REVIEW_COLOR;
            case APPROVED:
                return AppConstants.PAYROLL_APPROVED_COLOR;
            case POSTED:
                return AppConstants.PAYROLL_POSTED_COLOR;
            default:
                return AppConstants.TEXT_COLOR;
        }
    }

    private void updateProgress(int value, String text) {
        progressBar.setValue(value);
        progressBar.setString(text);
    }

    private void updatePeriodInfo(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        periodLabel.setText(String.format("Period: %s - %s",
                startDate.format(formatter), endDate.format(formatter)));
    }

    private void updateSummary() {
        if (currentPayslips.isEmpty()) {
            totalsLabel.setText("No data loaded");
            return;
        }

        int employeeCount = currentPayslips.size();
        double totalGross = currentPayslips.stream().mapToDouble(PaySlip::getGrossPay).sum();
        double totalNet = currentPayslips.stream().mapToDouble(PaySlip::getNetPay).sum();
        double totalDeductions = currentPayslips.stream().mapToDouble(PaySlip::getTotalDeductions).sum();

        totalsLabel.setText(String.format(
                "Employees: %d | Gross: %s | Deductions: %s | Net: %s",
                employeeCount,
                currencyFormat.format(totalGross),
                currencyFormat.format(totalDeductions),
                currencyFormat.format(totalNet)));
    }

    // Custom table cell renderers
    private class CurrencyRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            if (value instanceof Double) {
                setText(currencyFormat.format((Double) value));
            } else {
                setText("");
            }
            setHorizontalAlignment(SwingConstants.RIGHT);
        }
    }

    private class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setHorizontalAlignment(SwingConstants.CENTER);
            if (!isSelected) {
                setBackground(AppConstants.PAYROLL_CALCULATED_COLOR);
                setForeground(Color.WHITE);
            }

            return this;
        }
    }
}

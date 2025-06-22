package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.motorph.controller.EmployeeController;
import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.motorph.util.UIConstants;
import com.motorph.util.UIUtils;
import com.motorph.view.dialog.EditEmployeeDialog;
import com.motorph.view.dialog.EmployeeDetailsFrame;
import com.motorph.view.dialog.NewEmployeeDialog;

/**
 * Modern Employee List Panel matching HTML mockup design.
 * Features clean table layout, search functionality, and ribbon action bar.
 */
public class ModernEmployeeListPanel extends JPanel {

    private final MainFrame mainFrame;
    private final EmployeeController employeeController;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    // Ribbon action buttons
    private JButton viewAttendanceBtn;
    private JButton viewDetailsBtn;
    private JButton editEmployeeBtn;
    private JButton deleteEmployeeBtn;

    // Selected employee tracking
    private int selectedEmployeeRow = -1;

    private static final String[] COLUMN_NAMES = {
            "Emp. No.", "Name", "Position", "Department", "Status"
    };

    /**
     * Constructor for the employee list panel
     */
    public ModernEmployeeListPanel(MainFrame mainFrame, EmployeeController employeeController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        initPanel();
        loadEmployeeData();
    }

    /**
     * Initialize the modern panel layout
     */
    private void initPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND_COLOR);

        // Main container with proper spacing
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(UIConstants.BACKGROUND_COLOR);
        container.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        // Content card that wraps everything
        JPanel contentCard = UIUtils.createCardPanel();
        contentCard.setLayout(new BorderLayout(0, 24));

        // Header section
        JPanel headerPanel = createHeaderPanel();
        contentCard.add(headerPanel, BorderLayout.NORTH);

        // Table section
        JPanel tablePanel = createTablePanel();
        contentCard.add(tablePanel, BorderLayout.CENTER);

        container.add(contentCard, BorderLayout.CENTER);
        add(container, BorderLayout.CENTER);
    }

    /**
     * Create the header section with navigation and ribbon action bar
     */
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        // Top section with logo/title and navigation
        JPanel topSection = createTopNavigationPanel();

        // Title section
        JPanel titleSection = new JPanel(new BorderLayout());
        titleSection.setBackground(Color.WHITE);
        titleSection.setBorder(BorderFactory.createEmptyBorder(16, 0, 24, 0));

        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setFont(UIConstants.HEADING_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);
        titleSection.add(titleLabel, BorderLayout.WEST);

        // Ribbon action bar
        JPanel ribbonPanel = createRibbonActionBar();

        header.add(topSection, BorderLayout.NORTH);
        header.add(titleSection, BorderLayout.CENTER);
        header.add(ribbonPanel, BorderLayout.SOUTH);

        return header;
    }

    /**
     * Create the top navigation panel with logo and back button
     */
    private JPanel createTopNavigationPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        // Logo placeholder (matching HTML mockup)
        JLabel logoLabel = new JLabel("MotorPH");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setOpaque(true);
        logoLabel.setBackground(new Color(30, 41, 59)); // slate-800
        logoLabel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        // Back button
        JButton backButton = UIUtils.createSecondaryButton("← Back to Main Menu");
        backButton.addActionListener(e -> backToMainMenu());

        topPanel.add(logoLabel, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);

        return topPanel;
    }

    /**
     * Create the modern ribbon action bar
     */
    private JPanel createRibbonActionBar() {
        JPanel ribbonPanel = new JPanel(new BorderLayout());
        ribbonPanel.setBackground(new Color(248, 250, 252)); // bg-slate-100
        ribbonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));

        // Left side - action buttons
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actionsPanel.setBackground(new Color(248, 250, 252));

        // Add New button (always enabled)
        JButton addButton = createRibbonButton("+ Add New", UIConstants.BUTTON_COLOR, true);
        addButton.addActionListener(e -> openNewEmployeeDialog());
        actionsPanel.add(addButton);

        // Separator
        JPanel separator = new JPanel();
        separator.setPreferredSize(new java.awt.Dimension(1, 32));
        separator.setBackground(UIConstants.BORDER_COLOR);
        separator.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        actionsPanel.add(separator);

        // Context-aware buttons (disabled by default)
        viewAttendanceBtn = createRibbonButton("📅 View Attendance", Color.LIGHT_GRAY, false);
        viewDetailsBtn = createRibbonButton("👁 View Details", Color.LIGHT_GRAY, false);
        editEmployeeBtn = createRibbonButton("✏ Edit", Color.LIGHT_GRAY, false);
        deleteEmployeeBtn = createRibbonButton("🗑 Delete", new Color(239, 68, 68), false); // red-500

        // Add action listeners
        viewAttendanceBtn.addActionListener(e -> viewAttendanceForSelected());
        viewDetailsBtn.addActionListener(e -> viewDetailsForSelected());
        editEmployeeBtn.addActionListener(e -> editSelectedEmployee());
        deleteEmployeeBtn.addActionListener(e -> deleteSelectedEmployee());

        actionsPanel.add(viewAttendanceBtn);
        actionsPanel.add(Box.createHorizontalStrut(8));
        actionsPanel.add(viewDetailsBtn);
        actionsPanel.add(Box.createHorizontalStrut(8));
        actionsPanel.add(editEmployeeBtn);
        actionsPanel.add(Box.createHorizontalStrut(8));
        actionsPanel.add(deleteEmployeeBtn);

        // Right side - search
        JPanel searchPanel = createSearchPanel();

        ribbonPanel.add(actionsPanel, BorderLayout.WEST);
        ribbonPanel.add(searchPanel, BorderLayout.EAST);

        return ribbonPanel;
    }

    /**
     * Create a ribbon-style button
     */
    private JButton createRibbonButton(String text, Color bgColor, boolean enabled) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(enabled ? Color.WHITE : Color.GRAY);
        button.setBackground(bgColor);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setEnabled(enabled);
        button.setPreferredSize(new java.awt.Dimension(120, 32));
        button.setCursor(enabled ? new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
                : new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        // Add hover effects for enabled buttons
        if (enabled) {
            Color hoverColor = bgColor.darker();
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (button.isEnabled()) {
                        button.setBackground(hoverColor);
                    }
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (button.isEnabled()) {
                        button.setBackground(bgColor);
                    }
                }
            });
        }

        return button;
    }

    /**
     * Update ribbon button states based on selection
     */
    private void updateRibbonButtons(boolean hasSelection) {
        Color enabledColor = UIConstants.SECONDARY_BUTTON_COLOR;
        Color disabledColor = Color.LIGHT_GRAY;

        viewAttendanceBtn.setEnabled(hasSelection);
        viewDetailsBtn.setEnabled(hasSelection);
        editEmployeeBtn.setEnabled(hasSelection);
        deleteEmployeeBtn.setEnabled(hasSelection);

        viewAttendanceBtn.setBackground(hasSelection ? enabledColor : disabledColor);
        viewDetailsBtn.setBackground(hasSelection ? enabledColor : disabledColor);
        editEmployeeBtn.setBackground(hasSelection ? enabledColor : disabledColor);
        deleteEmployeeBtn.setBackground(hasSelection ? new Color(239, 68, 68) : disabledColor);

        viewAttendanceBtn.setForeground(hasSelection ? Color.WHITE : Color.GRAY);
        viewDetailsBtn.setForeground(hasSelection ? Color.WHITE : Color.GRAY);
        editEmployeeBtn.setForeground(hasSelection ? Color.WHITE : Color.GRAY);
        deleteEmployeeBtn.setForeground(hasSelection ? Color.WHITE : Color.GRAY);
    }

    /**
     * Create modern search panel
     */
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.setBackground(Color.WHITE);

        // Search icon (using text as placeholder for icon)
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 8));

        // Search field
        searchField = new JTextField(25) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Paint rounded background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                // Paint border
                g2.setColor(UIConstants.BORDER_COLOR);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        searchField.setFont(UIConstants.NORMAL_FONT);
        searchField.setBorder(BorderFactory.createEmptyBorder(8, 40, 8, 12));
        searchField.setOpaque(false);
        searchField.setText("Search by name or employee no...");
        searchField.setForeground(UIConstants.TEXT_MUTED);

        // Add focus listeners for placeholder behavior
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("Search by name or employee no...")) {
                    searchField.setText("");
                    searchField.setForeground(UIConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search by name or employee no...");
                    searchField.setForeground(UIConstants.TEXT_MUTED);
                }
            }
        });

        // Container for search field with icon overlay
        JPanel searchContainer = new JPanel();
        searchContainer.setLayout(new BorderLayout());
        searchContainer.setBackground(Color.WHITE);
        searchContainer.add(searchField, BorderLayout.CENTER);
        searchContainer.add(searchIcon, BorderLayout.WEST);

        searchPanel.add(searchContainer);
        return searchPanel;
    }

    /**
     * Create the modern table panel
     */
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        createEmployeeTable();

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    /**
     * Create the modern employee table with row selection
     */
    private void createEmployeeTable() {
        // Create table model (removed Actions column)
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No cells are editable - actions handled by ribbon
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setFont(UIConstants.TABLE_FONT);
        employeeTable.setRowHeight(UIConstants.TABLE_ROW_HEIGHT);
        employeeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.setShowGrid(true);
        employeeTable.setGridColor(UIConstants.TABLE_BORDER_COLOR);
        employeeTable.setIntercellSpacing(new java.awt.Dimension(1, 1));

        // Style table header
        employeeTable.getTableHeader().setBackground(UIConstants.TABLE_HEADER_BACKGROUND);
        employeeTable.getTableHeader().setForeground(UIConstants.TEXT_COLOR);
        employeeTable.getTableHeader().setFont(UIConstants.TABLE_HEADER_FONT);
        employeeTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.BORDER_COLOR));

        // Set column widths (adjusted for no Actions column)
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Emp. No.
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(250); // Name
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Position
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Department
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Status

        // Add row selection listener to enable/disable ribbon buttons
        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = employeeTable.getSelectedRow();
                selectedEmployeeRow = selectedRow;
                updateRibbonButtons(selectedRow >= 0);
            }
        });

        // Set up custom renderers
        setupTableRenderers();
    }

    /**
     * Set up custom table renderers for modern appearance with selection
     * highlighting
     */
    private void setupTableRenderers() {
        // Default renderer for most columns
        DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Selection highlighting (matching HTML mockup)
                if (isSelected) {
                    setBackground(new Color(224, 231, 255)); // Light indigo (e0e7ff)
                    setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(79, 70, 229))); // indigo-600
                } else {
                    // Alternate row colors
                    setBackground(row % 2 == 0 ? Color.WHITE : UIConstants.TABLE_ALT_ROW);
                    setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                }

                setFont(UIConstants.TABLE_FONT);

                return this;
            }
        };

        // Status column renderer with badges
        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));

                // Selection highlighting
                if (isSelected) {
                    panel.setBackground(new Color(224, 231, 255));
                    panel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(79, 70, 229)));
                } else {
                    panel.setBackground(row % 2 == 0 ? Color.WHITE : UIConstants.TABLE_ALT_ROW);
                }

                if (value != null) {
                    String status = value.toString();
                    boolean isActive = "Active".equalsIgnoreCase(status);
                    JLabel statusBadge = UIUtils.createStatusBadge(status, isActive);
                    panel.add(statusBadge);
                }

                return panel;
            }
        };

        // Apply renderers
        for (int i = 0; i < 4; i++) { // Updated for 4 columns (no Actions)
            employeeTable.getColumnModel().getColumn(i).setCellRenderer(defaultRenderer);
        }
        employeeTable.getColumnModel().getColumn(4).setCellRenderer(statusRenderer); // Status column
    }

    /**
     * Load employee data into the table
     */
    private void loadEmployeeData() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Get all employees
        List<Employee> employees = employeeController.getAllEmployees();

        // Add employees to table (updated for new column structure)
        for (Employee employee : employees) {
            Object[] rowData = {
                    employee.getEmployeeId(),
                    employee.getFullName(),
                    employee.getPosition() != null ? employee.getPosition() : "N/A",
                    getDepartmentFromPosition(employee.getPosition()),
                    employee.getStatus() != null ? employee.getStatus() : "Active"
            };
            tableModel.addRow(rowData);
        }
    }

    /**
     * Map employee position to department (based on HTML mockup data)
     */
    private String getDepartmentFromPosition(String position) {
        if (position == null)
            return "N/A";

        String pos = position.toLowerCase();
        if (pos.contains("chief executive") || pos.contains("chief operating")) {
            return "Executive";
        } else if (pos.contains("chief finance") || pos.contains("finance")) {
            return "Finance";
        } else if (pos.contains("hr") || pos.contains("human resources")) {
            return "Human Resources";
        } else if (pos.contains("accounting")) {
            return "Accounting";
        } else if (pos.contains("marketing")) {
            return "Marketing";
        } else if (pos.contains("it") || pos.contains("technology")) {
            return "IT";
        }
        return "General";
    }

    // Ribbon action methods

    /**
     * View attendance for the selected employee
     */
    private void viewAttendanceForSelected() {
        if (selectedEmployeeRow >= 0) {
            try {
                int employeeNumber = Integer.parseInt(tableModel.getValueAt(selectedEmployeeRow, 0).toString());
                Employee employee = employeeController.findEmployeeById(employeeNumber);

                // First try to get records for the last 30 days
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = endDate.minusDays(30);

                List<AttendanceRecord> records = employeeController.getAttendanceRecords(employeeNumber, startDate,
                        endDate);

                // If no recent records found, get all available records for this employee
                if (records.isEmpty()) {
                    // Try to get any available records (broader range)
                    startDate = LocalDate.of(2020, 1, 1); // Start from 2020 to catch historical data
                    endDate = LocalDate.of(2030, 12, 31); // Extend to future
                    records = employeeController.getAttendanceRecords(employeeNumber, startDate, endDate);
                }

                if (records.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "No attendance records found for Employee #" + employeeNumber + ".",
                            "Employee Attendance",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Determine the time period being shown
                String periodDescription;
                if (records.size() > 0) {
                    // Get the date range of actual records
                    LocalDate earliestDate = records.stream()
                            .map(AttendanceRecord::getDate)
                            .min(LocalDate::compareTo)
                            .orElse(LocalDate.now());
                    LocalDate latestDate = records.stream()
                            .map(AttendanceRecord::getDate)
                            .max(LocalDate::compareTo)
                            .orElse(LocalDate.now());

                    if (earliestDate.equals(latestDate)) {
                        periodDescription = "Records for " + earliestDate.toString();
                    } else {
                        periodDescription = "Records from " + earliestDate.toString() + " to " + latestDate.toString();
                    }
                } else {
                    periodDescription = "All Available Records";
                }

                // Prepare data for the attendance table
                String[] columnNames = { "Date", "Time In", "Time Out", "Total Hours", "Status" };
                Object[][] data = new Object[records.size()][5];

                for (int i = 0; i < records.size(); i++) {
                    AttendanceRecord record = records.get(i);
                    data[i][0] = record.getDate().toString();
                    data[i][1] = record.getTimeIn().toString();
                    data[i][2] = record.getTimeOut().toString();
                    data[i][3] = String.format("%.2f", record.getTotalHours());
                    data[i][4] = record.isLate() ? "Late" : "On Time";
                }

                // Create and style the attendance table
                JTable attendanceTable = new JTable(data, columnNames);
                attendanceTable.setFillsViewportHeight(true);
                attendanceTable.setAutoCreateRowSorter(true);

                // Style the table
                attendanceTable.getTableHeader().setBackground(UIConstants.PRIMARY_BUTTON_COLOR);
                attendanceTable.getTableHeader().setForeground(Color.WHITE);
                attendanceTable.getTableHeader().setFont(UIConstants.BUTTON_FONT);

                JScrollPane scrollPane = new JScrollPane(attendanceTable);
                scrollPane.setPreferredSize(new java.awt.Dimension(600, 300));

                // Show in a custom dialog
                JDialog attendanceDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                        "Attendance Records - " + employee.getFullName(), true);
                attendanceDialog.setLayout(new BorderLayout());

                // Add title panel
                JPanel titlePanel = new JPanel();
                titlePanel.setBackground(UIConstants.PRIMARY_BUTTON_COLOR);
                titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                JLabel titleLabel = new JLabel("📅 " + employee.getFullName() + " - " + periodDescription);
                titleLabel.setFont(UIConstants.TITLE_FONT);
                titleLabel.setForeground(Color.WHITE);
                titlePanel.add(titleLabel);

                attendanceDialog.add(titlePanel, BorderLayout.NORTH);
                attendanceDialog.add(scrollPane, BorderLayout.CENTER);

                // Add summary panel
                JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                summaryPanel.setBackground(UIConstants.PANEL_BACKGROUND);
                summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

                // Calculate summary statistics
                int totalRecords = records.size();
                long lateCount = records.stream().mapToLong(r -> r.isLate() ? 1 : 0).sum();
                double totalHours = records.stream().mapToDouble(AttendanceRecord::getTotalHours).sum();
                double avgHours = totalRecords > 0 ? totalHours / totalRecords : 0;

                JLabel summaryLabel = new JLabel(String.format(
                        "Summary: %d records | %d late arrivals | %.1f total hours | %.1f avg hours/day",
                        totalRecords, lateCount, totalHours, avgHours));
                summaryLabel.setFont(UIConstants.SMALL_FONT);
                summaryLabel.setForeground(UIConstants.TEXT_SECONDARY);
                summaryPanel.add(summaryLabel);

                // Add close button panel
                JPanel buttonPanel = new JPanel();
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
                JButton closeButton = UIUtils.createPrimaryButton("Close");
                closeButton.addActionListener(e -> attendanceDialog.dispose());
                buttonPanel.add(closeButton);

                // Combine summary and button panels
                JPanel bottomPanel = new JPanel(new BorderLayout());
                bottomPanel.add(summaryPanel, BorderLayout.CENTER);
                bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

                attendanceDialog.add(bottomPanel, BorderLayout.SOUTH);

                attendanceDialog.pack();
                attendanceDialog.setLocationRelativeTo(this);
                attendanceDialog.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error loading attendance data: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * View details for the selected employee
     */
    private void viewDetailsForSelected() {
        if (selectedEmployeeRow >= 0) {
            viewEmployeeAtRow(selectedEmployeeRow);
        }
    }

    /**
     * Edit the selected employee
     */
    private void editSelectedEmployee() {
        if (selectedEmployeeRow >= 0) {
            editEmployeeAtRow(selectedEmployeeRow);
        }
    }

    /**
     * Delete the selected employee
     */
    private void deleteSelectedEmployee() {
        if (selectedEmployeeRow >= 0) {
            deleteEmployeeAtRow(selectedEmployeeRow);
        }
    }

    /**
     * Open new employee dialog
     */
    private void openNewEmployeeDialog() {
        NewEmployeeDialog dialog = new NewEmployeeDialog(mainFrame, employeeController);
        dialog.setVisible(true);
        if (dialog.isEmployeeAdded()) {
            loadEmployeeData(); // Refresh the table
            JOptionPane.showMessageDialog(this,
                    "New employee added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Navigate back to the main menu
     */
    private void backToMainMenu() {
        mainFrame.showMainMenu();
    }

    /**
     * View employee details for the specified row
     */
    private void viewEmployeeAtRow(int row) {
        try {
            int employeeNumber = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            Employee employee = employeeController.findEmployeeById(employeeNumber);
            new EmployeeDetailsFrame(mainFrame, employee).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error viewing employee: " + e.getMessage());
        }
    }

    /**
     * Edit employee at the specified row
     */
    private void editEmployeeAtRow(int row) {
        try {
            int employeeNumber = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            Employee employee = employeeController.findEmployeeById(employeeNumber);

            if (employee != null) {
                EditEmployeeDialog dialog = new EditEmployeeDialog(mainFrame, employeeController, employee);
                dialog.setVisible(true);

                if (dialog.isEmployeeUpdated()) {
                    loadEmployeeData(); // Refresh the table to show updated data
                    JOptionPane.showMessageDialog(this,
                            "Employee information updated successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Employee not found: ID " + employeeNumber,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading employee for editing: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delete employee at the specified row
     */
    private void deleteEmployeeAtRow(int row) {
        try {
            int employeeNumber = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            Employee employee = employeeController.findEmployeeById(employeeNumber);

            if (employee != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete employee:\n" +
                                employee.getFullName() + " (ID: " + employeeNumber + ")?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    // TODO: Implement actual delete functionality when available
                    JOptionPane.showMessageDialog(this,
                            "Delete functionality will be implemented in a future update.",
                            "Feature Coming Soon",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Employee not found: ID " + employeeNumber,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error accessing employee data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

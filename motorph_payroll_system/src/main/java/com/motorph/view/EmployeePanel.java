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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
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
import com.motorph.model.Employee;
import com.motorph.util.AppConstants;
import com.motorph.util.AppUtils;
import com.motorph.view.dialog.AttendanceViewerDialog;
import com.motorph.view.dialog.EmployeeDialog;

/**
 * Employee Management Panel - handles employee list display and CRUD
 * operations.
 * Renamed from ModernEmployeeListPanel for better naming convention.
 */
public class EmployeePanel extends JPanel {

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

    // after mo ma populate ng tamang column names, punta ka sa loadEmployeeData() method

    /**
     * Constructor for the employee panel
     */
    public EmployeePanel(MainFrame mainFrame, EmployeeController employeeController) {
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
        setBackground(AppConstants.BACKGROUND_COLOR);

        // Main container with proper spacing
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(AppConstants.BACKGROUND_COLOR);
        container.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        // Content card that wraps everything
        JPanel contentCard = AppUtils.createCardPanel();
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
        titleLabel.setFont(AppConstants.TITLE_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);
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

        // Logo placeholder 
        JLabel logoLabel = new JLabel("MotorPH");
        logoLabel.setFont(AppConstants.SUBHEADING_FONT);
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setOpaque(true);
        logoLabel.setBackground(AppConstants.TEXT_COLOR);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        // Back button
        JButton backButton = AppUtils.createSecondaryButton("← Back to Main Menu");
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
        ribbonPanel.setBackground(AppConstants.NAVIGATION_BACKGROUND);
        ribbonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));

        // Left side - action buttons
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actionsPanel.setBackground(AppConstants.NAVIGATION_BACKGROUND);

        // Add New button (always enabled) - using AppUtils
        JButton addButton = AppUtils.createPrimaryButton("+ Add New");
        addButton.addActionListener(e -> openNewEmployeeDialog());
        actionsPanel.add(addButton);

        // Separator
        JPanel separator = new JPanel();
        separator.setPreferredSize(new java.awt.Dimension(1, 32));
        separator.setBackground(AppConstants.BORDER_COLOR);
        separator.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        actionsPanel.add(separator);

        // Context-aware buttons (disabled by default) - using AppUtils
        viewAttendanceBtn = AppUtils.createSecondaryButton("📅 View Attendance");
        viewDetailsBtn = AppUtils.createSecondaryButton("👁 View Details");
        editEmployeeBtn = AppUtils.createSecondaryButton("✏ Edit");
        deleteEmployeeBtn = AppUtils.createDangerButton("🗑 Delete");

        // Initially disable context buttons
        viewAttendanceBtn.setEnabled(false);
        viewDetailsBtn.setEnabled(false);
        editEmployeeBtn.setEnabled(false);
        deleteEmployeeBtn.setEnabled(false);

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
    /**
     * Update ribbon button states based on selection
     */
    private void updateRibbonButtons(boolean hasSelection) {
        viewAttendanceBtn.setEnabled(hasSelection);
        viewDetailsBtn.setEnabled(hasSelection);
        editEmployeeBtn.setEnabled(hasSelection);
        deleteEmployeeBtn.setEnabled(hasSelection);
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
                g2.setColor(AppConstants.BORDER_COLOR);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        searchField.setFont(AppConstants.NORMAL_FONT);
        searchField.setBorder(BorderFactory.createEmptyBorder(8, 40, 8, 12));
        searchField.setOpaque(false);
        searchField.setText("Search by name or employee no...");
        searchField.setForeground(AppConstants.TEXT_MUTED);

        // Add focus listeners for placeholder behavior
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("Search by name or employee no...")) {
                    searchField.setText("");
                    searchField.setForeground(AppConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search by name or employee no...");
                    searchField.setForeground(AppConstants.TEXT_MUTED);
                }
            }
        });

        // Add search functionality
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterEmployees();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterEmployees();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterEmployees();
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
        scrollPane.setBorder(BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1));
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
        employeeTable.setFont(AppConstants.TABLE_FONT);
        employeeTable.setRowHeight(AppConstants.TABLE_ROW_HEIGHT);
        employeeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.setShowGrid(true);
        employeeTable.setGridColor(AppConstants.TABLE_BORDER_COLOR);
        employeeTable.setIntercellSpacing(new java.awt.Dimension(1, 1));

        // Style table header
        employeeTable.getTableHeader().setBackground(AppConstants.TABLE_HEADER_BACKGROUND);
        employeeTable.getTableHeader().setForeground(AppConstants.TEXT_COLOR);
        employeeTable.getTableHeader().setFont(AppConstants.TABLE_HEADER_FONT);
        employeeTable.getTableHeader()
                .setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppConstants.BORDER_COLOR));

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
                    setBackground(row % 2 == 0 ? Color.WHITE : AppConstants.TABLE_ALT_ROW);
                    setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                }

                setFont(AppConstants.TABLE_FONT);

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
                    panel.setBackground(row % 2 == 0 ? Color.WHITE : AppConstants.TABLE_ALT_ROW);
                }

                if (value != null) {
                    String status = value.toString();
                    boolean isActive = "Active".equalsIgnoreCase(status);
                    JLabel statusBadge = AppUtils.createStatusBadge(status, isActive);
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

                if (employee != null) {
                    // Use the new professional attendance viewer dialog
                    AttendanceViewerDialog.showAttendanceViewer(
                            (Frame) SwingUtilities.getWindowAncestor(this),
                            employee,
                            employeeController);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Employee not found: ID " + employeeNumber,
                            "Employee Not Found",
                            JOptionPane.ERROR_MESSAGE);
                }

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
        if (EmployeeDialog.showAddDialog(mainFrame, employeeController)) {
            loadEmployeeData(); // Refresh the table
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
            if (employee != null) {
                showEmployeeDetails(employee);
            } else {
                AppUtils.showError(this, "Employee not found: ID " + employeeNumber);
            }
        } catch (Exception e) {
            AppUtils.handleException("viewing employee details", e);
        }
    }

    /**
     * Show employee details in a modern comprehensive dialog
     */
    private void showEmployeeDetails(Employee employee) {
        com.motorph.view.dialog.EmployeeDetailsDialog dialog = new com.motorph.view.dialog.EmployeeDetailsDialog(
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this),
                employee,
                employeeController);
        dialog.setVisible(true);
    }

    /**
     * Edit employee at the specified row
     */
    private void editEmployeeAtRow(int row) {
        try {
            int employeeNumber = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            Employee employee = employeeController.findEmployeeById(employeeNumber);

            if (employee != null) {
                if (EmployeeDialog.showEditDialog(mainFrame, employeeController, employee)) {
                    loadEmployeeData(); // Refresh the table to show updated data
                }
            } else {
                AppUtils.showError(this, "Employee not found: ID " + employeeNumber);
            }
        } catch (Exception e) {
            AppUtils.handleException("loading employee for editing", e);
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
                    // Delete the employee
                    boolean success = employeeController.deleteEmployee(employeeNumber);

                    if (success) {
                        JOptionPane.showMessageDialog(this,
                                "Employee " + employee.getFullName() + " (ID: " + employeeNumber
                                        + ") deleted successfully!",
                                "Delete Successful",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Refresh the table
                        loadEmployeeData();

                        // Clear selection and disable buttons
                        employeeTable.clearSelection();
                        updateRibbonButtons(false);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Failed to delete employee. Please try again.",
                                "Delete Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
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

    /**
     * Filter employees based on search text
     */
    private void filterEmployees() {
        String searchText = searchField.getText().toLowerCase().trim();

        // Don't filter if placeholder text is showing (fixed case sensitivity)
        if (searchText.isEmpty() || searchText.equals("search by name or employee no...")) {
            loadEmployeeData(); // Show all employees
            return;
        }

        // Clear existing data
        tableModel.setRowCount(0);

        // Get all employees and filter
        List<Employee> employees = employeeController.getAllEmployees();
        for (Employee employee : employees) {
            boolean matches = false;

            // Check employee ID
            if (String.valueOf(employee.getEmployeeId()).toLowerCase().contains(searchText)) {
                matches = true;
            }

            // Check employee name
            if (employee.getFullName() != null &&
                    employee.getFullName().toLowerCase().contains(searchText)) {
                matches = true;
            }

            // Check position
            if (employee.getPosition() != null &&
                    employee.getPosition().toLowerCase().contains(searchText)) {
                matches = true;
            }

            if (matches) {
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
    }
}

package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.motorph.controller.EmployeeController;
import com.motorph.model.Employee;
import com.motorph.util.UIConstants;
import com.motorph.view.dialog.EmployeeDetailsFrame;
import com.motorph.view.dialog.NewEmployeeDialog;

/**
 * Panel for displaying employee list as required by MPHCR-02.
 * Displays employees in JTable with Employee Number, Name, Position,
 * Department, Status, and Actions.
 * Matches the HTML prototype layout exactly.
 */
public class EmployeeListPanel extends JPanel {

    private final MainFrame mainFrame;
    private final EmployeeController employeeController;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    private static final String[] COLUMN_NAMES = {
            "Emp. No.", "Name", "Position", "Department", "Status", "Actions"
    };

    /**
     * Constructor for the employee list panel
     */
    public EmployeeListPanel(MainFrame mainFrame, EmployeeController employeeController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        initPanel();
        loadEmployeeData();
    }

    /**
     * Constructor for the employee list panel with payroll controller (legacy
     * support)
     */
    public EmployeeListPanel(MainFrame mainFrame, EmployeeController employeeController, Object payrollController) {
        this(mainFrame, employeeController);
    }

    /**
     * Initialize the panel
     */
    private void initPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIConstants.BACKGROUND_COLOR);

        // North panel with title and controls
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Employee Management System", SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.TITLE_FONT);
        northPanel.add(titleLabel, BorderLayout.NORTH);

        // Control panel with Add Employee button (matching prototype layout)
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(UIConstants.PANEL_BACKGROUND);
        controlPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        // Top section with buttons
        JPanel topButtonPanel = new JPanel(new BorderLayout());
        topButtonPanel.setBackground(UIConstants.PANEL_BACKGROUND);

        // Left side - Add Employee button
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        leftButtonPanel.setBackground(UIConstants.PANEL_BACKGROUND);
        JButton newEmployeeButton = createPrimaryButton("Add New Employee");
        leftButtonPanel.add(newEmployeeButton);
        // Right side - Back to Main Menu button
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10));
        rightButtonPanel.setBackground(UIConstants.PANEL_BACKGROUND);
        JButton backButton = com.motorph.util.UIUtils.createSecondaryButton("Back to Main Menu");
        rightButtonPanel.add(backButton);

        topButtonPanel.add(leftButtonPanel, BorderLayout.WEST);
        topButtonPanel.add(rightButtonPanel, BorderLayout.EAST);
        controlPanel.add(topButtonPanel, BorderLayout.NORTH);

        northPanel.add(controlPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        // Center panel with employee table
        createEmployeeTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBackground(UIConstants.BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);
        // Add action listeners
        newEmployeeButton.addActionListener(e -> openNewEmployeeDialog());
        backButton.addActionListener(e -> backToMainMenu());
    }

    /**
     * Create the employee table
     */
    private void createEmployeeTable() {
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only Actions column is editable
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);

        // Set column widths to match prototype
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(80); // Emp. No.
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Name
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Position
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Department
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(80); // Status
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(180); // Actions

        // Set up the Actions column with custom renderer and editor
        setupActionColumn();
    }

    /**
     * Load employee data into the table
     */
    private void loadEmployeeData() {
        try {
            List<Employee> employees = employeeController.getAllEmployees();

            // Clear existing data
            tableModel.setRowCount(0);

            // Add employee data to table (matching prototype columns)
            for (Employee employee : employees) {
                Object[] row = {
                        employee.getEmployeeId(),
                        employee.getFullName(), // Combined name for cleaner display
                        employee.getPosition(),
                        "IT", // Default department - could be enhanced later
                        "Active", // Default status - could be enhanced later
                        "Actions" // Placeholder - will be handled by custom renderer
                };
                tableModel.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading employee data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
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
     * Create a primary button (matching prototype style)
     */
    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(UIConstants.BUTTON_COLOR);
        button.setForeground(UIConstants.BUTTON_TEXT_COLOR);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new java.awt.Dimension(150, UIConstants.BUTTON_HEIGHT));
        return button;
    }

    /**
     * Setup action column with custom renderer and editor
     */
    private void setupActionColumn() {
        employeeTable.getColumn("Actions").setCellRenderer(new ActionButtonRenderer());
        employeeTable.getColumn("Actions").setCellEditor(new ActionButtonEditor());
        employeeTable.setRowHeight(40); // Increase row height for buttons
    }

    /**
     * Custom renderer for action buttons in table
     */
    private class ActionButtonRenderer extends DefaultTableCellRenderer {
        private final JPanel panel;
        private final JButton viewButton, editButton, deleteButton;

        public ActionButtonRenderer() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
            panel.setBackground(UIConstants.PANEL_BACKGROUND);

            viewButton = createActionButton("View", UIConstants.BUTTON_COLOR);
            editButton = createActionButton("Edit", UIConstants.BUTTON_COLOR);
            deleteButton = createActionButton("Delete", UIConstants.DELETE_BUTTON_COLOR);

            panel.add(viewButton);
            panel.add(editButton);
            panel.add(deleteButton);
        }

        private JButton createActionButton(String text, java.awt.Color bgColor) {
            JButton button = new JButton(text);
            button.setBackground(bgColor);
            button.setForeground(UIConstants.BUTTON_TEXT_COLOR);
            button.setFont(UIConstants.SMALL_FONT);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setPreferredSize(new java.awt.Dimension(55, 25));
            return button;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return panel;
        }
    }

    /**
     * Custom editor for action buttons in table
     */
    private class ActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton viewButton, editButton, deleteButton;
        private int currentRow;

        public ActionButtonEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
            panel.setBackground(UIConstants.PANEL_BACKGROUND);

            viewButton = createActionButton("View", UIConstants.BUTTON_COLOR);
            editButton = createActionButton("Edit", UIConstants.BUTTON_COLOR);
            deleteButton = createActionButton("Delete", UIConstants.DELETE_BUTTON_COLOR);
            // Add action listeners
            viewButton.addActionListener(e -> {
                stopCellEditing();
                viewEmployeeAtRow(currentRow);
            });

            editButton.addActionListener(e -> {
                stopCellEditing();
                editEmployeeAtRow(currentRow);
            });

            deleteButton.addActionListener(e -> {
                stopCellEditing();
                deleteEmployeeAtRow(currentRow);
            });

            panel.add(viewButton);
            panel.add(editButton);
            panel.add(deleteButton);
        }

        private JButton createActionButton(String text, java.awt.Color bgColor) {
            JButton button = new JButton(text);
            button.setBackground(bgColor);
            button.setForeground(UIConstants.BUTTON_TEXT_COLOR);
            button.setFont(UIConstants.SMALL_FONT);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setPreferredSize(new java.awt.Dimension(55, 25));
            return button;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Actions";
        }
    }

    /**
     * View employee details for the specified row
     */
    private void viewEmployeeAtRow(int row) {
        try {
            int employeeId = (Integer) tableModel.getValueAt(row, 0);
            Employee employee = employeeController.findEmployeeById(employeeId);
            new EmployeeDetailsFrame(mainFrame, employee).setVisible(true);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                    "Error viewing employee details: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Edit employee at the specified row
     */
    private void editEmployeeAtRow(int row) {
        try {
            int employeeId = (Integer) tableModel.getValueAt(row, 0);
            Employee employee = employeeController.findEmployeeById(employeeId);

            // Create and show edit dialog
            com.motorph.view.dialog.EditEmployeeDialog dialog = new com.motorph.view.dialog.EditEmployeeDialog(
                    mainFrame, employeeController, employee);
            dialog.setVisible(true);

            // If employee was updated, refresh the table
            if (dialog.isEmployeeUpdated()) {
                loadEmployeeData();
                JOptionPane.showMessageDialog(this,
                        "Employee updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                    "Error editing employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delete employee at the specified row
     */
    private void deleteEmployeeAtRow(int row) {
        try {
            int employeeId = (Integer) tableModel.getValueAt(row, 0);
            Employee employee = employeeController.findEmployeeById(employeeId);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete employee: " + employee.getFullName() + "?\n" +
                            "This action cannot be undone.",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                // Delete the employee
                boolean success = employeeController.deleteEmployee(employeeId);

                if (success) {
                    loadEmployeeData(); // Refresh the table
                    JOptionPane.showMessageDialog(this,
                            "Employee deleted successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Failed to delete employee. Employee may not exist.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                    "Error deleting employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

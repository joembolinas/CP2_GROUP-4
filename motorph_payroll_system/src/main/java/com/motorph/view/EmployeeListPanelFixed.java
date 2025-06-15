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
 * Panel for displaying employee list exactly as specified in HTML prototype.
 * Simple layout: Title -> Add New Employee button -> Table with action buttons
 */
public class EmployeeListPanelFixed extends JPanel {

    private final MainFrame mainFrame;
    private final EmployeeController employeeController;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    // Table columns exactly as in HTML prototype
    private static final String[] COLUMN_NAMES = {
            "Emp. No.", "Name", "Position", "Department", "Status", "Actions"
    };

    public EmployeeListPanelFixed(MainFrame mainFrame, EmployeeController employeeController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        initPanel();
        loadEmployeeData();
    }

    /**
     * Initialize the panel - simple layout matching HTML prototype
     */
    private void initPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIConstants.PANEL_BACKGROUND); // White background
        setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
                javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // North panel - Title and Add New Employee button
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(UIConstants.PANEL_BACKGROUND);

        // Section title - centered
        JLabel titleLabel = new JLabel("Employee Management", SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.HEADING_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 15, 0));
        northPanel.add(titleLabel, BorderLayout.NORTH);

        // Add New Employee button - left aligned
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        buttonPanel.setBackground(UIConstants.PANEL_BACKGROUND);

        JButton addEmployeeButton = createAddEmployeeButton();
        buttonPanel.add(addEmployeeButton);

        northPanel.add(buttonPanel, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);

        // Center panel - Table
        createEmployeeTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBackground(UIConstants.PANEL_BACKGROUND);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 0, 0, 0));
        add(scrollPane, BorderLayout.CENTER);

        // Add action listener
        addEmployeeButton.addActionListener(e -> openNewEmployeeDialog());
    }

    /**
     * Create Add New Employee button matching prototype style
     */
    private JButton createAddEmployeeButton() {
        JButton button = new JButton("Add New Employee");
        button.setBackground(UIConstants.BUTTON_COLOR); // #007BFF Blue
        button.setForeground(UIConstants.BUTTON_TEXT_COLOR); // White
        button.setFont(UIConstants.BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new java.awt.Dimension(160, 35));

        // Add rounded corners effect
        button.setOpaque(true);
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));

        return button;
    }

    /**
     * Create the employee table with action buttons
     */
    private void createEmployeeTable() {
        // Create table model
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only Actions column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5)
                    return JPanel.class; // Actions column
                return String.class;
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.setRowHeight(40); // Increase row height for buttons

        // Set column widths
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(80); // Emp. No.
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Name
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Position
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Department
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(80); // Status
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(180); // Actions

        // Set up the Actions column with custom renderer and editor
        employeeTable.getColumn("Actions").setCellRenderer(new ActionButtonRenderer());
        employeeTable.getColumn("Actions").setCellEditor(new ActionButtonEditor());

        // Style table header
        employeeTable.getTableHeader().setBackground(UIConstants.TABLE_HEADER_BACKGROUND);
        employeeTable.getTableHeader().setForeground(UIConstants.TEXT_COLOR);
        employeeTable.getTableHeader().setFont(UIConstants.NORMAL_FONT);
    }

    /**
     * Load employee data into the table
     */
    private void loadEmployeeData() {
        try {
            List<Employee> employees = employeeController.getAllEmployees();

            // Clear existing data
            tableModel.setRowCount(0);

            // Add employee data to table
            for (Employee employee : employees) {
                Object[] row = {
                        employee.getEmployeeId(),
                        employee.getFullName(),
                        employee.getPosition(),
                        "IT", // Default department
                        "Active", // Default status
                        createActionButtonPanel() // This will be handled by renderer/editor
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
     * Create action button panel for table cells
     */
    private JPanel createActionButtonPanel() {
        return new JPanel(); // Placeholder - actual buttons handled by renderer/editor
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
     * Custom renderer for action buttons in table cells
     */
    private class ActionButtonRenderer extends DefaultTableCellRenderer {
        private final JPanel panel;
        private final JButton viewButton, editButton, deleteButton;

        public ActionButtonRenderer() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
            panel.setBackground(UIConstants.PANEL_BACKGROUND);

            // Create action buttons
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
            button.setOpaque(true);
            return button;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return panel;
        }
    }

    /**
     * Custom editor for action buttons in table cells - this makes buttons
     * clickable
     */
    private class ActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton viewButton, editButton, deleteButton;
        private int currentRow = -1;

        public ActionButtonEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
            panel.setBackground(UIConstants.PANEL_BACKGROUND);

            // Create action buttons
            viewButton = createActionButton("View", UIConstants.BUTTON_COLOR);
            editButton = createActionButton("Edit", UIConstants.BUTTON_COLOR);
            deleteButton = createActionButton("Delete", UIConstants.DELETE_BUTTON_COLOR);

            // Add action listeners - THIS IS CRUCIAL FOR RESPONSIVENESS
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
            button.setOpaque(true);
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
        } catch (Exception e) {
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

            // For now, show details and inform about future implementation
            new EmployeeDetailsFrame(mainFrame, employee).setVisible(true);
            JOptionPane.showMessageDialog(this,
                    "Edit functionality will be implemented in future version.",
                    "Edit Employee",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
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
                    "Are you sure you want to delete employee: " + employee.getFullName() + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this,
                        String.format(
                                "Delete functionality will be implemented in future version.%nEmployee: %s would be deleted.",
                                employee.getFullName()),
                        "Delete Employee",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error deleting employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Refresh the employee data
     */
    public void refreshData() {
        loadEmployeeData();
    }
}

package com.motorph.view;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.motorph.controller.EmployeeController;
import com.motorph.controller.PayrollController;
import com.motorph.model.Employee;
import com.motorph.util.UIConstants;
import com.motorph.view.dialog.EmployeeDetailsFrame;
import com.motorph.view.dialog.NewEmployeeDialog;

/**
 * Panel for displaying employee list with management functions as required by MPHCR-02.
 * Displays employees in JTable with Employee Number, Last Name, First Name, 
 * SSS Number, PhilHealth Number, TIN, and Pag-IBIG Number.
 */
public class EmployeeListPanel extends JPanel {
    
    private final MainFrame mainFrame;
    private final EmployeeController employeeController;
    private final PayrollController payrollController;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> monthComboBox;
      private static final String[] COLUMN_NAMES = {
        "Emp. No.", "Name", "Position", "Department", "Status", "Actions"
    };
    
    private static final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    /**
     * Constructor for the employee list panel
     */
    public EmployeeListPanel(MainFrame mainFrame, EmployeeController employeeController, 
                           PayrollController payrollController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        this.payrollController = payrollController;
        initPanel();
        loadEmployeeData();
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
        
        // Top section with Add Employee button
        JPanel topButtonPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 10));
        topButtonPanel.setBackground(UIConstants.PANEL_BACKGROUND);
        JButton newEmployeeButton = createPrimaryButton("Add New Employee");
        topButtonPanel.add(newEmployeeButton);
        controlPanel.add(topButtonPanel, BorderLayout.NORTH);
        
        // Bottom section with month selection and other controls
        JPanel bottomControlPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        bottomControlPanel.setBackground(UIConstants.PANEL_BACKGROUND);
        
        bottomControlPanel.add(new JLabel("Select Month for Salary Computation:"));
        monthComboBox = new JComboBox<>(MONTHS);
        monthComboBox.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        bottomControlPanel.add(monthComboBox);
        
        JButton computeButton = createSecondaryButton("Compute Salary");
        JButton refreshButton = createSecondaryButton("Refresh");
        JButton backButton = createSecondaryButton("Back to Main Menu");
        
        bottomControlPanel.add(computeButton);
        bottomControlPanel.add(refreshButton);
        bottomControlPanel.add(backButton);
        
        controlPanel.add(bottomControlPanel, BorderLayout.SOUTH);
        
        northPanel.add(controlPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);
        
        // Center panel with employee table
        createEmployeeTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBackground(UIConstants.BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);
          // Add action listeners
        newEmployeeButton.addActionListener(e -> openNewEmployeeDialog());
        computeButton.addActionListener(e -> computeSalaryForSelectedEmployee());
        refreshButton.addActionListener(e -> {
            loadEmployeeData();
            JOptionPane.showMessageDialog(this, "Employee data refreshed successfully!", 
                    "Refresh", JOptionPane.INFORMATION_MESSAGE);
        });
        backButton.addActionListener(e -> mainFrame.showMainMenu());
    }
    
    /**
     * Create the employee table
     */
    private void createEmployeeTable() {
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);
          // Set column widths to match prototype
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Emp. No.
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Name
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Position
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Department
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Status
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
     * Setup action column with custom renderer and editor
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
     * Compute salary for selected employee for the selected month
     */
    private void computeSalaryForSelectedEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Please select an employee to compute salary.",
                    "No Selection", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int employeeId = (Integer) tableModel.getValueAt(selectedRow, 0);
            Employee employee = employeeController.findEmployeeById(employeeId);
            
            // Get selected month
            int selectedMonthIndex = monthComboBox.getSelectedIndex();
            Month selectedMonth = Month.of(selectedMonthIndex + 1);
            int currentYear = LocalDate.now().getYear();
            
            // Calculate date range for the selected month
            LocalDate startDate = LocalDate.of(currentYear, selectedMonth, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            
            // Generate payslip for the month
            var payslip = payrollController.generatePayslip(employeeId, startDate, endDate);
            
            // Create a detailed view showing employee details and computed salary
            StringBuilder details = new StringBuilder();
            details.append("=== EMPLOYEE DETAILS ===\n");
            details.append(String.format("Employee ID: %d\n", employee.getEmployeeId()));
            details.append(String.format("Name: %s\n", employee.getFullName()));
            details.append(String.format("Position: %s\n", employee.getPosition()));
            details.append(String.format("Basic Salary: ₱%,.2f\n", employee.getBasicSalary()));
            details.append(String.format("Hourly Rate: ₱%.2f\n", employee.getHourlyRate()));
            details.append("\n=== COMPUTED SALARY FOR ").append(selectedMonth.name()).append(" ").append(currentYear).append(" ===\n");
            details.append(String.format("Regular Hours: %.2f\n", payslip.getRegularHours()));
            details.append(String.format("Overtime Hours: %.2f\n", payslip.getOvertimeHours()));
            details.append(String.format("Gross Pay: ₱%,.2f\n", payslip.getGrossPay()));
            details.append(String.format("Net Pay: ₱%,.2f\n", payslip.getNetPay()));
            
            JOptionPane.showMessageDialog(this, 
                    details.toString(),
                    "Salary Computation for " + employee.getFullName(), 
                    JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Error computing salary: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
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
     * Create a secondary button (matching prototype style)
     */
    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new java.awt.Color(108, 117, 125)); // Bootstrap secondary
        button.setForeground(UIConstants.BUTTON_TEXT_COLOR);
        button.setFont(UIConstants.NORMAL_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new java.awt.Dimension(120, 30));
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
     */    private class ActionButtonRenderer extends javax.swing.table.DefaultTableCellRenderer {
        private final JPanel panel;
        private final JButton viewButton, editButton, deleteButton;
        
        public ActionButtonRenderer() {
            panel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 2));
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
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return panel;
        }
    }
    
    /**
     * Custom editor for action buttons in table
     */    private class ActionButtonEditor extends javax.swing.AbstractCellEditor 
            implements javax.swing.table.TableCellEditor {
        private final JPanel panel;
        private final JButton viewButton, editButton, deleteButton;
        private int currentRow;
        
        public ActionButtonEditor() {
            panel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 2));
            panel.setBackground(UIConstants.PANEL_BACKGROUND);
            
            viewButton = createActionButton("View", UIConstants.BUTTON_COLOR);
            editButton = createActionButton("Edit", UIConstants.BUTTON_COLOR);
            deleteButton = createActionButton("Delete", UIConstants.DELETE_BUTTON_COLOR);
            
            // Add action listeners
            viewButton.addActionListener(e -> {
                viewEmployeeAtRow(currentRow);
                fireEditingStopped();
            });
            
            editButton.addActionListener(e -> {
                editEmployeeAtRow(currentRow);
                fireEditingStopped();
            });
            
            deleteButton.addActionListener(e -> {
                deleteEmployeeAtRow(currentRow);
                fireEditingStopped();
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
        public java.awt.Component getTableCellEditorComponent(JTable table, Object value,
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
            new EmployeeDetailsFrame(mainFrame, employee).setVisible(true);        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, 
                    "Error viewing employee details: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Edit employee at the specified row (placeholder - could open edit dialog)
     */
    private void editEmployeeAtRow(int row) {
        try {
            int employeeId = (Integer) tableModel.getValueAt(row, 0);
            // For now, show employee details. Could be enhanced to open edit dialog
            Employee employee = employeeController.findEmployeeById(employeeId);
            new EmployeeDetailsFrame(mainFrame, employee).setVisible(true);
            JOptionPane.showMessageDialog(this, 
                    "Edit functionality will be implemented in future version.",
                    "Edit Employee", 
                    JOptionPane.INFORMATION_MESSAGE);        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, 
                    "Error editing employee: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Delete employee at the specified row (placeholder - would need confirmation)
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
                // For now, just show a message. Actual deletion would need to be implemented
                JOptionPane.showMessageDialog(this, 
                        """
                        Delete functionality will be implemented in future version.
                        Employee: %s would be deleted.
                        """.formatted(employee.getFullName()),
                        "Delete Employee", 
                        JOptionPane.INFORMATION_MESSAGE);
            }        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, 
                    "Error deleting employee: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

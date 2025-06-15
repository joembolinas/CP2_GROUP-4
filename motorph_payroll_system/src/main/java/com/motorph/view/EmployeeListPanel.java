package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.motorph.controller.EmployeeController;
import com.motorph.model.Employee;
import com.motorph.util.UIConstants;
import com.motorph.view.dialog.EmployeeDetailsDialog;
import com.motorph.view.dialog.NewEmployeeDialog;

/**
 * Panel for displaying employee list as required by MPHCR-02.
 * Shows all employees in a JTable with Employee Number, Last Name, First Name,
 * SSS Number, PhilHealth Number, TIN, and Pag-IBIG Number, plus action buttons.
 */
public class EmployeeListPanel extends JPanel {

    private final MainFrame mainFrame;
    private final EmployeeController employeeController;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    /**
     * Constructor for the employee list panel
     */
    public EmployeeListPanel(MainFrame mainFrame, EmployeeController employeeController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        initPanel();
        refreshEmployeeList();
    }

    /**
     * Initialize the panel components
     */
    private void initPanel() {
        setLayout(new BorderLayout(UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING));
        setBackground(UIConstants.BACKGROUND_COLOR);

        // Title panel
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        // Table panel
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Create the title panel
     */
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(UIConstants.BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Employee Management - All Employees", SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(UIConstants.HEADING_COLOR);
        titlePanel.add(titleLabel);

        return titlePanel;
    }

    /**
     * Create the table panel with employee data
     */
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(UIConstants.BACKGROUND_COLOR);

        // Create table model with column names as per MPHCR-02 requirements
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == UIConstants.EMPLOYEE_TABLE_COLUMNS.length - 1; // Only Actions column is editable
            }
        };

        tableModel.setColumnIdentifiers(UIConstants.EMPLOYEE_TABLE_COLUMNS);

        // Create table
        employeeTable = new JTable(tableModel);
        employeeTable.setFont(UIConstants.NORMAL_FONT);
        employeeTable.setRowHeight(35);
        employeeTable.getTableHeader().setFont(UIConstants.TABLE_HEADER_FONT);
        employeeTable.getTableHeader().setBackground(UIConstants.TABLE_HEADER_COLOR);
        employeeTable.getTableHeader().setForeground(Color.WHITE);

        // Set up action buttons column
        employeeTable.getColumn("Actions").setCellRenderer(new ActionButtonRenderer());
        employeeTable.getColumn("Actions").setCellEditor(new ActionButtonEditor());
        employeeTable.getColumn("Actions").setPreferredWidth(150);

        // Configure other columns
        employeeTable.getColumn("Employee Number").setPreferredWidth(120);
        employeeTable.getColumn("Last Name").setPreferredWidth(120);
        employeeTable.getColumn("First Name").setPreferredWidth(120);
        employeeTable.getColumn("SSS Number").setPreferredWidth(130);
        employeeTable.getColumn("PhilHealth Number").setPreferredWidth(150);
        employeeTable.getColumn("TIN").setPreferredWidth(130);
        employeeTable.getColumn("Pag-IBIG Number").setPreferredWidth(140);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setPreferredSize(UIConstants.LIST_DIALOG_SIZE);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    /**
     * Create the button panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING));
        buttonPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        JButton newEmployeeButton = createStyledButton("New Employee", UIConstants.SUCCESS_GREEN);
        JButton refreshButton = createStyledButton("Refresh", UIConstants.PRIMARY_BLUE);
        JButton backButton = createStyledButton("Back to Main Menu", UIConstants.DARK_GRAY);

        buttonPanel.add(newEmployeeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        // Add action listeners
        newEmployeeButton.addActionListener(e -> openNewEmployeeDialog());
        refreshButton.addActionListener(e -> refreshEmployeeList());
        backButton.addActionListener(e -> mainFrame.showMainMenu());

        return buttonPanel;
    }

    /**
     * Create a styled button
     */
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(UIConstants.LARGE_BUTTON_SIZE);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Refresh the employee list from the service
     */
    public void refreshEmployeeList() {
        tableModel.setRowCount(0); // Clear existing data

        List<Object[]> employeeData = employeeController.getEmployeesForTable();
        for (Object[] row : employeeData) {
            // Add "Actions" column placeholder (will be rendered as buttons)
            Object[] tableRow = new Object[row.length + 1];
            System.arraycopy(row, 0, tableRow, 0, row.length);
            tableRow[row.length] = ""; // Actions column placeholder

            tableModel.addRow(tableRow);
        }
    }

    /**
     * Open the new employee dialog
     */
    private void openNewEmployeeDialog() {
        NewEmployeeDialog dialog = new NewEmployeeDialog(mainFrame, employeeController);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        // Refresh the list if a new employee was added
        if (dialog.isEmployeeAdded()) {
            refreshEmployeeList();
            JOptionPane.showMessageDialog(this,
                    "New employee added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Open employee details dialog for viewing/editing
     */
    private void viewEmployeeDetails(int row) {
        int employeeId = (Integer) tableModel.getValueAt(row, 0);
        Employee employee = employeeController.findEmployeeById(employeeId);

        if (employee != null) {
            EmployeeDetailsDialog dialog = new EmployeeDetailsDialog(mainFrame, employee, employeeController);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Employee not found!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Custom renderer for action buttons in table
     */
    private class ActionButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton viewButton, editButton;

        public ActionButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setOpaque(true);

            viewButton = new JButton("View");
            viewButton.setFont(UIConstants.SMALL_FONT);
            viewButton.setBackground(UIConstants.PRIMARY_BLUE);
            viewButton.setForeground(Color.WHITE);
            viewButton.setPreferredSize(UIConstants.SMALL_BUTTON_SIZE);
            viewButton.setFocusPainted(false);

            editButton = new JButton("Edit");
            editButton.setFont(UIConstants.SMALL_FONT);
            editButton.setBackground(UIConstants.SUCCESS_GREEN);
            editButton.setForeground(Color.WHITE);
            editButton.setPreferredSize(UIConstants.SMALL_BUTTON_SIZE);
            editButton.setFocusPainted(false);

            add(viewButton);
            add(editButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            if (isSelected) {
                setBackground(UIConstants.TABLE_SELECTION);
            } else if (row % 2 == 0) {
                setBackground(UIConstants.TABLE_ROW_EVEN);
            } else {
                setBackground(UIConstants.TABLE_ROW_ODD);
            }

            return this;
        }
    }

    /**
     * Custom editor for action buttons in table
     */
    private class ActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton viewButton, editButton;
        private int currentRow;

        public ActionButtonEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

            viewButton = new JButton("View");
            viewButton.setFont(UIConstants.SMALL_FONT);
            viewButton.setBackground(UIConstants.PRIMARY_BLUE);
            viewButton.setForeground(Color.WHITE);
            viewButton.setPreferredSize(UIConstants.SMALL_BUTTON_SIZE);
            viewButton.setFocusPainted(false);

            editButton = new JButton("Edit");
            editButton.setFont(UIConstants.SMALL_FONT);
            editButton.setBackground(UIConstants.SUCCESS_GREEN);
            editButton.setForeground(Color.WHITE);
            editButton.setPreferredSize(UIConstants.SMALL_BUTTON_SIZE);
            editButton.setFocusPainted(false);

            // Add action listeners
            viewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    viewEmployeeDetails(currentRow);
                }
            });

            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    viewEmployeeDetails(currentRow); // For now, edit opens the same dialog
                }
            });

            panel.add(viewButton);
            panel.add(editButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}

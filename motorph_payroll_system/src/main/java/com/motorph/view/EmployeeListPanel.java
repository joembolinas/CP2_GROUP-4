package com.motorph.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

import com.motorph.controller.EmployeeController;
import com.motorph.model.Employee;
import com.motorph.util.UIConstants;
import com.motorph.view.dialog.EmployeeDetailsFrame;
import com.motorph.view.dialog.NewEmployeeDialog;

public class EmployeeListPanel extends JPanel {

    private final MainFrame mainFrame;
    private final EmployeeController employeeController;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    private static final String[] COLUMN_NAMES = {
            "Emp. No.", "Name", "Position", "Department", "Status", "Actions"
    };

    public EmployeeListPanel(MainFrame mainFrame, EmployeeController employeeController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        initPanel();
        loadEmployeeData();
    }

    private void initPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIConstants.BACKGROUND_COLOR);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Employee Management System", SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.TITLE_FONT);
        northPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(UIConstants.PANEL_BACKGROUND);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topButtonPanel = new JPanel(new BorderLayout());
        topButtonPanel.setBackground(UIConstants.PANEL_BACKGROUND);

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setBackground(UIConstants.PANEL_BACKGROUND);
        leftButtonPanel.setLayout(new BoxLayout(leftButtonPanel, BoxLayout.Y_AXIS));

        // Search field
        JTextField searchField = new JTextField("Search ID number");
        searchField.setFont(UIConstants.BUTTON_FONT);
        searchField.setMaximumSize(new Dimension(200, UIConstants.BUTTON_HEIGHT));
        searchField.setPreferredSize(new Dimension(200, UIConstants.BUTTON_HEIGHT));
        searchField.setForeground(Color.GRAY);

        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("Search ID number")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Search ID number");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        // Search button
        JButton searchButton = createPrimaryButton("Search");
        searchButton.setPreferredSize(new Dimension(100, UIConstants.BUTTON_HEIGHT));
        searchButton.setMaximumSize(new Dimension(100, UIConstants.BUTTON_HEIGHT));

        // Match height
        searchField.setPreferredSize(searchButton.getPreferredSize());

        // Panel to hold search field and button side by side
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(UIConstants.PANEL_BACKGROUND);
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.add(searchField);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel.add(searchButton);

        // New Employee Button below search
        JButton newEmployeeButton = createPrimaryButton("Add New Employee");
        newEmployeeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        newEmployeeButton.setMaximumSize(new Dimension(200, UIConstants.BUTTON_HEIGHT));

        // Add components to left button panel
        leftButtonPanel.add(searchPanel);
        leftButtonPanel.add(Box.createVerticalStrut(10));
        leftButtonPanel.add(newEmployeeButton);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10));
        rightButtonPanel.setBackground(UIConstants.PANEL_BACKGROUND);
        JButton backButton = com.motorph.util.UIUtils.createSecondaryButton("Back to Main Menu");
        rightButtonPanel.add(backButton);

        topButtonPanel.add(leftButtonPanel, BorderLayout.WEST);
        topButtonPanel.add(rightButtonPanel, BorderLayout.EAST);
        controlPanel.add(topButtonPanel, BorderLayout.NORTH);
        northPanel.add(controlPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        createEmployeeTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBackground(UIConstants.BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        newEmployeeButton.addActionListener(e -> openNewEmployeeDialog());
        backButton.addActionListener(e -> backToMainMenu());

        Runnable searchAction = () -> {
            try {
                String input = searchField.getText().trim();
                if (input.isEmpty() || input.equals("Search ID number")) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid Employee ID.",
                            "Input Required",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int searchId = Integer.parseInt(input);
                boolean found = false;
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    int empId = (Integer) tableModel.getValueAt(row, 0);
                    if (empId == searchId) {
                        employeeTable.setRowSelectionInterval(row, row);
                        employeeTable.scrollRectToVisible(employeeTable.getCellRect(row, 0, true));
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(this,
                            "Employee ID " + searchId + " not found.",
                            "No Match",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                searchField.setText(""); // Clear field
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid Employee ID format. Please enter a number.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        };

        searchButton.addActionListener(e -> searchAction.run());
        searchField.addActionListener(e -> searchAction.run());
    }

    private void createEmployeeTable() {
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);

        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(180);

        setupActionColumn();
    }

    private void loadEmployeeData() {
        try {
            List<Employee> employees = employeeController.getAllEmployees();
            tableModel.setRowCount(0);
            for (Employee employee : employees) {
                Object[] row = {
                        employee.getEmployeeId(),
                        employee.getFullName(),
                        employee.getPosition(),
                        "IT",
                        "Active",
                        "Actions"
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

    private void openNewEmployeeDialog() {
        NewEmployeeDialog dialog = new NewEmployeeDialog(mainFrame, employeeController);
        dialog.setVisible(true);
        if (dialog.isEmployeeAdded()) {
            loadEmployeeData();
            JOptionPane.showMessageDialog(this,
                    "New employee added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void backToMainMenu() {
        mainFrame.showMainMenu();
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(UIConstants.BUTTON_COLOR);
        button.setForeground(UIConstants.BUTTON_TEXT_COLOR);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, UIConstants.BUTTON_HEIGHT));
        return button;
    }

    private void setupActionColumn() {
        employeeTable.getColumn("Actions").setCellRenderer(new ActionButtonRenderer());
        employeeTable.getColumn("Actions").setCellEditor(new ActionButtonEditor());
        employeeTable.setRowHeight(40);
    }

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

        private JButton createActionButton(String text, Color bgColor) {
            JButton button = new JButton(text);
            button.setBackground(bgColor);
            button.setForeground(UIConstants.BUTTON_TEXT_COLOR);
            button.setFont(UIConstants.SMALL_FONT);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setPreferredSize(new Dimension(55, 25));
            return button;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return panel;
        }
    }

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

        private JButton createActionButton(String text, Color bgColor) {
            JButton button = new JButton(text);
            button.setBackground(bgColor);
            button.setForeground(UIConstants.BUTTON_TEXT_COLOR);
            button.setFont(UIConstants.SMALL_FONT);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setPreferredSize(new Dimension(55, 25));
            return button;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Actions";
        }
    }

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

    private void editEmployeeAtRow(int row) {
        try {
            int employeeId = (Integer) tableModel.getValueAt(row, 0);
            Employee employee = employeeController.findEmployeeById(employeeId);
            com.motorph.view.dialog.EditEmployeeDialog dialog = new com.motorph.view.dialog.EditEmployeeDialog(
                    mainFrame, employeeController, employee);
            dialog.setVisible(true);
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

    private void deleteEmployeeAtRow(int row) {
        try {
            int employeeId = (Integer) tableModel.getValueAt(row, 0);
            Employee employee = employeeController.findEmployeeById(employeeId);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete employee: " + employee.getFullName() + "?\nThis action cannot be undone.",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = employeeController.deleteEmployee(employeeId);
                if (success) {
                    loadEmployeeData();
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
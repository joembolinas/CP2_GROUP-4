package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.motorph.controller.EmployeeController;
import com.motorph.model.Employee;
import com.motorph.util.AppConstants;
import com.motorph.util.AppUtils;

/**
 * 🎯 Enhanced Employee Details Dialog - Professional Design with Complete CSV
 * Data Integration
 *
 * Modern dark-themed dialog that displays comprehensive employee information
 * from the CSV file in a well-organized, professional layout.
 */
public class EmployeeDetailsDialog extends JDialog {

    private final Employee employee;
    private final EmployeeController employeeController;
    private final NumberFormat currencyFormat;

    public EmployeeDetailsDialog(JFrame parent, Employee employee, EmployeeController employeeController) {
        super(parent, "🏢 View Employee Details", true);
        this.employee = employee;
        this.employeeController = employeeController;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        initDialog();
    }

    private void initDialog() {
        setLayout(new BorderLayout());
        setBackground(AppConstants.BACKGROUND_COLOR);

        // Main content panel with scrolling
        JPanel mainPanel = createMainPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(AppConstants.BACKGROUND_COLOR);
        scrollPane.getViewport().setBackground(AppConstants.BACKGROUND_COLOR);

        // Style the scrollbar for modern theme
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = AppConstants.BORDER_SOLID;
                this.trackColor = AppConstants.PANEL_BACKGROUND;
            }
        });

        // Button panel at bottom
        JPanel buttonPanel = createButtonPanel();

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Dialog settings with optimal sizing
        setSize(950, 800);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Add modern accent border to the dialog
        getRootPane().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.ACCENT_PRIMARY, 2),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(25, 25, 15, 25));

        // Main content card with professional styling
        JPanel contentCard = new JPanel(new BorderLayout());
        contentCard.setBackground(AppConstants.CARD_BACKGROUND);
        contentCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_SOLID, 1),
                new EmptyBorder(30, 30, 30, 30)));

        // Header section with employee photo and key info
        JPanel headerPanel = createHeaderSection();
        contentCard.add(headerPanel, BorderLayout.NORTH);

        // All information sections in vertical layout
        JPanel detailsPanel = createAllDetailsPanel();
        contentCard.add(detailsPanel, BorderLayout.CENTER);

        mainPanel.add(contentCard, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createHeaderSection() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppConstants.CARD_BACKGROUND);
        headerPanel.setBorder(new EmptyBorder(0, 0, 25, 0));

        // Left side - Employee photo and basic info
        JPanel leftSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftSection.setBackground(AppConstants.CARD_BACKGROUND);

        // Employee photo placeholder with modern styling
        JPanel photoContainer = new JPanel(new BorderLayout());
        photoContainer.setPreferredSize(new Dimension(140, 140));
        photoContainer.setBackground(AppConstants.PANEL_BACKGROUND);
        photoContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.ACCENT_PRIMARY, 3),
                new EmptyBorder(15, 15, 15, 15)));

        JLabel photoLabel = new JLabel("👤", SwingConstants.CENTER);
        photoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        photoLabel.setForeground(AppConstants.TEXT_SECONDARY);
        photoContainer.add(photoLabel, BorderLayout.CENTER);

        // Employee key information panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(AppConstants.CARD_BACKGROUND);
        infoPanel.setBorder(new EmptyBorder(5, 30, 5, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 0, 3, 15);

        // Employee ID - Highlighted
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(createLabelWithIcon("🆔", "Employee ID:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(createHighlightedValue("EMP-" + String.format("%05d", employee.getEmployeeId())), gbc);

        // Full Name - Highlighted
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(createLabelWithIcon("👤", "Name:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(createHighlightedValue(employee.getFullName()), gbc);

        // Job Title
        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(createLabelWithIcon("💼", "Job Title:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(createValueLabel(getValue(employee.getPosition())), gbc);

        // Department
        gbc.gridx = 0;
        gbc.gridy = 3;
        infoPanel.add(createLabelWithIcon("🏢", "Department:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(createValueLabel(getDepartmentFromPosition(employee.getPosition())), gbc);

        // Status with color coding
        gbc.gridx = 0;
        gbc.gridy = 4;
        infoPanel.add(createLabelWithIcon("📊", "Status:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(createStatusBadge(getValue(employee.getStatus())), gbc);

        leftSection.add(photoContainer);
        leftSection.add(infoPanel);

        headerPanel.add(leftSection, BorderLayout.CENTER);
        return headerPanel;
    }

    private JPanel createAllDetailsPanel() {
        JPanel mainDetailsPanel = new JPanel(new GridBagLayout());
        mainDetailsPanel.setBackground(AppConstants.CARD_BACKGROUND);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 20, 0);

        // Personal Information Section
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainDetailsPanel.add(createPersonalInfoSection(), gbc);

        // Employment Details Section
        gbc.gridy = 1;
        mainDetailsPanel.add(createEmploymentSection(), gbc);

        // Compensation & Benefits Section
        gbc.gridy = 2;
        mainDetailsPanel.add(createCompensationSection(), gbc);

        // Government Numbers Section
        gbc.gridy = 3;
        mainDetailsPanel.add(createGovernmentNumbersSection(), gbc);

        // Emergency Contact Section (placeholder)
        gbc.gridy = 4;
        mainDetailsPanel.add(createEmergencyContactSection(), gbc);

        return mainDetailsPanel;
    }

    private JPanel createPersonalInfoSection() {
        JPanel section = createSectionPanel("📋 Personal Information");

        addFieldToSection(section, "� Email:", "john.doe@motorph.com ✉️"); // Placeholder
        addFieldToSection(section, "�📞 Phone Number:", getValue(employee.getPhoneNumber()) + " 📞");
        addFieldToSection(section, "🏠 Address:", getValue(employee.getAddress()));
        addFieldToSection(section, "🎂 Date of Birth:", formatDate(employee.getBirthday()));
        addFieldToSection(section, "👤 Gender:", "Not specified"); // Placeholder
        addFieldToSection(section, "💑 Marital Status:", "Not specified"); // Placeholder

        return section;
    }

    private JPanel createEmploymentSection() {
        JPanel section = createSectionPanel("💼 Employment Details");

        addFieldToSection(section, "� Date Hired:", "Not specified"); // Could be derived from data
        addFieldToSection(section, "📊 Employment Status:", getValue(employee.getStatus()));
        addFieldToSection(section, "⏰ Employment Type:", "Full-time"); // Most employees are full-time
        addFieldToSection(section, "👨‍💼 Manager:", getValue(employee.getSupervisor()));

        return section;
    }

    private JPanel createCompensationSection() {
        JPanel section = createSectionPanel("💰 Compensation & Benefits");

        // Format all currency values with proper Philippine Peso formatting
        addFieldToSection(section, "💵 Basic Salary:", formatCurrency(employee.getBasicSalary()));
        addFieldToSection(section, "🍚 Rice Subsidy:", formatCurrency(employee.getRiceSubsidy()));
        addFieldToSection(section, "📱 Phone Allowance:", formatCurrency(employee.getPhoneAllowance()));
        addFieldToSection(section, "👔 Clothing Allowance:", formatCurrency(employee.getClothingAllowance()));
        addFieldToSection(section, "📈 Gross Semi-monthly:", formatCurrency(employee.getGrossSemiMonthlyRate()));
        addFieldToSection(section, "⏰ Hourly Rate:", formatCurrency(employee.getHourlyRate()));

        return section;
    }

    private JPanel createGovernmentNumbersSection() {
        JPanel section = createSectionPanel("🏛️ Government Numbers");

        addFieldToSection(section, "🆔 SSS Number:", getValue(employee.getSssNumber()));
        addFieldToSection(section, "🏥 PhilHealth Number:", getValue(employee.getPhilhealthNumber()));
        addFieldToSection(section, "📋 TIN Number:", getValue(employee.getTinNumber()));
        addFieldToSection(section, "🏠 Pag-IBIG Number:", getValue(employee.getPagibigNumber()));

        return section;
    }

    private JPanel createEmergencyContactSection() {
        JPanel section = createSectionPanel("🚨 Emergency Contact");

        addFieldToSection(section, "👥 Contact Person:", "Not specified"); // Placeholder
        addFieldToSection(section, "📞 Contact Number:", "Not specified"); // Placeholder
        addFieldToSection(section, "🔗 Relationship:", "Not specified"); // Placeholder
        addFieldToSection(section, "🏠 Contact Address:", "Not specified"); // Placeholder

        return section;
    }

    private JPanel createSectionPanel(String title) {
        JPanel section = new JPanel(new GridBagLayout());
        section.setBackground(AppConstants.PANEL_BACKGROUND);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_SOLID, 1),
                new EmptyBorder(20, 20, 20, 20)));

        // Section title with icon
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 15, 0);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppConstants.SUBHEADING_FONT);
        titleLabel.setForeground(AppConstants.ACCENT_PRIMARY);
        section.add(titleLabel, gbc);

        // Separator line
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 0);
        JSeparator separator = new JSeparator();
        separator.setForeground(AppConstants.BORDER_SOLID);
        section.add(separator, gbc);

        return section;
    }

    private void addFieldToSection(JPanel section, String label, String value) {
        GridBagConstraints gbc = new GridBagConstraints();
        int currentRow = section.getComponentCount() / 2; // Approximate row calculation

        // Label
        gbc.gridx = 0;
        gbc.gridy = currentRow + 2; // +2 to account for title and separator
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 20);
        section.add(createFieldLabel(label), gbc);

        // Value
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        section.add(createFieldValue(value), gbc);
    }

    // Helper methods for creating UI components
    private JLabel createLabelWithIcon(String icon, String text) {
        JLabel label = new JLabel(icon + " " + text);
        label.setFont(AppConstants.NORMAL_FONT);
        label.setForeground(AppConstants.TEXT_SECONDARY);
        return label;
    }

    private JLabel createHighlightedValue(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(AppConstants.NORMAL_FONT.getName(), Font.BOLD, AppConstants.NORMAL_FONT.getSize() + 1));
        label.setForeground(AppConstants.ACCENT_PRIMARY);
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(AppConstants.NORMAL_FONT);
        label.setForeground(AppConstants.TEXT_COLOR);
        return label;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(AppConstants.NORMAL_FONT);
        label.setForeground(AppConstants.TEXT_SECONDARY);
        return label;
    }

    private JLabel createFieldValue(String text) {
        JLabel label = new JLabel(text);
        label.setFont(AppConstants.NORMAL_FONT);
        label.setForeground(AppConstants.TEXT_COLOR);
        return label;
    }

    private JLabel createStatusBadge(String status) {
        JLabel statusLabel = new JLabel("● " + status);
        statusLabel
                .setFont(new Font(AppConstants.NORMAL_FONT.getName(), Font.BOLD, AppConstants.NORMAL_FONT.getSize()));

        if ("Active".equalsIgnoreCase(status) || "Regular".equalsIgnoreCase(status)) {
            statusLabel.setForeground(AppConstants.SUCCESS_COLOR);
        } else {
            statusLabel.setForeground(AppConstants.WARNING_COLOR);
        }

        return statusLabel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        // Back to List button
        JButton backButton = AppUtils.createSecondaryButton("⬅ Back to List");
        backButton.addActionListener(e -> dispose());

        // Edit Employee button
        JButton editButton = AppUtils.createPrimaryButton("✏️ Edit Employee");
        editButton.setBackground(AppConstants.EDIT_BUTTON_COLOR);
        editButton.addActionListener(e -> editEmployee());

        buttonPanel.add(backButton);
        buttonPanel.add(editButton);

        return buttonPanel;
    }

    // Action methods
    private void editEmployee() {
        // Use the existing EmployeeDialog.showEditDialog method
        if (com.motorph.view.dialog.EmployeeDialog.showEditDialog(
                (JFrame) getParent(), employeeController, employee)) {
            // Employee was successfully edited, close this details dialog
            dispose();
        }
        // If edit was cancelled or failed, keep the details dialog open
    }

    // Helper methods for data processing and formatting
    private String getValue(String value) {
        return (value != null && !value.trim().isEmpty()) ? value : "N/A";
    }

    private String formatCurrency(double amount) {
        return currencyFormat.format(amount);
    }

    private String formatDate(java.time.LocalDate date) {
        if (date != null) {
            return date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
        }
        return "N/A";
    }

    private String getDepartmentFromPosition(String position) {
        if (position == null)
            return "N/A";

        String pos = position.toLowerCase();
        if (pos.contains("chief executive") || pos.contains("ceo")) {
            return "Executive";
        } else if (pos.contains("chief operating") || pos.contains("coo")) {
            return "Operations";
        } else if (pos.contains("chief finance") || pos.contains("cfo") || pos.contains("finance")) {
            return "Finance";
        } else if (pos.contains("chief marketing") || pos.contains("cmo") || pos.contains("marketing")) {
            return "Marketing";
        } else if (pos.contains("hr") || pos.contains("human resources")) {
            return "Human Resources";
        } else if (pos.contains("accounting")) {
            return "Accounting";
        } else if (pos.contains("it") || pos.contains("technology")) {
            return "IT";
        } else if (pos.contains("sales")) {
            return "Sales";
        } else if (pos.contains("supply chain") || pos.contains("logistics")) {
            return "Operations";
        } else if (pos.contains("customer service")) {
            return "Customer Service";
        }
        return "General";
    }
}

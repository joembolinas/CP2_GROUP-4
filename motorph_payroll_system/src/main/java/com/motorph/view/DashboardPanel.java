package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.motorph.controller.EmployeeController;
import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.motorph.util.UIConstants;

/**
 * Modern Dashboard panel with statistics cards, quick links, and recent
 * activity.
 * Replaces the old MainMenuPanel with data-driven insights.
 */
public class DashboardPanel extends JPanel {

    private final MainFrame mainFrame;
    private final EmployeeController employeeController;

    public DashboardPanel(MainFrame mainFrame, EmployeeController employeeController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND_COLOR);

        // Main container with proper spacing
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(UIConstants.BACKGROUND_COLOR);
        container.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Header section
        JPanel headerPanel = createHeaderPanel();
        container.add(headerPanel, BorderLayout.NORTH);

        // Main content area
        JPanel contentPanel = createMainContentPanel();
        container.add(contentPanel, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIConstants.BACKGROUND_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(UIConstants.TEXT_COLOR);

        header.add(titleLabel, BorderLayout.WEST);
        return header;
    }

    private JPanel createMainContentPanel() {
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(UIConstants.BACKGROUND_COLOR);

        // Statistics cards at the top
        JPanel statsPanel = createStatisticsPanel();
        mainContent.add(statsPanel, BorderLayout.NORTH);

        // Content area with Quick Links and Recent Activity
        JPanel contentArea = new JPanel(new GridLayout(1, 2, 30, 0));
        contentArea.setBackground(UIConstants.BACKGROUND_COLOR);
        contentArea.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        JPanel quickLinksPanel = createQuickLinksPanel();
        JPanel recentActivityPanel = createRecentActivityPanel();

        contentArea.add(quickLinksPanel);
        contentArea.add(recentActivityPanel);

        mainContent.add(contentArea, BorderLayout.CENTER);
        return mainContent;
    }

    private JPanel createStatisticsPanel() {
        JPanel statsContainer = new JPanel(new GridLayout(1, 4, 20, 0));
        statsContainer.setBackground(UIConstants.BACKGROUND_COLOR);

        // Get actual data for statistics
        List<Employee> allEmployees = employeeController.getAllEmployees();
        int totalEmployees = allEmployees.size();

        // Calculate today's attendance statistics
        int onTimeToday = calculateOnTimeToday();
        int lateToday = calculateLateToday();
        int onLeave = 5; // Placeholder

        // Create statistic cards
        JPanel totalEmployeesCard = createStatCard("👥", "Total\nEmployees", String.valueOf(totalEmployees),
                UIConstants.PRIMARY_BUTTON_COLOR);
        JPanel onTimeCard = createStatCard("✅", "On Time\nToday", String.valueOf(onTimeToday),
                UIConstants.SUCCESS_COLOR);
        JPanel lateCard = createStatCard("⚠️", "Late\nToday", String.valueOf(lateToday), UIConstants.WARNING_COLOR);
        JPanel onLeaveCard = createStatCard("🏖️", "On Leave", String.valueOf(onLeave),
                UIConstants.DELETE_BUTTON_COLOR);

        statsContainer.add(totalEmployeesCard);
        statsContainer.add(onTimeCard);
        statsContainer.add(lateCard);
        statsContainer.add(onLeaveCard);

        return statsContainer;
    }

    private JPanel createStatCard(String icon, String label, String value, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(24, 20, 24, 20)));

        // Icon and value section
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        iconLabel.setForeground(accentColor);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(UIConstants.TEXT_COLOR);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        topSection.add(iconLabel, BorderLayout.WEST);
        topSection.add(valueLabel, BorderLayout.EAST);

        // Label section
        JLabel labelLabel = new JLabel("<html>" + label.replace("\n", "<br>") + "</html>");
        labelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelLabel.setForeground(UIConstants.TEXT_SECONDARY);

        card.add(topSection, BorderLayout.NORTH);
        card.add(Box.createVerticalStrut(8), BorderLayout.CENTER);
        card.add(labelLabel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createQuickLinksPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(UIConstants.BACKGROUND_COLOR);

        // Header
        JLabel headerLabel = new JLabel("Quick Links");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(UIConstants.TEXT_COLOR);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Quick links content
        JPanel linksPanel = new JPanel();
        linksPanel.setLayout(new BoxLayout(linksPanel, BoxLayout.Y_AXIS));
        linksPanel.setBackground(Color.WHITE);
        linksPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));

        // Add quick link buttons
        JPanel addEmployeeLink = createQuickLinkItem("👤", "Add New Employee",
                "Quickly add a new employee to the system", () -> {
                    mainFrame.showEmployeeList();
                    // You could trigger the add employee dialog here
                });

        JPanel processPayrollLink = createQuickLinkItem("💰", "Process Payroll",
                "Calculate and process employee payroll", () -> mainFrame.showPayrollManagement());

        JPanel generateReportsLink = createQuickLinkItem("📊", "Generate Reports",
                "Create payroll and compliance reports", () -> mainFrame.showReports());

        linksPanel.add(addEmployeeLink);
        linksPanel.add(Box.createVerticalStrut(16));
        linksPanel.add(processPayrollLink);
        linksPanel.add(Box.createVerticalStrut(16));
        linksPanel.add(generateReportsLink);

        container.add(headerLabel, BorderLayout.NORTH);
        container.add(linksPanel, BorderLayout.CENTER);

        return container;
    }

    private JPanel createQuickLinkItem(String icon, String title, String description, Runnable action) {
        JPanel item = new JPanel(new BorderLayout());
        item.setOpaque(false);
        item.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        iconLabel.setPreferredSize(new Dimension(32, 32));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Text content
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(UIConstants.TEXT_COLOR);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(UIConstants.TEXT_SECONDARY);

        textPanel.add(titleLabel);
        textPanel.add(descLabel);

        item.add(iconLabel, BorderLayout.WEST);
        item.add(textPanel, BorderLayout.CENTER);

        // Add click handler
        item.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (action != null) {
                    action.run();
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                item.setOpaque(true);
                item.setBackground(UIConstants.TABLE_ROW_HOVER);
                item.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                item.setOpaque(false);
                item.repaint();
            }
        });

        return item;
    }

    private JPanel createRecentActivityPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(UIConstants.BACKGROUND_COLOR);

        // Header
        JLabel headerLabel = new JLabel("Recent Activity");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(UIConstants.TEXT_COLOR);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Activity content
        JPanel activityPanel = new JPanel();
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        activityPanel.setBackground(Color.WHITE);
        activityPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));

        // Add activity items (placeholder data)
        activityPanel.add(createActivityItem("📊", "You generated the payroll for June 2025.", "5m ago"));
        activityPanel.add(Box.createVerticalStrut(16));
        activityPanel.add(createActivityItem("👤", "Rosie Atienza was added as a new employee.", "2h ago"));
        activityPanel.add(Box.createVerticalStrut(16));
        activityPanel.add(createActivityItem("✏️", "The details for employee #10007 were updated.", "1d ago"));

        container.add(headerLabel, BorderLayout.NORTH);
        container.add(activityPanel, BorderLayout.CENTER);

        return container;
    }

    private JPanel createActivityItem(String icon, String message, String time) {
        JPanel item = new JPanel(new BorderLayout());
        item.setOpaque(false);

        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        iconLabel.setPreferredSize(new Dimension(24, 24));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setForeground(UIConstants.TEXT_COLOR);

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        timeLabel.setForeground(UIConstants.TEXT_SECONDARY);

        contentPanel.add(messageLabel);
        contentPanel.add(timeLabel);

        item.add(iconLabel, BorderLayout.WEST);
        item.add(contentPanel, BorderLayout.CENTER);

        return item;
    }

    // Helper methods for calculating statistics
    private int calculateOnTimeToday() {
        LocalDate today = LocalDate.now();
        List<AttendanceRecord> todayRecords = employeeController.getAttendanceRecordsForDate(today);

        if (todayRecords.isEmpty()) {
            // If no data for today, calculate from recent data as example
            LocalDate cutoffDate = today.minusDays(30);
            List<AttendanceRecord> recentRecords = employeeController.getAttendanceRecordsInRange(cutoffDate, today);

            long onTimeCount = recentRecords.stream()
                    .filter(record -> record.getTimeIn() != null &&
                            record.getTimeIn().isBefore(LocalTime.of(9, 0))) // Assuming 9 AM is start time
                    .count();

            // Scale to daily average
            return (int) (onTimeCount / Math.max(1, recentRecords.size() / 30.0 *
                    employeeController.getAllEmployees().size()));
        }

        return (int) todayRecords.stream()
                .filter(record -> record.getTimeIn() != null &&
                        record.getTimeIn().isBefore(LocalTime.of(9, 0)))
                .count();
    }

    private int calculateLateToday() {
        LocalDate today = LocalDate.now();
        List<AttendanceRecord> todayRecords = employeeController.getAttendanceRecordsForDate(today);

        if (todayRecords.isEmpty()) {
            // If no data for today, calculate from recent data as example
            LocalDate cutoffDate = today.minusDays(30);
            List<AttendanceRecord> recentRecords = employeeController.getAttendanceRecordsInRange(cutoffDate, today);

            long lateCount = recentRecords.stream()
                    .filter(record -> record.getTimeIn() != null &&
                            record.getTimeIn().isAfter(LocalTime.of(9, 0))) // Assuming 9 AM is start time
                    .count();

            // Scale to daily average
            return (int) (lateCount / Math.max(1, recentRecords.size() / 30.0 *
                    employeeController.getAllEmployees().size()));
        }

        return (int) todayRecords.stream()
                .filter(record -> record.getTimeIn() != null &&
                        record.getTimeIn().isAfter(LocalTime.of(9, 0)))
                .count();
    }
}

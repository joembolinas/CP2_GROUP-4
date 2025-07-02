package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
import com.motorph.util.AppConstants;
import com.motorph.util.AppUtils;

/**
 * Modern Dashboard panel with statistics cards, quick links, and recent
 * activity.
 * Replaces the old MainMenuPanel with data-driven insights.
 */
public class Dashboard extends JPanel {

    private final MainFrame mainFrame;
    private final EmployeeController employeeController;

    public Dashboard(MainFrame mainFrame, EmployeeController employeeController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(AppConstants.BACKGROUND_COLOR);

        // Main container with proper spacing
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(AppConstants.BACKGROUND_COLOR);
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
        header.setBackground(AppConstants.BACKGROUND_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Left side - Dashboard title
        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        // Right side - Personalized greeting with date
        JPanel greetingPanel = createPersonalizedGreeting();

        header.add(titleLabel, BorderLayout.WEST);
        header.add(greetingPanel, BorderLayout.EAST);
        return header;
    }

    private JPanel createPersonalizedGreeting() {
        JPanel greetingPanel = new JPanel();
        greetingPanel.setLayout(new BoxLayout(greetingPanel, BoxLayout.Y_AXIS));
        greetingPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        // Get current user and date info
        String userName = "Administrator"; // Default fallback
        try {
            if (AppUtils.getCurrentUser() != null) {
                userName = AppUtils.getCurrentUser().getUsername();
            }
        } catch (Exception e) {
            // Use default if unable to get current user
        }
        LocalDate today = LocalDate.now();
        String dayName = today.getDayOfWeek().name();
        String formattedDay = dayName.substring(0, 1).toUpperCase() + dayName.substring(1).toLowerCase();
        String monthName = today.getMonth().name();
        String formattedMonth = monthName.substring(0, 1).toUpperCase()
                + monthName.substring(1).toLowerCase().substring(0, 3);

        // Create greeting text
        String greetingText = String.format("Hi %s! Today is %s %s %02d, %d",
                userName, formattedDay, formattedMonth, today.getDayOfMonth(), today.getYear());

        JLabel greetingLabel = new JLabel(greetingText);
        greetingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        greetingLabel.setForeground(AppConstants.TEXT_SECONDARY);
        greetingLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Add payroll cutoff countdown
        JLabel cutoffLabel = createCutoffCountdown();
        cutoffLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        greetingPanel.add(greetingLabel);
        greetingPanel.add(Box.createVerticalStrut(4));
        greetingPanel.add(cutoffLabel);

        return greetingPanel;
    }

    private JLabel createCutoffCountdown() {
        LocalDate today = LocalDate.now();
        LocalDate nextCutoff = calculateNextCutoff(today);
        long daysUntilCutoff = ChronoUnit.DAYS.between(today, nextCutoff);

        String cutoffText = String.format("📅 %d days until next payroll cutoff", daysUntilCutoff);

        JLabel cutoffLabel = new JLabel(cutoffText);
        cutoffLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cutoffLabel.setForeground(AppConstants.PRIMARY_COLOR);

        return cutoffLabel;
    }

    private LocalDate calculateNextCutoff(LocalDate currentDate) {
        // Assuming payroll cutoffs are on the 15th and last day of each month
        int day = currentDate.getDayOfMonth();
        LocalDate cutoff15th = currentDate.withDayOfMonth(15);
        LocalDate lastDay = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        if (day < 15) {
            return cutoff15th;
        } else if (day < currentDate.lengthOfMonth()) {
            return lastDay;
        } else {
            // Next month's 15th
            return currentDate.plusMonths(1).withDayOfMonth(15);
        }
    }

    private JPanel createMainContentPanel() {
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(AppConstants.BACKGROUND_COLOR);

        // Statistics cards at the top
        JPanel statsPanel = createStatisticsPanel();
        mainContent.add(statsPanel, BorderLayout.NORTH);

        // Content area with Quick Links and Interactive Calendar
        JPanel contentArea = new JPanel(new GridLayout(1, 2, 30, 0));
        contentArea.setBackground(AppConstants.BACKGROUND_COLOR);
        contentArea.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        JPanel quickLinksPanel = createQuickLinksPanel();
        JPanel calendarPanel = createInteractiveCalendarPanel();

        contentArea.add(quickLinksPanel);
        contentArea.add(calendarPanel);

        mainContent.add(contentArea, BorderLayout.CENTER);
        return mainContent;
    }

    private JPanel createStatisticsPanel() {
        JPanel statsContainer = new JPanel(new GridLayout(1, 4, 20, 0));
        statsContainer.setBackground(AppConstants.BACKGROUND_COLOR);

        // Get actual data for statistics
        List<Employee> allEmployees = employeeController.getAllEmployees();
        int totalEmployees = allEmployees.size();

        // Calculate additional useful metrics
        int activeEmployees = calculateActiveEmployees();
        double avgSalary = calculateAverageSalary();
        int recentHires = calculateRecentHires();

        // Create modern, functional statistic cards
        JPanel totalEmployeesCard = createStatCard("👥", "Total\nEmployees", String.valueOf(totalEmployees),
                AppConstants.PRIMARY_BUTTON_COLOR);

        JPanel activeEmployeesCard = createStatCard("✅", "Active\nEmployees", String.valueOf(activeEmployees),
                AppConstants.SUCCESS_COLOR);

        JPanel avgSalaryCard = createStatCard("💰", "Avg Monthly\nSalary", String.format("₱%.0f", avgSalary),
                AppConstants.ACCENT_PRIMARY); //TODO CHANGE IT

        JPanel recentHiresCard = createStatCard("📅", "New Hires\n(This Month)", String.valueOf(recentHires),
                AppConstants.WARNING_COLOR);

        statsContainer.add(totalEmployeesCard);
        statsContainer.add(activeEmployeesCard);
        statsContainer.add(avgSalaryCard);
        statsContainer.add(recentHiresCard);

        return statsContainer;
    }

    private JPanel createStatCard(String icon, String label, String value, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(24, 20, 24, 20)));

        // Icon and value section
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        iconLabel.setForeground(accentColor);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(AppConstants.TEXT_COLOR);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        topSection.add(iconLabel, BorderLayout.WEST);
        topSection.add(valueLabel, BorderLayout.EAST);

        // Label section
        JLabel labelLabel = new JLabel("<html>" + label.replace("\n", "<br>") + "</html>");
        labelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelLabel.setForeground(AppConstants.TEXT_SECONDARY);

        card.add(topSection, BorderLayout.NORTH);
        card.add(Box.createVerticalStrut(8), BorderLayout.CENTER);
        card.add(labelLabel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createQuickLinksPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(AppConstants.BACKGROUND_COLOR);

        // Header
        JLabel headerLabel = new JLabel("Quick Links");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(AppConstants.TEXT_COLOR);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Quick links content
        JPanel linksPanel = new JPanel();
        linksPanel.setLayout(new BoxLayout(linksPanel, BoxLayout.Y_AXIS));
        linksPanel.setBackground(Color.WHITE);
        linksPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
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
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(AppConstants.TEXT_SECONDARY);

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
                item.setBackground(AppConstants.TABLE_ROW_HOVER);
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
        container.setBackground(AppConstants.BACKGROUND_COLOR);

        // Header
        JLabel headerLabel = new JLabel("Recent Activity");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(AppConstants.TEXT_COLOR);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Activity content
        JPanel activityPanel = new JPanel();
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        activityPanel.setBackground(Color.WHITE);
        activityPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
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
        messageLabel.setForeground(AppConstants.TEXT_COLOR);

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        timeLabel.setForeground(AppConstants.TEXT_SECONDARY);

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

    // Helper methods for calculating new statistics
    private int calculateActiveEmployees() {
        List<Employee> allEmployees = employeeController.getAllEmployees();
        // For now, assume all employees are active. In a real system, you'd check
        // employment status
        return (int) allEmployees.stream()
                .filter(emp -> emp != null) // Simple active filter - you can enhance this
                .count();
    }

    private double calculateAverageSalary() {
        List<Employee> allEmployees = employeeController.getAllEmployees();
        if (allEmployees.isEmpty())
            return 0.0;

        // Calculate average basic salary
        double totalSalary = allEmployees.stream()
                .mapToDouble(emp -> emp.getBasicSalary())
                .sum();
        return totalSalary / allEmployees.size();
    }

    private int calculateRecentHires() {
        // For demo purposes, return a reasonable number
        // In a real system, you'd check hiring dates within the current month
        // Since we don't have hire dates in the current model, return a placeholder
        return 3; // You can enhance this when hire_date field is available
    }

    private JPanel createInteractiveCalendarPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(AppConstants.BACKGROUND_COLOR);

        // Header
        JLabel headerLabel = new JLabel("📅 Calendar Overview");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(AppConstants.TEXT_COLOR);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Calendar content
        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBackground(Color.WHITE);
        calendarPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Current month header
        LocalDate today = LocalDate.now();
        String monthYear = today.getMonth().name().substring(0, 1).toUpperCase() +
                today.getMonth().name().substring(1).toLowerCase() + " " + today.getYear();

        JLabel monthLabel = new JLabel(monthYear);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        monthLabel.setForeground(AppConstants.TEXT_COLOR);
        monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        monthLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Mini calendar grid
        JPanel miniCalendar = createMiniCalendar(today);

        // Upcoming events/payroll dates
        JPanel upcomingPanel = createUpcomingEvents();

        calendarPanel.add(monthLabel, BorderLayout.NORTH);
        calendarPanel.add(miniCalendar, BorderLayout.CENTER);
        calendarPanel.add(upcomingPanel, BorderLayout.SOUTH);

        container.add(headerLabel, BorderLayout.NORTH);
        container.add(calendarPanel, BorderLayout.CENTER);

        return container;
    }

    private JPanel createMiniCalendar(LocalDate currentDate) {
        JPanel calendar = new JPanel(new GridLayout(7, 7, 2, 2));
        calendar.setBackground(Color.WHITE);

        // Day headers
        String[] dayHeaders = { "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" };
        for (String day : dayHeaders) {
            JLabel dayLabel = new JLabel(day);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
            dayLabel.setForeground(AppConstants.TEXT_SECONDARY);
            dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
            calendar.add(dayLabel);
        }

        // Get first day of month and number of days
        LocalDate firstDay = currentDate.withDayOfMonth(1);
        int daysInMonth = currentDate.lengthOfMonth();
        int startDay = firstDay.getDayOfWeek().getValue() % 7; // Sunday = 0

        // Add empty cells for days before month starts
        for (int i = 0; i < startDay; i++) {
            calendar.add(new JLabel(""));
        }

        // Add days of the month
        for (int day = 1; day <= daysInMonth; day++) {
            JLabel dayLabel = new JLabel(String.valueOf(day));
            dayLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
            dayLabel.setPreferredSize(new Dimension(25, 25));

            // Highlight today
            if (day == currentDate.getDayOfMonth()) {
                dayLabel.setOpaque(true);
                dayLabel.setBackground(AppConstants.PRIMARY_COLOR);
                dayLabel.setForeground(Color.WHITE);
                dayLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            } else {
                dayLabel.setForeground(AppConstants.TEXT_COLOR);
            }

            // Highlight payroll dates (15th and last day)
            if (day == 15 || day == daysInMonth) {
                dayLabel.setForeground(AppConstants.WARNING_COLOR);
                dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
            }

            calendar.add(dayLabel);
        }

        return calendar;
    }

    private JPanel createUpcomingEvents() {
        JPanel events = new JPanel();
        events.setLayout(new BoxLayout(events, BoxLayout.Y_AXIS));
        events.setBackground(Color.WHITE);
        events.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JLabel eventsTitle = new JLabel("📋 Upcoming Events");
        eventsTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        eventsTitle.setForeground(AppConstants.TEXT_COLOR);

        LocalDate nextCutoff = calculateNextCutoff(LocalDate.now());
        long daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), nextCutoff);

        JLabel cutoffEvent = new JLabel(String.format("• Payroll cutoff in %d days", daysUntil));
        cutoffEvent.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        cutoffEvent.setForeground(AppConstants.TEXT_SECONDARY);

        JLabel monthEndEvent = new JLabel("• Monthly reports due end of month");
        monthEndEvent.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        monthEndEvent.setForeground(AppConstants.TEXT_SECONDARY);

        events.add(eventsTitle);
        events.add(Box.createVerticalStrut(8));
        events.add(cutoffEvent);
        events.add(Box.createVerticalStrut(4));
        events.add(monthEndEvent);

        return events;
    }
}

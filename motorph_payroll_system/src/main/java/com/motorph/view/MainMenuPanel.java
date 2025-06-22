package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.motorph.util.UIConstants;
import com.motorph.util.UIUtils;

/**
 * Modern main menu panel matching the HTML mockup design.
 * Features card-based layout with hover effects and clean typography.
 */
public class MainMenuPanel extends JPanel {

    private final MainFrame mainFrame;

    /**
     * Constructor for the main menu panel
     */
    public MainMenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initPanel();
    }

    /**
     * Initialize the panel with modern card-based layout
     */
    private void initPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND_COLOR);

        // Main container with proper spacing
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(UIConstants.BACKGROUND_COLOR);
        container.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Header section
        JPanel headerPanel = createHeaderPanel();
        container.add(headerPanel, BorderLayout.NORTH);

        // Main content area with cards
        JPanel contentPanel = createContentPanel();
        container.add(contentPanel, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);
    }

    /**
     * Create the header section with title and subtitle
     */
    private JPanel createHeaderPanel() {
        JPanel header = UIUtils.createCardPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.WHITE);

        // Main title
        JLabel titleLabel = new JLabel("MotorPH Payroll System", SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Design Mockup", SwingConstants.CENTER);
        subtitleLabel.setFont(UIConstants.NORMAL_FONT);
        subtitleLabel.setForeground(UIConstants.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(CENTER_ALIGNMENT);

        header.add(titleLabel);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitleLabel);

        // Wrap in a container for proper spacing
        JPanel headerContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerContainer.setBackground(UIConstants.BACKGROUND_COLOR);
        headerContainer.add(header);

        return headerContainer;
    }

    /**
     * Create the main content panel with dashboard cards
     */
    private JPanel createContentPanel() {
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(UIConstants.BACKGROUND_COLOR);

        // Create grid for cards (2x2 layout)
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 24, 24));
        cardsPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        // Create dashboard cards
        JPanel employeeCard = createDashboardCard(
                "👥", "Employee Management",
                "View, add, and edit employee data.",
                () -> mainFrame.showEmployeeManagement());

        JPanel payrollCard = createDashboardCard(
                "💰", "Payroll Management",
                "Process salaries and view payroll history.",
                () -> mainFrame.showPayrollManagement());

        JPanel reportsCard = createDashboardCard(
                "📊", "Reports",
                "Generate payroll and government reports.",
                () -> mainFrame.showReports());

        JPanel exitCard = createDashboardCard(
                "🚪", "Exit Application",
                "Close the payroll system.",
                () -> System.exit(0));

        cardsPanel.add(employeeCard);
        cardsPanel.add(payrollCard);
        cardsPanel.add(reportsCard);
        cardsPanel.add(exitCard);

        contentWrapper.add(cardsPanel, BorderLayout.CENTER);
        return contentWrapper;
    }

    /**
     * Create a dashboard card with modern styling and hover effects
     */
    private JPanel createDashboardCard(String icon, String title, String description, Runnable action) {
        JPanel card = UIUtils.createDashboardCard(title, description);
        card.setLayout(new GridBagLayout());
        card.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 16, 0);

        // Icon
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setForeground(getCardIconColor(title));
        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(iconLabel, gbc);

        // Title
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.SUBHEADING_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 8, 0);
        card.add(titleLabel, gbc);

        // Description
        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>");
        descLabel.setFont(UIConstants.NORMAL_FONT);
        descLabel.setForeground(UIConstants.TEXT_SECONDARY);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        card.add(descLabel, gbc);

        // Add click handler
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.run();
            }
        });

        return card;
    }

    /**
     * Get appropriate color for card icon based on functionality
     */
    private Color getCardIconColor(String title) {
        switch (title.toLowerCase()) {
            case "employee management":
                return UIConstants.PRIMARY_BUTTON_COLOR; // Indigo
            case "payroll management":
                return UIConstants.SUCCESS_COLOR; // Green
            case "reports":
                return UIConstants.WARNING_COLOR; // Amber
            case "exit application":
                return UIConstants.DELETE_BUTTON_COLOR; // Red
            default:
                return UIConstants.TEXT_SECONDARY;
        }
    }
}

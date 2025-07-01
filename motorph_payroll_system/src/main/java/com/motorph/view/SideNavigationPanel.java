package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Modern side navigation panel for the MotorPH Payroll System.
 * Features a clean, professional design with hover effects and proper spacing.
 */
public class SideNavigationPanel extends JPanel {

    private final MainFrame mainFrame;
    private JPanel selectedPanel;
    private final Color SIDEBAR_BACKGROUND = new Color(51, 65, 85); // slate-700
    private final Color SIDEBAR_HOVER = new Color(71, 85, 105); // slate-600
    private final Color SIDEBAR_SELECTED = new Color(99, 102, 241); // indigo-600
    private final Color SIDEBAR_TEXT = new Color(226, 232, 240); // slate-200
    private final Color SIDEBAR_TEXT_MUTED = new Color(148, 163, 184); // slate-400
    private final Color SIDEBAR_TEXT_SELECTED = Color.WHITE;

    public SideNavigationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(SIDEBAR_BACKGROUND);
        setPreferredSize(new Dimension(220, 0));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 1));

        // Create main content area
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(SIDEBAR_BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Add MotorPH logo/brand
        JPanel brandPanel = createBrandPanel();
        contentPanel.add(brandPanel);
        contentPanel.add(Box.createVerticalStrut(30));

        // Add navigation section
        JLabel navLabel = new JLabel("MAIN NAVIGATION");
        navLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        navLabel.setForeground(SIDEBAR_TEXT_MUTED);
        navLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        contentPanel.add(navLabel);

        // Add navigation items
        JPanel dashboardItem = createNavigationItem("🏠", "Dashboard", () -> mainFrame.showMainMenu());
        JPanel employeesItem = createNavigationItem("👥", "Employees", () -> mainFrame.showEmployeeList());
        JPanel attendanceItem = createNavigationItem("📅", "Attendance", () -> showAttendancePanel());
        JPanel payrollItem = createNavigationItem("💰", "Payroll", () -> mainFrame.showPayrollManagement());
        JPanel reportsItem = createNavigationItem("📊", "Reports", () -> mainFrame.showReports());

        contentPanel.add(dashboardItem);
        contentPanel.add(employeesItem);
        contentPanel.add(attendanceItem);
        contentPanel.add(payrollItem);
        contentPanel.add(reportsItem);

        // Add spacer to push logout to bottom
        contentPanel.add(Box.createVerticalGlue());

        // Add logout at bottom
        JPanel logoutItem = createNavigationItem("🚪", "Logout", () -> mainFrame.logout());
        logoutItem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, SIDEBAR_TEXT_MUTED),
                BorderFactory.createEmptyBorder(15, 0, 0, 0)));
        contentPanel.add(logoutItem);

        add(contentPanel, BorderLayout.CENTER);

        // Set default selection
        setSelectedItem(dashboardItem);
    }

    private JPanel createBrandPanel() {
        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        brandPanel.setBackground(SIDEBAR_BACKGROUND);
        brandPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel brandLabel = new JLabel("MotorPH");
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        brandLabel.setForeground(Color.WHITE);
        brandPanel.add(brandLabel);

        return brandPanel;
    }

    private JPanel createNavigationItem(String icon, String text, Runnable action) {
        JPanel itemPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (this == selectedPanel) {
                    g2.setColor(SIDEBAR_SELECTED);
                    g2.fillRoundRect(10, 2, getWidth() - 20, getHeight() - 4, 6, 6);
                }

                g2.dispose();
            }
        };

        itemPanel.setLayout(new BorderLayout());
        itemPanel.setBackground(SIDEBAR_BACKGROUND);
        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        itemPanel.setPreferredSize(new Dimension(220, 44));
        itemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        itemPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        // Create content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Icon label
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        iconLabel.setForeground(SIDEBAR_TEXT);
        iconLabel.setPreferredSize(new Dimension(24, 24));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Text label
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textLabel.setForeground(SIDEBAR_TEXT);
        textLabel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        contentPanel.add(iconLabel, BorderLayout.WEST);
        contentPanel.add(textLabel, BorderLayout.CENTER);
        itemPanel.add(contentPanel, BorderLayout.CENTER);

        // Add hover effect and click handler
        itemPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (itemPanel != selectedPanel) {
                    itemPanel.setBackground(SIDEBAR_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (itemPanel != selectedPanel) {
                    itemPanel.setBackground(SIDEBAR_BACKGROUND);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setSelectedItem(itemPanel);
                if (action != null) {
                    action.run();
                }
            }
        });

        return itemPanel;
    }

    private void setSelectedItem(JPanel item) {
        // Reset previous selection
        if (selectedPanel != null) {
            selectedPanel.setBackground(SIDEBAR_BACKGROUND);
            updateItemColors(selectedPanel, SIDEBAR_TEXT);
        }

        // Set new selection
        selectedPanel = item;
        if (selectedPanel != null) {
            selectedPanel.setBackground(SIDEBAR_SELECTED);
            updateItemColors(selectedPanel, SIDEBAR_TEXT_SELECTED);
        }

        repaint();
    }

    private void updateItemColors(JPanel item, Color textColor) {
        // Update text colors in the item
        updateComponentColors(item, textColor);
    }

    private void updateComponentColors(java.awt.Container container, Color textColor) {
        for (java.awt.Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setForeground(textColor);
            } else if (component instanceof java.awt.Container) {
                updateComponentColors((java.awt.Container) component, textColor);
            }
        }
    }

    private void showAttendancePanel() {
        // For now, redirect to employees since we don't have a separate attendance
        // panel
        // You can implement a dedicated attendance panel later
        mainFrame.showEmployeeList();
    }

    /**
     * Update the selected navigation item based on the current panel
     */
    public void updateSelection(String panelName) {
        // You can implement this to sync the navigation selection with the current
        // panel
        repaint();
    }
}

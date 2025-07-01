package com.motorph.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Utility class for creating modern, styled UI components that match the
 * HTML mockup design with clean, professional appearance.
 */
public class UIUtils {

    /**
     * Create a primary button (indigo) matching the modern design
     */
    public static JButton createPrimaryButton(String text) {
        return createModernButton(text, UIConstants.PRIMARY_BUTTON_COLOR,
                UIConstants.PRIMARY_BUTTON_HOVER, UIConstants.BUTTON_TEXT_COLOR);
    }

    /**
     * Create a secondary button (gray) for navigation actions
     */
    public static JButton createSecondaryButton(String text) {
        return createModernButton(text, UIConstants.SECONDARY_BUTTON_COLOR,
                UIConstants.SECONDARY_BUTTON_HOVER, UIConstants.BUTTON_TEXT_COLOR);
    }

    /**
     * Create a danger button (red) for delete actions
     */
    public static JButton createDangerButton(String text) {
        return createModernButton(text, UIConstants.DELETE_BUTTON_COLOR,
                UIConstants.DELETE_BUTTON_HOVER, UIConstants.BUTTON_TEXT_COLOR);
    }

    /**
     * Create a small action button for table cells with icon-style appearance
     */
    public static JButton createActionButton(String text, boolean isDanger) {
        Color bgColor, hoverColor;
        if (isDanger) {
            bgColor = UIConstants.ACTION_DELETE_COLOR;
            hoverColor = UIConstants.ACTION_DELETE_HOVER;
        } else if (text.toLowerCase().contains("edit")) {
            bgColor = UIConstants.ACTION_EDIT_COLOR;
            hoverColor = UIConstants.ACTION_EDIT_HOVER;
        } else {
            bgColor = UIConstants.ACTION_VIEW_COLOR;
            hoverColor = UIConstants.ACTION_VIEW_HOVER;
        }

        JButton button = createModernButton(text, bgColor, hoverColor, UIConstants.BUTTON_TEXT_COLOR);

        // Smaller size for table cells
        button.setFont(UIConstants.SMALL_FONT);
        button.setPreferredSize(new Dimension(65, 28));

        return button;
    }

    /**
     * Create a modern card-style panel with subtle shadow effect
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Paint subtle shadow
                g2.setColor(UIConstants.CARD_SHADOW);
                g2.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2,
                        UIConstants.BORDER_RADIUS, UIConstants.BORDER_RADIUS);

                // Paint main background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2,
                        UIConstants.BORDER_RADIUS, UIConstants.BORDER_RADIUS);

                g2.dispose();
            }
        };

        panel.setBackground(UIConstants.CARD_BACKGROUND);
        panel.setBorder(new EmptyBorder(UIConstants.CARD_PADDING, UIConstants.CARD_PADDING,
                UIConstants.CARD_PADDING, UIConstants.CARD_PADDING));
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Create a modern input field with clean styling
     */
    public static JTextField createModernTextField() {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Paint background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(),
                        UIConstants.SMALL_BORDER_RADIUS, UIConstants.SMALL_BORDER_RADIUS);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        field.setFont(UIConstants.NORMAL_FONT);
        field.setPreferredSize(new Dimension(200, UIConstants.FIELD_HEIGHT));
        field.setBorder(new EmptyBorder(8, 12, 8, 12));
        field.setBackground(Color.WHITE);
        field.setOpaque(false);

        return field;
    }

    /**
     * Create a status badge component
     */
    public static JLabel createStatusBadge(String text, boolean isActive) {
        JLabel badge = new JLabel(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Paint rounded background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        if (isActive) {
            badge.setBackground(UIConstants.STATUS_ACTIVE_BG);
            badge.setForeground(UIConstants.STATUS_ACTIVE_TEXT);
        } else {
            badge.setBackground(UIConstants.STATUS_INACTIVE_BG);
            badge.setForeground(UIConstants.STATUS_INACTIVE_TEXT);
        }

        badge.setFont(UIConstants.SMALL_FONT);
        badge.setHorizontalAlignment(JLabel.CENTER);
        badge.setBorder(new EmptyBorder(4, 8, 4, 8));
        badge.setOpaque(false);

        return badge;
    }

    /**
     * Create a content panel with modern styling
     */
    public static JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UIConstants.PANEL_BACKGROUND);
        panel.setBorder(new EmptyBorder(UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING,
                UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING));
        return panel;
    }

    /**
     * Create a navigation-style panel with light background
     */
    public static JPanel createNavigationPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UIConstants.NAVIGATION_BACKGROUND);
        panel.setBorder(new EmptyBorder(12, 20, 12, 20));
        return panel;
    }

    /**
     * Create a main dashboard card with hover effects
     */
    public static JPanel createDashboardCard(String title, String description) {
        class HoverPanel extends JPanel {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Paint shadow (more prominent on hover)
                Color shadowColor = isHovered ? new Color(0, 0, 0, 20) : UIConstants.CARD_SHADOW;
                g2.setColor(shadowColor);
                int shadowOffset = isHovered ? 4 : 2;
                g2.fillRoundRect(shadowOffset, shadowOffset,
                        getWidth() - shadowOffset, getHeight() - shadowOffset,
                        UIConstants.BORDER_RADIUS, UIConstants.BORDER_RADIUS);

                // Paint main background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset,
                        UIConstants.BORDER_RADIUS, UIConstants.BORDER_RADIUS);

                g2.dispose();
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        }

        HoverPanel card = new HoverPanel();
        card.setBackground(UIConstants.CARD_BACKGROUND);
        card.setOpaque(false);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effects
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setHovered(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setHovered(false);
            }
        });

        return card;
    }

    /**
     * Create a modern button with rounded corners and hover effects
     */
    private static JButton createModernButton(String text, Color bgColor, Color hoverColor, Color fgColor) {
        class HoverButton extends JButton {
            private boolean isHovered = false;

            public HoverButton(String text) {
                super(text);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Paint background with current color
                Color currentBg = isHovered ? hoverColor : bgColor;
                g2.setColor(currentBg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(),
                        UIConstants.SMALL_BORDER_RADIUS, UIConstants.SMALL_BORDER_RADIUS);

                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Don't paint default border
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        }

        HoverButton button = new HoverButton(text);

        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, UIConstants.BUTTON_HEIGHT));

        // Add padding
        button.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setHovered(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setHovered(false);
            }
        });

        return button;
    }
}

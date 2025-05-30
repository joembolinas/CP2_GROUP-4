package com.motorph.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Utility class for creating styled UI components that match the prototype design.
 */
public class UIUtils {
    
    /**
     * Create a primary button (blue) matching the prototype style
     */
    public static JButton createPrimaryButton(String text) {
        return createStyledButton(text, UIConstants.BUTTON_COLOR, UIConstants.BUTTON_TEXT_COLOR);
    }
    
    /**
     * Create a danger button (red) for delete actions
     */
    public static JButton createDangerButton(String text) {
        return createStyledButton(text, UIConstants.DELETE_BUTTON_COLOR, UIConstants.BUTTON_TEXT_COLOR);
    }
    
    /**
     * Create a small action button for table cells
     */
    public static JButton createActionButton(String text, boolean isDanger) {
        Color bgColor = isDanger ? UIConstants.DELETE_BUTTON_COLOR : UIConstants.BUTTON_COLOR;
        JButton button = createStyledButton(text, bgColor, UIConstants.BUTTON_TEXT_COLOR);
        
        // Smaller size for table cells
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(70, 25));
        
        return button;
    }
    
    /**
     * Create a styled button with custom colors and modern appearance
     */
    private static JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Paint rounded background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), UIConstants.BORDER_RADIUS, UIConstants.BORDER_RADIUS);
                
                g2.dispose();
                super.paintComponent(g);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                // Don't paint default border - we handle it in paintComponent
            }
        };
        
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add padding similar to prototype
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        
        return button;
    }
    
    /**
     * Create a panel with white background and light border like in prototype
     */
    public static JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UIConstants.PANEL_BACKGROUND);
        panel.setBorder(new EmptyBorder(UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING, 
                                       UIConstants.DEFAULT_PADDING, UIConstants.DEFAULT_PADDING));
        return panel;
    }
    
    /**
     * Create a navigation-style panel with light gray background
     */
    public static JPanel createNavigationPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UIConstants.NAVIGATION_BACKGROUND);
        panel.setBorder(new EmptyBorder(8, 15, 8, 15));
        return panel;
    }
}

package com.motorph.view.dialog;

import com.motorph.util.InputValidator;
import com.motorph.util.ErrorHandler;
import com.motorph.util.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Dialog for selecting a date range with improved error handling.
 * Part of the intuitive UI implementation required by MPHCR01.
 */
public class DateRangeDialog extends JDialog {
    private JTextField startDateField;
    private JTextField endDateField;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean confirmed = false;
    
    public DateRangeDialog(JFrame parent, String title) {
        super(parent, title, true);
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Main panel with form fields
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        // Start date
        JLabel startDateLabel = new JLabel("Start Date (MM/DD/YYYY):");
        startDateLabel.setFont(UIConstants.NORMAL_FONT);
        startDateField = new JTextField(10);
        startDateField.setFont(UIConstants.NORMAL_FONT);
        formPanel.add(startDateLabel);
        formPanel.add(startDateField);
        
        // End date
        JLabel endDateLabel = new JLabel("End Date (MM/DD/YYYY):");
        endDateLabel.setFont(UIConstants.NORMAL_FONT);
        endDateField = new JTextField(10);
        endDateField.setFont(UIConstants.NORMAL_FONT);
        formPanel.add(endDateLabel);
        formPanel.add(endDateField);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        
        // Style buttons
        okButton.setBackground(UIConstants.BUTTON_COLOR);
        okButton.setFont(UIConstants.NORMAL_FONT);
        cancelButton.setBackground(UIConstants.BUTTON_COLOR);
        cancelButton.setFont(UIConstants.NORMAL_FONT);
        
        okButton.addActionListener(e -> {
            try {
                // Validate input fields
                LocalDate start = InputValidator.validateDate(startDateField.getText());
                LocalDate end = InputValidator.validateDate(endDateField.getText());
                InputValidator.validateDateRange(start, end);
                
                // If validation passes, set values and close dialog
                this.startDate = start;
                this.endDate = end;
                this.confirmed = true;
                dispose();
            } catch (IllegalArgumentException ex) {
                // Display validation error with improved error handling
                JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
        cancelButton.addActionListener(e -> {
            this.confirmed = false;
            dispose();
        });
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        // Add panels to dialog
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Set dialog properties
        pack();
        setResizable(false);
        setLocationRelativeTo(getParent());
    }
    
    /**
     * Get the selected start date if confirmed
     * @return LocalDate or null if canceled
     */
    public LocalDate getStartDate() {
        return confirmed ? startDate : null;
    }
    
    /**
     * Get the selected end date if confirmed
     * @return LocalDate or null if canceled
     */
    public LocalDate getEndDate() {
        return confirmed ? endDate : null;
    }
    
    /**
     * Check if user confirmed the dialog
     * @return true if confirmed, false if canceled
     */
    public boolean isConfirmed() {
        return confirmed;
    }
}

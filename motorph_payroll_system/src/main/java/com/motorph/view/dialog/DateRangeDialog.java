package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.motorph.util.AppUtils;
import com.motorph.util.AppConstants;

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
        formPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        // Start date
        JLabel startDateLabel = new JLabel("Start Date (MM/DD/YYYY):");
        startDateLabel.setFont(AppConstants.NORMAL_FONT);
        startDateField = new JTextField(10);
        startDateField.setFont(AppConstants.NORMAL_FONT);
        formPanel.add(startDateLabel);
        formPanel.add(startDateField);

        // End date
        JLabel endDateLabel = new JLabel("End Date (MM/DD/YYYY):");
        endDateLabel.setFont(AppConstants.NORMAL_FONT);
        endDateField = new JTextField(10);
        endDateField.setFont(AppConstants.NORMAL_FONT);
        formPanel.add(endDateLabel);
        formPanel.add(endDateField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        // Style buttons
        okButton.setBackground(AppConstants.BUTTON_COLOR);
        okButton.setFont(AppConstants.NORMAL_FONT);
        cancelButton.setBackground(AppConstants.BUTTON_COLOR);
        cancelButton.setFont(AppConstants.NORMAL_FONT);

        okButton.addActionListener(e -> {
            try {
                // Validate input fields
                LocalDate start = AppUtils.validateDate(startDateField.getText());
                LocalDate end = AppUtils.validateDate(endDateField.getText());
                AppUtils.validateDateRange(start, end);

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
                        JOptionPane.ERROR_MESSAGE);
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
     * 
     * @return LocalDate or null if canceled
     */
    public LocalDate getStartDate() {
        return confirmed ? startDate : null;
    }

    /**
     * Get the selected end date if confirmed
     * 
     * @return LocalDate or null if canceled
     */
    public LocalDate getEndDate() {
        return confirmed ? endDate : null;
    }

    /**
     * Check if user confirmed the dialog
     * 
     * @return true if confirmed, false if canceled
     */
    public boolean isConfirmed() {
        return confirmed;
    }
}

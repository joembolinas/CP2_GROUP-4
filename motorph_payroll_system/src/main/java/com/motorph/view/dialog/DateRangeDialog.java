package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.motorph.util.UIConstants;

/**
 * Dialog for selecting a date range with custom validation, slash formatting,
 * Enter key support, and clearing fields on error.
 */
public class DateRangeDialog extends JDialog {
    private JTextField startDateField;
    private JTextField endDateField;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean confirmed = false;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public DateRangeDialog(JFrame parent, String title) {
        super(parent, title, true);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        // Start Date
        JLabel startLabel = new JLabel("Start Date (MM/DD/YYYY):");
        startLabel.setFont(UIConstants.NORMAL_FONT);
        startDateField = new JTextField(10);
        startDateField.setFont(UIConstants.NORMAL_FONT);
        addDateFormatListener(startDateField);
        startDateField.addActionListener(e -> onOk());
        formPanel.add(startLabel);
        formPanel.add(startDateField);

        // End Date
        JLabel endLabel = new JLabel("End Date (MM/DD/YYYY):");
        endLabel.setFont(UIConstants.NORMAL_FONT);
        endDateField = new JTextField(10);
        endDateField.setFont(UIConstants.NORMAL_FONT);
        addDateFormatListener(endDateField);
        endDateField.addActionListener(e -> onOk());
        formPanel.add(endLabel);
        formPanel.add(endDateField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        getRootPane().setDefaultButton(okButton); // Enter triggers OK

        okButton.setBackground(UIConstants.BUTTON_COLOR);
        okButton.setFont(UIConstants.NORMAL_FONT);
        cancelButton.setBackground(UIConstants.BUTTON_COLOR);
        cancelButton.setFont(UIConstants.NORMAL_FONT);

        okButton.addActionListener(e -> onOk());
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setLocationRelativeTo(getParent());
    }

    /**
     * Automatically insert slashes as user types.
     */
    private void addDateFormatListener(JTextField dateField) {
        dateField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                String digits = dateField.getText().replaceAll("[^\\d]", "");
                StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length() && i < 8; i++) {
                    formatted.append(digits.charAt(i));
                    if ((i == 1 || i == 3) && i < digits.length() - 1) {
                        formatted.append('/');
                    }
                }

                String newText = formatted.toString();
                if (!newText.equals(dateField.getText())) {
                    dateField.setText(newText);
                }
            }
        });
    }

    /**
     * Validates input and clears fields if error occurs.
     */
    private void onOk() {
        String startText = startDateField.getText();
        String endText = endDateField.getText();

        if (startText.isEmpty() || endText.isEmpty()) {
            showError("Please fill in both start and end dates.");
            return;
        }

        try {
            LocalDate start = LocalDate.parse(startText, FORMATTER);
            LocalDate end = LocalDate.parse(endText, FORMATTER);

            if (end.isBefore(start)) {
                showError("End date cannot be earlier than start date.");
                return;
            }

            this.startDate = start;
            this.endDate = end;
            this.confirmed = true;
            dispose();

        } catch (DateTimeParseException e) {
            showError("Invalid date format. Please use MM/DD/YYYY.");
        }
    }

    /**
     * Shows an error message and clears both date fields.
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
        startDateField.setText("");
        endDateField.setText("");
        startDateField.requestFocus();
    }

    public LocalDate getStartDate() {
        return confirmed ? startDate : null;
    }

    public LocalDate getEndDate() {
        return confirmed ? endDate : null;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}

package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.motorph.util.InputValidator;
import com.motorph.util.UIConstants;

/**
 * Dialog for entering employee number with validation.
 */
public class EmployeeNumberInputDialog extends JDialog {
    private JTextField employeeNumberField;
    private int employeeNumber = -1;
    private boolean confirmed = false;
    
    public EmployeeNumberInputDialog(JFrame parent, String title) {
        super(parent, title, true);
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        JLabel label = new JLabel("Enter Employee No:");
        label.setFont(UIConstants.NORMAL_FONT);
        employeeNumberField = new JTextField(10);
        employeeNumberField.setFont(UIConstants.NORMAL_FONT);
        
        inputPanel.add(label);
        inputPanel.add(employeeNumberField);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
                // Validate employee number
                this.employeeNumber = InputValidator.validateEmployeeNumber(employeeNumberField.getText());
                this.confirmed = true;
                dispose();
            } catch (IllegalArgumentException ex) {
                // Display validation error
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
        
        // Add panels
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
        
        // Set dialog properties
        pack();
        setResizable(false);
        setLocationRelativeTo(getParent());
        
        // Set default button
        getRootPane().setDefaultButton(okButton);
    }
    
    /**
     * Get the entered employee number if confirmed
     * @return Employee number or -1 if canceled
     */
    public int getEmployeeNumber() {
        return confirmed ? employeeNumber : -1;
    }
    
    /**
     * Check if user confirmed the dialog
     * @return true if confirmed, false if canceled
     */
    public boolean isConfirmed() {
        return confirmed;
    }
}

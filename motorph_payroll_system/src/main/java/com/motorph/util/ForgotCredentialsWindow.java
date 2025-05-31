package com.motorph.util;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.util.List;
import com.motorph.repository.CredentialManager;

public class ForgotCredentialsWindow extends JDialog {

    private JTextField employeeNumberField;
    private JTextField lastNameField;
    private JButton remindButton;
    private JLabel resultLabel;

    public ForgotCredentialsWindow(Window owner) {
        super(owner, "Forgot Username/Password", ModalityType.APPLICATION_MODAL);
        setSize(450, 300);
        setLocationRelativeTo(owner);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Employee Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Employee Number:"), gbc);

        gbc.gridx = 1;
        employeeNumberField = new JTextField(15);
        add(employeeNumberField, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        lastNameField = new JTextField(15);
        add(lastNameField, gbc);

        // Button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        remindButton = new JButton("Recover Credentials");
        add(remindButton, gbc);

        // Result Label
        gbc.gridy = 3;
        resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, gbc);

        // Action Listener
        remindButton.addActionListener(e -> handleRecovery());
        getRootPane().setDefaultButton(remindButton);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void handleRecovery() {
        String empNum = employeeNumberField.getText().trim();
        String lastNameInput = lastNameField.getText().trim();

        if (empNum.isEmpty() || lastNameInput.isEmpty()) {
            resultLabel.setText("Please fill in all fields.");
            return;
        }

        List<String[]> rows = CredentialManager.loadAllRows();

        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row.length < 3) continue;

            String rowLastName = row[1].replaceAll("\\s+", "");
            String rowEmpNum = row[0].trim();
            String constructedUsername = rowLastName + rowEmpNum;

            // Match case-sensitive last name and employee number
            if (rowLastName.equals(lastNameInput.replaceAll("\\s+", "")) && rowEmpNum.equals(empNum)) {

                // Confirm recovery
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Username: " + constructedUsername + "\nWould you like to reset your password?",
                        "Credentials Found",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    String newPassword = JOptionPane.showInputDialog(this, "Enter your new password:");
                    if (newPassword != null && !newPassword.trim().isEmpty()) {
                        boolean updated = CredentialManager.updatePassword(constructedUsername, newPassword.trim());
                        if (updated) {
                            JOptionPane.showMessageDialog(this, "Password reset successfully.");
                            employeeNumberField.setText("");
                            lastNameField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to update password.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Password cannot be empty.");
                    }
                }

                resultLabel.setText("Your username is: " + constructedUsername);
                return;
            }
        }

        resultLabel.setText("No user found with those details.");
    }
}

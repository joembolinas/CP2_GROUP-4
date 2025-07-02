package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.motorph.model.PaySlip;
import com.motorph.util.AppConstants;
import com.motorph.util.AppUtils;

/**
 * Dialog for displaying an employee's payslip.
 */
public class PayslipDialog extends JDialog {

    public PayslipDialog(JFrame parent, PaySlip paySlip, String title) {
        super(parent, title, true);
        initComponents(paySlip);
    }

    private void initComponents(PaySlip paySlip) {
        // Create text area for payslip details
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Format the payslip details
        StringBuilder payslipDetails = new StringBuilder();
        payslipDetails.append("═══════════════════════════════════════════\n");
        payslipDetails.append("           EMPLOYEE PAYSLIP\n");
        payslipDetails.append("═══════════════════════════════════════════\n");
        payslipDetails.append("Employee No: ").append(paySlip.getEmployee().getEmployeeId()).append("\n");
        payslipDetails.append("Name: ").append(paySlip.getEmployee().getFullName()).append("\n");
        payslipDetails.append("Position: ").append(paySlip.getEmployee().getPosition()).append("\n");
        payslipDetails.append("Period: ").append(AppUtils.formatDateRange(paySlip.getStartDate(), paySlip.getEndDate()))
                .append("\n");
        payslipDetails.append("───────────────────────────────────────────\n");
        payslipDetails.append("HOURS WORKED:\n");
        payslipDetails.append(String.format("Regular Hours: %.2f\n", paySlip.getRegularHours()));
        payslipDetails.append(String.format("Overtime Hours: %.2f\n", paySlip.getOvertimeHours()));
        payslipDetails
                .append(String.format("Total Hours: %.2f\n", paySlip.getRegularHours() + paySlip.getOvertimeHours()));
        payslipDetails.append("───────────────────────────────────────────\n");
        payslipDetails.append("PAY DETAILS:\n");
        payslipDetails.append(String.format("Hourly Rate: ₱%,.2f\n", paySlip.getEmployee().getHourlyRate()));
        payslipDetails.append(String.format("Gross Pay: ₱%,.2f\n", paySlip.getGrossPay()));
        payslipDetails.append("───────────────────────────────────────────\n");
        payslipDetails.append("DEDUCTIONS:\n");
        payslipDetails.append(String.format("SSS: ₱%,.2f\n", paySlip.getDeductions().get("sss")));
        payslipDetails.append(String.format("PhilHealth: ₱%,.2f\n", paySlip.getDeductions().get("philhealth")));
        payslipDetails.append(String.format("Pag-IBIG: ₱%,.2f\n", paySlip.getDeductions().get("pagibig")));
        payslipDetails
                .append(String.format("Withholding Tax: ₱%,.2f\n", paySlip.getDeductions().get("withholdingTax")));
        payslipDetails.append("───────────────────────────────────────────\n");
        payslipDetails.append("ALLOWANCES:\n");
        payslipDetails.append(String.format("Rice Subsidy: ₱%,.2f\n", paySlip.getAllowances().get("rice")));
        payslipDetails.append(String.format("Phone Allowance: ₱%,.2f\n", paySlip.getAllowances().get("phone")));
        payslipDetails.append(String.format("Clothing Allowance: ₱%,.2f\n", paySlip.getAllowances().get("clothing")));
        payslipDetails.append("───────────────────────────────────────────\n");
        payslipDetails.append(String.format("FINAL NET PAY: ₱%,.2f\n", paySlip.getNetPay()));
        payslipDetails.append("═══════════════════════════════════════════\n");

        textArea.setText(payslipDetails.toString());

        // Add to a scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 500));

        // Add the scroll pane to the dialog
        getContentPane().add(scrollPane);

        // Add close button at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeButton = new JButton("Close");
        closeButton.setFont(AppConstants.NORMAL_FONT);
        closeButton.setBackground(AppConstants.BUTTON_COLOR);
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Set dialog properties
        pack();
        setLocationRelativeTo(getParent());
    }
}

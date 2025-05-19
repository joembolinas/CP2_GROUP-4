package com.motorph;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayrollCalculatorGUI extends JFrame {

    private JTextField hoursField, rateField;
    private JTextArea resultArea;

    public PayrollCalculatorGUI() {
        setTitle("Payroll Calculator");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        inputPanel.add(new JLabel("Hours Worked:"));
        hoursField = new JTextField();
        inputPanel.add(hoursField);

        inputPanel.add(new JLabel("Hourly Rate:"));
        rateField = new JTextField();
        inputPanel.add(rateField);

        JButton calculateButton = new JButton("Calculate");
        inputPanel.add(calculateButton);

        JButton clearButton = new JButton("Clear");
        inputPanel.add(clearButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculatePayroll();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hoursField.setText("");
                rateField.setText("");
                resultArea.setText("");
            }
        });
    }

    private void calculatePayroll() {
        try {
            double hours = Double.parseDouble(hoursField.getText());
            double rate = Double.parseDouble(rateField.getText());

            PayrollCalculator calc = new PayrollCalculator();

            double gross = calc.calculateGrossPay(hours, rate);
            double sss = calc.calculateSSSContribution(gross);
            double philhealth = calc.calculatePhilHealthContribution(gross);
            double pagibig = calc.calculatePagIbigContribution(gross);
            double taxable = gross - (sss + philhealth + pagibig);
            double tax = calc.calculateWithholdingTax(taxable);
            double net = calc.calculateNetPay(gross);

            resultArea.setText(String.format(
                "Gross Pay:         PHP %.2f%n" +
                "SSS Deduction:     PHP %.2f%n" +
                "PhilHealth:        PHP %.2f%n" +
                "Pag-IBIG:          PHP %.2f%n" +
                "Withholding Tax:   PHP %.2f%n" +
                "------------------------------%n" +
                "Net Pay:           PHP %.2f%n",
                gross, sss, philhealth, pagibig, tax, net
            ));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Set a nice look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            PayrollCalculatorGUI gui = new PayrollCalculatorGUI();
            gui.setVisible(true);
        });
    }
}

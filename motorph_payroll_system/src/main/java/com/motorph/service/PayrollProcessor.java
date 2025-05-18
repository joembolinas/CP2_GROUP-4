package com.motorph.service;

import java.util.HashMap;
import java.util.Map;

import com.motorph.util.PayrollConstants;

/**
 * Handles all payroll calculations including gross pay, net pay, and various deductions
 * like SSS, PhilHealth, Pag-IBIG, and withholding tax.
 */
public class PayrollProcessor {
    private final Map<Double, Double> sssTable;

    public PayrollProcessor() {
        this.sssTable = initSSSTable();
    }

    private Map<Double, Double> initSSSTable() {
        Map<Double, Double> table = new HashMap<>();
        table.put(4250.0, 180.0);
        table.put(4750.0, 202.5);
        table.put(5250.0, 225.0);
        table.put(5750.0, 247.5);
        table.put(6250.0, 270.0);
        table.put(6750.0, 292.5);
        table.put(7250.0, 315.0);
        table.put(7750.0, 337.5);
        table.put(8250.0, 360.0);
        table.put(8750.0, 382.5);
        table.put(9250.0, 405.0);
        table.put(9750.0, 427.5);
        table.put(10250.0, 450.0);
        table.put(10750.0, 472.5);
        table.put(11250.0, 495.0);
        table.put(11750.0, 517.5);
        table.put(12250.0, 540.0);
        table.put(12750.0, 562.5);
        table.put(13250.0, 585.0);
        table.put(13750.0, 607.5);
        table.put(14250.0, 630.0);
        table.put(14750.0, 652.5);
        table.put(15250.0, 675.0);
        table.put(15750.0, 697.5);
        table.put(16250.0, 720.0);
        table.put(16750.0, 742.5);
        table.put(17250.0, 765.0);
        table.put(17750.0, 787.5);
        table.put(18250.0, 810.0);
        table.put(18750.0, 832.5);
        table.put(19250.0, 855.0);
        table.put(19750.0, 877.5);
        table.put(20250.0, 900.0);
        table.put(20750.0, 922.5);
        table.put(Double.MAX_VALUE, 945.0);
        return table;
    }

    public double calculateGrossPay(double hoursWorked, double hourlyRate) {
        return hoursWorked * hourlyRate;
    }

    public double calculateNetPay(double grossPay) {
        double sssDeduction = calculateSSSContribution(grossPay);
        double philHealthDeduction = calculatePhilHealthContribution(grossPay);
        double pagIbigDeduction = calculatePagIbigContribution(grossPay);
        double taxableIncome = grossPay - (sssDeduction + philHealthDeduction + pagIbigDeduction);
        double withholdingTax = calculateWithholdingTax(taxableIncome);
        double netPay = grossPay - (sssDeduction + philHealthDeduction + pagIbigDeduction + withholdingTax);
        return Math.max(netPay, 0.0);
    }

    public double calculateSSSContribution(double grossPay) {
        double contribution = 0.0;
        for (Map.Entry<Double, Double> entry : sssTable.entrySet()) {
            if (grossPay < entry.getKey()) {
                contribution = entry.getValue();
                break;
            }
        }
        return contribution;
    }

    public double calculatePhilHealthContribution(double grossPay) {
        return grossPay * PayrollConstants.PHILHEALTH_EMPLOYEE_CONTRIBUTION_RATE;
    }

    public double calculatePagIbigContribution(double grossPay) {
        return grossPay * PayrollConstants.PAGIBIG_EMPLOYEE_CONTRIBUTION_RATE;
    }

    public double calculateWithholdingTax(double taxableIncome) {
        final double TAX_BRACKET_1_THRESHOLD = 20833.00; // Monthly non-taxable up to P250,000/year
        final double TAX_BRACKET_2_THRESHOLD = 33333.00; // P250,001 to P400,000 / year
        final double TAX_BRACKET_3_THRESHOLD = 66667.00; // P400,001 to P800,000 / year
        final double TAX_BRACKET_4_THRESHOLD = 166667.00; // P800,001 to P2,000,000 / year
        final double TAX_BRACKET_5_THRESHOLD = 666667.00; // P2,000,001 to P8,000,000 / year

        final double TAX_RATE_2 = 0.20;
        final double TAX_RATE_3 = 0.25;
        final double TAX_RATE_4 = 0.30;
        final double TAX_RATE_5 = 0.32;
        final double TAX_RATE_6 = 0.35;

        final double FIXED_TAX_FOR_BRACKET_3 = 2500.00;    // Tax for income exceeding P20,833 up to P33,333 is (P33,333-P20,833)*20%
        final double FIXED_TAX_FOR_BRACKET_4 = 10833.33;   // P2,500 + (P66,667-P33,333)*25%
        final double FIXED_TAX_FOR_BRACKET_5 = 40833.33;   // P10,833.33 + (P166,667-P66,667)*30%
        final double FIXED_TAX_FOR_BRACKET_6 = 200833.33;  // P40,833.33 + (P666,667-P166,667)*32%

        if (taxableIncome <= TAX_BRACKET_1_THRESHOLD) { // Up to 20,833
            return 0.0;
        } else if (taxableIncome <= TAX_BRACKET_2_THRESHOLD) { // 20,833.01 to 33,333
            return (taxableIncome - TAX_BRACKET_1_THRESHOLD) * TAX_RATE_2;
        } else if (taxableIncome <= TAX_BRACKET_3_THRESHOLD) { // 33,333.01 to 66,667
            return FIXED_TAX_FOR_BRACKET_3 + (taxableIncome - TAX_BRACKET_2_THRESHOLD) * TAX_RATE_3;
        } else if (taxableIncome <= TAX_BRACKET_4_THRESHOLD) { // 66,667.01 to 166,667
            return FIXED_TAX_FOR_BRACKET_4 + (taxableIncome - TAX_BRACKET_3_THRESHOLD) * TAX_RATE_4;
        } else if (taxableIncome <= TAX_BRACKET_5_THRESHOLD) { // 166,667.01 to 666,667
            return FIXED_TAX_FOR_BRACKET_5 + (taxableIncome - TAX_BRACKET_4_THRESHOLD) * TAX_RATE_5;
        } else { // Over 666,667
            return FIXED_TAX_FOR_BRACKET_6 + (taxableIncome - TAX_BRACKET_5_THRESHOLD) * TAX_RATE_6;
        }
    }
}

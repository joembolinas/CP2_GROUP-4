package com.motorph;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles all payroll calculations including gross pay, net pay, and various deductions
 * like SSS, PhilHealth, Pag-IBIG, and withholding tax.
 */
public class PayrollCalculator {
    private static final double PHILHEALTH_RATE = 0.03;
    private static final double PAGIBIG_RATE = 0.02;
    private static final double SSS_CAP_PERCENT = 0.10;
    private static final double PHILHEALTH_CAP_PERCENT = 0.03;
    private static final double PAGIBIG_CAP_PERCENT = 0.02;
    private static final double TAX_CAP_PERCENT = 0.20;

    private final Map<Double, Double> sssTable;

    public PayrollCalculator() {
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
        double sssDeduction = Math.min(calculateSSSContribution(grossPay), grossPay * SSS_CAP_PERCENT);
        double philHealthDeduction = Math.min(calculatePhilHealthContribution(grossPay), grossPay * PHILHEALTH_CAP_PERCENT);
        double pagIbigDeduction = Math.min(calculatePagIbigContribution(grossPay), grossPay * PAGIBIG_CAP_PERCENT);
        double taxableIncome = grossPay - (sssDeduction + philHealthDeduction + pagIbigDeduction);
        double withholdingTax = Math.min(calculateWithholdingTax(taxableIncome), taxableIncome * TAX_CAP_PERCENT);
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
        return grossPay * PHILHEALTH_RATE;
    }

    public double calculatePagIbigContribution(double grossPay) {
        return grossPay * PAGIBIG_RATE;
    }

    public double calculateWithholdingTax(double taxableIncome) {
        final double TAX_BRACKET_1 = 2083.0;
        final double TAX_BRACKET_2 = 33333.0;
        final double TAX_BRACKET_3 = 66667.0;
        final double TAX_BRACKET_4 = 166667.0;
        final double TAX_BRACKET_5 = 666667.0;
        if (taxableIncome <= 0) {
            return 0.0;
        } else if (taxableIncome <= TAX_BRACKET_1) {
            return 0.0;
        } else if (taxableIncome <= TAX_BRACKET_2) {
            return (taxableIncome - TAX_BRACKET_1) * 0.20;
        } else if (taxableIncome <= TAX_BRACKET_3) {
            return 6250 + (taxableIncome - TAX_BRACKET_2) * 0.25;
        } else if (taxableIncome <= TAX_BRACKET_4) {
            return 14583.33 + (taxableIncome - TAX_BRACKET_3) * 0.30;
        } else if (taxableIncome <= TAX_BRACKET_5) {
            return 44583.33 + (taxableIncome - TAX_BRACKET_4) * 0.32;
        } else {
            return 204583.33 + (taxableIncome - TAX_BRACKET_5) * 0.35;
        }
    }
}

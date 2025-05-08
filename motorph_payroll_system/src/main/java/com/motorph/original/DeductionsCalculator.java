package com.motorph.original;

public class DeductionsCalculator {
    public static double calculateSSS(double s) { 
        if (s <= 3250) return 135; 
        if (s <= 24750) return 1102.5; 
        return 1125; 
    }

    public static double calculatePhilhealth(double s) { 
        double min = 10000, max = 80000; 
        double share = 0.015; 
        if (s < min) return min * share; 
        if (s > max) return max * share; 
        return s * share; 
    }

    public static double calculatePagibig(double s) { 
        return Math.min(s <= 1500 ? s * 0.01 : s * 0.02, 100); 
    }

    public static double calculateWithholdingTax(double s, double sss, double ph, double pi) { 
        double t = s - (sss + ph + pi); 
        if (t <= 20833) return 0; 
        if (t <= 33332) return (t - 20833) * 0.2; 
        if (t <= 66666) return 2500 + (t - 33333) * 0.25; 
        if (t <= 166666) return 10833.33 + (t - 66667) * 0.3; 
        if (t <= 666666) return 40833.33 + (t - 166667) * 0.32; 
        return 200833.33 + (t - 666667) * 0.35; 
    }
}
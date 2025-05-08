package com.motorph.original;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
public class MotorPHDataModels {
    public static class EmployeeData {
        private int employeeId; private String lastName, firstName, position, department, status; private LocalDate birthday; private double basicSalary, riceSubsidy, phoneAllowance, clothingAllowance;
        public EmployeeData(int employeeId, String lastName, String firstName, LocalDate birthday, String position, String department, String status, double basicSalary, double riceSubsidy, double phoneAllowance, double clothingAllowance) {
            this.employeeId = employeeId; this.lastName = lastName; this.firstName = firstName; this.birthday = birthday; this.position = position; this.department = department; this.status = status; this.basicSalary = basicSalary; this.riceSubsidy = riceSubsidy; this.phoneAllowance = phoneAllowance; this.clothingAllowance = clothingAllowance;
        }
        public double getHourlyRate() { return basicSalary / (22 * 8); }
        public int getEmployeeId() { return employeeId; }
        public String getLastName() { return lastName; }
        public String getFirstName() { return firstName; }
        public LocalDate getBirthday() { return birthday; }
        public String getPosition() { return position; }
        public String getDepartment() { return department; }
        public String getStatus() { return status; }
        public double getBasicSalary() { return basicSalary; }
        public double getRiceSubsidy() { return riceSubsidy; }
        public double getPhoneAllowance() { return phoneAllowance; }
        public double getClothingAllowance() { return clothingAllowance; }
    }
    public static class AttendanceRecord {
        private int employeeId; private LocalDate date; private LocalTime timeIn, timeOut; private boolean present;
        public AttendanceRecord(int employeeId, LocalDate date, LocalTime timeIn, LocalTime timeOut) { this.employeeId = employeeId; this.date = date; this.timeIn = timeIn; this.timeOut = timeOut; this.present = true; }
        public AttendanceRecord(int employeeId, LocalDate date, boolean present) { this.employeeId = employeeId; this.date = date; this.present = present; }
        public double getHoursWorked() { if (!present || timeIn == null || timeOut == null) return 0.0; double h = Duration.between(timeIn, timeOut).toMinutes() / 60.0; if (h > 5) h -= 1; return Math.min(h, 8.0); }
        public double getOvertimeHours() { if (!present || timeIn == null || timeOut == null) return 0.0; double h = Duration.between(timeIn, timeOut).toMinutes() / 60.0; if (h > 5) h -= 1; return Math.max(0, h - 8.0); }
        public int getEmployeeId() { return employeeId; }
        public LocalDate getDate() { return date; }
        public LocalTime getTimeIn() { return timeIn; }
        public LocalTime getTimeOut() { return timeOut; }
        public boolean isPresent() { return present; }
    }
    public static class PayrollResult {
        private EmployeeData employee; private LocalDate startDate, endDate; private int daysWorked; private double hoursWorked, overtimeHours, basicPay, overtimePay, riceSubsidy, phoneAllowance, clothingAllowance, sssDeduction, philhealthDeduction, pagibigDeduction, withholdingTax;
        public PayrollResult(EmployeeData employee, LocalDate startDate, LocalDate endDate) { this.employee = employee; this.startDate = startDate; this.endDate = endDate; }
        public double getGrossPay() { return basicPay + overtimePay + getTotalAllowances(); }
        public double getTotalAllowances() { return riceSubsidy + phoneAllowance + clothingAllowance; }
        public double getTotalDeductions() { return sssDeduction + philhealthDeduction + pagibigDeduction + withholdingTax; }
        public double getNetPay() { return getGrossPay() - getTotalDeductions(); }
        public EmployeeData getEmployee() { return employee; }
        public LocalDate getStartDate() { return startDate; }
        public LocalDate getEndDate() { return endDate; }
        public int getDaysWorked() { return daysWorked; }
        public void setDaysWorked(int d) { daysWorked = d; }
        public double getHoursWorked() { return hoursWorked; }
        public void setHoursWorked(double h) { hoursWorked = h; }
        public double getOvertimeHours() { return overtimeHours; }
        public void setOvertimeHours(double h) { overtimeHours = h; }
        public double getBasicPay() { return basicPay; }
        public void setBasicPay(double b) { basicPay = b; }
        public double getOvertimePay() { return overtimePay; }
        public void setOvertimePay(double o) { overtimePay = o; }
        public double getRiceSubsidy() { return riceSubsidy; }
        public void setRiceSubsidy(double r) { riceSubsidy = r; }
        public double getPhoneAllowance() { return phoneAllowance; }
        public void setPhoneAllowance(double p) { phoneAllowance = p; }
        public double getClothingAllowance() { return clothingAllowance; }
        public void setClothingAllowance(double c) { clothingAllowance = c; }
        public double getSssDeduction() { return sssDeduction; }
        public void setSssDeduction(double s) { sssDeduction = s; }
        public double getPhilhealthDeduction() { return philhealthDeduction; }
        public void setPhilhealthDeduction(double p) { philhealthDeduction = p; }
        public double getPagibigDeduction() { return pagibigDeduction; }
        public void setPagibigDeduction(double p) { pagibigDeduction = p; }
        public double getWithholdingTax() { return withholdingTax; }
        public void setWithholdingTax(double w) { withholdingTax = w; }
    }
}
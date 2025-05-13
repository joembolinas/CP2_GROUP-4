package com.motorph;

/**
 * Represents an employee with personal information and salary details.
 * Provides methods to get employee information and calculate hourly rate.
 */
public class Employee {
    private int employeeId;
    private String lastName;
    private String firstName;
    private String position;
    private String status;
    private double basicSalary;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;
    private double hourlyRate;

    public Employee(int employeeId, String lastName, String firstName, String position, 
                   String status, double basicSalary, double riceSubsidy, 
                   double phoneAllowance, double clothingAllowance) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.position = position;
        this.status = status;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        calculateHourlyRate();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void calculateHourlyRate() {
        // Assuming 21 work days and 8 hours per day
        this.hourlyRate = basicSalary / (PayrollConstants.STANDARD_WORK_DAYS_PER_MONTH * PayrollConstants.REGULAR_HOURS_PER_DAY);
    }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { 
        this.basicSalary = basicSalary;
        calculateHourlyRate(); // Update hourly rate when basic salary changes
    }
    public double getRiceSubsidy() { return riceSubsidy; }
    public void setRiceSubsidy(double riceSubsidy) { this.riceSubsidy = riceSubsidy; }
    public double getPhoneAllowance() { return phoneAllowance; }
    public void setPhoneAllowance(double phoneAllowance) { this.phoneAllowance = phoneAllowance; }
    public double getClothingAllowance() { return clothingAllowance; }
    public void setClothingAllowance(double clothingAllowance) { this.clothingAllowance = clothingAllowance; }
    public double getHourlyRate() { return hourlyRate; }
}

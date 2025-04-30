# MotorPH Payroll System - User Manual

![MotorPH Payroll System Logo](https://placekitten.com/800/200)

## Table of Contents

1. Introduction
2. Getting Started
3. Main Menu Navigation
4. Employee Management
5. Payroll Management
6. Reports
7. Troubleshooting
8. Appendix

---

## Introduction

Welcome to the MotorPH Payroll System User Manual. This document provides comprehensive guidance on operating the MotorPH Payroll System, a Java-based application designed for managing employee information, calculating payroll, and generating reports.

### System Overview

The MotorPH Payroll System provides the following core functionalities:

- **Employee Management**: Search for employees, view employee listings, and track attendance
- **Payroll Processing**: Calculate pay for all employees or individual employees, with automatic handling of overtime and allowances
- **Report Generation**: Generate detailed payslips and summary reports (weekly/monthly)

---

## Getting Started

### System Requirements

- Java Runtime Environment (JRE) 11 or higher
- Internet connection (required to access employee and attendance data)
- Minimum 1GB RAM
- 50MB free disk space

### Launching the Application

1. Open your terminal or command prompt
2. Navigate to the directory containing the MotorPH Payroll System application
3. Execute the following command:

```
java -jar motorph_payroll.jar
```

4. The system will load employee and attendance data from online sources
5. Once data loading is complete, the main menu will appear

**Note**: If there are any issues with data loading, an error message will be displayed. Ensure you have a stable internet connection and try again.

---

## Main Menu Navigation

The main menu is the central navigation hub of the system. From here, you can access all major features.

![Main Menu Screenshot](https://placekitten.com/600/300)

### Menu Options

The main menu offers four options:

1. **Employee Management**: Access employee-related functions (search, list, view attendance)
2. **Payroll Management**: Access payroll-related functions (generate payroll for all or specific employees)
3. **Reports**: Access reporting features (payslips, weekly/monthly summaries)
4. **Exit**: Close the application

### Navigation Tips

- Enter the number corresponding to your desired option (1-4)
- Invalid inputs will prompt an error message; you'll need to enter a valid option
- Most screens have a "Return to Main Menu" option (usually option 4 or 3)
- For any operation requiring dates, use MM/DD/YYYY format (e.g., 03/22/2025)

---

## Employee Management

The Employee Management module allows you to search for employees, view the complete employee listing, and track employee attendance.

### 1. Search Employee

This feature allows you to find specific employees by name or employee number.

**Steps to search for an employee:**

1. From the Main Menu, select option 1 (Employee Management)
2. Select option 1 (Search Employee)
3. Enter the search term (name or employee number)
4. The system will display all matching employees with their details:
   - Employee Number
   - Name
   - Position
   - Status
   - Hourly Rate

**Example search results:**
```
Emp#      Name                  Position             Status          Hourly Rate    
----------------------------------------------------------------------------------
10001     Jose Crisostomo       Team Leader          Regular         255.80       
```

### 2. List All Employees

This feature displays a complete list of all employees in the system.

**Steps to view all employees:**

1. From the Main Menu, select option 1 (Employee Management)
2. Select option 2 (List All Employees)
3. The system will display all employees with their details

### 3. View Attendance

This feature allows you to view the attendance records for a specific employee within a date range.

**Steps to view attendance:**

1. From the Main Menu, select option 1 (Employee Management)
2. Select option 3 (Attendance)
3. Enter the Employee Number
4. Enter the start date (MM/DD/YYYY)
5. Enter the end date (MM/DD/YYYY)
6. The system will display the attendance records with the following details:
   - Date
   - Time In
   - Time Out
   - Duration (hours)
   - Remarks (On Time or Late)

**Example attendance records:**
```
Date       | In     | Out    | Duration  | Remarks   
-------------------------------------------------
03/06/2025 | 8:00   | 17:00  | 9.00      | On Time  
03/07/2025 | 8:15   | 17:15  | 9.00      | Late     
03/08/2025 | 8:05   | 17:30  | 9.42      | On Time  
```

**Note**: Employees arriving after 8:10 AM are marked as "Late".

---

## Payroll Management

The Payroll Management module allows you to generate payroll for all employees or calculate pay for a specific employee.

### 1. Generate Payroll (All Employees)

This feature calculates pay for all employees within a specified date range.

**Steps to generate payroll for all employees:**

1. From the Main Menu, select option 2 (Payroll Management)
2. Select option 1 (Generate Payroll)
3. Enter the start date (MM/DD/YYYY)
4. Enter the end date (MM/DD/YYYY)
5. The system will display a summary of all employees with their:
   - Employee Number
   - Name
   - Regular Hours
   - Overtime Hours
   - Hourly Rate
   - Gross Pay
   - Allowances
   - Net Pay

**Example payroll summary:**
```
Emp#    Name                     Reg Hours  OT Hours  Hourly Rate       Gross Pay       Allowances         Net Pay
═════════════════════════════════════════════════════════════════════════════════════════════════════════════
10001   Jose Crisostomo             40.00      5.00       255.80        11,511.00         5,000.00        15,020.35
10002   Christian Mata              38.50      0.00       133.93         5,156.31         3,500.00         7,891.97
```

### 2. Custom Payroll (Individual Employee)

This feature generates a detailed payslip for a specific employee within a date range.

**Steps to generate a custom payroll:**

1. From the Main Menu, select option 2 (Payroll Management)
2. Select option 2 (Custom Payroll)
3. Enter the Employee Number
4. Enter the start date (MM/DD/YYYY)
5. Enter the end date (MM/DD/YYYY)
6. The system will display a detailed payslip with:
   - Employee information
   - Hours worked breakdown
   - Pay details
   - Deductions
   - Allowances
   - Final net pay

**Example individual payslip:**
```
═══════════════════════════════════════════
           EMPLOYEE PAYSLIP
═══════════════════════════════════════════
Employee No: 10001
Name: Jose Crisostomo
Position: Team Leader
Period: 03/01/2025 to 03/15/2025
Working Days: 10 of 21 days
───────────────────────────────────────────
HOURS WORKED:
Regular Hours: 80.00
Overtime Hours: 5.00
Total Hours: 85.00
───────────────────────────────────────────
PAY DETAILS:
Hourly Rate: ₱255.80
Regular Pay: ₱20,464.00
Overtime Pay: ₱1,598.75
Gross Pay: ₱22,062.75
───────────────────────────────────────────
DEDUCTIONS:
SSS: ₱877.50
PhilHealth: ₱330.94
Pag-IBIG: ₱441.26
Withholding Tax: ₱4,082.61
Total Deductions: ₱5,732.31
───────────────────────────────────────────
ALLOWANCES (Pro-rated for 10 days):
Rice Subsidy: ₱1,000.00
Phone Allowance: ₱500.00
Clothing Allowance: ₱500.00
Total Allowances: ₱2,000.00
───────────────────────────────────────────
FINAL NET PAY: ₱18,330.44
═══════════════════════════════════════════
```

---

## Reports

The Reports module allows you to generate various reports including individual payslips and summary reports.

### 1. Payslip

This feature is identical to the Custom Payroll option in the Payroll Management menu. It generates a detailed payslip for a specific employee.

**Steps to generate a payslip report:**

1. From the Main Menu, select option 3 (Reports)
2. Select option 1 (Payslip)
3. Follow the same steps as the Custom Payroll feature

### 2. Weekly Summary

This feature generates a summary report for all employees within a weekly period.

**Steps to generate a weekly summary:**

1. From the Main Menu, select option 3 (Reports)
2. Select option 2 (Weekly Summary)
3. Enter the start date (MM/DD/YYYY)
4. Enter the end date (MM/DD/YYYY)
5. The system will display a summary with:
   - Employee Number
   - Name
   - Total Work Hours
   - Net Pay
   - Gross Pay

**Example weekly summary:**
```
Emp#      Name                     Total Work Hours  Net Pay         Gross Pay       
-------------------------------------------------------------------------------------
10001     Jose Crisostomo          42.50             9,165.22        8,500.00       
10002     Christian Mata           40.00             7,500.35        6,950.00       
```

### 3. Monthly Summary

This feature generates a summary report for all employees within a monthly period.

**Steps to generate a monthly summary:**

1. From the Main Menu, select option 3 (Reports)
2. Select option 3 (Monthly Summary)
3. Enter the start date (MM/DD/YYYY)
4. Enter the end date (MM/DD/YYYY)
5. The system will display a monthly summary similar to the weekly summary

---

## Troubleshooting

### Common Issues and Solutions

**Issue**: System displays "Error loading data" message at startup.
**Solution**: 
- Ensure you have an active internet connection
- Check if the data source URLs are accessible
- Restart the application

**Issue**: Invalid employee number entered.
**Solution**:
- Ensure you are entering a numeric employee ID
- Verify the employee exists in the system using the Search Employee function

**Issue**: Date entry errors.
**Solution**:
- Always use MM/DD/YYYY format (e.g., 03/22/2025)
- Ensure the dates are valid calendar dates

**Issue**: Calculation discrepancies.
**Solution**:
- Verify the attendance records for the employee
- Check if the employee has the correct hourly rate
- Ensure the date range includes all relevant work days

---

## Appendix

### Calculation Formulas

#### Gross Pay Calculation
```
Gross Pay = Regular Pay + Overtime Pay
Regular Pay = Regular Hours × Hourly Rate
Overtime Pay = Overtime Hours × Hourly Rate × 1.25
```

#### Deductions Calculation
```
SSS - Based on SSS contribution table
PhilHealth = Gross Pay × 0.03 / 2 (employee share)
Pag-IBIG = Gross Pay × 0.02
Withholding Tax - Based on progressive tax table
```

#### Allowances Calculation
```
Pro-rated Allowance = (Monthly Allowance / 21) × Working Days
```

### SSS Contribution Table

The system uses a comprehensive SSS contribution table. Here's a sample of some brackets:

| Monthly Salary Range | SSS Contribution |
|----------------------|------------------|
| 0 - 4,249.99         | 180.00           |
| 4,250 - 4,749.99     | 202.50           |
| 4,750 - 5,249.99     | 225.00           |
| 5,250 - 5,749.99     | 247.50           |
| ...                  | ...              |
| 20,250 - 20,749.99   | 900.00           |
| 20,750 and above     | 945.00           |

---

© 2025 MotorPH. All rights reserved.

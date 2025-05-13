The "MotorPH Payroll System" is a console-based application designed for managing employee information, calculating payroll, and generating related reports. Users interact with the system through a series of text-based menus and prompts. The following description incorporates examples from a typical user session.

# **Main Menu**

The system starts by presenting the main menu:

```
=== MotorPH Payroll System ===
1. Employee Management
2. Payroll Management
3. Reports
4. Exit
Enter your choice:

```

Users navigate by entering a numerical choice.

## **1. Employee Management**

Selecting "1" leads to the Employee Management sub-menu:

```
Employee Management:
1. Search Employee
2. List All Employees
3. Attendance
4. Return to Main Menu
Enter your choice:

```

### **Search Employee:**

* Prompts: `Enter search term (name or employee number):`
* Processes the search against employee data.
* Output (if found):
  ```
  Emp#      Name                   Position                 Status          Hourly Rate
  10001     Manuel III Garcia      Chief Executive Officer Regular         535.71

  ```
* Output (if not found): (Assumed: "Employee not found.")
* Returns to the Employee Management menu.

### **List All Employees:**

* Retrieves all employee records.
* Output: A table listing all employees.
  ```
  		Emp#      Name                    Position                 Status          Hourly Rate
  -------------------------------------------------------------------------------------
  10001     Manuel III Garcia       Chief Executive...       Regular         535.71
  10002     Antonio Lim             Chief Operating...       Regular         357.14
  ... (and so on for all employees)
  10034     Beatriz Santos          Customer Servic...       Regular         313.51

  ```
* Returns to the Employee Management menu.

### **Attendance:**

* Prompts:
  * `Enter Employee No:`
  * `Date From (MM/DD/YYYY):`
  * `Date To (MM/DD/YYYY):`
* Retrieves and displays the attendance log for the specified employee and date range.
* Output:
  ```
  Date       | In     | Out    | Duration | Remarks
  06/03/2024 | 10:35  | 19:44  | 9.15     | Late
  06/04/2024 | 10:11  | 20:16  | 10.08    | Late
  ... (and so on for the date range)

  ```
* Returns to the Employee Management menu.
* **Return to Main Menu:** Navigates back to the main menu.
* **Invalid Choice:** If an invalid option is entered, the Employee Management menu is redisplayed.

## **2. Payroll Management**

Selecting "2" from the main menu opens the Payroll Management sub-menu:

```
Payroll Management:
1. Generate Payroll (Calculate All Employees)
2. Custom Payroll
3. Return to Main Menu
Enter your choice:

```

*(Note: The console output shows "Generate Payroll (Calculate All Employees)" while the flowchart and previous description had "Generate Payroll (All)". The description will use the console version.)*

### **Generate Payroll (Calculate All Employees):**

* Prompts:
  * `Date From (MM/DD/YYYY):`
  * `Date To (MM/DD/YYYY):`
* Calculates payroll for all employees within the specified date range.
* Output: A summary table.
  ```
  ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
  Emp#    Name                     Reg Hours  OT Hours   Hourly Rate  Gross Pay       Allowances       Net Pay
  ─────────────────────────────────────────────────────────────────────────────────────────────────────────────
  10001   Manuel III Garcia          157.80     32.65        535.71     106,398.70        4,285.71       85,669.51
  10002   Antonio Lim                158.73     32.40        357.14      71,154.19        4,285.71       58,460.75
  ... (and so on for all employees)
  ═════════════════════════════════════════════════════════════════════════════════════════════════════════════

  ```
* Prompts: `Press Enter to return to menu...`
* Returns to the Payroll Management menu.

### **Custom Payroll:**

* Prompts:
  * `Enter Employee No:`
  * `Date From (MM/DD/YYYY):`
  * `Date To (MM/DD/YYYY):`
* Calculates payroll for the specific employee and date range.
* Output: A detailed payslip.
  ```
  ═══════════════════════════════════════════
                 EMPLOYEE PAYSLIP
  ═══════════════════════════════════════════
  Employee No: 10003
  Name: Bianca Sofia Aquino
  Position: Chief Finance Officer
  Period: 06/01/2024 to 06/30/2024
  Working Days: 20 of 21 days
  ───────────────────────────────────────────
  HOURS WORKED:
  Regular Hours: 157.90
  Overtime Hours: 17.88
  Total Hours: 175.78
  ───────────────────────────────────────────
  PAY DETAILS:
  Hourly Rate: ?357.14
  Regular Pay: ?56392.41
  Overtime Pay: ?7983.57
  Gross Pay: ?64375.97
  ───────────────────────────────────────────
  DEDUCTIONS:
  SSS: ?945.00
  PhilHealth: ?965.64
  Pag-IBIG: ?1287.52
  Withholding Tax: ?13211.20
  Total Deductions: ?15433.72
  ───────────────────────────────────────────
  ALLOWANCES (Pro-rated for 20 days):
  Rice Subsidy: ?1428.57
  Phone Allowance: ?1904.76
  Clothing Allowance: ?952.38
  Total Allowances: ?4285.71
  ───────────────────────────────────────────
  FINAL NET PAY: ?53227.97
  ═══════════════════════════════════════════

  ```
* Prompts: `Press Enter to return to menu...`
* Returns to the Payroll Management menu.
* **Return to Main Menu:** Navigates back to the main menu.
* **Invalid Choice:** If an invalid option is entered, the Payroll Management menu is redisplayed.

## **3. Reports**

Selecting "3" from the main menu displays the Reports sub-menu:

```
Reports:
1. Payslip
2. Weekly Summary
3. Monthly Summary
4. Return to Main Menu
Enter your choice:

```

### **Payslip:**

* Prompts:
  * `Enter Employee No:`
  * `Date From (MM/DD/YYYY):`
  * `Date To (MM/DD/YYYY):`
* Generates and displays a payslip report. The format is identical to the "Custom Payroll" output.
  ```
  ═══════════════════════════════════════════
                 PAYSLIP REPORT
  ═══════════════════════════════════════════
  Employee No: 10004
  Name: Isabella Reyes
  Position: Chief Marketing Officer
  Period: 06/01/2024 to 06/30/2024
  Working Days: 20 of 21 days
  ... (rest of the payslip details as shown in Custom Payroll) ...
  FINAL NET PAY: ?52779.93
  ═══════════════════════════════════════════

  ```
* Prompts: `Press Enter to return to menu...`
* Returns to the Reports menu.

### **Weekly Summary:**

* Prompts:
  * `Date From (MM/DD/YYYY):`
  * `Date To (MM/DD/YYYY):` (e.g., a 7-day period)
* Generates and displays a weekly summary report.
* Output:
  ```
  Weekly Summary Report:
  Date From (MM/DD/YYYY): 06/01/2024
  Date To (MM/DD/YYYY): 06/07/2024
  Emp#      Name                    Total Work Hours Net Pay         Gross Pay
  -------------------------------------------------------------------------------------
  10001     Manuel III Garcia       48.65            21746.46        27220.76
  10002     Antonio Lim             49.42            15005.87        18489.44
  ... (and so on for all employees for the week)

  ```
* Prompts: `Press Enter to return to menu...`
* Returns to the Reports menu.

### **Monthly Summary:**

* Prompts:
  * `Date From (MM/DD/YYYY):`
  * `Date To (MM/DD/YYYY):` (e.g., a month-long period)
* Generates and displays a monthly summary report.
* Output:
  ```
  Monthly Summary Report:
  Date From (MM/DD/YYYY): 06/01/2024
  Date To (MM/DD/YYYY): 06/30/2024
  Emp#      Name                    Total Work Hours Net Pay         Gross Pay
  -------------------------------------------------------------------------------------
  10001     Manuel III Garcia       190.45           85669.51        106398.70
  10002     Antonio Lim             191.13           58460.75        71154.19
  ... (and so on for all employees for the month)

  ```
* Prompts: `Press Enter to return to menu...`
* Returns to the Reports menu.
* **Return to Main Menu:** Navigates back to the main menu.
* **Invalid Choice:** If an invalid option is entered, the Reports menu is redisplayed.

**4. Exit**

Selecting "4" from the main menu terminates the application.

* Output: `Exiting system...`

**Invalid Main Menu Choice**

If an invalid option is entered in the main menu, the main menu is redisplayed.

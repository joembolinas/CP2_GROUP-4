# MotorPH Payroll System Overview

## Project Summary

The MotorPH Payroll System is a Java-based application designed to streamline and automate payroll management for MotorPH company. The application has recently undergone significant refactoring to improve code organization, maintainability, and robustness by implementing the Model-View-Controller (MVC) architectural pattern and consolidating redundant components.

## Key Features

1. **Employee Management**

   - Employee data storage and retrieval
   - Search functionality by employee name or ID
   - Comprehensive employee listings
   - Employee attendance tracking and reporting
2. **Payroll Processing**

   - Automatic calculation of regular and overtime hours
   - Computation of gross and net pay
   - Handling of government-mandated deductions (SSS, PhilHealth, Pag-IBIG, withholding tax)
   - Management of employee allowances (rice subsidy, phone allowance, clothing allowance)
3. **Reporting System**

   - Individual employee payslip generation
   - Weekly and monthly summary reports
   - Customizable date range for reports
4. **User Interface**

   - Intuitive graphical user interface (GUI)
   - Menu-driven navigation system
   - Interactive dialogs for user input
   - Consistent styling and visual presentation

## Technical Architecture

### MVC Architecture Implementation

The system follows the Model-View-Controller (MVC) design pattern:

1. **Model Layer** (`com.motorph.model`)

   - `Employee.java`: Represents employee data and attributes
   - `AttendanceRecord.java`: Manages attendance information
   - `PaySlip.java`: Handles payslip data structure and calculations
2. **View Layer** (`com.motorph.view`)

   - `MainFrame.java`: Main application window
   - `MainMenuPanel.java`: Primary navigation menu
   - Specialized panels for different functions:
     - `EmployeeManagementPanel.java`
     - `PayrollPanel.java`
     - `ReportsPanel.java`
   - Dialog components for user interactions
3. **Controller Layer** (`com.motorph.controller`)

   - `EmployeeController.java`: Manages employee-related operations
   - `PayrollController.java`: Handles payroll processing
   - `ReportController.java`: Controls report generation
4. **Service Layer** (`com.motorph.service`)

   - `EmployeeService.java`: Business logic for employee management
   - `PayrollService.java`: Business logic for payroll calculations
   - `PayrollCalculator.java`: Handles calculation algorithms
   - `ReportService.java`: Business logic for report generation
5. **Repository Layer** (`com.motorph.repository`)

   - `DataRepository.java`: Consolidated data access component that loads and manages employee and attendance data
6. **Utility Layer** (`com.motorph.util`)

   - `DateUtils.java`: Date formatting and manipulation utilities
   - `ErrorHandler.java`: Centralized error handling
   - `InputValidator.java`: User input validation
   - `PayrollConstants.java`: System-wide constants
   - `UIConstants.java`: UI styling constants

### Data Management

- Data is loaded from CSV files (employeeDetails.csv, attendanceRecord.csv)
- The system supports flexible date/time parsing with robust error handling
- Repository pattern is used for data access abstraction

## Recent Improvements

### Refactoring Achievements

1. **Code Consolidation**

   - Merged redundant repository classes into a single `DataRepository` class
   - Combined `MotorPHPayrollMain.java` and `MotorPHPayrollApp.java` into a single entry point (`Main.java`)
   - Removed unnecessary utility classes and duplicate code
2. **Enhanced Error Handling**

   - Improved exception handling throughout the application
   - Added comprehensive logging for better diagnostics
   - Implemented input validation for critical user inputs
3. **Improved Architecture**

   - Clearer separation of concerns between layers
   - Better organized package structure
   - More consistent naming conventions
4. **UI Improvements**

   - Consistent styling across all components
   - More intuitive user flows
   - Better error messages and user feedback

## Technologies Used

- **Java 17**: Core programming language
- **Swing**: GUI framework
- **Maven**: Build and dependency management
- **Apache Commons CSV**: CSV file parsing
- **Java Logging API**: Application logging

## Future Considerations

1. Further consolidation of utility classes
2. Implementation of unit tests for key components
3. Migration from CSV files to a more robust database solution
4. Addition of user authentication and authorization features
5. Development of more comprehensive validation frameworks


# Class and Structure Inventory

## File and Folder Structure

```plaintext
motorph_payroll_system/
├── attendanceRecord.csv       # CSV file containing employee attendance data
├── employeeDetails.csv        # CSV file containing employee information
├── pom.xml                    # Maven project configuration
├── README.md                  # Project readme file
└── src/                       # Source code directory
    └── main/
        └── java/
            └── com/
                └── motorph/               # Base package
                    ├── Main.java          # Application entry point
                    ├── controller/        # Controller layer (MVC)
                    │   ├── EmployeeController.java
                    │   ├── PayrollController.java
                    │   └── ReportController.java
                    ├── model/             # Model layer (MVC)
                    │   ├── AttendanceRecord.java
                    │   ├── Employee.java
                    │   └── PaySlip.java
                    ├── repository/        # Data access layer
                    │   └── DataRepository.java  
	 	    │	├── service/           # Business logic layer
                    │   ├── EmployeeService.java
                    │   ├── PayrollProcessor.java
                    │   ├── PayrollService.java
                    │   └── ReportService.java
                    ├── util/              # Utility classes
                    │   ├── DateUtils.java
                    │   ├── ErrorHandler.java
                    │   ├── InputValidator.java
                    │   ├── PayrollConstants.java
                    │   └── UIConstants.java
                    └── view/              # View layer (MVC)
                        ├── ApplicationMenuBar.java
                        ├── EmployeeManagementPanel.java
                        ├── MainFrame.java
                        ├── MainMenuPanel.java
                        ├── PayrollPanel.java
                        ├── ReportsPanel.java
                        └── dialog/        # Dialog components
                            ├── DateRangeDialog.java
                            ├── EmployeeNumberInputDialog.java
                            ├── PayslipDialog.java
                            └── SearchResultDialog.java
```

## Class Inventory

| Class Name                | Access Modifier | Description                                                                                                       |
| ------------------------- | --------------- | ----------------------------------------------------------------------------------------------------------------- |
| Main                      | public          | The entry point of the application responsible for initializing all components and starting the user interface.   |
| Employee                  | public          | Represents an employee with personal and employment details including ID, name, position, and salary information. |
| AttendanceRecord          | public          | Represents a single attendance record for an employee including date, time in, time out, and calculated hours.    |
| PaySlip                   | public          | Represents a payslip for an employee for a specific period, containing all pay and deduction calculations.        |
| DataRepository            | public          | Consolidated repository for loading and accessing employee and attendance data from CSV files.                    |
| EmployeeService           | public          | Service class providing business logic for employee-related operations.                                           |
| PayrollProcessor          | public          | Handles all payroll calculations including gross pay, net pay, and various deductions.                            |
| PayrollService            | public          | Service class providing business logic for payroll-related operations.                                            |
| ReportService             | public          | Service class providing business logic for report generation.                                                     |
| EmployeeController        | public          | Controller for coordinating employee-related operations between view and model.                                   |
| PayrollController         | public          | Controller for coordinating payroll-related operations between view and model.                                    |
| ReportController          | public          | Controller for coordinating report generation operations between view and model.                                  |
| DateUtils                 | public          | Utility class for date-related operations including formatting and parsing.                                       |
| ErrorHandler              | public          | Centralized error handling utility for displaying and logging errors.                                             |
| InputValidator            | public          | Validates user inputs and provides standardized error messages.                                                   |
| PayrollConstants          | public          | Contains constants used in payroll calculations such as tax rates and contribution rates.                         |
| UIConstants               | public          | Contains constants for UI components to maintain consistent styling.                                              |
| MainFrame                 | public          | Main window of the application that contains all panels and controls navigation.                                  |
| ApplicationMenuBar        | public          | Menu bar providing access to all application functions.                                                           |
| MainMenuPanel             | public          | Main menu panel displaying primary navigation options.                                                            |
| EmployeeManagementPanel   | public          | Panel for employee management functions such as search and viewing attendance.                                    |
| PayrollPanel              | public          | Panel for payroll management functions such as generating payroll and payslips.                                   |
| ReportsPanel              | public          | Panel for report generation functions such as summary reports.                                                    |
| DateRangeDialog           | public          | Dialog for selecting a date range for reports and payroll calculations.                                           |
| EmployeeNumberInputDialog | public          | Dialog for entering an employee number.                                                                           |
| PayslipDialog             | public          | Dialog for displaying a payslip with formatting.                                                                  |
| SearchResultDialog        | public          | Dialog for displaying employee search results.                                                                    |

## Attribute Inventory

### Employee Class

| Attribute Name       | Class Name | Data Type | Access Modifier | Description                                          |
| -------------------- | ---------- | --------- | --------------- | ---------------------------------------------------- |
| employeeId           | Employee   | int       | private         | Unique identifier for the employee                   |
| lastName             | Employee   | String    | private         | Last name of the employee                            |
| firstName            | Employee   | String    | private         | First name of the employee                           |
| birthday             | Employee   | LocalDate | private         | Date of birth of the employee                        |
| address              | Employee   | String    | private         | Residential address of the employee                  |
| phoneNumber          | Employee   | String    | private         | Contact number of the employee                       |
| sssNumber            | Employee   | String    | private         | SSS (Social Security System) number of the employee  |
| philhealthNumber     | Employee   | String    | private         | PhilHealth (health insurance) number of the employee |
| tinNumber            | Employee   | String    | private         | TIN (Tax Identification Number) of the employee      |
| pagibigNumber        | Employee   | String    | private         | Pag-IBIG (housing fund) number of the employee       |
| status               | Employee   | String    | private         | Employment status (Regular, Probationary, etc.)      |
| position             | Employee   | String    | private         | Job position or title of the employee                |
| supervisor           | Employee   | String    | private         | Name of the employee's immediate supervisor          |
| basicSalary          | Employee   | double    | private         | Monthly basic salary of the employee                 |
| riceSubsidy          | Employee   | double    | private         | Monthly rice subsidy allowance                       |
| phoneAllowance       | Employee   | double    | private         | Monthly phone allowance                              |
| clothingAllowance    | Employee   | double    | private         | Monthly clothing allowance                           |
| grossSemiMonthlyRate | Employee   | double    | private         | Semi-monthly gross rate (before deductions)          |
| hourlyRate           | Employee   | double    | private         | Hourly rate for computing pay based on hours worked  |

### AttendanceRecord Class

| Attribute Name | Class Name       | Data Type | Access Modifier | Description                                              |
| -------------- | ---------------- | --------- | --------------- | -------------------------------------------------------- |
| employeeId     | AttendanceRecord | int       | private         | Employee ID this attendance record belongs to            |
| date           | AttendanceRecord | LocalDate | private         | Date of the attendance record                            |
| timeIn         | AttendanceRecord | LocalTime | private         | Time when the employee clocked in                        |
| timeOut        | AttendanceRecord | LocalTime | private         | Time when the employee clocked out                       |
| totalHours     | AttendanceRecord | double    | private         | Total hours worked calculated from timeIn and timeOut    |
| late           | AttendanceRecord | boolean   | private         | Flag indicating if the employee was late for this record |

### PaySlip Class

| Attribute Name | Class Name | Data Type           | Access Modifier | Description                                               |
| -------------- | ---------- | ------------------- | --------------- | --------------------------------------------------------- |
| employee       | PaySlip    | Employee            | private         | Reference to the employee this payslip belongs to         |
| startDate      | PaySlip    | LocalDate           | private         | Start date of the pay period                              |
| endDate        | PaySlip    | LocalDate           | private         | End date of the pay period                                |
| regularHours   | PaySlip    | double              | private         | Regular hours worked during the pay period                |
| overtimeHours  | PaySlip    | double              | private         | Overtime hours worked during the pay period               |
| grossPay       | PaySlip    | double              | private         | Gross pay before deductions                               |
| netPay         | PaySlip    | double              | private         | Net pay after all deductions                              |
| deductions     | PaySlip    | Map<String, Double> | private         | Map of deduction names to amounts (SSS, PhilHealth, etc.) |
| allowances     | PaySlip    | Map<String, Double> | private         | Map of allowance names to amounts (rice, phone, etc.)     |

### DataRepository Class

| Attribute Name     | Class Name     | Data Type                   | Access Modifier | Description                                         |
| ------------------ | -------------- | --------------------------- | --------------- | --------------------------------------------------- |
| employeesFilePath  | DataRepository | String                      | private         | Path to the employee CSV file                       |
| attendanceFilePath | DataRepository | String                      | private         | Path to the attendance CSV file                     |
| employees          | DataRepository | List `<Employee>`         | private         | List of employees loaded from the CSV file          |
| attendanceRecords  | DataRepository | List `<AttendanceRecord>` | private         | List of attendance records loaded from the CSV file |
| logger             | DataRepository | Logger                      | private         | Logger for repository operations                    |

### PayrollProcessor Class

| Attribute Name | Class Name       | Data Type           | Access Modifier | Description                                                   |
| -------------- | ---------------- | ------------------- | --------------- | ------------------------------------------------------------- |
| sssTable       | PayrollProcessor | Map<Double, Double> | private final   | Table for SSS contribution calculation based on salary ranges |
| philHealthRate | PayrollProcessor | double              | private final   | PhilHealth contribution rate as a percentage                  |
| pagIbigRate    | PayrollProcessor | double              | private final   | Pag-IBIG contribution rate as a percentage                    |

## Method Inventory

### Employee Class

| Method Name          | Class Name | Return Type | Access Modifier | Description                                                    |
| -------------------- | ---------- | ----------- | --------------- | -------------------------------------------------------------- |
| getEmployeeId        | Employee   | int         | public          | Returns the employee ID                                        |
| getFullName          | Employee   | String      | public          | Returns the full name (first name + last name) of the employee |
| getFirstName         | Employee   | String      | public          | Returns the first name of the employee                         |
| getLastName          | Employee   | String      | public          | Returns the last name of the employee                          |
| getBirthday          | Employee   | LocalDate   | public          | Returns the birth date of the employee                         |
| getAddress           | Employee   | String      | public          | Returns the address of the employee                            |
| getPhoneNumber       | Employee   | String      | public          | Returns the phone number of the employee                       |
| getStatus            | Employee   | String      | public          | Returns the employment status of the employee                  |
| getPosition          | Employee   | String      | public          | Returns the job position of the employee                       |
| getSupervisor        | Employee   | String      | public          | Returns the name of the employee's supervisor                  |
| getHourlyRate        | Employee   | double      | public          | Returns the hourly rate of the employee                        |
| getBasicSalary       | Employee   | double      | public          | Returns the monthly basic salary of the employee               |
| getRiceSubsidy       | Employee   | double      | public          | Returns the rice subsidy amount                                |
| getPhoneAllowance    | Employee   | double      | public          | Returns the phone allowance amount                             |
| getClothingAllowance | Employee   | double      | public          | Returns the clothing allowance amount                          |

### AttendanceRecord Class

| Method Name         | Class Name       | Return Type | Access Modifier | Description                                               |
| ------------------- | ---------------- | ----------- | --------------- | --------------------------------------------------------- |
| getEmployeeId       | AttendanceRecord | int         | public          | Returns the employee ID for this attendance record        |
| getDate             | AttendanceRecord | LocalDate   | public          | Returns the date of this attendance record                |
| getTimeIn           | AttendanceRecord | LocalTime   | public          | Returns the time when the employee clocked in             |
| getTimeOut          | AttendanceRecord | LocalTime   | public          | Returns the time when the employee clocked out            |
| getTotalHours       | AttendanceRecord | double      | public          | Returns the calculated total hours worked                 |
| isLate              | AttendanceRecord | boolean     | public          | Returns true if the employee was late, false otherwise    |
| calculateTotalHours | AttendanceRecord | double      | private         | Calculates the total hours worked from timeIn and timeOut |
| checkIfLate         | AttendanceRecord | boolean     | private         | Determines if the employee was late based on timeIn       |

### PaySlip Class

| Method Name            | Class Name | Return Type         | Access Modifier | Description                                                                 |
| ---------------------- | ---------- | ------------------- | --------------- | --------------------------------------------------------------------------- |
| generate               | PaySlip    | void                | public          | Generates the payslip by calculating hours, pay, deductions, and allowances |
| display                | PaySlip    | void                | public          | Displays a formatted version of the payslip                                 |
| getRegularHours        | PaySlip    | double              | public          | Returns the regular hours worked                                            |
| getOvertimeHours       | PaySlip    | double              | public          | Returns the overtime hours worked                                           |
| getGrossPay            | PaySlip    | double              | public          | Returns the gross pay amount                                                |
| getNetPay              | PaySlip    | double              | public          | Returns the net pay amount                                                  |
| getDeductions          | PaySlip    | Map<String, Double> | public          | Returns the map of deductions                                               |
| getAllowances          | PaySlip    | Map<String, Double> | public          | Returns the map of allowances                                               |
| calculateRegularHours  | PaySlip    | double              | private         | Calculates the regular hours worked in the pay period                       |
| calculateOvertimeHours | PaySlip    | double              | private         | Calculates the overtime hours worked in the pay period                      |
| calculateAllowances    | PaySlip    | Map<String, Double> | private         | Calculates the allowances for the pay period                                |
| calculateDeductions    | PaySlip    | Map<String, Double> | private         | Calculates the deductions from the gross pay                                |

### DataRepository Class

| Method Name                      | Class Name     | Return Type                 | Access Modifier | Description                                        |
| -------------------------------- | -------------- | --------------------------- | --------------- | -------------------------------------------------- |
| loadEmployees                    | DataRepository | List `<Employee>`         | public          | Loads employees from the CSV file                  |
| loadAttendanceRecords            | DataRepository | List `<AttendanceRecord>` | public          | Loads attendance records from the CSV file         |
| getAllEmployees                  | DataRepository | List `<Employee>`         | public          | Returns all employees                              |
| getAllAttendanceRecords          | DataRepository | List `<AttendanceRecord>` | public          | Returns all attendance records                     |
| getEmployeeById                  | DataRepository | Employee                    | public          | Returns an employee by ID                          |
| getAttendanceRecordsByEmployeeId | DataRepository | List `<AttendanceRecord>` | public          | Returns attendance records for a specific employee |
| getAttendanceRecordsByDateRange  | DataRepository | List `<AttendanceRecord>` | public          | Returns attendance records within a date range     |
| parseDate                        | DataRepository | LocalDate                   | private         | Parses date strings from various formats           |
| parseTime                        | DataRepository | LocalTime                   | private         | Parses time strings from various formats           |

### EmployeeService Class

| Method Name                     | Class Name      | Return Type                 | Access Modifier | Description                                        |
| ------------------------------- | --------------- | --------------------------- | --------------- | -------------------------------------------------- |
| findEmployeeById                | EmployeeService | Employee                    | public          | Finds an employee by their ID                      |
| searchEmployees                 | EmployeeService | List `<Employee>`         | public          | Searches for employees by name or ID               |
| getAllEmployees                 | EmployeeService | List `<Employee>`         | public          | Returns all employees                              |
| getAttendanceRecordsByEmployee  | EmployeeService | List `<AttendanceRecord>` | public          | Returns attendance records for a specific employee |
| getAttendanceRecordsByDateRange | EmployeeService | List `<AttendanceRecord>` | public          | Returns attendance records within a date range     |

### PayrollProcessor Class

| Method Name                     | Class Name       | Return Type         | Access Modifier | Description                                                 |
| ------------------------------- | ---------------- | ------------------- | --------------- | ----------------------------------------------------------- |
| calculateGrossPay               | PayrollProcessor | double              | public          | Calculates gross pay based on hours worked and hourly rate  |
| calculateNetPay                 | PayrollProcessor | double              | public          | Calculates net pay by subtracting deductions from gross pay |
| calculateSSSContribution        | PayrollProcessor | double              | public          | Calculates SSS contribution based on salary                 |
| calculatePhilHealthContribution | PayrollProcessor | double              | public          | Calculates PhilHealth contribution based on salary          |
| calculatePagIbigContribution    | PayrollProcessor | double              | public          | Calculates Pag-IBIG contribution based on salary            |
| calculateWithholdingTax         | PayrollProcessor | double              | public          | Calculates withholding tax based on taxable income          |
| initSSSTable                    | PayrollProcessor | Map<Double, Double> | private         | Initializes the SSS contribution table                      |

### PayrollService Class

| Method Name            | Class Name     | Return Type        | Access Modifier | Description                                                             |
| ---------------------- | -------------- | ------------------ | --------------- | ----------------------------------------------------------------------- |
| generatePayslip        | PayrollService | PaySlip            | public          | Generates a payslip for a specific employee and date range              |
| generatePayrollForAll  | PayrollService | List `<PaySlip>` | public          | Generates payslips for all employees for a date range                   |
| calculateRegularHours  | PayrollService | double             | private         | Calculates regular hours worked for a specific employee and date range  |
| calculateOvertimeHours | PayrollService | double             | private         | Calculates overtime hours worked for a specific employee and date range |

### ReportService Class

| Method Name           | Class Name    | Return Type               | Access Modifier | Description                                        |
| --------------------- | ------------- | ------------------------- | --------------- | -------------------------------------------------- |
| generatePayslipReport | ReportService | PaySlip                   | public          | Generates a payslip report for a specific employee |
| generateSummaryReport | ReportService | List<Map<String, Object>> | public          | Generates a summary report for all employees       |
| formatCurrency        | ReportService | String                    | private         | Formats a number as a currency string              |
| formatHours           | ReportService | String                    | private         | Formats a number as hours                          |

### EmployeeController Class

| Method Name          | Class Name         | Return Type                 | Access Modifier | Description                                              |
| -------------------- | ------------------ | --------------------------- | --------------- | -------------------------------------------------------- |
| findEmployeeById     | EmployeeController | Employee                    | public          | Finds an employee by ID with error handling              |
| searchEmployees      | EmployeeController | List `<Employee>`         | public          | Searches for employees by name or ID with error handling |
| getAllEmployees      | EmployeeController | List `<Employee>`         | public          | Returns all employees with error handling                |
| getAttendanceRecords | EmployeeController | List `<AttendanceRecord>` | public          | Gets attendance records with error handling              |

### PayrollController Class

| Method Name           | Class Name        | Return Type        | Access Modifier | Description                                                  |
| --------------------- | ----------------- | ------------------ | --------------- | ------------------------------------------------------------ |
| generatePayslip       | PayrollController | PaySlip            | public          | Generates a payslip with input validation and error handling |
| generatePayrollForAll | PayrollController | List `<PaySlip>` | public          | Generates payroll for all employees with error handling      |
| validateDateRange     | PayrollController | void               | private         | Validates that a date range is valid                         |

### ReportController Class

| Method Name           | Class Name       | Return Type               | Access Modifier | Description                                      |
| --------------------- | ---------------- | ------------------------- | --------------- | ------------------------------------------------ |
| generatePayslipReport | ReportController | PaySlip                   | public          | Generates a payslip report with input validation |
| generateSummaryReport | ReportController | List<Map<String, Object>> | public          | Generates a summary report with input validation |
| formatReportDate      | ReportController | String                    | private         | Formats a date for display in reports            |

### MainFrame Class

| Method Name            | Class Name | Return Type | Access Modifier | Description                               |
| ---------------------- | ---------- | ----------- | --------------- | ----------------------------------------- |
| initUI                 | MainFrame  | void        | private         | Initializes the user interface components |
| showMainMenu           | MainFrame  | void        | public          | Shows the main menu panel                 |
| showEmployeeManagement | MainFrame  | void        | public          | Shows the employee management panel       |
| showPayrollManagement  | MainFrame  | void        | public          | Shows the payroll management panel        |
| showReports            | MainFrame  | void        | public          | Shows the reports panel                   |
| createMenuBar          | MainFrame  | JMenuBar    | private         | Creates the application menu bar          |

### Utility Classes

| Method Name            | Class Name     | Return Type | Access Modifier | Description                                         |
| ---------------------- | -------------- | ----------- | --------------- | --------------------------------------------------- |
| formatDate             | DateUtils      | String      | public          | Formats a LocalDate to a string                     |
| formatDateRange        | DateUtils      | String      | public          | Creates a human-readable date range string          |
| showError              | ErrorHandler   | void        | public          | Displays an error message dialog and logs the error |
| validateEmployeeNumber | InputValidator | int         | public          | Validates employee number input                     |
| validateDateInput      | InputValidator | LocalDate   | public          | Validates date input                                |

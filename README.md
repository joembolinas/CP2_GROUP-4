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

## Project Cleanup Summary

### Structural Improvements Made

The MotorPH Payroll System has undergone a comprehensive cleanup and restructuring process that has significantly improved the project's organization and maintainability:

#### **Files Reorganized** 📁

- **Test files properly separated:** All test classes moved from `src/main/java` to `src/test/java`
- **Data files organized:** CSV files moved to dedicated `data/` directory
- **Development utilities relocated:** Moved `CSVCreateAndWrite.java` and `CredentialManager.java` to test directory

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

### Major Refactoring and Cleanup (2025)

1. **Project Structure Cleanup**

   - **Removed redundant/empty files**: Eliminated 8 empty or duplicate files (`EmployeeListPanelNew.java`, `NavigationBar.java`, `EmployeeDetailsDialog.java`, etc.)
   - **Proper test organization**: Moved all test files from `src/main/java` to `src/test/java` following Maven standards
   - **Data organization**: Created dedicated `data/` directory for CSV files
   - **Package consolidation**: Streamlined repository package by moving development utilities to test directory
2. **Code Consolidation**

   - Merged redundant repository classes into a single `DataRepository` class
   - Combined `MotorPHPayrollMain.java` and `MotorPHPayrollApp.java` into a single entry point (`Main.java`)
   - Removed unnecessary utility classes and duplicate code
   - Eliminated duplicate UI panels (`EmployeeListPanelFixed.java` was redundant)
3. **Enhanced Error Handling**

   - Improved exception handling throughout the application
   - Added comprehensive logging for better diagnostics
   - Implemented input validation for critical user inputs
4. **Improved Architecture**

   - Clearer separation of concerns between layers
   - Better organized package structure following Maven conventions
   - More consistent naming conventions
   - **17% reduction** in main source files (46 → 40 files)
5. **UI Improvements**

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

1. **Code Quality**

   - Implementation of comprehensive unit tests for key components
   - Addition of integration tests for complete workflows
   - Code coverage analysis and improvement
2. **Data Management**

   - Migration from CSV files to a more robust database solution (H2, SQLite, or PostgreSQL)
   - Implementation of data backup and recovery mechanisms
   - Addition of data validation and integrity checks
3. **Security and Authentication**

   - Implementation of user authentication and authorization features
   - Role-based access control for different user types
   - Secure password handling and session management
4. **User Experience**

   - Development of more comprehensive validation frameworks
   - Addition of keyboard shortcuts and accessibility features
   - Implementation of user preferences and settings
5. **Reporting and Analytics**

   - Enhanced reporting capabilities with charts and graphs
   - Export functionality for reports (PDF, Excel)
   - Data analytics and trends visualization
6. **Performance and Scalability**

   - Performance optimization for large datasets
   - Memory usage optimization
   - Concurrent processing capabilities

# Class and Structure Inventory

## File and Folder Structure

```plaintext
motorph_payroll_system/
├── data/                          # CSV data files (organized)
│   ├── attendanceRecord.csv       # Employee attendance data
│   └── employeeDetails.csv        # Employee information
├── pom.xml                        # Maven project configuration
└── src/                           # Source code directory
    ├── main/java/com/motorph/     # Main application code
    │   ├── Main.java              # Application entry point
    │   ├── controller/            # Controller layer (MVC)
    │   │   ├── EmployeeController.java
    │   │   ├── PayrollController.java
    │   │   └── ReportController.java
    │   ├── model/                 # Model layer (MVC)
    │   │   ├── AttendanceRecord.java
    │   │   ├── Employee.java
    │   │   └── PaySlip.java
    │   ├── repository/            # Data access layer (cleaned)
    │   │   └── DataRepository.java
    │   ├── service/               # Business logic layer
    │   │   ├── EmployeeService.java
    │   │   ├── PayrollProcessor.java
    │   │   ├── PayrollService.java
    │   │   └── ReportService.java
    │   ├── util/                  # Utility classes
    │   │   ├── DateUtils.java
    │   │   ├── ErrorHandler.java
    │   │   ├── InputValidator.java
    │   │   ├── PayrollConstants.java
    │   │   ├── UIConstants.java
    │   │   └── UIUtils.java
    │   └── view/                  # View layer (MVC)
    │       ├── ApplicationMenuBar.java
    │       ├── EmployeeListPanel.java
    │       ├── EmployeeManagementPanel.java
    │       ├── MainFrame.java
    │       ├── MainMenuPanel.java
    │       ├── PayrollPanel.java
    │       ├── ReportsPanel.java
    │       ├── dialog/            # Dialog components
    │       │   ├── DateRangeDialog.java
    │       │   ├── EditEmployeeDialog.java
    │       │   ├── EmployeeDetailsFrame.java
    │       │   ├── EmployeeNumberInputDialog.java
    │       │   ├── NewEmployeeDialog.java
    │       │   ├── PayslipDialog.java
    │       │   └── SearchResultDialog.java
    │       └── renderer/          # Custom UI renderers
    │           └── ActionButtonRenderer.java
    └── test/java/com/motorph/     # Test files (properly organized)
        ├── CSVCreateAndWrite.java     # Development utilities
        ├── CSVTest.java
        ├── CSVWriteTest.java
        ├── CredentialManager.java     # Future authentication
        ├── EmployeeServiceTest.java
        ├── ManualCSVTest.java
        └── SimpleCSVTest.java
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
| UIUtils                   | public          | Utility class for creating styled UI components that match the application design.                                |
| MainFrame                 | public          | Main window of the application that contains all panels and controls navigation.                                  |
| ApplicationMenuBar        | public          | Menu bar providing access to all application functions.                                                           |
| MainMenuPanel             | public          | Main menu panel displaying primary navigation options.                                                            |
| EmployeeListPanel         | public          | Panel for displaying employee list with search and action capabilities (unified implementation).                  |
| EmployeeManagementPanel   | public          | Panel for employee management functions such as search and viewing attendance.                                    |
| PayrollPanel              | public          | Panel for payroll management functions such as generating payroll and payslips.                                   |
| ReportsPanel              | public          | Panel for report generation functions such as summary reports.                                                    |
| DateRangeDialog           | public          | Dialog for selecting a date range for reports and payroll calculations.                                           |
| EditEmployeeDialog        | public          | Dialog for editing existing employee information with form validation.                                            |
| EmployeeDetailsFrame      | public          | Frame for displaying complete employee details in a formatted view.                                               |
| EmployeeNumberInputDialog | public          | Dialog for entering an employee number with validation.                                                           |
| NewEmployeeDialog         | public          | Dialog for adding new employees to the system with form validation.                                               |
| PayslipDialog             | public          | Dialog for displaying a payslip with proper formatting.                                                           |
| SearchResultDialog        | public          | Dialog for displaying employee search results in a table format.                                                  |
| ActionButtonRenderer      | public          | Custom table cell renderer for action buttons in employee tables (View, Edit, Delete).                            |

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

## System Architecture Visualization

### Overall System Architecture

```mermaid
flowchart TB
    subgraph "User Interface Layer"
        UI[Main Frame]
        Menu[Application Menu Bar]
        Panels[Management Panels]
        Dialogs[Dialog Components]
    end
    
    subgraph "Controller Layer (MVC)"
        EC[Employee Controller]
        PC[Payroll Controller] 
        RC[Report Controller]
    end
    
    subgraph "Service Layer (Business Logic)"
        ES[Employee Service]
        PS[Payroll Service]
        PP[Payroll Processor]
        RS[Report Service]
    end
    
    subgraph "Model Layer"
        EMP[Employee Model]
        ATT[Attendance Record]
        PAY[PaySlip Model]
    end
    
    subgraph "Data Layer"
        DR[Data Repository]
        CSV[(CSV Files)]
    end
    
    subgraph "Utility Layer"
        UTILS[Date Utils<br/>Error Handler<br/>Input Validator<br/>UI Utils]
    end
    
    %% Connections
    UI --> EC
    UI --> PC
    UI --> RC
    
    EC --> ES
    PC --> PS
    RC --> RS
    
    ES --> EMP
    PS --> PP
    PS --> PAY
    RS --> ATT
    
    ES --> DR
    PS --> DR
    RS --> DR
    
    DR --> CSV
    
    EC -.-> UTILS
    PC -.-> UTILS
    RC -.-> UTILS
    UI -.-> UTILS
    
    %% Styling
    classDef uiLayer fill:#e1f5fe,stroke:#01579b,stroke-width:2px
    classDef controllerLayer fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    classDef serviceLayer fill:#e8f5e8,stroke:#1b5e20,stroke-width:2px
    classDef modelLayer fill:#fff3e0,stroke:#e65100,stroke-width:2px
    classDef dataLayer fill:#fce4ec,stroke:#880e4f,stroke-width:2px
    classDef utilLayer fill:#f1f8e9,stroke:#33691e,stroke-width:2px
    
    class UI,Menu,Panels,Dialogs uiLayer
    class EC,PC,RC controllerLayer
    class ES,PS,PP,RS serviceLayer
    class EMP,ATT,PAY modelLayer
    class DR,CSV dataLayer
    class UTILS utilLayer
```

### MVC Pattern Implementation

```mermaid
classDiagram
    %% Model Layer
    class Employee {
        -int employeeId
        -String firstName
        -String lastName
        -String position
        -double hourlyRate
        +getFullName() String
        +getEmployeeId() int
        +getHourlyRate() double
    }
    
    class AttendanceRecord {
        -int employeeId
        -LocalDate date
        -LocalTime timeIn
        -LocalTime timeOut
        +getTotalHours() double
        +isLate() boolean
    }
    
    class PaySlip {
        -Employee employee
        -LocalDate startDate
        -LocalDate endDate
        -double grossPay
        -double netPay
        +generate() void
        +display() void
    }
    
    %% View Layer
    class MainFrame {
        -CardLayout cardLayout
        -JPanel cardPanel
        +showPanel(String) void
        +showMainMenu() void
    }
    
    class EmployeeListPanel {
        -JTable employeeTable
        -DefaultTableModel tableModel
        +refreshTable() void
        +showEmployeeDetails() void
    }
    
    class PayrollPanel {
        -JButton generateButton
        -DateRangeDialog dateDialog
        +generatePayslip() void
        +displayResults() void
    }
    
    %% Controller Layer
    class EmployeeController {
        -EmployeeService employeeService
        +findEmployeeById(int) Employee
        +searchEmployees(String) List~Employee~
    }
    
    class PayrollController {
        -PayrollService payrollService
        +generatePayslip(int, LocalDate, LocalDate) PaySlip
    }
    
    class ReportController {
        -ReportService reportService
        +generatePayslipReport(int, LocalDate, LocalDate) PaySlip
    }
    
    %% Service Layer
    class EmployeeService {
        -List~Employee~ employees
        -DataRepository repository
        +findEmployeeById(int) Employee
        +searchEmployees(String) List~Employee~
    }
    
    class PayrollService {
        -PayrollProcessor calculator
        -DataRepository repository
        +generatePayslip(int, LocalDate, LocalDate) PaySlip
    }
    
    %% Data Repository
    class DataRepository {
        -String employeesFilePath
        -String attendanceFilePath
        +loadEmployees() List~Employee~
        +loadAttendanceRecords() List~AttendanceRecord~
    }
    
    %% Relationships
    MainFrame --> EmployeeListPanel
    MainFrame --> PayrollPanel
    
    EmployeeListPanel --> EmployeeController
    PayrollPanel --> PayrollController
    
    EmployeeController --> EmployeeService
    PayrollController --> PayrollService
    ReportController --> ReportService
    
    EmployeeService --> DataRepository
    PayrollService --> DataRepository
    
    EmployeeService --> Employee
    PayrollService --> PaySlip
    DataRepository --> AttendanceRecord
    
    PaySlip --> Employee
    AttendanceRecord --> Employee : belongs to
```

### Project Directory Structure

```mermaid
flowchart TD
    ROOT[motorph_payroll_system/]
    
    ROOT --> DATA[data/]
    ROOT --> POM[pom.xml]
    ROOT --> SRC[src/]
    
    DATA --> ATTEND[attendanceRecord.csv]
    DATA --> EMPLOY[employeeDetails.csv]
    
    SRC --> MAIN[main/]
    SRC --> TEST[test/]
    
    MAIN --> JAVA[java/com/motorph/]
    TEST --> TESTJAVA[java/com/motorph/]
    
    JAVA --> MAINCLASS[Main.java]
    JAVA --> CONTROLLER[controller/]
    JAVA --> MODEL[model/]
    JAVA --> REPO[repository/]
    JAVA --> SERVICE[service/]
    JAVA --> UTIL[util/]
    JAVA --> VIEW[view/]
    
    CONTROLLER --> EC[EmployeeController.java]
    CONTROLLER --> PC[PayrollController.java]
    CONTROLLER --> RC[ReportController.java]
    
    MODEL --> EMPM[Employee.java]
    MODEL --> ATTM[AttendanceRecord.java]
    MODEL --> PAYM[PaySlip.java]
    
    REPO --> DR[DataRepository.java]
    
    SERVICE --> ES[EmployeeService.java]
    SERVICE --> PS[PayrollService.java]
    SERVICE --> PP[PayrollProcessor.java]
    SERVICE --> RS[ReportService.java]
    
    UTIL --> DU[DateUtils.java]
    UTIL --> EH[ErrorHandler.java]
    UTIL --> IV[InputValidator.java]
    UTIL --> UC[UIConstants.java]
    UTIL --> UU[UIUtils.java]
    UTIL --> PC2[PayrollConstants.java]
    
    VIEW --> MF[MainFrame.java]
    VIEW --> AMB[ApplicationMenuBar.java]
    VIEW --> ELP[EmployeeListPanel.java]
    VIEW --> EMP2[EmployeeManagementPanel.java]
    VIEW --> PP2[PayrollPanel.java]
    VIEW --> RP[ReportsPanel.java]
    VIEW --> MMP[MainMenuPanel.java]
    VIEW --> DIALOG[dialog/]
    VIEW --> RENDER[renderer/]
    
    DIALOG --> DRD[DateRangeDialog.java]
    DIALOG --> EED[EditEmployeeDialog.java]
    DIALOG --> EDF[EmployeeDetailsFrame.java]
    DIALOG --> ENID[EmployeeNumberInputDialog.java]
    DIALOG --> NED[NewEmployeeDialog.java]
    DIALOG --> PD[PayslipDialog.java]
    DIALOG --> SRD[SearchResultDialog.java]
    
    RENDER --> ABR[ActionButtonRenderer.java]
    
    TESTJAVA --> CSVT[CSVTest.java]
    TESTJAVA --> CSVWT[CSVWriteTest.java]
    TESTJAVA --> EST[EmployeeServiceTest.java]
    TESTJAVA --> MCT[ManualCSVTest.java]
    TESTJAVA --> SCT[SimpleCSVTest.java]
    TESTJAVA --> CSVCAT[CSVCreateAndWrite.java]
    TESTJAVA --> CM[CredentialManager.java]
    
    %% Styling
    classDef rootStyle fill:#ff9999,stroke:#ff0000,stroke-width:3px
    classDef dataStyle fill:#99ccff,stroke:#0066cc,stroke-width:2px
    classDef srcStyle fill:#99ff99,stroke:#00cc00,stroke-width:2px
    classDef packageStyle fill:#ffcc99,stroke:#ff6600,stroke-width:2px
    classDef javaFile fill:#ffffcc,stroke:#cccc00,stroke-width:1px
    classDef configFile fill:#ffccff,stroke:#cc00cc,stroke-width:2px
    
    class ROOT rootStyle
    class DATA,ATTEND,EMPLOY dataStyle
    class SRC,MAIN,TEST,JAVA,TESTJAVA srcStyle
    class CONTROLLER,MODEL,REPO,SERVICE,UTIL,VIEW,DIALOG,RENDER packageStyle
    class POM configFile
```

### Application Workflow

```mermaid
flowchart TD
    START([Application Start])
    INIT[Initialize Application<br/>Load CSV Data]
    MAIN_UI[Display Main Menu]
    
    START --> INIT
    INIT --> MAIN_UI
    
    MAIN_UI --> EMP_MGMT{Employee<br/>Management}
    MAIN_UI --> PAYROLL{Payroll<br/>Processing}
    MAIN_UI --> REPORTS{Report<br/>Generation}
    
    %% Employee Management Flow
    EMP_MGMT --> SEARCH[Search Employee]
    EMP_MGMT --> VIEW_LIST[View Employee List]
    EMP_MGMT --> ADD_EMP[Add New Employee]
    
    SEARCH --> SEARCH_RESULT[Display Results]
    VIEW_LIST --> EMP_TABLE[Employee Table with Actions]
    ADD_EMP --> EMP_FORM[Employee Form Dialog]
    
    EMP_TABLE --> VIEW_DETAILS[View Details]
    EMP_TABLE --> EDIT_EMP[Edit Employee]
    EMP_TABLE --> DELETE_EMP[Delete Employee]
    
    VIEW_DETAILS --> EMP_DETAIL_FRAME[Employee Details Frame]
    EDIT_EMP --> EDIT_FORM[Edit Employee Dialog]
    DELETE_EMP --> CONFIRM_DELETE{Confirm Deletion}
    
    %% Payroll Processing Flow
    PAYROLL --> SELECT_EMP[Select Employee]
    SELECT_EMP --> DATE_RANGE[Select Date Range]
    DATE_RANGE --> CALC_PAY[Calculate Payslip]
    CALC_PAY --> DISPLAY_PAY[Display Payslip]
    
    %% Report Generation Flow
    REPORTS --> REP_TYPE{Report Type}
    REP_TYPE --> PAYSLIP_REP[Payslip Report]
    REP_TYPE --> SUMMARY_REP[Summary Report]
    
    PAYSLIP_REP --> SELECT_EMP2[Select Employee]
    SELECT_EMP2 --> DATE_RANGE2[Select Date Range]
    DATE_RANGE2 --> GEN_PAYSLIP[Generate Payslip Report]
    
    SUMMARY_REP --> DATE_RANGE3[Select Date Range]
    DATE_RANGE3 --> GEN_SUMMARY[Generate Summary Report]
    
    %% Return to main menu
    SEARCH_RESULT --> MAIN_UI
    EMP_DETAIL_FRAME --> MAIN_UI
    EDIT_FORM --> MAIN_UI
    EMP_FORM --> MAIN_UI
    CONFIRM_DELETE --> MAIN_UI
    DISPLAY_PAY --> MAIN_UI
    GEN_PAYSLIP --> MAIN_UI
    GEN_SUMMARY --> MAIN_UI
    
    %% Styling
    classDef startEnd fill:#ff9999,stroke:#ff0000,stroke-width:3px
    classDef process fill:#99ccff,stroke:#0066cc,stroke-width:2px
    classDef decision fill:#ffcc99,stroke:#ff6600,stroke-width:2px
    classDef dialog fill:#cc99ff,stroke:#6600cc,stroke-width:2px
    
    class START,MAIN_UI startEnd
    class INIT,SEARCH,VIEW_LIST,ADD_EMP,SELECT_EMP,CALC_PAY,GEN_PAYSLIP,GEN_SUMMARY process
    class EMP_MGMT,PAYROLL,REPORTS,REP_TYPE,CONFIRM_DELETE decision
    class SEARCH_RESULT,EMP_TABLE,EMP_FORM,EMP_DETAIL_FRAME,EDIT_FORM,DATE_RANGE,DISPLAY_PAY dialog
```

### Project Cleanup Visualization

```mermaid
flowchart LR
    subgraph "BEFORE Cleanup"
        B_ROOT[Project Root]
        B_SRC[src/main/java/]
        B_TEST[Mixed Test Files]
        B_EMPTY[Empty Files ❌]
        B_DUP[Duplicate Files ❌]
        B_CSV[Scattered CSV Files]
        
        B_ROOT --> B_CSV
        B_SRC --> B_TEST
        B_SRC --> B_EMPTY
        B_SRC --> B_DUP
        
        B_EMPTY --> B_EMPTY1[EmployeeListPanelNew.java]
        B_EMPTY --> B_EMPTY2[NavigationBar.java]
        B_EMPTY --> B_EMPTY3[EmployeeDetailsDialog.java]
        
        B_DUP --> B_DUP1[EmployeeListPanel.java]
        B_DUP --> B_DUP2[EmployeeListPanelFixed.java]
        
        B_TEST --> B_TEST1[CSVTest.java in main/]
        B_TEST --> B_TEST2[EmployeeServiceTest.java in main/]
    end
    
    subgraph "AFTER Cleanup"
        A_ROOT[Project Root]
        A_DATA[data/ Directory]
        A_SRC[src/main/java/]
        A_TEST[src/test/java/]
        A_CLEAN[Clean Structure ✅]
        
        A_ROOT --> A_DATA
        A_ROOT --> A_SRC
        A_ROOT --> A_TEST
        A_SRC --> A_CLEAN
        
        A_DATA --> A_CSV1[attendanceRecord.csv]
        A_DATA --> A_CSV2[employeeDetails.csv]
        
        A_SRC --> A_MAIN1[Main.java]
        A_SRC --> A_CONTROLLERS[Controllers/]
        A_SRC --> A_MODELS[Models/]
        A_SRC --> A_SERVICES[Services/]
        A_SRC --> A_VIEWS[Views/]
        A_SRC --> A_UTILS[Utils/]
        
        A_TEST --> A_TEST1[CSVTest.java]
        A_TEST --> A_TEST2[EmployeeServiceTest.java]
        A_TEST --> A_TEST3[Development Utils]
    end
    
    %% Transformation Arrow
    B_ROOT -.->|"CLEANUP PROCESS<br/>17% File Reduction"| A_ROOT
    
    %% Styling
    classDef beforeStyle fill:#ffcccc,stroke:#ff0000,stroke-width:2px
    classDef afterStyle fill:#ccffcc,stroke:#00aa00,stroke-width:2px
    classDef problemStyle fill:#ff9999,stroke:#cc0000,stroke-width:3px
    classDef solutionStyle fill:#99ff99,stroke:#00cc00,stroke-width:3px
    
    class B_ROOT,B_SRC,B_TEST,B_CSV beforeStyle
    class B_EMPTY,B_DUP,B_EMPTY1,B_EMPTY2,B_EMPTY3,B_DUP1,B_DUP2,B_TEST1,B_TEST2 problemStyle
    class A_ROOT,A_DATA,A_SRC,A_TEST afterStyle
    class A_CLEAN,A_CSV1,A_CSV2,A_MAIN1,A_CONTROLLERS,A_MODELS,A_SERVICES,A_VIEWS,A_UTILS,A_TEST1,A_TEST2,A_TEST3 solutionStyle
```

### Data Flow Architecture

```mermaid
flowchart TB
    %% External Data Sources
    CSV_EMP[(Employee CSV<br/>employeeDetails.csv)]
    CSV_ATT[(Attendance CSV<br/>attendanceRecord.csv)]
    
    %% Data Repository Layer
    subgraph "Data Repository Layer"
        DR[Data Repository<br/>Single Point of Access]
    end
    
    %% Service Layer
    subgraph "Business Logic Layer"
        ES[Employee Service<br/>Employee Operations]
        PS[Payroll Service<br/>Payroll Calculations]
        PP[Payroll Processor<br/>Complex Calculations]
        RS[Report Service<br/>Report Generation]
    end
    
    %% Controller Layer
    subgraph "Controller Layer"
        EC[Employee Controller<br/>Employee Management]
        PC[Payroll Controller<br/>Payroll Processing]
        RC[Report Controller<br/>Report Operations]
    end
    
    %% View Layer
    subgraph "Presentation Layer"
        MF[Main Frame<br/>Application Window]
        ELP[Employee List Panel]
        PP_UI[Payroll Panel]
        RP_UI[Reports Panel]
        DIALOGS[Various Dialogs]
    end
    
    %% User
    USER[👤 End User]
    
    %% Data Flow
    CSV_EMP --> DR
    CSV_ATT --> DR
    
    DR --> ES
    DR --> PS
    DR --> RS
    
    ES --> EC
    PS --> PC
    PS --> PP
    RS --> RC
    
    EC --> ELP
    PC --> PP_UI
    RC --> RP_UI
    
    ELP --> MF
    PP_UI --> MF
    RP_UI --> MF
    MF --> DIALOGS
    
    MF --> USER
    DIALOGS --> USER
    
    %% User interactions
    USER -.-> MF
    USER -.-> DIALOGS
    
    %% Styling
    classDef dataSource fill:#e3f2fd,stroke:#1976d2,stroke-width:3px
    classDef repository fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px
    classDef service fill:#e8f5e8,stroke:#388e3c,stroke-width:2px
    classDef controller fill:#fff3e0,stroke:#f57c00,stroke-width:2px
    classDef view fill:#fce4ec,stroke:#c2185b,stroke-width:2px
    classDef user fill:#ffecb3,stroke:#ffa000,stroke-width:3px
    
    class CSV_EMP,CSV_ATT dataSource
    class DR repository
    class ES,PS,PP,RS service
    class EC,PC,RC controller
    class MF,ELP,PP_UI,RP_UI,DIALOGS view
    class USER user
```

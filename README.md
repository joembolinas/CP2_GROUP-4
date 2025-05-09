# MotorPH Payroll System

A modular, object-oriented Java payroll system for MotorPH, designed to manage employees, attendance, and payroll calculations. The system loads employee and attendance data from CSV files, calculates payslips, and provides a menu-driven interface for payroll management and reporting.

## Features
- Employee management (search, list, view attendance)
- Payroll calculation (gross/net pay, SSS, PhilHealth, Pag-IBIG, tax)
- Payslip generation for custom periods
- Summary reports (weekly/monthly)
- Loads data from Google Sheets (CSV)

## Project Structure
- `Employee.java` — Employee details and salary logic
- `AttendanceRecord.java` — Attendance and time tracking
- `PayrollCalculator.java` — Payroll and deduction calculations
- `PaySlip.java` — Payslip generation and display
- `CSVLoader.java` — Loads employees and attendance from CSV
- `MenuManager.java` — Menu navigation and user interaction
- `MotorPHPayrollMain.java` — Application entry point

## Setup & Usage
1. **Requirements:**
   - Java 17 or higher
   - Maven (for build and dependency management)
2. **Build:**
   ```bash
   mvn clean package
   ```
3. **Run:**
   ```bash
   java -cp target/classes com.motorph.MotorPHPayrollMain
   ```
4. **CSV Data:**
   - Employee and attendance data are loaded from Google Sheets CSV URLs (see `CSVLoader.java`).

## Class Diagram

@startuml
skinparam classAttributeIconSize 0

abstract class Person {
    +String getFullName()
}

class Employee {
    +int employeeId
    +String lastName
    +String firstName
    +String position
    +String status
    +double basicSalary
    +double riceSubsidy
    +double phoneAllowance
    +double clothingAllowance
    +double hourlyRate
    +Employee(int employeeId, String lastName, String firstName, String position,
             String status, double basicSalary, double riceSubsidy,
             double phoneAllowance, double clothingAllowance)
    +String getFullName()
    +void calculateHourlyRate()
}

class AttendanceRecord {
    +int employeeId
    +LocalDate date
    +LocalTime timeIn
    +LocalTime timeOut
    +AttendanceRecord(int employeeId, LocalDate date, LocalTime timeIn, LocalTime timeOut)
    +Duration getDuration()
    +boolean isLate()
    +double getTotalHours()
}

class PaySlip {
    +Employee employee
    +LocalDate startDate
    +LocalDate endDate
    +double regularHours
    +double overtimeHours
    +double grossPay
    +double netPay
    +Map<String, Double> deductions
    +Map<String, Double> allowances
    +PaySlip(Employee employee, LocalDate startDate, LocalDate endDate)
    +void generate()
    +void display()
    +double calculateRegularHours()
    +double calculateOvertimeHours()
    +Map<String, Double> calculateAllowances()
    +Map<String, Double> calculateDeductions()
}

class PayrollCalculator {
    +final Map<Double, Double> sssTable
    +final double philHealthRate = 0.03
    +final double pagIbigRate = 0.02
    +PayrollCalculator()
    +double calculateGrossPay(double hoursWorked, double hourlyRate)
    +double calculateNetPay(double grossPay)
    +double calculateSSSContribution(double grossPay)
    +double calculatePhilHealthContribution(double grossPay)
    +double calculatePagIbigContribution(double grossPay)
    +double calculateWithholdingTax(double taxableIncome)
    +Map<Double, Double> initSSSTable()
}

class CSVLoader {
    +String employeesUrl
    +String attendanceUrl
    +CSVLoader(String employeesUrl, String attendanceUrl)
    +List`<Employee>` loadEmployees()
    +List`<AttendanceRecord>` loadAttendanceRecords()
    +LocalDate parseFlexibleDate(String dateStr)
}

class MenuManager {
    +static final double OVERTIME_RATE = 1.25
    +static final int REGULAR_HOURS_PER_DAY = 8
    +static final int WORK_DAYS_PER_MONTH = 21
    +static final LocalTime LATE_THRESHOLD = LocalTime.of(8, 10)
    +List`<Employee>` employees
    +List`<AttendanceRecord>` attendanceRecords
    +Scanner scanner
    +PayrollCalculator payrollCalculator
    +MenuManager(List`<Employee>` employees, List`<AttendanceRecord>` attendanceRecords)
    +void startApplication()
    +void displayMainMenu()
    +void employeeManagementMenu()
    +void payrollManagementMenu()
    +void reportsMenu()
    +void generatePayroll()
    +void generateEmployeePayslip()
    +void viewAttendance()
    +void searchEmployee()
    +void listAllEmployees()
    +LocalDate getDateInput(String prompt)
    +double getHourlyRate(Employee employee)
    +Map<String, Double> calculateAllowances(Employee employee, LocalDate startDate, LocalDate endDate)
    +LocalDate parseFlexibleDate(String dateStr)
}

class MotorPHPayrollMain {
    +CSVLoader csvLoader
    +MenuManager menuManager
    +MotorPHPayrollMain(CSVLoader csvLoader, MenuManager menuManager)
    +static void main(String[] args)
    +static void initializeSystem()
}

Person <|-- Employee
MotorPHPayrollMain o-- CSVLoader
MotorPHPayrollMain o-- MenuManager
MenuManager o-- PayrollCalculator
MenuManager o-- Employee
MenuManager o-- AttendanceRecord
PaySlip o-- Employee
PaySlip o-- AttendanceRecord
PayrollCalculator o-- "1" Map : uses
AttendanceRecord o-- "1" Duration : uses
AttendanceRecord o-- "1" LocalTime : uses
AttendanceRecord o-- "1" LocalDate : uses

note right of MotorPHPayrollMain
    Entry point of the application
    that initializes all components
    and starts the application.
end note

note right of MenuManager
    Manages all menus, navigation
    and program flow. Handles user
    interactions and coordinates
    with other classes.
end note

note left of CSVLoader
    Handles loading data from CSV files,
    including employees and attendance
    records. Contains parsing logic for
    different date formats.
end note

note left of Employee
    Represents an employee with personal
    information and salary details.
    Provides methods to get employee
    information and calculate hourly rate.
end note

note right of AttendanceRecord
    Represents a single attendance record
    for an employee. Calculates duration,
    checks if employee was late, and
    calculates total hours worked.
end note

note right of PayrollCalculator
    Handles all payroll calculations
    including gross pay, net pay, and
    various deductions like SSS,
    PhilHealth, Pag-IBIG, and
    withholding tax.
end note

note left of PaySlip
    Represents a payslip for an employee
    for a specific period. Calculates and
    displays payslip information including
    allowances and deductions.
end note
@enduml

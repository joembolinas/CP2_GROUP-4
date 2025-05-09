### Decision and Thinking Process for Refactoring from Procedural to modular

#### **1. Identifying the Problem with the Procedural Approach**

The original procedural approach likely had the following issues:

- **Scalability Challenges**: As the payroll system grew, adding new features or modifying existing ones became increasingly difficult due to tightly coupled logic and lack of modularity.
- **Code Duplication**: Common operations like payroll calculations, attendance filtering, and report generation were likely repeated in multiple places, increasing maintenance overhead.
- **Poor Maintainability**: Debugging and understanding the code was cumbersome because the logic was scattered across multiple functions without clear boundaries.
- **Lack of Abstraction**: The procedural approach did not effectively encapsulate related data and behavior, leading to a mix of responsibilities in single functions.

---

#### **2. Recognizing the Benefits** 

The decision to refactor this was driven by the following advantages:

- **Modularity**: By encapsulating related data and behavior into classes, the system could be broken into smaller, manageable components.
- **Reusability**: Classes like `PayrollManager`, `ReportManager`, and `EmployeeManager` could be reused across different parts of the application.
- **Scalability**: Adding new features (e.g., new types of reports or deductions) became easier by extending existing classes or adding new ones.
- **Maintainability**: Clear separation of concerns made the code easier to read, debug, and modify.
- **Abstraction**: OOP allowed the developer to abstract complex operations (e.g., payroll calculations) into methods, hiding unnecessary details from other parts of the system.

---

#### **3. Refactoring Process**

The refactoring process involved the following steps:

1. **Identifying Key Entities**:

   - The developer identified the main entities in the payroll system:
     - **Employee**: Represented by `MotorPHDataModels.EmployeeData`.
     - **Attendance Record**: Represented by `MotorPHDataModels.AttendanceRecord`.
     - **Payroll Result**: Represented by `MotorPHDataModels.PayrollResult`.
2. **Defining Attributes and Methods**:

   - Each entity was encapsulated into a class with relevant attributes and methods:
     - `EmployeeData` encapsulated employee details like `employeeId`, `basicSalary`, and `department`.
     - `AttendanceRecord` encapsulated attendance details like `date`, `timeIn`, and `timeOut`.
     - `PayrollResult` encapsulated payroll details like `grossPay`, `deductions`, and `netPay`.
3. **Encapsulating Responsibilities**:

   - Responsibilities were distributed across specialized classes:
     - **`EmployeeManager`**: Managed employee data (e.g., searching, displaying details).
     - **`PayrollManager`**: Handled payroll calculations and attendance filtering.
     - **`ReportManager`**: Generated reports like payslips and summaries.
     - **`DeductionsCalculator`**: Centralized deduction logic (e.g., SSS, PhilHealth).
4. **Establishing Relationships**:

   - Relationships between classes were clearly defined:
     - `PayrollManager` depended on `EmployeeManager` for employee data and `DeductionsCalculator` for deduction logic.
     - `ReportManager` used `PayrollManager` to generate payroll data for reports.
5. **Using Constructors**:

   - Constructors were used to initialize objects with their required dependencies, ensuring proper encapsulation and reducing the risk of errors.
6. **Creating a Class Diagram**:

   - A class diagram was likely created to visualize the structure, attributes, methods, and relationships between classes. This helped ensure a logical and organized design.

---

#### **4. Benefits Realized Post-Refactor**

The refactor to OOP provided the following tangible benefits:

1. **Cleaner Code**:

   - The codebase was now organized into logical units (classes), making it easier to understand and navigate.
2. **Improved Maintainability**:

   - Changes to one part of the system (e.g., payroll calculations) could be made without affecting unrelated parts.
3. **Enhanced Reusability**:

   - Classes like `DeductionsCalculator` and `PayrollManager` could be reused in other projects or extended for new features.
4. **Scalability**:

   - Adding new features (e.g., new types of reports or allowances) became straightforward due to the modular design.
5. **Better Debugging**:

   - Issues could be traced to specific classes or methods, reducing debugging time.

---

#### **Example of Refactoring**

Here’s an example of how a procedural payroll calculation might have been refactored into OOP:

**Procedural Approach**:

```java
// Procedural payroll calculation
double calculatePayroll(int employeeId, List<AttendanceRecord> attendanceRecords) {
    // Logic to calculate payroll
    return grossPay - deductions;
}
```

**Refactored modular Approach**:

```java
// modular payroll calculation
class PayrollManager {
    public PayrollResult processEmployeePayroll(int employeeId, LocalDate startDate, LocalDate endDate) {
        EmployeeData employee = findEmployeeById(employeeId);
        List<AttendanceRecord> attendance = filterAttendance(employeeId, startDate, endDate);
        return calculatePayroll(employee, attendance, startDate, endDate);
    }
}
```

---

### **Conclusion**

The decision to refactor the program from procedural to OOP was driven by the need for better modularity, maintainability, and scalability. By encapsulating data and behavior into classes, the developer created a more organized and reusable system that could easily adapt to future requirements. This refactor not only improved the code quality but also made the system more robust and easier to work with.

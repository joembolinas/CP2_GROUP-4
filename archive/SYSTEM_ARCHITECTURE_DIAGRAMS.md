# 🏗️ MotorPH System Architecture Diagrams

## Complete System Architecture

```mermaid
graph TB
    subgraph "Presentation Layer"
        A[MainFrame]
        B[EmployeeListPanel]
        C[NewEmployeeDialog]
        D[EmployeeDetailsFrame]
        E[ApplicationMenuBar]
    end
    
    subgraph "Controller Layer"
        F[EmployeeController]
        G[PayrollController]
        H[ReportController]
    end
    
    subgraph "Service Layer"
        I[EmployeeService]
        J[PayrollService]
        K[ReportService]
        L[PayrollProcessor]
    end
    
    subgraph "Repository Layer"
        M[DataRepository]
        N[CSVCreateAndWrite]
    end
    
    subgraph "Data Layer"
        O[employeeDetails.csv]
        P[attendanceRecord.csv]
    end
    
    subgraph "Utility Layer"
        Q[UIConstants]
        R[UIUtils]
        S[DateUtils]
        T[InputValidator]
        U[ErrorHandler]
    end
    
    subgraph "Model Layer"
        V[Employee]
        W[PaySlip]
        X[AttendanceRecord]
    end
    
    subgraph "External Libraries"
        Y[OpenCSV 5.7.1]
        Z[Java Swing]
    end
    
    A --> B
    A --> C
    A --> D
    A --> E
    
    B --> F
    C --> F
    D --> F
    E --> G
    E --> H
    
    F --> I
    G --> J
    H --> K
    
    I --> M
    J --> M
    K --> M
    J --> L
    
    M --> N
    M --> O
    M --> P
    
    B --> Q
    C --> Q
    D --> Q
    B --> R
    C --> R
    
    I --> S
    C --> T
    F --> U
    
    I --> V
    J --> W
    M --> X
    
    N --> Y
    A --> Z
    
    style A fill:#e3f2fd
    style I fill:#e8f5e8
    style O fill:#fff3e0
    style Y fill:#fce4ec
```

## MPHCR-02 Feature Integration

```mermaid
flowchart TD
    subgraph "MPHCR-02 New Components"
        A[EmployeeListPanel<br/>📊 Employee Table Display]
        B[NewEmployeeDialog<br/>➕ Employee Creation]
        C[ActionButtonRenderer<br/>🎨 Custom Table UI]
        D[ActionButtonEditor<br/>⚡ Button Interactions]
        E[CSV Enhancement<br/>💾 File Persistence]
    end
    
    subgraph "Enhanced Existing Components"
        F[EmployeeService<br/>🔧 CRUD Operations]
        G[EmployeeController<br/>🎮 Enhanced Logic]
        H[UIConstants<br/>🎨 Style System]
        I[Employee Model<br/>📋 Extended Fields]
    end
    
    subgraph "Data Flow"
        J[User Input] --> B
        B --> G
        G --> F
        F --> E
        E --> K[CSV Files]
        
        L[User Views List] --> A
        A --> C
        C --> D
        D --> M[Employee Actions]
    end
    
    A --> F
    B --> F
    C --> H
    D --> H
    F --> I
    
    style A fill:#4caf50,color:#fff
    style B fill:#2196f3,color:#fff
    style E fill:#ff9800,color:#fff
    style H fill:#9c27b0,color:#fff
```

## User Interaction Flow

```mermaid
sequenceDiagram
    actor User
    participant UI as EmployeeListPanel
    participant Dialog as NewEmployeeDialog
    participant Controller as EmployeeController
    participant Service as EmployeeService
    participant CSV as CSV File
    
    Note over User,CSV: MPHCR-02 Complete User Journey
    
    User->>UI: Launch Employee Management
    UI->>Service: loadAllEmployees()
    Service->>CSV: read existing data
    CSV-->>Service: employee list
    Service-->>UI: populate table
    UI-->>User: display employee list
    
    User->>UI: Click "Add New Employee"
    UI->>Dialog: open NewEmployeeDialog
    Dialog-->>User: show employee form
    
    User->>Dialog: fill form and submit
    Dialog->>Dialog: validate input
    Dialog->>Controller: addEmployee(employee)
    Controller->>Service: addEmployee(employee)
    Service->>Service: validate business rules
    Service->>CSV: append new employee
    CSV-->>Service: confirm write success
    Service-->>Controller: return success
    Controller-->>Dialog: confirm addition
    Dialog-->>User: show success message
    Dialog->>UI: close and refresh
    UI->>Service: reload employee list
    Service-->>UI: updated list
    UI-->>User: display updated table
    
    User->>UI: Click "View" on employee
    UI->>+Controller: viewEmployee(id)
    Controller->>+Service: findEmployeeById(id)
    Service-->>-Controller: employee details
    Controller-->>-UI: open EmployeeDetailsFrame
    UI-->>User: show employee details
```

## Component Dependency Graph

```mermaid
graph LR
    subgraph "UI Components"
        A[EmployeeListPanel]
        B[NewEmployeeDialog]
        C[EmployeeDetailsFrame]
    end
    
    subgraph "Controllers"
        D[EmployeeController]
    end
    
    subgraph "Services"
        E[EmployeeService]
    end
    
    subgraph "Utilities"
        F[UIConstants]
        G[UIUtils]
        H[InputValidator]
    end
    
    subgraph "External"
        I[OpenCSV]
        J[Java Swing]
    end
    
    A -->|uses| F
    A -->|uses| G
    A -->|calls| D
    A -->|extends| J
    
    B -->|uses| F
    B -->|uses| H
    B -->|calls| D
    B -->|extends| J
    
    C -->|uses| F
    C -->|uses| G
    C -->|extends| J
    
    D -->|calls| E
    
    E -->|uses| I
    
    style A fill:#bbdefb
    style B fill:#c8e6c9
    style C fill:#ffcdd2
    style D fill:#fff9c4
    style E fill:#f3e5f5
```

## Data Model Relationships

```mermaid
erDiagram
    Employee {
        int employeeId PK
        string lastName
        string firstName
        date birthday
        string address
        string phoneNumber
        string sssNumber
        string philhealthNumber
        string tinNumber
        string pagibigNumber
        string status
        string position
        string supervisor
        double basicSalary
        double riceSubsidy
        double phoneAllowance
        double clothingAllowance
        double grossSemiMonthlyRate
        double hourlyRate
    }
    
    AttendanceRecord {
        int employeeId FK
        date date
        time timeIn
        time timeOut
    }
    
    PaySlip {
        int employeeId FK
        string period
        double grossPay
        double netPay
        double totalDeductions
    }
    
    Employee ||--o{ AttendanceRecord : "has many"
    Employee ||--o{ PaySlip : "generates"
```

## File Structure and Organization

```mermaid
graph TD
    A[motorph_payroll_system/] --> B[src/main/java/com/motorph/]
    
    B --> C[controller/]
    B --> D[model/]
    B --> E[service/]
    B --> F[repository/]
    B --> G[view/]
    B --> H[util/]
    B --> I[test/]
    
    C --> C1[EmployeeController.java]
    C --> C2[PayrollController.java]
    C --> C3[ReportController.java]
    
    D --> D1[Employee.java]
    D --> D2[PaySlip.java]
    D --> D3[AttendanceRecord.java]
    
    E --> E1[EmployeeService.java ⭐]
    E --> E2[PayrollService.java]
    E --> E3[ReportService.java]
    E --> E4[PayrollProcessor.java]
    
    F --> F1[DataRepository.java]
    F --> F2[CSVCreateAndWrite.java ⭐]
    
    G --> G1[EmployeeListPanel.java ⭐]
    G --> G2[NewEmployeeDialog.java ⭐]
    G --> G3[EmployeeDetailsFrame.java]
    G --> G4[MainFrame.java]
    
    H --> H1[UIConstants.java ⭐]
    H --> H2[UIUtils.java]
    H --> H3[DateUtils.java]
    H --> H4[InputValidator.java]
    
    I --> I1[EmployeeServiceTest.java ⭐]
    I --> I2[SimpleCSVTest.java ⭐]
    I --> I3[CSVTest.java]
    
    style E1 fill:#4caf50,color:#fff
    style F2 fill:#4caf50,color:#fff
    style G1 fill:#4caf50,color:#fff
    style G2 fill:#4caf50,color:#fff
    style H1 fill:#4caf50,color:#fff
    style I1 fill:#4caf50,color:#fff
    style I2 fill:#4caf50,color:#fff
```

## Technology Stack Overview

```mermaid
graph TB
    subgraph "Frontend Layer"
        A[Java Swing Components]
        B[Custom UI Components]
        C[UIConstants Styling]
    end
    
    subgraph "Application Layer"
        D[MVC Controllers]
        E[Business Services]
        F[Data Models]
    end
    
    subgraph "Data Layer"
        G[CSV File Storage]
        H[OpenCSV Library]
        I[File I/O Operations]
    end
    
    subgraph "Cross-Cutting Concerns"
        J[Error Handling]
        K[Input Validation]
        L[Logging]
        M[Testing]
    end
    
    A --> D
    B --> D
    C --> A
    
    D --> E
    E --> F
    
    E --> G
    G --> H
    H --> I
    
    D --> J
    E --> K
    F --> L
    A --> M
    
    style A fill:#2196f3,color:#fff
    style E fill:#4caf50,color:#fff
    style G fill:#ff9800,color:#fff
    style J fill:#f44336,color:#fff
```

---

*These diagrams represent the complete architecture of the MPHCR-02 enhanced MotorPH Payroll System, highlighting the new components and their integration with existing system elements.*

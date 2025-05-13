
---



### CHANGE REQUEST FORM - MPHCR01-Feature 1

| **PROJECT NAME:** | MotorPH Employee App | **CHANGE NAME:** Addition of Feature 1 | **NUMBER:** MPHCR01 |
| ----------------------- | -------------------- | -------------------------------------------- | ------------------------- |
| **REQUESTED BY:** | MotorPH              | **CONTACT:**                           | **DATE:**           |

**DESCRIPTION OF CHANGE:**
Provision of a more intuitive and interactive interface in the MotorPH Employee App by implementing a version of the application that has a Graphical User Interface based on the class diagram that you have designed. The application should be able to handle errors in the input of the users.

**REASON FOR CHANGE:**
The use of a console-based program is time-consuming. The management is also after the integrity of the data the application accepts.

| **PRIORITY** | High |
| ------------------ | ---- |

**IMPACT ON DELIVERABLES:**
Minimal impact

| **DATE NEEDED:** 19 May 2025 | **APPROVAL OF REQUEST:** Approved | **DATE:** |
| ---------------------------------- | --------------------------------------- | --------------- |

**COMMENTS:**

1. Convert the console-based application to a working GUI-based application.
2. Provide exception handling for the input of Employee Number and Pay Coverage.

---


### CHANGE REQUEST FORM - MPHCR02-Feature 2

| **PROJECT NAME:** | MotorPH Employee App | **CHANGE NAME:** Addition of Feature 2 | **NUMBER:** MPHCR02 |
| ----------------------- | -------------------- | -------------------------------------------- | ------------------------- |
| **REQUESTED BY:** | MotorPH              | **CONTACT:**                           | **DATE:**           |

**DESCRIPTION OF CHANGE:**
The current version of MotorPH Employee App only displays the information of an employee together with his/her pay details once prompted. This change request aims to improve the interface by providing more intuitive and interactive ways to view and create employee records. The enhancements for this update include:
(1) Viewing all employee records;
(2) Viewing a specific employee’s record;
(3) Viewing the pay details of a specific employee after selecting a month for salary computation; and
(4) Creating new employee records and saving them to the CSV file.

**REASON FOR CHANGE:**
The application needs a more organized and user-friendly interface to efficiently access, display, and add employee records. Adding a create functionality allows for better data management and aligns with the system’s goal of supporting basic employee record operations.

| **PRIORITY** | High |
| ------------------ | ---- |

**IMPACT ON DELIVERABLES:**
Significant

| **DATE NEEDED:** 02 Jun 2025 | **APPROVAL OF REQUEST:** Approved | **DATE:** |
| ---------------------------------- | --------------------------------------- | --------------- |

**COMMENTS:**

1. Add a form or frame that displays the following fields for all employees in a JTable: Employee Number, Last Name, First Name, SSS Number, PhilHealth Number, TIN, and Pag-IBIG Number.
2. Allow the user to select an employee from the table and click the "View Employee" button to open a new frame displaying the employee’s full details.
3. In the new frame, prompt the user to select the month for which the program will compute the employee’s salary.
4. After clicking the "Compute" button, display both the employee’s details and the computed salary information within the same frame.
5. Add a "New Employee" button that opens a form for entering new employee information.
6. Upon submission, the new employee’s data should be appended to the CSV file, and the JTable should refresh to show the updated list of employees.

---



### CHANGE REQUEST FORM - MPHCR03-Feature 3

| **PROJECT NAME:** | MotorPH Employee App | **CHANGE NAME:** Addition of Feature 3 | **NUMBER:** MPHCR03 |
| ----------------------- | -------------------- | -------------------------------------------- | ------------------------- |
| **REQUESTED BY:** | MotorPH              | **CONTACT:**                           | **DATE:**           |

**DESCRIPTION OF CHANGE:**
Provision of update and delete functionalities to the Employee Records of the MotorPH Employee App.

**REASON FOR CHANGE:**
Employee records must be updated and deleted as needed. Current version does not have the functionality to update and delete records from the file.

| **PRIORITY** | High |
| ------------------ | ---- |

**IMPACT ON DELIVERABLES:**
Significant

| **DATE NEEDED:** 09 Jun 2025 | **APPROVAL OF REQUEST:** Approved | **DATE:** |
| ---------------------------------- | --------------------------------------- | --------------- |

**COMMENTS:**

1. Once a row is selected in the JTable, the employee data will then be displayed in the textboxes.
2. An "Update" button will be enabled to modify and save the employee details of the selected record.
3. A "Delete" button will be enabled to remove the selected employee record from the CSV file.
4. After updating or deleting a record, the JTable must be refreshed to reflect the latest data.

---



### CHANGE REQUEST FORM - MPHCR04-Feature 4

| **PROJECT NAME:** | MotorPH Employee App | **CHANGE NAME:** Addition of Feature 4 | **NUMBER:** MPHCR04 |
| ----------------------- | -------------------- | -------------------------------------------- | ------------------------- |
| **REQUESTED BY:** | MotorPH              | **CONTACT:**                           | **DATE:**           |

**DESCRIPTION OF CHANGE:**
Provision of login feature in the application.

**REASON FOR CHANGE:**
To make the application secure, the users of the application must enter their correct login credentials to be able to access the application properly.

| **PRIORITY** | High |
| ------------------ | ---- |

**IMPACT ON DELIVERABLES:**
Significant

| **DATE NEEDED:** 30 Jun 2025 | **APPROVAL OF REQUEST:** Approved | **DATE:** |
| ---------------------------------- | --------------------------------------- | --------------- |

**COMMENTS:**

1. Employees must enter the correct username and password to gain access to the application.
2. A separate CSV file will serve as your data source for the authorized accounts for the application.
3. Employees who enter an incorrect username and/or password must be denied access, and a prompt should inform them that their login credentials are invalid.

---

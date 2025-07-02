package com.motorph.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;

/**
 * Consolidated repository for loading and accessing employee and attendance
 * data.
 * This class combines functionality from CSVLoader, CSVRepository,
 * EmployeeRepository,
 * and AttendanceRepository to reduce redundancy.
 */
public class DataRepository {
    private static final Logger logger = Logger.getLogger(DataRepository.class.getName());

    private final String employeesFilePath;
    private final String attendanceFilePath;
    private List<Employee> employees;
    private List<AttendanceRecord> attendanceRecords;

    private static final DateTimeFormatter[] DATE_FORMATTERS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd")
    };

    private static final DateTimeFormatter[] TIME_FORMATTERS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("HH:mm"),
            DateTimeFormatter.ofPattern("H:mm"),
            DateTimeFormatter.ofPattern("HH:mm:ss"),
            DateTimeFormatter.ofPattern("h:mm a"),
            DateTimeFormatter.ofPattern("h:mm"), // Format seen in data: e.g., "8:59"
            DateTimeFormatter.ofPattern("H:mm") // Single digit hour format
    };

    static {
        try {
            FileHandler fh = new FileHandler("payroll_system.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.INFO);
        } catch (IOException ex) {
            System.err.println("Could not initialize logger: " + ex.getMessage());
        }
    }

    public DataRepository(String employeesFilePath, String attendanceFilePath) {
        this.employeesFilePath = employeesFilePath;
        this.attendanceFilePath = attendanceFilePath;
        logger.info("DataRepository initialized");
    }

    /**
     * Get all employees, loading them if necessary
     * 
     * @return List of employees
     */
    public List<Employee> getAllEmployees() {
        if (employees == null) {
            try {
                loadEmployees();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error loading employees: {0}", e.getMessage());
                employees = new ArrayList<>();
            }
        }
        return employees;
    }

    /**
     * Find an employee by ID
     * 
     * @param employeeId The employee ID to find
     * @return Employee or null if not found
     */
    public Employee findEmployeeById(int employeeId) {
        return getAllEmployees().stream()
                .filter(emp -> emp.getEmployeeId() == employeeId)
                .findFirst()
                .orElse(null);
    }

    /**
     * Search for employees by name or ID
     * 
     * @param searchTerm The search term (name or ID)
     * @return List of matching employees
     */
    public List<Employee> searchEmployees(String searchTerm) {
        String term = searchTerm.toLowerCase();

        return getAllEmployees().stream()
                .filter(employee -> {
                    String empId = String.valueOf(employee.getEmployeeId()).toLowerCase();
                    String lastName = employee.getLastName().toLowerCase();
                    String firstName = employee.getFirstName().toLowerCase();
                    String fullName = (firstName + " " + lastName).toLowerCase();

                    return empId.contains(term) ||
                            lastName.contains(term) ||
                            firstName.contains(term) ||
                            fullName.contains(term);
                })
                .collect(Collectors.toList());
    }

    /**
     * Get all attendance records, loading them if necessary
     * 
     * @return List of attendance records
     */
    public List<AttendanceRecord> getAllAttendanceRecords() {
        if (attendanceRecords == null) {
            try {
                loadAttendanceRecords();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error loading attendance records: {0}", e.getMessage());
                attendanceRecords = new ArrayList<>();
            }
        }
        return attendanceRecords;
    }

    /**
     * Get attendance records for a specific employee
     * 
     * @param employeeId The employee ID
     * @return List of attendance records
     */
    public List<AttendanceRecord> getAttendanceRecords(int employeeId) {
        return getAllAttendanceRecords().stream()
                .filter(record -> record.getEmployeeId() == employeeId)
                .collect(Collectors.toList());
    }

    /**
     * Get attendance records for a specific employee and date range
     * 
     * @param employeeId The employee ID
     * @param startDate  Start date for the range
     * @param endDate    End date for the range
     * @return List of attendance records
     */
    public List<AttendanceRecord> getAttendanceRecords(int employeeId, LocalDate startDate, LocalDate endDate) {
        return getAllAttendanceRecords().stream()
                .filter(record -> record.getEmployeeId() == employeeId)
                .filter(record -> {
                    LocalDate recordDate = record.getDate();
                    return (recordDate.equals(startDate) || recordDate.isAfter(startDate)) &&
                            (recordDate.equals(endDate) || recordDate.isBefore(endDate));
                })
                .collect(Collectors.toList());
    }

    /**
     * Loads employee data from a CSV file
     * 
     * @throws IOException if the file cannot be read
     */
    private void loadEmployees() throws IOException {
        List<Employee> loadedEmployees = new ArrayList<>();
        logger.log(Level.INFO, "Loading employees from: {0}", employeesFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(employeesFilePath))) {
            CSVParser parser = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(reader);

            int totalRecords = 0;
            int successfulRecords = 0;

            for (CSVRecord record : parser) {
                totalRecords++;
                try {
                    Employee employee = createEmployeeFromRecord(record);
                    if (employee != null) {
                        loadedEmployees.add(employee);
                        successfulRecords++;
                        logger.log(Level.FINE, "Added employee: {0} {1}",
                                new Object[] { employee.getFirstName(), employee.getLastName() });
                    }
                } catch (IllegalArgumentException ex) {
                    logger.log(Level.WARNING, "Invalid data format in employee record {0}: {1}",
                            new Object[] { totalRecords, ex.getMessage() });
                }
            }
            logger.log(Level.INFO, "Successfully loaded {0} of {1} employee records",
                    new Object[] { successfulRecords, totalRecords });

            // Debug log for employee 10001
            for (Employee emp : loadedEmployees) {
                if (emp.getEmployeeId() == 10001) {
                    logger.log(Level.INFO,
                            "Employee 10001 details: ID={0}, Name={1} {2}, Birthday={3}, Address={4}, SSS={5}",
                            new Object[] { emp.getEmployeeId(), emp.getFirstName(), emp.getLastName(),
                                    emp.getBirthday(), emp.getAddress(), emp.getSssNumber() });
                    break;
                }
            }

        } catch (IOException ex) {
            logger.log(Level.WARNING, "Could not read from employee file: {0}", ex.getMessage());
            logger.info("Using sample employee data");
            loadSampleEmployees(loadedEmployees);
        }

        this.employees = loadedEmployees;
    }

    private Employee createEmployeeFromRecord(CSVRecord record) {
        try {
            int id = Integer.parseInt(record.get(0));
            String lastName = record.get(1);
            String firstName = record.get(2);

            // Parse birthday with flexible date format
            LocalDate birthday = null;
            if (!record.get(3).isEmpty()) {
                birthday = parseFlexibleDate(record.get(3));
            }

            String address = record.get(4);
            String phoneNumber = record.get(5);
            String sssNumber = record.get(6);
            String philhealthNumber = record.get(7);
            String tinNumber = record.get(8);
            String pagibigNumber = record.get(9);
            String status = record.get(10);
            String position = record.get(11);
            String supervisor = record.get(12);

            double basicSalary = parseAmount(record.get(13));
            double riceSubsidy = parseAmount(record.get(14));
            double phoneAllowance = parseAmount(record.get(15));
            double clothingAllowance = parseAmount(record.get(16));
            double grossSemiMonthlyRate = parseAmount(record.get(17));

            // Use the full constructor to include all employee details
            return new Employee(
                    id, lastName, firstName, birthday, address, phoneNumber,
                    sssNumber, philhealthNumber, tinNumber, pagibigNumber,
                    status, position, supervisor, basicSalary, riceSubsidy,
                    phoneAllowance, clothingAllowance, grossSemiMonthlyRate);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid number format: " + ex.getMessage());
        }
    }

    private void loadSampleEmployees(List<Employee> employees) {
        // Create sample employees with full details if this set of employee you see on
        // the jtable for employee your app is on the problem hahaha
        LocalDate sampleDate = LocalDate.of(1990, 1, 1);
        employees.add(new Employee(
                10001, "Garcia", "Manuel", sampleDate, "Sample Address", "123-456-789",
                "SSS-123456", "PH-123456", "TIN-123456", "PAGIBIG-123456",
                "Regular", "Chief Executive Officer", "N/A", 90000, 1500, 2000, 1000, 45000));
        employees.add(new Employee(
                10002, "Santos", "Antonio", sampleDate, "Sample Address", "123-456-789",
                "SSS-123456", "PH-123456", "TIN-123456", "PAGIBIG-123456",
                "Regular", "Chief Operating Officer", "Garcia, Manuel", 60000, 1500, 2000, 1000, 30000));
        employees.add(new Employee(
                10003, "Reyes", "Bianca", sampleDate, "Sample Address", "123-456-789",
                "SSS-123456", "PH-123456", "TIN-123456", "PAGIBIG-123456",
                "Regular", "Chief Finance Officer", "Garcia, Manuel", 60000, 1500, 2000, 1000, 30000));
        employees.add(new Employee(
                10004, "Lim", "Isabella", sampleDate, "Sample Address", "123-456-789",
                "SSS-123456", "PH-123456", "TIN-123456", "PAGIBIG-123456",
                "Regular", "Chief Marketing Officer", "Garcia, Manuel", 60000, 1500, 2000, 1000, 30000));
        employees.add(new Employee(
                10005, "Lee", "Harper", sampleDate, "Sample Address", "123-456-789",
                "SSS-123456", "PH-123456", "TIN-123456", "PAGIBIG-123456",
                "Regular", "IT Operations", "Lim, Antonio", 52670, 1500, 1000, 1000, 26335));
        logger.info("Loaded sample employee data");
    }

    /**
     * Loads attendance records from a CSV file
     * 
     * @throws IOException if the file cannot be read
     */
    private void loadAttendanceRecords() throws IOException {
        List<AttendanceRecord> records = new ArrayList<>();
        logger.log(Level.INFO, "Loading attendance from: {0}", attendanceFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(attendanceFilePath))) {
            CSVParser parser = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(reader);

            int totalRecords = 0;
            int successfulRecords = 0;

            for (CSVRecord record : parser) {
                totalRecords++;
                try {
                    AttendanceRecord attendanceRecord = createAttendanceFromRecord(record);
                    if (attendanceRecord != null) {
                        records.add(attendanceRecord);
                        successfulRecords++;
                        logger.log(Level.FINE, "Added attendance record for employee ID: {0} on {1}",
                                new Object[] { attendanceRecord.getEmployeeId(), attendanceRecord.getDate() });
                    }
                } catch (Exception ex) {
                    logger.log(Level.WARNING, "Invalid data format in attendance record {0}: {1}",
                            new Object[] { totalRecords, ex.getMessage() });
                }
            }

            logger.log(Level.INFO, "Successfully loaded {0} of {1} attendance records",
                    new Object[] { successfulRecords, totalRecords });

        } catch (IOException ex) {
            logger.log(Level.WARNING, "Could not read from attendance file: {0}", ex.getMessage());
            logger.info("Using sample attendance data");
            loadSampleAttendanceRecords(records);
        }

        this.attendanceRecords = records;
    }

    private AttendanceRecord createAttendanceFromRecord(CSVRecord record) {
        try {
            int employeeId = Integer.parseInt(record.get(0));
            LocalDate date = parseFlexibleDate(record.get(3)); // Using correct column index for Date (3)
            LocalTime timeIn = parseFlexibleDateTime(record.get(4)); // Using correct column index for Log In (4)
            LocalTime timeOut = parseFlexibleDateTime(record.get(5)); // Using correct column index for Log Out (5)

            return new AttendanceRecord(employeeId, date, timeIn, timeOut);
        } catch (NumberFormatException | DateTimeParseException ex) {
            logger.log(Level.WARNING, "Invalid format in record: {0}", ex.getMessage());
            return null;
        }
    }

    private void loadSampleAttendanceRecords(List<AttendanceRecord> records) {
        LocalDate today = LocalDate.now();
        records.add(new AttendanceRecord(10001, today, LocalTime.of(8, 0), LocalTime.of(17, 0)));
        records.add(new AttendanceRecord(10001, today.minusDays(1), LocalTime.of(8, 30), LocalTime.of(17, 30)));
        records.add(new AttendanceRecord(10002, today, LocalTime.of(8, 15), LocalTime.of(17, 15)));
        records.add(new AttendanceRecord(10002, today.minusDays(1), LocalTime.of(8, 0), LocalTime.of(17, 0)));
        logger.info("Loaded sample attendance data");
    }

    private double parseAmount(String amountStr) {
        if (amountStr == null || amountStr.trim().isEmpty()) {
            return 0.0;
        }

        String cleaned = amountStr.replace("\"", "").replace(",", "").trim();
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException ex) {
            logger.log(Level.WARNING, "Unable to parse amount: {0}", amountStr);
            return 0.0;
        }
    }

    private static LocalDate parseFlexibleDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            logger.log(Level.WARNING, "Empty date string found, using current date.");
            return LocalDate.now();
        }

        // Clean up the date string - sometimes quotes or extra spaces might be present
        String cleanedDateStr = dateStr.trim().replace("\"", "");

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(cleanedDateStr, formatter);
            } catch (DateTimeParseException e) {
                // Try the next formatter
            }
        }

        logger.log(Level.WARNING, "Could not parse date: {0}. Using current date.", dateStr);
        return LocalDate.now();
    }

    private static LocalTime parseFlexibleDateTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            logger.log(Level.WARNING, "Empty time string found, using default time 8:00.");
            return LocalTime.of(8, 0);
        }

        // Clean up the time string - sometimes quotes or extra spaces might be present
        String cleanedTimeStr = timeStr.trim().replace("\"", "");

        for (DateTimeFormatter formatter : TIME_FORMATTERS) {
            try {
                return LocalTime.parse(cleanedTimeStr, formatter);
            } catch (DateTimeParseException e) {
                // Try the next formatter
            }
        }

        logger.log(Level.WARNING, "Could not parse time: {0}. Using 8:00.", timeStr);
        return LocalTime.of(8, 0);
    }
}

package com.motorph;

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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVLoader {
    private static final Logger logger = Logger.getLogger(CSVLoader.class.getName());
    private final String employeesUrl;
    private final String attendanceUrl;
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
        DateTimeFormatter.ofPattern("h:mm a")
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

    public CSVLoader(String employeesUrl, String attendanceUrl) {
        this.employeesUrl = employeesUrl;
        this.attendanceUrl = attendanceUrl;
        logger.info("CSVLoader initialized");
    }

    public List<Employee> loadEmployees() throws IOException {
        List<Employee> employees = new ArrayList<>();
        // Always use path relative to project root
        final String filePath = "motorph_payroll_system/employeeDetails.csv";
        logger.log(Level.INFO, "Loading employees from: {0}", filePath);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader);

            for (CSVRecord record : parser) {
                try {
                    Employee employee = createEmployeeFromRecord(record);
                    if (employee != null) {
                        employees.add(employee);
                        logger.log(Level.INFO, "Added employee: {0} {1}", 
                            new Object[]{employee.getFirstName(), employee.getLastName()});
                    }
                } catch (IllegalArgumentException ex) {
                    logger.log(Level.WARNING, "Invalid data format in employee record: {0}", 
                        ex.getMessage());
                }
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Could not read from employee file: {0}", ex.getMessage());
            logger.info("Using sample employee data");
            loadSampleEmployees(employees);
        }
        
        return employees;
    }

    private Employee createEmployeeFromRecord(CSVRecord record) {
        try {
            int id = Integer.parseInt(record.get(0));
            String lastName = record.get(1);
            String firstName = record.get(2);
            String position = record.get(11);
            String status = record.get(10);
            
            double basicSalary = parseAmount(record.get(13));
            double riceSubsidy = parseAmount(record.get(14));
            double phoneAllowance = parseAmount(record.get(15));
            double clothingAllowance = parseAmount(record.get(16));
            
            return new Employee(id, lastName, firstName, position, status, basicSalary, 
                riceSubsidy, phoneAllowance, clothingAllowance);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid number format: " + ex.getMessage());
        }
    }

    private void loadSampleEmployees(List<Employee> employees) {
        employees.add(new Employee(10001, "Garcia", "Manuel", "Chief Executive Officer", "Regular", 90000, 1500, 1000, 1000));
        employees.add(new Employee(10002, "Santos", "Antonio", "Chief Operating Officer", "Regular", 60000, 1500, 1000, 1000));
        employees.add(new Employee(10003, "Reyes", "Bianca", "Chief Finance Officer", "Regular", 60000, 1500, 1000, 1000));
        employees.add(new Employee(10004, "Lim", "Isabella", "Chief Marketing Officer", "Regular", 60000, 1500, 1000, 1000));
        employees.add(new Employee(10005, "Lee", "Harper", "IT Operations", "Regular", 52670, 1500, 1000, 1000));
        logger.info("Loaded sample employee data");
    }

    public List<AttendanceRecord> loadAttendanceRecords() throws IOException {
        List<AttendanceRecord> records = new ArrayList<>();
        // Always use path relative to project root
        final String filePath = "motorph_payroll_system/attendanceRecord.csv";
        logger.log(Level.INFO, "Loading attendance from: {0}", filePath);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader);

            for (CSVRecord record : parser) {
                try {
                    AttendanceRecord attendance = createAttendanceFromRecord(record);
                    if (attendance != null) {
                        records.add(attendance);
                        logger.log(Level.INFO, "Added attendance for employee {0} on {1}", 
                            new Object[]{attendance.getEmployeeId(), attendance.getDate()});
                    }
                } catch (IllegalArgumentException ex) {
                    logger.log(Level.WARNING, "Invalid format in attendance record: {0}", 
                        ex.getMessage());
                }
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Could not read from attendance file: {0}", ex.getMessage());
            logger.info("Using sample attendance data");
            loadSampleAttendanceRecords(records);
        }
        
        return records;
    }

    private AttendanceRecord createAttendanceFromRecord(CSVRecord record) {
        try {
            int employeeId = Integer.parseInt(record.get(0));
            LocalDate date = parseFlexibleDate(record.get(3));
            LocalTime timeIn = parseFlexibleDateTime(record.get(4));
            LocalTime timeOut = parseFlexibleDateTime(record.get(5));
            
            return new AttendanceRecord(employeeId, date, timeIn, timeOut);
        } catch (NumberFormatException | DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid data format: " + ex.getMessage());
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
        
        String cleaned = amountStr.replace("\"", "").replace(",", "");
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException ex) {
            logger.log(Level.WARNING, "Could not parse amount: {0}", amountStr);
            return 0.0;
        }
    }
    
    private static LocalDate parseFlexibleDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return LocalDate.now();
        }
        
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException ignored) {
                // Continue to next format
            }
        }
        
        logger.log(Level.WARNING, "Could not parse date: {0}. Using current date.", dateStr);
        return LocalDate.now();
    }
    
    private static LocalTime parseFlexibleDateTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            return LocalTime.of(8, 0);
        }
        
        for (DateTimeFormatter formatter : TIME_FORMATTERS) {
            try {
                return LocalTime.parse(timeStr, formatter);
            } catch (DateTimeParseException ignored) {
                // Continue to next format
            }
        }
        
        logger.log(Level.WARNING, "Could not parse time: {0}. Using 8:00.", timeStr);
        return LocalTime.of(8, 0);
    }
}

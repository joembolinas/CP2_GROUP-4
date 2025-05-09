package com.motorph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading data from CSV files, including employees and attendance records.
 * Contains parsing logic for different date formats.
 */
public class CSVLoader {
    private String employeesUrl;
    private String attendanceUrl;

    public CSVLoader(String employeesUrl, String attendanceUrl) {
        this.employeesUrl = employeesUrl;
        this.attendanceUrl = attendanceUrl;
    }

    public List<Employee> loadEmployees() throws IOException {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(employeesUrl).openStream()))) {
            String line;
            boolean isHeaderSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!isHeaderSkipped) {
                    isHeaderSkipped = true;
                    continue;
                }
                String[] data = parseCSVLine(line);
                if (data.length > 0) {
                    try {
                        int employeeId = Integer.parseInt(data[0]);
                        String lastName = data[1];
                        String firstName = data[2];
                        String position = data[11];
                        String status = data[10];
                        double basicSalary = Double.parseDouble(data[13]);
                        double riceSubsidy = Double.parseDouble(data[14]);
                        double phoneAllowance = Double.parseDouble(data[15]);
                        double clothingAllowance = Double.parseDouble(data[16]);
                        employees.add(new Employee(employeeId, lastName, firstName, position, status, basicSalary, riceSubsidy, phoneAllowance, clothingAllowance));
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        // Skip invalid lines
                        continue;
                    }
                }
            }
        }
        return employees;
    }

    public List<AttendanceRecord> loadAttendanceRecords() throws IOException {
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(attendanceUrl).openStream()))) {
            String line;
            boolean isHeaderSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!isHeaderSkipped) {
                    isHeaderSkipped = true;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length >= 6) {
                    try {
                        int employeeId = Integer.parseInt(data[0]);
                        LocalDate date = parseFlexibleDate(data[3]);
                        LocalTime timeIn = LocalTime.parse(data[4]);
                        LocalTime timeOut = LocalTime.parse(data[5]);
                        attendanceRecords.add(new AttendanceRecord(employeeId, date, timeIn, timeOut));
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        // Skip invalid lines
                        continue;
                    }
                }
            }
        }
        return attendanceRecords;
    }

    private String[] parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        fields.add(sb.toString());
        return fields.toArray(new String[0]);
    }

    public static LocalDate parseFlexibleDate(String dateStr) {
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("M/dd/yyyy"),
            DateTimeFormatter.ofPattern("MM/d/yyyy")
        };
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (Exception e) {
                // Try next formatter
                continue;
            }
        }
        try {
            String[] parts = dateStr.split("/");
            if (parts.length == 3) {
                int month = Integer.parseInt(parts[0]);
                int day = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);
                return LocalDate.of(year, month, day);
            }
        } catch (Exception e) {
            // Ignore and return null
        }
        return null;
    }
}

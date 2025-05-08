package com.motorph.original;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<MotorPHDataModels.EmployeeData> employees = createSampleEmployees();
        List<MotorPHDataModels.AttendanceRecord> attendanceRecords = createSampleAttendanceRecords();
        EmployeeManager employeeManager = new EmployeeManager(employees);
        PayrollManager payrollManager = new PayrollManager(employees, attendanceRecords);
        ReportManager reportManager = new ReportManager(employees, attendanceRecords);
        reportManager.setPayrollManager(payrollManager);
        MenuManager menuManager = new MenuManager(employeeManager, payrollManager, reportManager);
        menuManager.start();
    }

    private static List<MotorPHDataModels.EmployeeData> createSampleEmployees() {
        List<MotorPHDataModels.EmployeeData> e = new ArrayList<>();
        e.add(new MotorPHDataModels.EmployeeData(1, "Doe", "John", LocalDate.of(1990, Month.MAY, 15), "Manager", "HR", "Regular", 50000, 1500, 1000, 1000));
        e.add(new MotorPHDataModels.EmployeeData(2, "Smith", "Jane", LocalDate.of(1992, Month.AUGUST, 20), "Dev", "IT", "Regular", 40000, 1500, 800, 800));
        e.add(new MotorPHDataModels.EmployeeData(3, "Johnson", "Bob", LocalDate.of(1985, Month.JANUARY, 10), "Accountant", "Finance", "Regular", 35000, 1500, 800, 800));
        e.add(new MotorPHDataModels.EmployeeData(4, "Williams", "Sarah", LocalDate.of(1991, Month.MARCH, 25), "Sales", "Sales", "Regular", 30000, 1500, 500, 800));
        e.add(new MotorPHDataModels.EmployeeData(5, "Brown", "Michael", LocalDate.of(1988, Month.JULY, 8), "Designer", "Marketing", "Regular", 32000, 1500, 500, 800));
        return e;
    }

    private static List<MotorPHDataModels.AttendanceRecord> createSampleAttendanceRecords() {
        List<MotorPHDataModels.AttendanceRecord> r = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int empId = 1; empId <= 5; empId++) {
            for (int day = 0; day < 10; day++) {
                LocalDate date = today.minusDays(day);
                if (date.getDayOfWeek().getValue() <= 5) {
                    if (day % 3 == 0) r.add(new MotorPHDataModels.AttendanceRecord(empId, date, LocalTime.of(8, 0), LocalTime.of(18, 30)));
                    else r.add(new MotorPHDataModels.AttendanceRecord(empId, date, LocalTime.of(8, 0), LocalTime.of(17, 0)));
                } else r.add(new MotorPHDataModels.AttendanceRecord(empId, date, false));
            }
            r.add(new MotorPHDataModels.AttendanceRecord(empId, today.minusDays(12), false));
        }
        return r;
    }
}
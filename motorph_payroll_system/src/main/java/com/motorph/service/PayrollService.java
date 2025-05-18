package com.motorph.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.motorph.model.PaySlip;
public class PayrollService {
    private final List<Employee> employees;
    private final List<AttendanceRecord> attendanceRecords;    private final PayrollProcessor payrollCalculator;
    
    public PayrollService(List<Employee> employees, List<AttendanceRecord> attendanceRecords, 
                          PayrollProcessor payrollCalculator) {
        this.employees = employees;
        this.attendanceRecords = attendanceRecords;
        this.payrollCalculator = payrollCalculator;
    }
    
    /**
     * Generate a payslip for a specific employee
     * 
     * @param employeeId The employee ID
     * @param startDate Start date for pay period
     * @param endDate End date for pay period
     * @return PaySlip for the employee
     * @throws IllegalArgumentException if employee not found
     */
    public PaySlip generatePayslip(int employeeId, LocalDate startDate, LocalDate endDate) 
            throws IllegalArgumentException {
        // Find the employee
        Employee employee = employees.stream()
                .filter(emp -> emp.getEmployeeId() == employeeId)
                .findFirst()
                .orElse(null);
                
        if (employee == null) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " not found");
        }
        
        // Get attendance records for the date range
        List<AttendanceRecord> records = attendanceRecords.stream()
                .filter(record -> record.getEmployeeId() == employeeId)
                .filter(record -> {
                    LocalDate recordDate = record.getDate();
                    return recordDate != null && 
                           !recordDate.isBefore(startDate) && 
                           !recordDate.isAfter(endDate);
                })
                .toList();
        
        // Create and generate the payslip
        PaySlip paySlip = new PaySlip(employee, startDate, endDate);
        paySlip.generate(attendanceRecords, payrollCalculator);
        
        return paySlip;
    }
    
    /**
     * Generate payroll for all employees for a given date range
     * 
     * @param startDate Start date for pay period
     * @param endDate End date for pay period
     * @return List of PaySlip objects for all employees
     */
    public List<PaySlip> generatePayroll(LocalDate startDate, LocalDate endDate) {
        List<PaySlip> paySlips = new ArrayList<>();
        
        for (Employee employee : employees) {
            try {
                PaySlip paySlip = generatePayslip(employee.getEmployeeId(), startDate, endDate);
                paySlips.add(paySlip);
            } catch (Exception e) {
                // Log error and continue with next employee
                System.err.println("Error generating payslip for employee " + 
                        employee.getEmployeeId() + ": " + e.getMessage());
            }
        }
        
        return paySlips;
    }
}

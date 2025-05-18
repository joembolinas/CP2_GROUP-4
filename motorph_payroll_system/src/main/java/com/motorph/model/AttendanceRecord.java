package com.motorph.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a single attendance record for an employee.
 * This class stores information about when an employee checked in and out on a specific date.
 * It provides methods for calculating duration, determining if an employee was late,
 * and calculating total hours worked.
 */
public class AttendanceRecord {
    private int employeeId;
    private LocalDate date;
    private LocalTime timeIn;
    private LocalTime timeOut;

    /**
     * Constructs a new AttendanceRecord with the specified details.
     *
     * @param employeeId The unique identifier of the employee
     * @param date The date of the attendance record
     * @param timeIn The time when the employee checked in
     * @param timeOut The time when the employee checked out
     * @throws IllegalArgumentException if timeOut is before timeIn
     */
    public AttendanceRecord(int employeeId, LocalDate date, LocalTime timeIn, LocalTime timeOut) {
        this.employeeId = employeeId;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        // Validate that timeOut is after timeIn
        if (timeOut != null && timeIn != null && timeOut.isBefore(timeIn)) {
            throw new IllegalArgumentException("Time out cannot be before time in");
        }
    }

    /**
     * Calculates the duration between check-in and check-out times.
     *
     * @return The duration between timeIn and timeOut as a Duration object
     */
    public Duration getDuration() {
        return Duration.between(timeIn, timeOut);
    }

    /**
     * Determines if the employee was late based on check-in time.
     * An employee is considered late if they check in after 8:10 AM.
     *
     * @return true if the employee was late, false otherwise
     */
    public boolean isLate() {
        // Considered late after 8:10 AM
        return timeIn.isAfter(LocalTime.of(8, 10));
    }    /**
     * Calculates the total hours worked based on the duration.
     *
     * @return The total hours worked as a decimal value
     */
    public double getTotalHours() {
        return getDuration().toMinutes() / 60.0;
    }
    
    /**
     * Calculates the regular hours worked (up to 8 hours).
     *
     * @return The regular hours worked, capped at 8 hours
     */
    public double getRegularHours() {
        double totalHours = getTotalHours();
        return Math.min(totalHours, 8.0);
    }
    
    /**
     * Calculates the overtime hours worked (beyond 8 hours).
     *
     * @return The overtime hours worked
     */
    public double getOvertimeHours() {
        double totalHours = getTotalHours();
        return totalHours > 8.0 ? totalHours - 8.0 : 0.0;
    }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getTimeIn() { return timeIn; }
    public void setTimeIn(LocalTime timeIn) { this.timeIn = timeIn; }
    public LocalTime getTimeOut() { return timeOut; }
    public void setTimeOut(LocalTime timeOut) { this.timeOut = timeOut; }
}

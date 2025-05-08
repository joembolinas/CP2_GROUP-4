package com.motorph.original;
import java.time.LocalDate;
 import java.util.List;
 import java.util.Map;
import java.util.stream.Collectors;
public class ReportManager {
    private List<MotorPHDataModels.EmployeeData> employees;
    private List<MotorPHDataModels.AttendanceRecord> attendanceRecords;
    private PayrollManager payrollManager;
    public ReportManager(List<MotorPHDataModels.EmployeeData> e, List<MotorPHDataModels.AttendanceRecord> a) { employees = e; attendanceRecords = a; }
    public void setPayrollManager(PayrollManager p) { payrollManager = p; }
    public String generatePayslip(int id, LocalDate s, LocalDate e) { return generatePayslip(payrollManager.processEmployeePayroll(id, s, e)); }
    public String generateWeeklySummaryReport(LocalDate s, LocalDate e) { return generateWeeklySummaryReport(payrollManager.processAllEmployeePayroll(s, e), s, e); }
    public String generateMonthlySummaryReport(LocalDate s, LocalDate e) { return generateMonthlySummaryReport(payrollManager.processAllEmployeePayroll(s, e), s, e); }
    public String generateAttendanceReport(int id, LocalDate s, LocalDate e) { return payrollManager.displayAttendanceRecords(id, s, e); }
    public String generatePayslip(MotorPHDataModels.PayrollResult p) { var e = p.getEmployee(); return "ID: "+e.getEmployeeId()+"\nName: "+e.getFirstName()+" "+e.getLastName()+"\nGross: "+p.getGrossPay()+"\nDeductions: "+p.getTotalDeductions()+"\nNet: "+p.getNetPay(); }
    public String generateWeeklySummaryReport(List<MotorPHDataModels.PayrollResult> r, LocalDate s, LocalDate e) { StringBuilder sb = new StringBuilder(); for (var p : r) { var emp = p.getEmployee(); sb.append(emp.getEmployeeId()+" "+emp.getFirstName()+" "+emp.getLastName()+" "+p.getGrossPay()+" "+p.getTotalDeductions()+" "+p.getNetPay()+"\n"); } return sb.toString(); }
    public String generateMonthlySummaryReport(List<MotorPHDataModels.PayrollResult> r, LocalDate s, LocalDate e) { StringBuilder sb = new StringBuilder(); Map<String, List<MotorPHDataModels.PayrollResult>> dept = r.stream().collect(Collectors.groupingBy(x -> x.getEmployee().getDepartment())); for (var d : dept.entrySet()) { sb.append("Dept: "+d.getKey()+"\n"); for (var p : d.getValue()) { var emp = p.getEmployee(); sb.append(emp.getEmployeeId()+" "+emp.getFirstName()+" "+emp.getLastName()+" "+p.getGrossPay()+" "+p.getTotalDeductions()+" "+p.getNetPay()+"\n"); } } return sb.toString(); }
}
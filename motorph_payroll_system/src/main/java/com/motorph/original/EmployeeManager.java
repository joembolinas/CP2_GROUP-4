package com.motorph.original;
import java.util.ArrayList;
 import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
public class EmployeeManager {
    private List<MotorPHDataModels.EmployeeData> employees;
    public EmployeeManager(List<MotorPHDataModels.EmployeeData> e) { employees = new ArrayList<>(e); }
    public EmployeeManager() { employees = new ArrayList<>(); }
    public void loadEmployeesFromFile(String f) { try { employees = CsvReader.readEmployeeDataFromFile(f); } catch (Exception ex) {} }
    public void loadEmployeesFromUrl(String u) { try { employees = CsvReader.readEmployeeDataFromUrl(u); } catch (Exception ex) {} }
    public void addEmployee(MotorPHDataModels.EmployeeData e) { employees.add(e); }
    public List<MotorPHDataModels.EmployeeData> getAllEmployees() { return new ArrayList<>(employees); }
    public Optional<MotorPHDataModels.EmployeeData> findEmployeeById(int id) { return employees.stream().filter(e -> e.getEmployeeId() == id).findFirst(); }
    public List<MotorPHDataModels.EmployeeData> searchEmployeesByName(String n) { String s = n.toLowerCase(); return employees.stream().filter(e -> e.getFirstName().toLowerCase().contains(s) || e.getLastName().toLowerCase().contains(s)).collect(Collectors.toList()); }
    public String displayEmployeeList() { StringBuilder sb = new StringBuilder(); for (var e : employees) sb.append(e.getEmployeeId()+" "+e.getLastName()+" "+e.getFirstName()+" "+e.getPosition()+" "+e.getDepartment()+" "+e.getStatus()+"\n"); return sb.toString(); }
    public String displayEmployeeDetails(int id) { var o = findEmployeeById(id); if (o.isPresent()) { var e = o.get(); return "ID: "+e.getEmployeeId()+"\nName: "+e.getFirstName()+" "+e.getLastName()+"\nPosition: "+e.getPosition()+"\nDepartment: "+e.getDepartment()+"\nStatus: "+e.getStatus()+"\nSalary: "+e.getBasicSalary()+"\nHourly: "+e.getHourlyRate(); } return "Employee with ID "+id+" not found."; }
}
package com.motorph.original;
import java.io.File;
 import java.io.FileReader;
 import java.io.IOException;
 import java.io.StringReader;
 import java.time.LocalDate;
 import java.time.LocalTime;
 import java.time.format.DateTimeFormatter;
 import java.time.format.DateTimeParseException;
 import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
public class CsvReader {
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");
    public static List<MotorPHDataModels.EmployeeData> readEmployeeDataFromFile(String f) throws IOException { List<MotorPHDataModels.EmployeeData> l = new ArrayList<>(); try (FileReader r = new FileReader(new File(f)); CSVParser p = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(r)) { for (CSVRecord rec : p) l.add(new MotorPHDataModels.EmployeeData(Integer.parseInt(rec.get("Employee #")), rec.get("Last Name"), rec.get("First Name"), parseDate(rec.get("Birthday")), rec.get("Position"), rec.get("Department"), rec.get("Status"), parseDouble(rec.get("Basic Salary")), parseDouble(rec.get("Rice Subsidy")), parseDouble(rec.get("Phone Allowance")), parseDouble(rec.get("Clothing Allowance")))); } return l; }
    public static List<MotorPHDataModels.EmployeeData> readEmployeeDataFromUrl(String u) throws IOException { String c = fetchContentFromUrl(u); List<MotorPHDataModels.EmployeeData> l = new ArrayList<>(); try (CSVParser p = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new StringReader(c))) { for (CSVRecord rec : p) l.add(new MotorPHDataModels.EmployeeData(Integer.parseInt(rec.get("Employee #")), rec.get("Last Name"), rec.get("First Name"), parseDate(rec.get("Birthday")), rec.get("Position"), rec.get("Department"), rec.get("Status"), parseDouble(rec.get("Basic Salary")), parseDouble(rec.get("Rice Subsidy")), parseDouble(rec.get("Phone Allowance")), parseDouble(rec.get("Clothing Allowance")))); } return l; }
    public static List<MotorPHDataModels.AttendanceRecord> readAttendanceFromFile(String f) throws IOException { List<MotorPHDataModels.AttendanceRecord> l = new ArrayList<>(); try (FileReader r = new FileReader(new File(f)); CSVParser p = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(r)) { for (CSVRecord rec : p) { int id = Integer.parseInt(rec.get("Employee #")); LocalDate d = parseDate(rec.get("Date")); String ti = rec.get("Time In"), to = rec.get("Time Out"); if (ti.isEmpty() || to.isEmpty()) l.add(new MotorPHDataModels.AttendanceRecord(id, d, false)); else l.add(new MotorPHDataModels.AttendanceRecord(id, d, parseTime(ti), parseTime(to))); } } return l; }
    public static List<MotorPHDataModels.AttendanceRecord> readAttendanceFromUrl(String u) throws IOException { String c = fetchContentFromUrl(u); List<MotorPHDataModels.AttendanceRecord> l = new ArrayList<>(); try (CSVParser p = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new StringReader(c))) { for (CSVRecord rec : p) { int id = Integer.parseInt(rec.get("Employee #")); LocalDate d = parseDate(rec.get("Date")); String ti = rec.get("Time In"), to = rec.get("Time Out"); if (ti.isEmpty() || to.isEmpty()) l.add(new MotorPHDataModels.AttendanceRecord(id, d, false)); else l.add(new MotorPHDataModels.AttendanceRecord(id, d, parseTime(ti), parseTime(to))); } } return l; }
    private static String fetchContentFromUrl(String u) throws IOException { try (CloseableHttpClient h = HttpClients.createDefault()) { HttpGet req = new HttpGet(u); try (CloseableHttpResponse resp = h.execute(req)) { HttpEntity e = resp.getEntity(); if (e != null) return EntityUtils.toString(e); } } throw new IOException("Failed to fetch content from URL: " + u); }
    private static LocalDate parseDate(String s) { if (s == null || s.trim().isEmpty()) return null; try { return LocalDate.parse(s, DF); } catch (DateTimeParseException e) { try { return LocalDate.parse(s, DateTimeFormatter.ofPattern("M/d/yyyy")); } catch (DateTimeParseException e2) { try { return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd")); } catch (DateTimeParseException e3) { return null; } } } }
    private static LocalTime parseTime(String s) { if (s == null || s.trim().isEmpty()) return null; try { return LocalTime.parse(s, TF); } catch (DateTimeParseException e) { try { return LocalTime.parse(s, DateTimeFormatter.ofPattern("h:mm a")); } catch (DateTimeParseException e2) { return null; } } }
    private static double parseDouble(String v) { if (v == null || v.trim().isEmpty()) return 0.0; try { return Double.parseDouble(v.replaceAll("[₱,]", "").trim()); } catch (NumberFormatException e) { return 0.0; } }
}
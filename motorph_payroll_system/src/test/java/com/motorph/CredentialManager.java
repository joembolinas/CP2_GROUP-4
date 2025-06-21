package com.motorph.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CredentialManager {
    private static final String CREDENTIAL_FILE_PATH = "data/employeeCredentials.csv"; // relative to project root
                                                                                       // resources

    public static List<String[]> loadAllRows() {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CREDENTIAL_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                rows.add(line.split(",", -1));
            }
        } catch (IOException e) {
            System.err.println("Could not load credentials file: " + e.getMessage());
        }
        return rows;
    }    public static String[] authenticate(String inputUsername, String inputPassword) {
        // TEMPORARY: Login disabled for testing - always return successful authentication
        System.out.println("*** LOGIN TEMPORARILY DISABLED FOR TESTING ***");
        System.out.println("Attempted login with username: " + inputUsername);
        
        // Return a dummy successful authentication result
        return new String[]{"10001", "Admin", "password", "System", "Administrator"};
        
        /* ORIGINAL CODE COMMENTED OUT:
        List<String[]> rows = loadAllRows();
        String inputUsernameClean = inputUsername.trim().replaceAll("\\s+", "");
        String inputPasswordTrim = inputPassword.trim();
        System.out.println("Looking for credentials at: " + new File(CREDENTIAL_FILE_PATH).getAbsolutePath());

        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row.length < 3)
                continue;

            String lastName = row[1].replaceAll("\\s+", ""); // preserve casing
            String employeeNumber = row[0].trim();
            String expectedUsername = lastName + employeeNumber;
            String storedPassword = row[2].trim();

            if (expectedUsername.equals(inputUsernameClean) && storedPassword.equals(inputPasswordTrim)) {
                return row;
            }
        }
        return null;
        */
    }

    public static boolean updatePassword(String username, String newPassword) {
        List<String[]> rows = loadAllRows();
        String usernameClean = username.trim().replaceAll("\\s+", "");
        boolean updated = false;

        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row.length < 3)
                continue;

            String lastName = row[1].replaceAll("\\s+", ""); // preserve casing
            String employeeNumber = row[0];
            String constructedUsername = lastName + employeeNumber;

            if (constructedUsername.equals(usernameClean)) {
                row[2] = newPassword;
                updated = true;
                break;
            }
        }

        if (updated) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(CREDENTIAL_FILE_PATH))) {
                for (String[] row : rows) {
                    pw.println(String.join(",", row));
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}

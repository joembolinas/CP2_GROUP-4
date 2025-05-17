package com.motorph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuManagerGui extends JFrame {

    public MenuManagerGui() {
        setTitle("MotorPH Payroll System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.lightGray);
        menuBar.setPreferredSize(new Dimension(600, 30));

        // Create menus
        JMenu menu = new JMenu("Options");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        

        // Create menu items
        JMenuItem searchEmployee = new JMenuItem("Search Employee");
        JMenuItem listEmployees = new JMenuItem("List All Employees");
        JMenuItem attendance = new JMenuItem("Attendance");
        JMenuItem mainMenu = new JMenuItem("Main Menu");

        // Add items to menu
        menu.add(searchEmployee);
        menu.add(listEmployees);
        menu.add(attendance);
        menu.addSeparator();
        menu.add(mainMenu);

        // Add menu to menu bar
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Add a content panel
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select an option from the menu.", SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        add(panel);

        // Action listeners
        searchEmployee.addActionListener(e -> JOptionPane.showMessageDialog(this, "Search Employee clicked"));
        listEmployees.addActionListener(e -> JOptionPane.showMessageDialog(this, "List All Employees clicked"));
        attendance.addActionListener(e -> JOptionPane.showMessageDialog(this, "Attendance clicked"));
        mainMenu.addActionListener(e -> JOptionPane.showMessageDialog(this, "Main Menu clicked"));
    }

    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MenuManagerGui gui = new MenuManagerGui();
            gui.setVisible(true);
        });
    }
}




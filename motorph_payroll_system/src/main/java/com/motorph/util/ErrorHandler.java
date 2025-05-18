package com.motorph.util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Centralized error handling utility for the application.
 * Addresses MPHCR01 requirement for proper error handling.
 */
public class ErrorHandler {
    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class.getName());
    
    /**
     * Displays an error message dialog and logs the error
     * 
     * @param parent The parent frame for the dialog
     * @param message The error message to display
     * @param title The title for the error dialog
     * @param exception The exception to log (can be null)
     */
    public static void showError(JFrame parent, String message, String title, Exception exception) {
        // Log the error
        if (exception != null) {
            LOGGER.log(Level.SEVERE, message, exception);
        } else {
            LOGGER.log(Level.SEVERE, message);
        }
        
        // Show error dialog
        JOptionPane.showMessageDialog(
            parent,
            message,
            title,
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Displays an input validation error message
     * 
     * @param parent The parent frame for the dialog
     * @param message The validation error message
     */
    public static void showValidationError(JFrame parent, String message) {
        showError(parent, message, "Input Validation Error", null);
    }
    
    /**
     * Handles exceptions by categorizing them and showing appropriate messages
     * 
     * @param parent The parent frame for the dialog
     * @param exception The exception to handle
     * @param context Context information for the error
     */
    public static void handleException(JFrame parent, Exception exception, String context) {
        String message;
        
        if (exception instanceof NumberFormatException) {
            message = "Please enter a valid numeric value.";
        } else if (exception instanceof IllegalArgumentException) {
            message = exception.getMessage();
        } else {
            message = "An error occurred: " + exception.getMessage();
        }
        
        showError(parent, message, context, exception);
    }
}

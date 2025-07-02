package com.motorph.model;

/**
 * Enum representing the different states of a payroll run
 */
public enum PayrollStatus {
    DRAFT("Draft", "Initial state before calculation"),
    DATA_LOADED("Data Loaded", "CSV data has been uploaded and validated"),
    CALCULATED("Calculated", "Payroll has been calculated, pending review"),
    REVIEW("In Review", "Currently being reviewed and can be modified"),
    PENDING_REVIEW("Pending Review", "Ready for HR review and validation"),
    APPROVED("Approved", "Reviewed and approved by HR"),
    POSTED("Posted", "Posted to accounting system for payment processing"),
    REJECTED("Rejected", "Rejected during review, needs recalculation");

    private final String displayName;
    private final String description;

    PayrollStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

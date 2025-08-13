package com.g2.hotelm.model;

public enum ReservationStatus {
    PENDING("Pending"),
    CONFIRMED("Confirmed"), 
    CHECKED_IN("Checked In"),
    CHECKED_OUT("Checked Out"),
    CANCELLED("Cancelled");

    private final String displayName;

    ReservationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isActive() {
        return this == CONFIRMED || this == CHECKED_IN;
    }
}

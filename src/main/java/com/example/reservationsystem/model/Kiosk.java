package com.example.reservationsystem.model;

public class Kiosk {
    private int kioskID;
    private String location;

    // Constructor
    public Kiosk(int kioskID, String location) {
        this.kioskID = kioskID;
        this.location = location;
    }

    // Getters and Setters
    public int getKioskID() {
        return kioskID;
    }

    public void setKioskID(int kioskID) {
        this.kioskID = kioskID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Methods
    public String displayWelcomeMessage() {
        return "Welcome to Hotel ABC! Use this kiosk to book your stay.";
    }

    public String guideBookingProcess() {
        return "Step 1: Enter your details.\nStep 2: Select check-in and check-out dates.\nStep 3: Choose number of guests.\nStep 4: Select a room.\nStep 5: Confirm your booking.";
    }

    public boolean validateInput(String input, String type) {
        switch (type.toLowerCase()) {
            case "name":
                return input != null && !input.trim().isEmpty();
            case "phone":
                return input != null && input.matches("\\d{10}");
            case "email":
                return input == null || input.matches("^[A-Za-z0-9+_.-]+@(.+)$");
            case "guests":
                try {
                    int guests = Integer.parseInt(input);
                    return guests > 0 && guests <= 4; // Based on room rules
                } catch (NumberFormatException e) {
                    return false;
                }
            default:
                return false;
        }
    }

    public void confirmBooking(Reservation reservation) {
        reservation.confirmReservation();
        // Placeholder: Update database
    }
}
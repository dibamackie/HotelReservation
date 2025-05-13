package com.example.reservationsystem.model;

public class Admin {
    private int adminID;
    private String username;
    private String password;

    // Constructor
    public Admin(int adminID, String username, String password) {
        this.adminID = adminID;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Methods
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public Guest searchGuest(String nameOrPhone) {
        // Placeholder: Search database for guest by name or phone number
        return null; // Return Guest object
    }

    public void checkOutGuest(Reservation reservation) {
        reservation.setStatus("Checked-Out");
        // Placeholder: Update database, generate bill, set room status to Available
    }

    public String generateReport(Reservation reservation) {
        // Placeholder: Generate report based on reservation details
        return "Report for Reservation ID: " + reservation.getReservationID();
    }
}
package com.example.reservationsystem.model;

public class Guest {
    private int guestID;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String feedback;

    // Constructor
    public Guest(int guestID, String name, String phoneNumber, String email, String address, String feedback) {
        this.guestID = guestID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.feedback = feedback;
    }

    // Getters and Setters
    public int getGuestID() {
        return guestID;
    }

    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    // Methods
    public String getGuestDetails() {
        return "Guest ID: " + guestID + ", Name: " + name + ", Phone: " + phoneNumber +
                ", Email: " + email + ", Address: " + address + ", Feedback: " + (feedback != null ? feedback : "None");
    }

    public void setGuestDetails(String name, String phoneNumber, String email, String address, String feedback) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.feedback = feedback;
    }

    public boolean validateGuestDetails() {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            return false;
        }
        if (email != null && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return false;
        }
        return true;
    }
}
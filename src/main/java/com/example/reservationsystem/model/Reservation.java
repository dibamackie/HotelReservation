package com.example.reservationsystem.model;

import java.time.LocalDate;

public class Reservation {
    private int reservationID;
    private int guestID;
    private int roomID;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private String status; // e.g., "Pending", "Confirmed", "Cancelled"

    // Constructor
    public Reservation(int reservationID, int guestID, int roomID, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests, String status) {
        this.reservationID = reservationID;
        this.guestID = guestID;
        this.roomID = roomID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
    }

    // Getters and Setters
    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getGuestID() {
        return guestID;
    }

    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Methods
    public void createReservation() {
        this.status = "Pending";
        // Placeholder: Save to database
    }

    public void cancelReservation() {
        this.status = "Cancelled";
        // Placeholder: Update database
    }

    public String getReservationDetails() {
        return "Reservation ID: " + reservationID + ", Guest ID: " + guestID + ", Room ID: " + roomID +
                ", Check-In: " + checkInDate + ", Check-Out: " + checkOutDate +
                ", Guests: " + numberOfGuests + ", Status: " + status;
    }

    public void confirmReservation() {
        this.status = "Confirmed";
        // Placeholder: Update database
    }
}
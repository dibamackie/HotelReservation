package com.example.reservationsystem.model;

import java.time.LocalDate;

public class BookingDetails {
    private String firstName;
    private String phoneNumber;
    private String emailAddress;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private String roomId;
    private String roomType;

    public BookingDetails(String firstName, String phoneNumber, String emailAddress, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests, String roomId, String roomType) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.roomId = roomId;
        this.roomType = roomType;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }
}
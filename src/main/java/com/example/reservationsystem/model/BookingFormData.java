package com.example.reservationsystem.model;

import java.time.LocalDate;

public class BookingFormData {
    private String firstName;
    private String phoneNumber;
    private String emailAddress;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String numberOfGuests;

    public BookingFormData(String firstName, String phoneNumber, String emailAddress, LocalDate checkInDate, LocalDate checkOutDate, String numberOfGuests) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
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

    public String getNumberOfGuests() {
        return numberOfGuests;
    }
}
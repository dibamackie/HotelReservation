package com.example.reservationsystem.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReservationView {
    private final StringProperty reservationId;
    private final StringProperty guestName;
    private final StringProperty phoneNumber;
    private final StringProperty guestId;
    private final StringProperty checkInDate;
    private final StringProperty checkOutDate;

    public ReservationView(String reservationId, String guestName, String phoneNumber, String guestId, String checkInDate, String checkOutDate) {
        this.reservationId = new SimpleStringProperty(reservationId);
        this.guestName = new SimpleStringProperty(guestName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.guestId = new SimpleStringProperty(guestId);
        this.checkInDate = new SimpleStringProperty(checkInDate);
        this.checkOutDate = new SimpleStringProperty(checkOutDate);
    }

    public StringProperty reservationIdProperty() {
        return reservationId;
    }

    public StringProperty guestNameProperty() {
        return guestName;
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public StringProperty guestIdProperty() {
        return guestId;
    }

    public StringProperty checkInDateProperty() {
        return checkInDate;
    }

    public StringProperty checkOutDateProperty() {
        return checkOutDate;
    }

    // Getters for the raw values (if needed)
    public String getReservationId() {
        return reservationId.get();
    }

    public String getGuestName() {
        return guestName.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getGuestId() {
        return guestId.get();
    }

    public String getCheckInDate() {
        return checkInDate.get();
    }

    public String getCheckOutDate() {
        return checkOutDate.get();
    }
}
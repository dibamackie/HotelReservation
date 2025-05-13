package com.example.reservationsystem.model;

public class Room {
    private int roomID;
    private RoomType roomType;
    private int numberOfBeds;
    private double price;
    private String status; // e.g., "Available", "Booked"

    // Constructor
    public Room(int roomID, RoomType roomType, int numberOfBeds, double price, String status) {
        this.roomID = roomID;
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
        this.status = status;
    }

    // Getters and Setters
    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Methods
    public String getRoomDetails() {
        return "Room ID: " + roomID + ", Type: " + roomType + ", Beds: " + numberOfBeds +
                ", Price: $" + price + ", Status: " + status;
    }

    public void setRoomDetails(RoomType roomType, int numberOfBeds, double price, String status) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
        this.status = status;
    }

    public boolean checkRoomAvailability() {
        return "Available".equalsIgnoreCase(status);
    }
}
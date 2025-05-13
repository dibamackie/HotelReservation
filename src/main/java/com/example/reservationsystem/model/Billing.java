package com.example.reservationsystem.model;

public class Billing {
    private int billID;
    private int reservationID;
    private double amount;
    private double tax;
    private double totalAmount;
    private double discount;

    // Constructor
    public Billing(int billID, int reservationID, double amount, double tax, double totalAmount, double discount) {
        this.billID = billID;
        this.reservationID = reservationID;
        this.amount = amount;
        this.tax = tax;
        this.totalAmount = totalAmount;
        this.discount = discount;
    }

    // Getters and Setters
    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    // Methods
    public void generateBill() {
        calculateTotal();
        // Placeholder: Save bill to database
    }

    public void applyDiscount(double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100.");
        }
        this.discount = discountPercentage;
        calculateTotal();
    }

    public void calculateTotal() {
        double discountedAmount = amount * (1 - discount / 100);
        this.totalAmount = discountedAmount + tax;
    }

    public String printBill() {
        return "Bill ID: " + billID + "\nReservation ID: " + reservationID +
                "\nBase Amount: $" + amount + "\nTax: $" + tax +
                "\nDiscount: " + discount + "%\nTotal Amount: $" + totalAmount;
    }
}
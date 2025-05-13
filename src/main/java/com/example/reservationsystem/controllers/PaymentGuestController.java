package com.example.reservationsystem.controllers;

import com.example.reservationsystem.model.BookingDetails;
import com.example.reservationsystem.Utility.HotelLogger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

public class PaymentGuestController {

    private static final Logger logger = HotelLogger.getLogger();

    // Navigation buttons
    @FXML
    private Button bookingBtn;

    @FXML
    private Button roomSelectionBtn;

    @FXML
    private Button reservationBtn;

    @FXML
    private Button paymentBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button payOnlineBtn;

    // Labels to display booking details
    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label checkInLabel;

    @FXML
    private Label checkOutLabel;

    @FXML
    private Label guestsLabel;

    @FXML
    private Label roomLabel;

    // Labels for payment summary
    @FXML
    private Label roomPriceLabel;

    @FXML
    private Label taxLabel;

    @FXML
    private Label totalLabel;

    private BookingDetails bookingDetails; // Store the booking details

    public void setBookingDetails(BookingDetails bookingDetails) {
        this.bookingDetails = bookingDetails;
        // Populate the labels with booking details
        nameLabel.setText(bookingDetails.getFirstName());
        phoneLabel.setText(bookingDetails.getPhoneNumber());
        emailLabel.setText(bookingDetails.getEmailAddress());
        checkInLabel.setText(bookingDetails.getCheckInDate().toString());
        checkOutLabel.setText(bookingDetails.getCheckOutDate().toString());
        guestsLabel.setText(String.valueOf(bookingDetails.getNumberOfGuests()));
        roomLabel.setText(bookingDetails.getRoomId() + " - " + bookingDetails.getRoomType());

        // Calculate and display payment details
        double roomPrice = calculateRoomPrice();
        double tax = roomPrice * 0.13; // 13% tax
        double total = roomPrice + tax;

        roomPriceLabel.setText(String.format("$%.2f", roomPrice));
        taxLabel.setText(String.format("$%.2f", tax));
        totalLabel.setText(String.format("$%.2f", total));
    }

    private double calculateRoomPrice() {
        // Define base prices per night for each room type
        double pricePerNight;
        switch (bookingDetails.getRoomType().toUpperCase()) {
            case "SINGLE":
                pricePerNight = 100.00;
                break;
            case "DOUBLE":
                pricePerNight = 150.00;
                break;
            case "DELUXE":
                pricePerNight = 200.00;
                break;
            case "PENTHOUSE":
                pricePerNight = 300.00;
                break;
            default:
                pricePerNight = 100.00; // Default price
                break;
        }

        // Calculate the number of nights
        long nights = ChronoUnit.DAYS.between(bookingDetails.getCheckInDate(), bookingDetails.getCheckOutDate());
        if (nights < 1) {
            nights = 1; // Minimum 1 night
        }

        return pricePerNight * nights;
    }

    @FXML
    private void handleBookingGuest() {
        try {
            Stage stage = (Stage) bookingBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/BookingGuest.fxml")), 720, 410);
            stage.setScene(scene);
            logger.info("Guest navigated to Booking page.");
        } catch (Exception e) {
            logger.severe("Error loading BookingGuest.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRoomSelectionGuest() {
        try {
            Stage stage = (Stage) roomSelectionBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/RoomsGuest.fxml")), 720, 410);
            stage.setScene(scene);
            logger.info("Guest navigated to Room Selection page.");
        } catch (Exception e) {
            logger.severe("Error loading RoomsGuest.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReservationGuest() {
        try {
            Stage stage = (Stage) reservationBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/reservationGuest.fxml")),720, 410);
            stage.setScene(scene);
            logger.info("Guest navigated to Reservation page.");
        } catch (Exception e) {
            logger.severe("Error loading reservationGuest.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePaymentGuest() {
        // Already on payment page
        logger.info("Guest is already on Payment Detail page.");
    }

    @FXML
    private void handleExitGuest() {
        try {
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/welcome.fxml")), 720, 410);
            stage.setScene(scene);
            logger.info("Guest navigated back to Welcome page.");
        } catch (Exception e) {
            logger.severe("Error navigating back to welcome.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePayOnline() {
        // Placeholder for future payment functionality
        logger.info("Guest clicked 'Pay Online' button (placeholder action).");
    }

    @FXML
    private void handleExitAdmin() {
        try {
            logger.info("Admin exited the application.");
            Platform.exit(); // Close the JavaFX application
        } catch (Exception e) {
            logger.severe("Error exiting the application: " + e.getMessage());
        }
    }
}
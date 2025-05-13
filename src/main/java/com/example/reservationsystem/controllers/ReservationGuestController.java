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
import java.util.logging.Logger;

public class ReservationGuestController {

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
    private Button modifyBookingBtn;

    @FXML
    private Button proceedToPaymentBtn;

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
        // Already on reservation page
        logger.info("Guest is already on Reservation page.");
    }

    @FXML
    private void handlePaymentGuest() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reservationsystem/paymentGuest.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            PaymentGuestController controller = loader.getController();
            controller.setBookingDetails(bookingDetails); // Pass the booking details
            Stage stage = (Stage) paymentBtn.getScene().getWindow();
            stage.setScene(scene);
            logger.info("Guest navigated to Payment Detail page.");
        } catch (Exception e) {
            logger.severe("Error loading paymentGuest.fxml: " + e.getMessage());
            e.printStackTrace();
        }
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
    private void handleModifyBooking() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reservationsystem/BookingGuest.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            BookingGuestController controller = loader.getController();
            controller.setBookingDetailsForModification(bookingDetails);
            Stage stage = (Stage) modifyBookingBtn.getScene().getWindow();
            stage.setScene(scene);
            logger.info("Guest navigated back to Booking page to modify booking.");
        } catch (Exception e) {
            logger.severe("Error navigating back to BookingGuest.fxml: " + e.getMessage());
            e.printStackTrace();
        }
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
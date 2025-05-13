package com.example.reservationsystem.controllers;

import com.example.reservationsystem.Utility.HotelLogger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement; // Already present, but ensure it's there
import java.sql.ResultSet; // Already present, but ensure it's there
import java.sql.SQLException; // Already present, but ensure it's there
import java.sql.Statement; // Add this for Statement
import com.example.reservationsystem.Utility.DBConnection;

public class CheckOutGuestController {

    private static final Logger logger = HotelLogger.getLogger();

    @FXML
    private TextField guestNameField, numGuestsField, checkInField, checkOutField, roomDetailsField,
            totalCostField, depositField, balanceDueField;

    @FXML
    private ComboBox<String> ratingComboBox;

    @FXML
    private TextArea commentsField;

    @FXML
    private Label messageLabel;

    @FXML
    private void initialize() {
        ratingComboBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
    }

    @FXML
    private void handleBookingGuest() {
        logger.info("Guest navigated to Booking page.");
    }

    @FXML
    private void handleReservationGuest() {
        logger.info("Guest navigated to Reservation Detail page.");
    }

    @FXML
    private void handleRoomSelectionGuest() {
        logger.info("Guest navigated to Room Selection page.");
    }

    @FXML
    private void handlePaymentGuest() {
        // Already on feedback page
    }

    @FXML
    private void handleRulesGuest() {
        logger.info("Guest viewed Rules and Regulations.");
    }

    @FXML
    private void handleExitGuest() {
        logger.info("Guest exited the kiosk.");
    }

    @FXML
    private void handleSubmitFeedback() {
        try {
            if (ratingComboBox.getValue() == null) {
                messageLabel.setText("Please select a rating.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }
            Connection conn = DBConnection.getConnection();
            // Placeholder: Assume guest and reservation IDs
            int guestId = 1;
            int reservationId = 1;
            String feedbackQuery = "INSERT INTO Feedback (GuestID, ReservationID, Comments, Rating) VALUES (?, ?, ?, ?)";
            PreparedStatement feedbackStmt = conn.prepareStatement(feedbackQuery);
            feedbackStmt.setInt(1, guestId);
            feedbackStmt.setInt(2, reservationId);
            feedbackStmt.setString(3, commentsField.getText());
            feedbackStmt.setInt(4, Integer.parseInt(ratingComboBox.getValue()));
            feedbackStmt.executeUpdate();

            messageLabel.setText("Feedback submitted successfully! Thank you!");
            messageLabel.setStyle("-fx-text-fill: green;");
            logger.info("Guest submitted feedback with rating: " + ratingComboBox.getValue());
        } catch (SQLException e) {
            messageLabel.setText("Error submitting feedback.");
            messageLabel.setStyle("-fx-text-fill: red;");
            logger.severe("Error submitting feedback: " + e.getMessage());
        } catch (Exception e) {
            messageLabel.setText("Error submitting feedback.");
            messageLabel.setStyle("-fx-text-fill: red;");
            logger.severe("Error submitting feedback: " + e.getMessage());
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
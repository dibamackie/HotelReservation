package com.example.reservationsystem.controllers;

import com.example.reservationsystem.Utility.HotelLogger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.logging.Logger;

public class GuestSearchAdminController {

    private static final Logger logger = HotelLogger.getLogger();

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Object> reservationTable;

    @FXML
    private TableColumn<Object, String> resIDColumn, guestNameColumn, phoneColumn, guestIDColumn, checkInColumn, checkOutColumn;

    @FXML
    private Label messageLabel;

    // Navigation buttons
    @FXML
    private Button guestSearchBtn;

    @FXML
    private Button bookingBtn;

    @FXML
    private Button reservationBtn;

    @FXML
    private Button roomSelectionBtn;

    @FXML
    private Button checkOutBtn;

    @FXML
    private Button generateReportBtn;

    @FXML
    private Button rulesBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private void handleGuestSearchAdmin() {
        // Already on guest search page
        logger.info("Admin is already on Guest Search page.");
    }

    @FXML
    private void handleBookingAdmin() {
        try {
            Stage stage = (Stage) bookingBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/BookingAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Booking page.");
        } catch (Exception e) {
            logger.severe("Error loading BookingAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReservationAdmin() {
        try {
            Stage stage = (Stage) reservationBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/reservationAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Reservation page.");
        } catch (Exception e) {
            logger.severe("Error loading reservationAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRoomSelectionAdmin() {
        try {
            Stage stage = (Stage) roomSelectionBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/roomsAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Room Selection page.");
        } catch (Exception e) {
            logger.severe("Error loading roomsAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCheckOutAdmin() {
        try {
            Stage stage = (Stage) checkOutBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/check-outAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Check-Out page.");
        } catch (Exception e) {
            logger.severe("Error loading check-outAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGenerateReportAdmin() {
        try {
            Stage stage = (Stage) generateReportBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/generateReportAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Generate Report page.");
        } catch (Exception e) {
            logger.severe("Error loading generateReportAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRulesAdmin() {
        try {
            Stage stage = (Stage) rulesBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/rulesAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin viewed Rules and Regulations.");
        } catch (Exception e) {
            logger.severe("Error loading rulesAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExitAdmin() {
        try {
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            Scene menuScene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/menu.fxml")), 720, 500);
            stage.setScene(menuScene);
            logger.info("Admin navigated back to Admin Menu from Guest Search.");
        } catch (Exception e) {
            logger.severe("Error navigating back to menu.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            messageLabel.setText("Please enter a name or phone number to search.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        // Placeholder: Search database for reservations by name or phone number
        logger.info("Admin searched for guest: " + searchText);
    }

    @FXML
    private void handleCancelReservation() {
        try {
            // Placeholder: Cancel selected reservation and update database
            messageLabel.setText("Reservation cancelled successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
            logger.info("Admin cancelled a reservation.");
        } catch (Exception e) {
            messageLabel.setText("Error cancelling reservation.");
            messageLabel.setStyle("-fx-text-fill: red;");
            logger.severe("Error cancelling reservation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCheckInReservation() {
        try {
            // Placeholder: Check-in the selected reservation
            messageLabel.setText("Check-in completed successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
            logger.info("Admin checked in a reservation.");
        } catch (Exception e) {
            messageLabel.setText("Error during check-in.");
            messageLabel.setStyle("-fx-text-fill: red;");
            logger.severe("Error during check-in: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
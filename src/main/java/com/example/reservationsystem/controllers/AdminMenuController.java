package com.example.reservationsystem.controllers;

import com.example.reservationsystem.Utility.HotelLogger;
import javafx.application.Platform; // Added import
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.logging.Logger;

public class AdminMenuController {

    private static final Logger logger = HotelLogger.getLogger();

    // For login.fxml
    @FXML
    private TextField userNameText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Label messageLabel;

    // For menu.fxml buttons
    @FXML
    private Button guestSearchButton;

    @FXML
    private Button bookingButton;

    @FXML
    private Button reservationButton;

    @FXML
    private Button roomSelectionButton;

    @FXML
    private Button checkOutButton;

    @FXML
    private Button generateReportButton;

    @FXML
    private Button exitButton;

    @FXML
    private void handleGuestSearchAdmin() {
        try {
            Stage stage = (Stage) guestSearchButton.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/guestSearchAdmin.fxml")), 700, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Guest Search page.");
        } catch (Exception e) {
            logger.severe("Error loading guestSearchAdmin.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void handleBookingAdmin() {
        try {
            Stage stage = (Stage) bookingButton.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/BookingAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Booking page.");
        } catch (Exception e) {
            logger.severe("Error loading BookingAdmin.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void handleReservationAdmin() {
        try {
            Stage stage = (Stage) reservationButton.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/reservationAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Reservation page.");
        } catch (Exception e) {
            logger.severe("Error loading reservationAdmin.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void handleRoomSelectionAdmin() {
        try {
            Stage stage = (Stage) roomSelectionButton.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/roomsAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Room Selection page.");
        } catch (Exception e) {
            logger.severe("Error loading roomsAdmin.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void handleCheckOutAdmin() {
        try {
            Stage stage = (Stage) checkOutButton.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/check-outAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Check-Out page.");
        } catch (Exception e) {
            logger.severe("Error loading check-outAdmin.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void handleGenerateReportAdmin() {
        try {
            Stage stage = (Stage) generateReportButton.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/generateReportAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Generate Report page.");
        } catch (Exception e) {
            logger.severe("Error loading generateReportAdmin.fxml: " + e.getMessage());
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

    @FXML
    private void handleUserNameInput() {
        // Optional: Validate username
    }

    @FXML
    private void handlePasswordInput() {
        // Optional: Validate password
    }

    @FXML
    private void handleLogin() {
        try {
            messageLabel.setText("Login successful!");
            messageLabel.setStyle("-fx-text-fill: green;");
            logger.info("Admin logged in successfully (no credential validation).");

            // Navigate to menu page (load menu.fxml)
            Stage stage = (Stage) userNameText.getScene().getWindow();
            Scene menuScene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/menu.fxml")), 720, 500);
            stage.setScene(menuScene);
        } catch (Exception e) {
            messageLabel.setText("Error during login.");
            messageLabel.setStyle("-fx-text-fill: red;");
            logger.severe("Error during login: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        // Clear fields and return to welcome page
        userNameText.clear();
        passwordText.clear();
        messageLabel.setText("");
        try {
            Stage stage = (Stage) userNameText.getScene().getWindow();
            Scene welcomeScene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/welcome.fxml")), 720, 500);
            stage.setScene(welcomeScene);
            logger.info("Admin cancelled login and returned to welcome page.");
        } catch (Exception e) {
            logger.severe("Error loading welcome.fxml: " + e.getMessage());
        }
    }
}
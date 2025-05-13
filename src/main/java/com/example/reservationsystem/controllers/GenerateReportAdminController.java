package com.example.reservationsystem.controllers;

import com.example.reservationsystem.Utility.HotelLogger;
import com.example.reservationsystem.Utility.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;



public class GenerateReportAdminController {

    private static final Logger logger = HotelLogger.getLogger();

    @FXML
    private TextField guestNameField, guestIDField, reservationIDField, checkInField, checkOutField, roomIDsField,
            baseAmountField, taxField, discountField, totalAmountField;

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
    private Button generateReportBtnNav;

    @FXML
    private Button rulesBtn;

    @FXML
    private Button exitBtn;

    private double baseAmount; // Store for discount calculation
    private double tax; // Store for discount calculation

    // Method to set the report data from CheckOutAdminController
    public void setReportData(String guestName, String guestId, String reservationId, String checkInDate, String checkOutDate,
                              String roomIds, double baseAmount, double tax, double totalAmount) {
        this.guestNameField.setText(guestName);
        this.guestIDField.setText(guestId);
        this.reservationIDField.setText(reservationId);
        this.checkInField.setText(checkInDate);
        this.checkOutField.setText(checkOutDate);
        this.roomIDsField.setText(roomIds);
        this.baseAmountField.setText(String.format("%.2f", baseAmount));
        this.taxField.setText(String.format("%.2f", tax));
        this.discountField.setText(""); // Leave discount empty for manual input
        this.totalAmountField.setText(String.format("%.2f", totalAmount));

        // Store baseAmount and tax for discount calculation
        this.baseAmount = baseAmount;
        this.tax = tax;
    }

    @FXML
    private void handleGuestSearchAdmin() {
        try {
            Stage stage = (Stage) guestSearchBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/guestSearchAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Guest Search page from Generate Report.");
        } catch (Exception e) {
            logger.severe("Error loading guestSearchAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBookingAdmin() {
        try {
            Stage stage = (Stage) bookingBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/BookingAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Booking page from Generate Report.");
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
            logger.info("Admin navigated to Reservation page from Generate Report.");
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
            logger.info("Admin navigated to Room Selection page from Generate Report.");
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
            logger.info("Admin navigated to Check-Out page from Generate Report.");
        } catch (Exception e) {
            logger.severe("Error loading check-outAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGenerateReportAdmin() {
        // Already on generate report page
        logger.info("Admin is already on Generate Report page.");
    }

    @FXML
    private void handleRulesAdmin() {
        try {
            Stage stage = (Stage) rulesBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/rulesAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin viewed Rules and Regulations from Generate Report.");
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
            logger.info("Admin navigated back to Admin Menu from Generate Report.");
        } catch (Exception e) {
            logger.severe("Error navigating back to menu.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleApplyDiscount() {
        try {
            String discountText = discountField.getText().trim();
            double discount = discountText.isEmpty() ? 0.0 : Double.parseDouble(discountText);
            if (discount < 0 || discount > 100) {
                messageLabel.setText("Discount must be between 0 and 100.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Recalculate total amount with discount
            double subtotal = baseAmount + tax;
            double discountAmount = subtotal * (discount / 100);
            double newTotal = subtotal - discountAmount;
            totalAmountField.setText(String.format("%.2f", newTotal));

            // Update the billing record in the database with the new discount and total
            Connection conn = DBConnection.getConnection();
            String updateQuery = "UPDATE Billing SET Discount = ?, TotalAmount = ? WHERE ReservationID = ?";
            PreparedStatement stmt = conn.prepareStatement(updateQuery);
            stmt.setDouble(1, discount);
            stmt.setDouble(2, newTotal);
            stmt.setInt(3, Integer.parseInt(reservationIDField.getText()));
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                messageLabel.setText("Discount applied successfully!");
                messageLabel.setStyle("-fx-text-fill: green;");
                logger.info("Admin applied discount: " + discount + "% for reservation ID: " + reservationIDField.getText());
            } else {
                messageLabel.setText("Failed to apply discount.");
                messageLabel.setStyle("-fx-text-fill: red;");
                logger.severe("Failed to apply discount for reservation ID: " + reservationIDField.getText());
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid discount value.");
            messageLabel.setStyle("-fx-text-fill: red;");
            logger.severe("Error applying discount: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            messageLabel.setText("Error updating billing record.");
            messageLabel.setStyle("-fx-text-fill: red;");
            logger.severe("Error updating billing record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGenerateReportAction() {
        try {
            // Placeholder: Generate and save report (e.g., as PDF)
            messageLabel.setText("Report generated successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
            logger.info("Admin generated a report for reservation ID: " + reservationIDField.getText());
        } catch (Exception e) {
            messageLabel.setText("Error generating report.");
            messageLabel.setStyle("-fx-text-fill: red;");
            logger.severe("Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
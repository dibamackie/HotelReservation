package com.example.reservationsystem.controllers;

import com.example.reservationsystem.model.BookingFormData;
import com.example.reservationsystem.Utility.HotelLogger;
import com.example.reservationsystem.Utility.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class RoomsAdminController implements Initializable {

    private static final Logger logger = HotelLogger.getLogger();

    // Navigation buttons
    @FXML
    private Button rulesBtn;

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
    private Button exitBtn;

    // Room buttons
    @FXML
    private Button room101, room102, room201, room202, room301, room302, room401, room402, room501, room502;

    @FXML
    private Label messageLabel;

    private BookingAdminController bookingController; // Reference to BookingAdminController
    private BookingFormData formData; // Store the form data

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the background color of each room button based on availability
        setRoomAvailabilityColors();
    }

    private void setRoomAvailabilityColors() {
        if (formData == null || formData.getCheckInDate() == null || formData.getCheckOutDate() == null) {
            messageLabel.setText("Error: Please select check-in and check-out dates.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        LocalDate checkInDate = formData.getCheckInDate();
        LocalDate checkOutDate = formData.getCheckOutDate();

        try {
            Connection conn = DBConnection.getConnection();
            // Query to check for overlapping reservations for each room
            String query = "SELECT r.RoomID, COUNT(res.ReservationID) as ReservationCount " +
                    "FROM Room r " +
                    "LEFT JOIN Reservation res ON r.RoomID = res.RoomID " +
                    "AND res.Status IN ('Pending', 'Checked-In') " +
                    "AND NOT (res.CheckOutDate <= ? OR res.CheckInDate >= ?) " +
                    "WHERE r.RoomID IN (101, 102, 201, 202, 301, 302, 401, 402, 501, 502) " +
                    "GROUP BY r.RoomID";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, java.sql.Date.valueOf(checkInDate));
            stmt.setDate(2, java.sql.Date.valueOf(checkOutDate));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int roomId = rs.getInt("RoomID");
                int reservationCount = rs.getInt("ReservationCount");
                Button roomButton = getRoomButton(roomId);
                if (roomButton != null) {
                    if (reservationCount == 0) {
                        // No overlapping reservations, room is available
                        roomButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    } else {
                        // Overlapping reservations exist, room is booked
                        roomButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                    }
                }
            }
        } catch (SQLException e) {
            logger.severe("Error loading room availability: " + e.getMessage());
            messageLabel.setText("Error loading room availability.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    private Button getRoomButton(int roomId) {
        switch (roomId) {
            case 101: return room101;
            case 102: return room102;
            case 201: return room201;
            case 202: return room202;
            case 301: return room301;
            case 302: return room302;
            case 401: return room401;
            case 402: return room402;
            case 501: return room501;
            case 502: return room502;
            default: return null;
        }
    }

    public void setBookingController(BookingAdminController controller) {
        this.bookingController = controller;
    }

    public void setFormData(BookingFormData formData) {
        this.formData = formData;
        setRoomAvailabilityColors(); // Recompute availability when form data is set
    }

    @FXML
    private void handleSelectRoom() {
        Button source = (Button) messageLabel.getScene().getFocusOwner();
        String roomText = source.getText();
        String roomId = roomText.split(" - ")[0]; // Extract RoomID from "RoomID - Type"

        // Check if the room is available for the selected dates
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT COUNT(*) as ReservationCount " +
                    "FROM Reservation " +
                    "WHERE RoomID = ? " +
                    "AND Status IN ('Pending', 'Checked-In') " +
                    "AND NOT (CheckOutDate <= ? OR CheckInDate >= ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(roomId));
            stmt.setDate(2, java.sql.Date.valueOf(formData.getCheckInDate()));
            stmt.setDate(3, java.sql.Date.valueOf(formData.getCheckOutDate()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int reservationCount = rs.getInt("ReservationCount");
                if (reservationCount > 0) {
                    messageLabel.setText("Error: Room " + roomId + " is not available for the selected dates.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                    return;
                }
            }
        } catch (SQLException e) {
            logger.severe("Error checking room availability: " + e.getMessage());
            messageLabel.setText("Error checking room availability.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
            return;
        }

        // Pass the selected room ID and form data back to BookingAdminController and navigate back
        if (bookingController != null && formData != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reservationsystem/BookingAdmin.fxml"));
                Scene scene = new Scene(loader.load(), 800, 600);
                BookingAdminController controller = loader.getController();
                controller.setSelectedRoomId(roomId, formData);
                Stage stage = (Stage) source.getScene().getWindow();
                stage.setScene(scene);
                logger.info("Admin selected room " + roomId + " and navigated back to Booking page.");
            } catch (Exception e) {
                logger.severe("Error navigating back to BookingAdmin.fxml: " + e.getMessage());
                messageLabel.setText("Error navigating back to Booking page.");
                messageLabel.setStyle("-fx-text-fill: red;");
                e.printStackTrace();
            }
        } else {
            messageLabel.setText("Error: Booking controller or form data not set.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleRulesAdmin() {
        try {
            Stage stage = (Stage) rulesBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/rulesAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin viewed Rules and Regulations from Room Selection.");
        } catch (Exception e) {
            logger.severe("Error loading rulesAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBookingAdmin() {
        try {
            Stage stage = (Stage) bookingBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/BookingAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Booking page from Room Selection.");
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
            logger.info("Admin navigated to Reservation page from Room Selection.");
        } catch (Exception e) {
            logger.severe("Error loading reservationAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRoomSelectionAdmin() {
        // Already on room selection page
        logger.info("Admin is already on Room Selection page.");
    }

    @FXML
    private void handleCheckOutAdmin() {
        try {
            Stage stage = (Stage) checkOutBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/check-outAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Check-Out page from Room Selection.");
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
            logger.info("Admin navigated to Generate Report page from Room Selection.");
        } catch (Exception e) {
            logger.severe("Error loading generateReportAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExitAdmin() {
        try {
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            Scene menuScene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/menu.fxml")), 720, 500);
            stage.setScene(menuScene);
            logger.info("Admin navigated back to Admin Menu from Room Selection.");
        } catch (Exception e) {
            logger.severe("Error navigating back to menu.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
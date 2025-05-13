package com.example.reservationsystem.controllers;

import com.example.reservationsystem.model.BookingFormData;
import com.example.reservationsystem.Utility.HotelLogger;
import com.example.reservationsystem.Utility.DBConnection;
import javafx.application.Platform;
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

public class RoomsGuestController implements Initializable {

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

    // Room buttons
    @FXML
    private Button room101, room102, room201, room202, room301, room302, room401, room402, room501, room502;

    @FXML
    private Label messageLabel;

    private BookingGuestController bookingController; // Reference to BookingGuestController
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

    public void setBookingController(BookingGuestController controller) {
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

        // Pass the selected room ID and form data back to BookingGuestController and navigate back
        if (bookingController != null && formData != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reservationsystem/BookingGuest.fxml"));
                Scene scene = new Scene(loader.load(), 800, 600);
                BookingGuestController controller = loader.getController();
                controller.setSelectedRoomId(roomId, formData);
                Stage stage = (Stage) source.getScene().getWindow();
                stage.setScene(scene);
                logger.info("Guest selected room " + roomId + " and navigated back to Booking page.");
            } catch (Exception e) {
                logger.severe("Error navigating back to BookingGuest.fxml: " + e.getMessage());
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
    private void handleBookingGuest() {
        try {
            Stage stage = (Stage) bookingBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/BookingGuest.fxml")),720, 410);
            stage.setScene(scene);
            logger.info("Guest navigated to Booking page.");
        } catch (Exception e) {
            logger.severe("Error loading BookingGuest.fxml: " + e.getMessage());
            messageLabel.setText("Error: Failed to navigate to Booking page.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRoomSelectionGuest() {
        // Already on room selection page
        logger.info("Guest is already on Room Selection page.");
    }

    @FXML
    private void handleReservationGuest() {
        try {
            Stage stage = (Stage) reservationBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/reservationGuest.fxml")), 720, 410);
            stage.setScene(scene);
            logger.info("Guest navigated to Reservation page.");
        } catch (Exception e) {
            logger.severe("Error loading reservationGuest.fxml: " + e.getMessage());
            messageLabel.setText("Error: Failed to navigate to Reservation page.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePaymentGuest() {
        try {
            Stage stage = (Stage) paymentBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/paymentGuest.fxml")), 720, 410);
            stage.setScene(scene);
            logger.info("Guest navigated to Payment Detail page.");
        } catch (Exception e) {
            logger.severe("Error loading paymentGuest.fxml: " + e.getMessage());
            messageLabel.setText("Error: Failed to navigate to Payment Detail page.");
            messageLabel.setStyle("-fx-text-fill: red;");
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
            messageLabel.setText("Error: Failed to navigate back to Welcome page.");
            messageLabel.setStyle("-fx-text-fill: red;");
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

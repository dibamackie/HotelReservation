package com.example.reservationsystem.controllers;

import com.example.reservationsystem.model.ReservationView;
import com.example.reservationsystem.Utility.HotelLogger;
import com.example.reservationsystem.Utility.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ReservationAdminController implements Initializable {

    private static final Logger logger = HotelLogger.getLogger();

    @FXML
    private TextField searchField;

    @FXML
    private TableView<ReservationView> reservationTable;

    @FXML
    private TableColumn<ReservationView, String> resIDColumn, guestNameColumn, phoneColumn, guestIDColumn, checkInColumn, checkOutColumn;

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

    private ObservableList<ReservationView> reservationList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the TableView columns
        resIDColumn.setCellValueFactory(cellData -> cellData.getValue().reservationIdProperty());
        guestNameColumn.setCellValueFactory(cellData -> cellData.getValue().guestNameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        guestIDColumn.setCellValueFactory(cellData -> cellData.getValue().guestIdProperty());
        checkInColumn.setCellValueFactory(cellData -> cellData.getValue().checkInDateProperty());
        checkOutColumn.setCellValueFactory(cellData -> cellData.getValue().checkOutDateProperty());

        // Initialize the reservation list
        reservationList = FXCollections.observableArrayList();
        reservationTable.setItems(reservationList);

        // Load all reservations when the page loads
        loadAllReservations();
    }

    private void loadAllReservations() {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT r.ReservationID, g.Name, g.PhoneNumber, r.GuestID, r.CheckInDate, r.CheckOutDate " +
                    "FROM Reservation r " +
                    "JOIN Guest g ON r.GuestID = g.GuestID " +
                    "WHERE r.Status != 'Cancelled'";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            reservationList.clear();
            while (rs.next()) {
                ReservationView reservation = new ReservationView(
                        String.valueOf(rs.getInt("ReservationID")),
                        rs.getString("Name"),
                        rs.getString("PhoneNumber"),
                        String.valueOf(rs.getInt("GuestID")),
                        rs.getString("CheckInDate"),
                        rs.getString("CheckOutDate")
                );
                reservationList.add(reservation);
            }
            logger.info("Loaded all non-cancelled reservations into the table.");
        } catch (SQLException e) {
            logger.severe("Error loading reservations: " + e.getMessage());
            messageLabel.setText("Error loading reservations.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            messageLabel.setText("Please enter a name or phone number to search.");
            messageLabel.setStyle("-fx-text-fill: red;");
            loadAllReservations(); // Reset to show all non-cancelled reservations
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT r.ReservationID, g.Name, g.PhoneNumber, r.GuestID, r.CheckInDate, r.CheckOutDate " +
                    "FROM Reservation r " +
                    "JOIN Guest g ON r.GuestID = g.GuestID " +
                    "WHERE (g.Name LIKE ? OR g.PhoneNumber LIKE ?) AND r.Status != 'Cancelled'";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, "%" + searchText + "%");
            ResultSet rs = stmt.executeQuery();

            reservationList.clear();
            while (rs.next()) {
                ReservationView reservation = new ReservationView(
                        String.valueOf(rs.getInt("ReservationID")),
                        rs.getString("Name"),
                        rs.getString("PhoneNumber"),
                        String.valueOf(rs.getInt("GuestID")),
                        rs.getString("CheckInDate"),
                        rs.getString("CheckOutDate")
                );
                reservationList.add(reservation);
            }
            logger.info("Admin searched for reservation: " + searchText);
            if (reservationList.isEmpty()) {
                messageLabel.setText("No active reservations found for: " + searchText);
                messageLabel.setStyle("-fx-text-fill: orange;");
            } else {
                messageLabel.setText("Search completed.");
                messageLabel.setStyle("-fx-text-fill: green;");
            }
        } catch (SQLException e) {
            logger.severe("Error searching reservations: " + e.getMessage());
            messageLabel.setText("Error searching reservations.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGuestSearchAdmin() {
        try {
            Stage stage = (Stage) guestSearchBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/guestSearchAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Guest Search page from Reservation.");
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
            logger.info("Admin navigated to Booking page from Reservation.");
        } catch (Exception e) {
            logger.severe("Error loading BookingAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReservationAdmin() {
        // Already on reservation page
        logger.info("Admin is already on Reservation page.");
    }

    @FXML
    private void handleRoomSelectionAdmin() {
        try {
            Stage stage = (Stage) roomSelectionBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/roomsAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Room Selection page from Reservation.");
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
            logger.info("Admin navigated to Check-Out page from Reservation.");
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
            logger.info("Admin navigated to Generate Report page from Reservation.");
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
            logger.info("Admin viewed Rules and Regulations from Reservation.");
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
            logger.info("Admin navigated back to Admin Menu from Reservation.");
        } catch (Exception e) {
            logger.severe("Error navigating back to menu.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelReservation() {
        ReservationView selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation == null) {
            messageLabel.setText("Please select a reservation to cancel.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            // Start a transaction
            conn.setAutoCommit(false);
            try {
                // Update the reservation status to 'Cancelled'
                String updateReservationQuery = "UPDATE Reservation SET Status = 'Cancelled' WHERE ReservationID = ?";
                PreparedStatement reservationStmt = conn.prepareStatement(updateReservationQuery);
                reservationStmt.setInt(1, Integer.parseInt(selectedReservation.getReservationId()));
                int affectedRows = reservationStmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Cancelling reservation failed, no rows affected.");
                }

                // Update the room status to 'Available'
                String updateRoomQuery = "UPDATE Room SET Status = 'Available' WHERE RoomID = (SELECT RoomID FROM Reservation WHERE ReservationID = ?)";
                PreparedStatement roomStmt = conn.prepareStatement(updateRoomQuery);
                roomStmt.setInt(1, Integer.parseInt(selectedReservation.getReservationId()));
                int roomAffectedRows = roomStmt.executeUpdate();

                if (roomAffectedRows == 0) {
                    logger.warning("No room status updated for reservation ID: " + selectedReservation.getReservationId());
                }

                // Commit the transaction
                conn.commit();
                messageLabel.setText("Reservation cancelled successfully!");
                messageLabel.setStyle("-fx-text-fill: green;");
                logger.info("Admin cancelled reservation ID: " + selectedReservation.getReservationId());
            } catch (SQLException e) {
                // Roll back the transaction on error
                conn.rollback();
                logger.severe("Error cancelling reservation: " + e.getMessage());
                messageLabel.setText("Error cancelling reservation.");
                messageLabel.setStyle("-fx-text-fill: red;");
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
            loadAllReservations(); // Refresh the table after cancellation
        } catch (SQLException e) {
            logger.severe("Error connecting to database: " + e.getMessage());
            messageLabel.setText("Error connecting to database.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCheckInReservation() {
        ReservationView selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation == null) {
            messageLabel.setText("Please select a reservation to check in.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String updateQuery = "UPDATE Reservation SET Status = 'Checked-In' WHERE ReservationID = ?";
            PreparedStatement stmt = conn.prepareStatement(updateQuery);
            stmt.setInt(1, Integer.parseInt(selectedReservation.getReservationId()));
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                messageLabel.setText("Check-in completed successfully!");
                messageLabel.setStyle("-fx-text-fill: green;");
                logger.info("Admin checked in reservation ID: " + selectedReservation.getReservationId());
            } else {
                messageLabel.setText("Failed to check in reservation.");
                messageLabel.setStyle("-fx-text-fill: red;");
                logger.severe("Failed to check in reservation ID: " + selectedReservation.getReservationId());
            }
            loadAllReservations(); // Refresh the table after check-in
        } catch (SQLException e) {
            logger.severe("Error during check-in: " + e.getMessage());
            messageLabel.setText("Error during check-in.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
}
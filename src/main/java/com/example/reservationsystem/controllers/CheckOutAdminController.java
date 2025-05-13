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
import javafx.beans.property.SimpleStringProperty;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.logging.Logger;




public class CheckOutAdminController implements Initializable {

    private static final Logger logger = HotelLogger.getLogger();

    @FXML
    private TextField searchField, discountField;

    @FXML
    private TableView<ReservationView> checkoutTable;

    @FXML
    private TableColumn<ReservationView, String> nameColumn, phoneColumn, roomIDColumn, checkInColumn, checkOutColumn;

    @FXML
    private Label messageLabel, feedbackReminderLabel;

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
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().guestNameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        roomIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty("")); // RoomID not in ReservationView; will fetch in handleCheckOutAndGenerateReport
        checkInColumn.setCellValueFactory(cellData -> cellData.getValue().checkInDateProperty());
        checkOutColumn.setCellValueFactory(cellData -> cellData.getValue().checkOutDateProperty());

        // Initialize the reservation list
        reservationList = FXCollections.observableArrayList();
        checkoutTable.setItems(reservationList);

        // Load checked-in reservations when the page loads
        loadCheckedInReservations();
    }

    private void loadCheckedInReservations() {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT r.ReservationID, g.Name, g.PhoneNumber, r.GuestID, r.RoomID, r.CheckInDate, r.CheckOutDate " +
                    "FROM Reservation r " +
                    "JOIN Guest g ON r.GuestID = g.GuestID " +
                    "WHERE r.Status = 'Checked-In'";
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
            logger.info("Loaded checked-in reservations into the table on Check-Out page.");
        } catch (SQLException e) {
            logger.severe("Error loading checked-in reservations: " + e.getMessage());
            messageLabel.setText("Error loading checked-in reservations.");
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
            loadCheckedInReservations(); // Reset to show all checked-in reservations
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT r.ReservationID, g.Name, g.PhoneNumber, r.GuestID, r.RoomID, r.CheckInDate, r.CheckOutDate " +
                    "FROM Reservation r " +
                    "JOIN Guest g ON r.GuestID = g.GuestID " +
                    "WHERE (g.Name LIKE ? OR g.PhoneNumber LIKE ?) AND r.Status = 'Checked-In'";
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
            logger.info("Admin searched for checked-in guest: " + searchText);
            if (reservationList.isEmpty()) {
                messageLabel.setText("No checked-in reservations found for: " + searchText);
                messageLabel.setStyle("-fx-text-fill: orange;");
            } else {
                messageLabel.setText("Search completed.");
                messageLabel.setStyle("-fx-text-fill: green;");
            }
        } catch (SQLException e) {
            logger.severe("Error searching checked-in reservations: " + e.getMessage());
            messageLabel.setText("Error searching checked-in reservations.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGuestSearchAdmin() {
        try {
            Stage stage = (Stage) guestSearchBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/guestSearchAdmin.fxml")), 700, 510);
            stage.setScene(scene);
            logger.info("Admin navigated to Guest Search page from Check-Out.");
        } catch (Exception e) {
            logger.severe("Error loading guestSearchAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBookingAdmin() {
        try {
            Stage stage = (Stage) bookingBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/BookingAdmin.fxml")), 700, 510);
            stage.setScene(scene);
            logger.info("Admin navigated to Booking page from Check-Out.");
        } catch (Exception e) {
            logger.severe("Error loading BookingAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReservationAdmin() {
        try {
            Stage stage = (Stage) reservationBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/reservationAdmin.fxml")), 700, 510);
            stage.setScene(scene);
            logger.info("Admin navigated to Reservation page from Check-Out.");
        } catch (Exception e) {
            logger.severe("Error loading reservationAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRoomSelectionAdmin() {
        try {
            Stage stage = (Stage) roomSelectionBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/roomsAdmin.fxml")), 700, 510);
            stage.setScene(scene);
            logger.info("Admin navigated to Room Selection page from Check-Out.");
        } catch (Exception e) {
            logger.severe("Error loading roomsAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCheckOutAdmin() {
        // Already on check-out page
        logger.info("Admin is already on Check-Out page.");
    }

    @FXML
    private void handleGenerateReportAdmin() {
        try {
            Stage stage = (Stage) generateReportBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/generateReportAdmin.fxml")), 700, 510);
            stage.setScene(scene);
            logger.info("Admin navigated to Generate Report page from Check-Out.");
        } catch (Exception e) {
            logger.severe("Error loading generateReportAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRulesAdmin() {
        try {
            Stage stage = (Stage) rulesBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/rulesAdmin.fxml")), 700, 510);
            stage.setScene(scene);
            logger.info("Admin viewed Rules and Regulations from Check-Out.");
        } catch (Exception e) {
            logger.severe("Error loading rulesAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExitAdmin() {
        try {
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            Scene menuScene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/menu.fxml")), 700, 510);
            stage.setScene(menuScene);
            logger.info("Admin navigated back to Admin Menu from Check-Out.");
        } catch (Exception e) {
            logger.severe("Error navigating back to menu.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCheckOutAndGenerateReport() {
        ReservationView selectedReservation = checkoutTable.getSelectionModel().getSelectedItem();
        if (selectedReservation == null) {
            messageLabel.setText("Please select a reservation to check out.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            int reservationId = Integer.parseInt(selectedReservation.getReservationId());

            // Fetch additional details from the database
            String query = "SELECT r.RoomID, r.CheckInDate, r.CheckOutDate " +
                    "FROM Reservation r " +
                    "WHERE r.ReservationID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                messageLabel.setText("Reservation details not found.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            String roomId = String.valueOf(rs.getInt("RoomID"));
            LocalDate checkInDate = LocalDate.parse(selectedReservation.getCheckInDate());
            LocalDate checkOutDate = LocalDate.parse(selectedReservation.getCheckOutDate());

            // Calculate the number of nights
            long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
            if (nights <= 0) {
                nights = 1; // Minimum 1 night
            }

            // Calculate billing details (assume $100 per night for simplicity)
            double baseAmount = nights * 100.00;
            double taxRate = 0.13; // 13% tax
            double tax = baseAmount * taxRate;
            double totalAmount = baseAmount + tax;

            // Insert billing record (discount is 0 for now, can be updated in GenerateReportAdmin)
            String billingQuery = "INSERT INTO Billing (ReservationID, Amount, Tax, TotalAmount, Discount) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement billingStmt = conn.prepareStatement(billingQuery);
            billingStmt.setInt(1, reservationId);
            billingStmt.setDouble(2, baseAmount);
            billingStmt.setDouble(3, tax);
            billingStmt.setDouble(4, totalAmount);
            billingStmt.setDouble(5, 0.0); // Discount is 0 initially
            billingStmt.executeUpdate();

            // Update reservation status to 'Checked-Out'
            String updateQuery = "UPDATE Reservation SET Status = 'Checked-Out' WHERE ReservationID = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setInt(1, reservationId);
            int affectedRows = updateStmt.executeUpdate();

            if (affectedRows > 0) {
                messageLabel.setText("Check-out completed successfully!");
                messageLabel.setStyle("-fx-text-fill: green;");
                feedbackReminderLabel.setVisible(true);
                logger.info("Admin processed check-out for reservation ID: " + reservationId);

                // Navigate to GenerateReportAdmin and pass the data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reservationsystem/generateReportAdmin.fxml"));
                Scene scene = new Scene(loader.load(), 600, 400);
                GenerateReportAdminController controller = loader.getController();
                controller.setReportData(
                        selectedReservation.getGuestName(),
                        selectedReservation.getGuestId(),
                        selectedReservation.getReservationId(),
                        selectedReservation.getCheckInDate(),
                        selectedReservation.getCheckOutDate(),
                        roomId,
                        baseAmount,
                        tax,
                        totalAmount
                );

                Stage stage = (Stage) checkOutBtn.getScene().getWindow();
                stage.setScene(scene);
                logger.info("Navigated to Generate Report page with data for reservation ID: " + reservationId);
            } else {
                messageLabel.setText("Failed to check out reservation.");
                messageLabel.setStyle("-fx-text-fill: red;");
                logger.severe("Failed to check out reservation ID: " + reservationId);
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid data format.");
            messageLabel.setStyle("-fx-text-fill: red;");
            logger.severe("Error processing data: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            messageLabel.setText("Error during check-out.");
            messageLabel.setStyle("-fx-text-fill: red;");
            logger.severe("Error during check-out: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            messageLabel.setText("Error navigating to Generate Report page.");
            messageLabel.setStyle("-fx-text-fill: red;");
            logger.severe("Error navigating to Generate Report page: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
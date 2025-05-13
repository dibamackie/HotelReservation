package com.example.reservationsystem.controllers;

import com.example.reservationsystem.model.BookingFormData;
import com.example.reservationsystem.Utility.HotelLogger;
import com.example.reservationsystem.Utility.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Logger;

public class BookingAdminController {

    private static final Logger logger = HotelLogger.getLogger();

    @FXML
    private TextField firstNameField, phoneNumberField, emailAddressField, selectedRoomField;

    @FXML
    private DatePicker checkInDate, checkOutDate;

    @FXML
    private MenuButton guestNumMenu;

    @FXML
    private Button selectRoomBtn;

    @FXML
    private Label messageLabel;

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

    private String selectedRoomId; // Store the selected room ID

    @FXML
    private void handleRulesAdmin() {
        try {
            Stage stage = (Stage) rulesBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/rulesAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin viewed Rules and Regulations from Booking.");
        } catch (Exception e) {
            logger.severe("Error loading rulesAdmin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBookingAdmin() {
        // Already on booking page
        logger.info("Admin is already on Booking page.");
    }

    @FXML
    private void handleReservationAdmin() {
        try {
            Stage stage = (Stage) reservationBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/reservationAdmin.fxml")), 720, 500);
            stage.setScene(scene);
            logger.info("Admin navigated to Reservation page from Booking.");
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
            logger.info("Admin navigated to Room Selection page from Booking.");
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
            logger.info("Admin navigated to Check-Out page from Booking.");
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
            logger.info("Admin navigated to Generate Report page from Booking.");
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
            logger.info("Admin navigated back to Admin Menu from Booking.");
        } catch (Exception e) {
            logger.severe("Error navigating back to menu.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFirstNameInput() {
        // Validate first name
        if (firstNameField.getText().trim().isEmpty()) {
            messageLabel.setText("First Name is required.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handlePhoneNumberInput() {
        // Validate phone number
        String phone = phoneNumberField.getText().trim();
        if (!phone.matches("\\d{10}")) {
            messageLabel.setText("Phone Number must be 10 digits.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleGuestNumSelection() {
        // Update guestNumMenu text based on selection
        guestNumMenu.setText(((MenuItem) guestNumMenu.getItems().get(0)).getText());
    }

    @FXML
    private void handleAddAnotherRoom() {
        // Logic to add another room (e.g., clear selected room ID and TextField for new input)
        selectedRoomId = null;
        selectedRoomField.setText(""); // Clear the TextField
        messageLabel.setText("Add another room.");
        messageLabel.setStyle("-fx-text-fill: green;");
        logger.info("Admin added another room to booking.");
    }

    @FXML
    private void handleSelectRoom() {
        try {
            // Capture the current form data
            BookingFormData formData = new BookingFormData(
                    firstNameField.getText().trim(),
                    phoneNumberField.getText().trim(),
                    emailAddressField.getText().trim(),
                    checkInDate.getValue(),
                    checkOutDate.getValue(),
                    guestNumMenu.getText()
            );

            // Navigate to RoomsAdmin page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reservationsystem/roomsAdmin.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            RoomsAdminController controller = loader.getController();
            controller.setBookingController(this);
            controller.setFormData(formData); // Pass the form data
            Stage stage = (Stage) selectRoomBtn.getScene().getWindow();
            stage.setScene(scene);
            logger.info("Admin navigated to Room Selection page to select a room.");
        } catch (Exception e) {
            logger.severe("Error loading roomsAdmin.fxml: " + e.getMessage());
            messageLabel.setText("Error: Failed to navigate to Room Selection page.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    // Method to receive the selected room ID and form data from RoomsAdminController
    public void setSelectedRoomId(String roomId, BookingFormData formData) {
        this.selectedRoomId = roomId;
        // Repopulate the form fields
        firstNameField.setText(formData.getFirstName());
        phoneNumberField.setText(formData.getPhoneNumber());
        emailAddressField.setText(formData.getEmailAddress());
        checkInDate.setValue(formData.getCheckInDate());
        checkOutDate.setValue(formData.getCheckOutDate());
        guestNumMenu.setText(formData.getNumberOfGuests());
        selectedRoomField.setText(roomId); // Set the selected room ID in the TextField
        messageLabel.setText("Room selected successfully!");
        messageLabel.setStyle("-fx-text-fill: green;");
        logger.info("Selected Room ID: " + roomId);
    }

    @FXML
    private void handleSaveBooking() {
        try {
            if (validateInputs()) {
                Connection conn = DBConnection.getConnection();
                // Start a transaction
                conn.setAutoCommit(false);
                try {
                    // Validate RoomID
                    if (selectedRoomId == null) {
                        messageLabel.setText("Error: Please select a room.");
                        messageLabel.setStyle("-fx-text-fill: red;");
                        conn.rollback();
                        return;
                    }
                    int roomId = Integer.parseInt(selectedRoomId);
                    if (!roomExistsAndIsAvailable(conn, roomId)) {
                        messageLabel.setText("Error: Room ID " + roomId + " does not exist or is not available.");
                        messageLabel.setStyle("-fx-text-fill: red;");
                        conn.rollback();
                        return;
                    }

                    // Insert guest into Guest table
                    String guestQuery = "INSERT INTO Guest (Name, PhoneNumber, Email) VALUES (?, ?, ?)";
                    PreparedStatement guestStmt = conn.prepareStatement(guestQuery, Statement.RETURN_GENERATED_KEYS);
                    guestStmt.setString(1, firstNameField.getText().trim());
                    guestStmt.setString(2, phoneNumberField.getText().trim());
                    guestStmt.setString(3, emailAddressField.getText().trim());
                    int affectedRows = guestStmt.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Inserting guest failed, no rows affected.");
                    }

                    // Retrieve the generated GuestID
                    ResultSet rs = guestStmt.getGeneratedKeys();
                    int guestId = -1;
                    if (rs.next()) {
                        guestId = rs.getInt(1);
                        logger.info("Generated GuestID: " + guestId);
                    } else {
                        throw new SQLException("Inserting guest failed, no ID obtained.");
                    }

                    // Insert reservation into Reservation table
                    String reservationQuery = "INSERT INTO Reservation (GuestID, RoomID, CheckInDate, CheckOutDate, NumberOfGuests, Status) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement reservationStmt = conn.prepareStatement(reservationQuery);
                    reservationStmt.setInt(1, guestId);
                    reservationStmt.setInt(2, roomId);
                    reservationStmt.setDate(3, java.sql.Date.valueOf(checkInDate.getValue()));
                    reservationStmt.setDate(4, java.sql.Date.valueOf(checkOutDate.getValue()));
                    reservationStmt.setInt(5, Integer.parseInt(guestNumMenu.getText()));
                    reservationStmt.setString(6, "Pending");
                    int reservationRows = reservationStmt.executeUpdate();

                    if (reservationRows == 0) {
                        throw new SQLException("Inserting reservation failed, no rows affected.");
                    }

                    // Update the room status to 'Booked'
                    String updateRoomQuery = "UPDATE Room SET Status = 'Booked' WHERE RoomID = ?";
                    PreparedStatement updateRoomStmt = conn.prepareStatement(updateRoomQuery);
                    updateRoomStmt.setInt(1, roomId);
                    int roomUpdateRows = updateRoomStmt.executeUpdate();

                    if (roomUpdateRows == 0) {
                        throw new SQLException("Updating room status failed, no rows affected.");
                    }

                    // Commit the transaction
                    conn.commit();
                    logger.info("Transaction committed successfully.");

                    messageLabel.setText("Booking saved successfully!");
                    messageLabel.setStyle("-fx-text-fill: green;");
                    logger.info("Admin saved a booking for guest: " + firstNameField.getText());

                    // Navigate to ReservationAdmin page
                    Stage stage = (Stage) bookingBtn.getScene().getWindow();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/reservationAdmin.fxml")), 720, 500);
                    stage.setScene(scene);
                    logger.info("Admin navigated to Reservation page after saving booking.");
                } catch (SQLException e) {
                    // Roll back the transaction on error
                    conn.rollback();
                    logger.severe("Database error saving booking, transaction rolled back: " + e.getMessage());
                    messageLabel.setText("Error: Failed to save booking to database.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                    e.printStackTrace();
                } finally {
                    conn.setAutoCommit(true);
                }
            } else {
                messageLabel.setText("Error: Please fill all required fields.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (SQLException e) {
            logger.severe("Error setting up database connection: " + e.getMessage());
            messageLabel.setText("Error: Database connection failed.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        } catch (Exception e) {
            logger.severe("Error saving booking or navigating: " + e.getMessage());
            messageLabel.setText("Error: Failed to save booking or navigate.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        return !firstNameField.getText().trim().isEmpty() &&
                !phoneNumberField.getText().trim().isEmpty() &&
                checkInDate.getValue() != null &&
                checkOutDate.getValue() != null &&
                !guestNumMenu.getText().equals("Select Number of Guests") &&
                selectedRoomId != null;
    }

    private boolean roomExistsAndIsAvailable(Connection conn, int roomId) throws SQLException {
        String query = "SELECT Status FROM Room WHERE RoomID = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, roomId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String status = rs.getString("Status");
            return "Available".equals(status);
        }
        return false;
    }
}
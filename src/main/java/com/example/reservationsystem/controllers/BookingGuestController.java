package com.example.reservationsystem.controllers;

import com.example.reservationsystem.model.BookingFormData;
import com.example.reservationsystem.model.BookingDetails;
import com.example.reservationsystem.Utility.HotelLogger;
import com.example.reservationsystem.Utility.DBConnection;
import javafx.application.Platform;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Logger;

public class BookingGuestController {

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
    private Button bookingBtn;

    @FXML
    private Button roomSelectionBtn;

    @FXML
    private Button reservationBtn;

    @FXML
    private Button paymentBtn;

    @FXML
    private Button exitBtn;

    private String selectedRoomId; // Store the selected room ID
    private BookingFormData formData; // Store the form data

    @FXML
    private void handleBookingGuest() {
        // Already on booking page
        logger.info("Guest is already on Booking page.");
    }

    @FXML
    private void handleRoomSelectionGuest() {
        try {
            // Capture the current form data
            this.formData = new BookingFormData(
                    firstNameField.getText().trim(),
                    phoneNumberField.getText().trim(),
                    emailAddressField.getText().trim(),
                    checkInDate.getValue(),
                    checkOutDate.getValue(),
                    guestNumMenu.getText()
            );

            // Navigate to RoomsGuest page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reservationsystem/RoomsGuest.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            RoomsGuestController controller = loader.getController();
            controller.setBookingController(this);
            controller.setFormData(formData); // Pass the form data
            Stage stage = (Stage) roomSelectionBtn.getScene().getWindow();
            stage.setScene(scene);
            logger.info("Guest navigated to Room Selection page.");
        } catch (Exception e) {
            logger.severe("Error loading RoomsGuest.fxml: " + e.getMessage());
            messageLabel.setText("Error: Failed to navigate to Room Selection page.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
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
        logger.info("Guest added another room to booking.");
    }

    @FXML
    private void handleSelectRoom() {
        try {
            // Capture the current form data
            this.formData = new BookingFormData(
                    firstNameField.getText().trim(),
                    phoneNumberField.getText().trim(),
                    emailAddressField.getText().trim(),
                    checkInDate.getValue(),
                    checkOutDate.getValue(),
                    guestNumMenu.getText()
            );

            // Navigate to RoomsGuest page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reservationsystem/RoomsGuest.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            RoomsGuestController controller = loader.getController();
            controller.setBookingController(this);
            controller.setFormData(formData); // Pass the form data
            Stage stage = (Stage) selectRoomBtn.getScene().getWindow();
            stage.setScene(scene);
            logger.info("Guest navigated to Room Selection page to select a room.");
        } catch (Exception e) {
            logger.severe("Error loading RoomsGuest.fxml: " + e.getMessage());
            messageLabel.setText("Error: Failed to navigate to Room Selection page.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    // Method to receive the selected room ID and form data from RoomsGuestController
    public void setSelectedRoomId(String roomId, BookingFormData formData) {
        this.selectedRoomId = roomId;
        this.formData = formData; // Update the instance variable
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

    // Method to pre-fill the form with existing booking details for modification
    public void setBookingDetailsForModification(BookingDetails bookingDetails) {
        this.selectedRoomId = bookingDetails.getRoomId();
        this.formData = new BookingFormData(
                bookingDetails.getFirstName(),
                bookingDetails.getPhoneNumber(),
                bookingDetails.getEmailAddress(),
                bookingDetails.getCheckInDate(),
                bookingDetails.getCheckOutDate(),
                String.valueOf(bookingDetails.getNumberOfGuests())
        );
        firstNameField.setText(bookingDetails.getFirstName());
        phoneNumberField.setText(bookingDetails.getPhoneNumber());
        emailAddressField.setText(bookingDetails.getEmailAddress());
        checkInDate.setValue(bookingDetails.getCheckInDate());
        checkOutDate.setValue(bookingDetails.getCheckOutDate());
        guestNumMenu.setText(String.valueOf(bookingDetails.getNumberOfGuests()));
        selectedRoomField.setText(bookingDetails.getRoomId());
        messageLabel.setText("Modify your booking details.");
        messageLabel.setStyle("-fx-text-fill: blue;");
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

                    // Create BookingDetails object
                    String roomType = getRoomType(roomId);
                    BookingDetails bookingDetails = new BookingDetails(
                            firstNameField.getText().trim(),
                            phoneNumberField.getText().trim(),
                            emailAddressField.getText().trim(),
                            checkInDate.getValue(),
                            checkOutDate.getValue(),
                            Integer.parseInt(guestNumMenu.getText()),
                            selectedRoomId,
                            roomType
                    );

                    // Navigate to ReservationGuest page and pass booking details
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reservationsystem/reservationGuest.fxml"));
                    Scene scene = new Scene(loader.load(), 800, 600);
                    ReservationGuestController controller = loader.getController();
                    controller.setBookingDetails(bookingDetails);
                    Stage stage = (Stage) bookingBtn.getScene().getWindow();
                    stage.setScene(scene);
                    logger.info("Guest navigated to Reservation page after saving booking.");
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
        String query = "SELECT COUNT(*) as ReservationCount " +
                "FROM Reservation " +
                "WHERE RoomID = ? " +
                "AND Status IN ('Pending', 'Checked-In') " +
                "AND NOT (CheckOutDate <= ? OR CheckInDate >= ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, roomId);
        stmt.setDate(2, java.sql.Date.valueOf(formData.getCheckInDate()));
        stmt.setDate(3, java.sql.Date.valueOf(formData.getCheckOutDate()));
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int reservationCount = rs.getInt("ReservationCount");
            return reservationCount == 0;
        }
        return false;
    }

    private String getRoomType(int roomId) {
        // Map RoomID to Room Type based on your room definitions
        switch (roomId) {
            case 101: return "Single";
            case 102: return "Double";
            case 201: return "Deluxe";
            case 202: return "Single";
            case 301: return "Double";
            case 302: return "Penthouse";
            case 401: return "Single";
            case 402: return "Deluxe";
            case 501: return "Penthouse";
            case 502: return "Double";
            default: return "Unknown";
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
package com.example.reservationsystem;

import com.example.reservationsystem.Utility.HotelLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.util.logging.Logger;

public class HotelReservationApp extends Application {

    private static final Logger logger = HotelLogger.getLogger();

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create the welcome page programmatically
            Label welcomeLabel = new Label("Welcome to Hotel Reservation");
            welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            // Load and play the video
            String videoPath = getClass().getResource("/com/example/reservationsystem/welcome-video.mp4").toExternalForm();
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setFitWidth(400); // Set the width of the video
            mediaView.setFitHeight(225); // Set the height (16:9 aspect ratio)

            // Set the video to loop
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
            logger.info("Welcome video started playing.");

            Button adminLoginButton = new Button("Admin Login");
            adminLoginButton.setStyle("-fx-font-size: 16px; -fx-pref-width: 150px;");
            adminLoginButton.setOnAction(e -> {
                try {
                    // Load logIn.fxml for Admin Login
                    Scene loginScene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/logIn.fxml")), 720, 500);
                    primaryStage.setScene(loginScene);
                    mediaPlayer.stop(); // Stop the video when navigating away
                    logger.info("Navigated to Admin Login page.");
                } catch (Exception ex) {
                    logger.severe("Error loading logIn.fxml: " + ex.getMessage());
                }
            });

            Button guestKioskButton = new Button("Guest Kiosk");
            guestKioskButton.setStyle("-fx-font-size: 16px; -fx-pref-width: 150px;");
            guestKioskButton.setOnAction(e -> {
                try {
                    // Load BookingGuest.fxml for Guest Kiosk
                    Scene guestScene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/reservationsystem/BookingGuest.fxml")), 720, 500);
                    primaryStage.setScene(guestScene);
                    mediaPlayer.stop(); // Stop the video when navigating away
                    logger.info("Navigated to Guest Kiosk page.");
                } catch (Exception ex) {
                    logger.severe("Error loading BookingGuest.fxml: " + ex.getMessage());
                }
            });

            VBox layout = new VBox(20, mediaView, welcomeLabel, adminLoginButton, guestKioskButton);
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(20));

            Scene scene = new Scene(layout, 800, 600);
            primaryStage.setTitle("Hotel Reservation System");
            primaryStage.setScene(scene);
            primaryStage.show();

            logger.info("Hotel Reservation App started successfully.");
        } catch (Exception e) {
            logger.severe("Error starting Hotel Reservation App: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
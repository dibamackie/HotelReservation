package com.example.reservationsystem.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBConnection {

    private static final Logger logger = HotelLogger.getLogger();
    private static Connection connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_reservation_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Caprice1984*"; // Update if your password is different

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                logger.info("Database connection established successfully.");
            } catch (ClassNotFoundException e) {
                logger.severe("MySQL JDBC Driver not found: " + e.getMessage());
                throw new SQLException("Failed to load JDBC driver.", e);
            } catch (SQLException e) {
                logger.severe("Failed to connect to database: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed successfully.");
            } catch (SQLException e) {
                logger.severe("Error closing database connection: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}
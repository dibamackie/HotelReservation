package com.example.reservationsystem.Utility;

import java.util.logging.*;
import java.io.IOException;

public class HotelLogger {

    private static final Logger logger = Logger.getLogger("HotelReservationSystem");
    static {
        try {
            // Configure file handler with rotation: 1MB limit, 10 files
            FileHandler fileHandler = new FileHandler("system_logs.%g.log", 1_000_000, 10, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.info("HotelLogger initialized with file rotation (1MB limit, 10 files).");
        } catch (Exception e) {
            logger.severe("Failed to initialize HotelLogger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
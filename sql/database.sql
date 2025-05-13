-- Create the database
CREATE DATABASE IF NOT EXISTS hotel_reservation_db;
USE hotel_reservation_db;

-- Table: Guest
-- Stores guest information, including feedback (which can be updated after check-out)
CREATE TABLE Guest (
    GuestID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL,
    Email VARCHAR(100),
    Address VARCHAR(255),
    Feedback TEXT,
    UNIQUE (PhoneNumber),
    INDEX idx_phone (PhoneNumber)
);

-- Table: Room
-- Stores room details, including type, number of beds, price, and availability status
CREATE TABLE Room (
    RoomID INT PRIMARY KEY AUTO_INCREMENT,
    RoomType VARCHAR(20) NOT NULL,
    NumberOfBeds INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Status VARCHAR(20) NOT NULL DEFAULT 'Available',
    CONSTRAINT chk_room_type CHECK (RoomType IN ('SINGLE', 'DOUBLE', 'DELUX', 'PENTHOUSE')),
    CONSTRAINT chk_status CHECK (Status IN ('Available', 'Booked'))
);

-- Table: Reservation
-- Stores reservation details, linking guests to rooms
CREATE TABLE Reservation (
    ReservationID INT PRIMARY KEY AUTO_INCREMENT,
    GuestID INT NOT NULL,
    RoomID INT NOT NULL,
    CheckInDate DATE NOT NULL,
    CheckOutDate DATE NOT NULL,
    NumberOfGuests INT NOT NULL,
    Status VARCHAR(20) NOT NULL DEFAULT 'Pending',
    FOREIGN KEY (GuestID) REFERENCES Guest(GuestID) ON DELETE CASCADE,
    FOREIGN KEY (RoomID) REFERENCES Room(RoomID) ON DELETE RESTRICT,
    CONSTRAINT chk_dates CHECK (CheckOutDate > CheckInDate),
    CONSTRAINT chk_guests CHECK (NumberOfGuests > 0),
    CONSTRAINT chk_res_status CHECK (Status IN ('Pending', 'Confirmed', 'Cancelled', 'Checked-Out'))
);

-- Table: Billing
-- Stores billing information for each reservation
CREATE TABLE Billing (
    BillID INT PRIMARY KEY AUTO_INCREMENT,
    ReservationID INT NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    Tax DECIMAL(10, 2) NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    Discount DECIMAL(5, 2) DEFAULT 0.0,
    FOREIGN KEY (ReservationID) REFERENCES Reservation(ReservationID) ON DELETE CASCADE,
    CONSTRAINT chk_amount CHECK (Amount >= 0),
    CONSTRAINT chk_tax CHECK (Tax >= 0),
    CONSTRAINT chk_total CHECK (TotalAmount >= 0),
    CONSTRAINT chk_discount CHECK (Discount >= 0 AND Discount <= 100)
);

-- Table: Admin
-- Stores admin credentials (minimum two admin accounts required)
CREATE TABLE Admin (
    AdminID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(50) NOT NULL
);

-- Table: Feedback
-- Stores guest feedback after check-out
CREATE TABLE Feedback (
    FeedbackID INT PRIMARY KEY AUTO_INCREMENT,
    GuestID INT NOT NULL,
    ReservationID INT NOT NULL,
    Comments TEXT,
    Rating INT NOT NULL,
    FOREIGN KEY (GuestID) REFERENCES Guest(GuestID) ON DELETE CASCADE,
    FOREIGN KEY (ReservationID) REFERENCES Reservation(ReservationID) ON DELETE CASCADE,
    CONSTRAINT chk_rating CHECK (Rating BETWEEN 1 AND 5)
);

-- Table: Kiosk
-- Stores kiosk information (for contactless booking)
CREATE TABLE Kiosk (
    KioskID INT PRIMARY KEY AUTO_INCREMENT,
    Location VARCHAR(100) NOT NULL
);

-- Insert initial data: Admin accounts (minimum two required)
INSERT INTO Admin (Username, Password) VALUES ('admin1', 'pass1');
INSERT INTO Admin (Username, Password) VALUES ('admin2', 'pass2');

-- Insert initial data: Sample rooms (matching the rooms in roomsAdmin.fxml and roomsGuest.fxml)
INSERT INTO Room (RoomID, RoomType, NumberOfBeds, Price, Status) VALUES
(101, 'SINGLE', 1, 100.00, 'Available'),
(102, 'DOUBLE', 2, 150.00, 'Available'),
(201, 'DELUX', 1, 200.00, 'Available'),
(202, 'SINGLE', 1, 100.00, 'Available'),
(301, 'DOUBLE', 2, 150.00, 'Available'),
(302, 'PENTHOUSE', 1, 300.00, 'Available'),
(401, 'SINGLE', 1, 100.00, 'Available'),
(402, 'DELUX', 1, 200.00, 'Available'),
(501, 'PENTHOUSE', 1, 300.00, 'Available'),
(502, 'DOUBLE', 2, 150.00, 'Available');

-- Insert initial data: Sample kiosks (two kiosks as per project requirement)
INSERT INTO Kiosk (KioskID, Location) VALUES (1, 'Lobby 1');
INSERT INTO Kiosk (KioskID, Location) VALUES (2, 'Lobby 2');
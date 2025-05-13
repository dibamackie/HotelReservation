package com.example.reservationsystem.model;

public class Feedback {
    private int feedbackID;
    private int guestID;
    private int reservationID;
    private String comments;
    private int rating;

    // Constructor
    public Feedback(int feedbackID, int guestID, int reservationID, String comments, int rating) {
        this.feedbackID = feedbackID;
        this.guestID = guestID;
        this.reservationID = reservationID;
        this.comments = comments;
        this.rating = rating;
    }

    // Getters and Setters
    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getGuestID() {
        return guestID;
    }

    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Methods
    public void submitFeedback() {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        // Placeholder: Save feedback to database
    }

    public String getFeedbackDetails() {
        return "Feedback ID: " + feedbackID + ", Guest ID: " + guestID +
                ", Reservation ID: " + reservationID + ", Rating: " + rating +
                ", Comments: " + (comments != null ? comments : "None");
    }
}
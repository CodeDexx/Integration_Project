package com.example.integration_project.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Emmanuelle
 * This class represent a Ticket generated when a client books a Show.
 * This Ticket generated will be shown in a popup view when the client clicks on the book button.
 */
public class Ticket {
    private final String aTicketID;
    private final String aMovieName;
    private final String aShowtime;
    private final LocalDateTime aPurchaseDateTime;

    /**
     * Creates a ticket with the necessary information
     * @param pTicketID The ticket unique identifier
     * @param pMovieName The name of the movie
     * @param pShowTime The selected showtime
     * @param pPurchaseDateTime The purchase date and time
     */

    public Ticket(String pTicketID, String pMovieName, String pShowTime, LocalDateTime pPurchaseDateTime) {
        aTicketID = pTicketID;
        aMovieName = pMovieName;
        aShowtime = pShowTime;
        aPurchaseDateTime = pPurchaseDateTime;
    }

    public String getaTicketID() {
        return aTicketID;
    }
    public String getaMovieName() {
        return aMovieName;
    }
    public String getaShowtime() {
        return aShowtime;
    }
    public LocalDateTime getaPurchaseDateTime() {
        return aPurchaseDateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Ticket Confirmation\n" +
                "Ticket ID: " + aTicketID + "\n" +
                "Movie: " + aMovieName + "\n" +
                "Showtime: " + aShowtime + "\n" +
                "Purchased On: " + aPurchaseDateTime.format(fmt) + "\n";
    }
}

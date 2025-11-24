package com.example.integration_project.Model;

/**
 * @author Emmanuelle
 * This class represent a Ticket generatedd when a client books a Show.
 * This Ticket generated will be shown in a popup view when the ckient clicks on the book button.
 */
public class Ticket {
    private final String aTicketID;

    /**
     * Creates a ticket with the necessary information
     * @param pTicketId The ticket unique identifier
     */

    public Ticket(String pTicketId) {
        aTicketID = pTicketId;
    }

    public String getaTicketID() {
        return aTicketID;
    }

    @Override
    public String toString() {
        return "Ticket Confirmation\n" + "Ticket ID: " + aTicketID;
    }
}

package com.example.integration_project.Model;

/**
 * @author Emmanuelle
 * This class represent a Ticket generated when a client books a Show.
 * This Ticket generated will be shown in a popup view when the ckient clicks on the book button.
 */
public class Ticket {
    private final String aTicketID;

    /**
     * Creates a ticket with the necessary information
     * @param pTicketID The ticket unique identifier
     */

    public Ticket(String pTicketID) {
        aTicketID = pTicketID;
    }

    public String getaTicketID() {
        return aTicketID;
    }

    @Override
    public String toString() {
        return "Ticket Confirmation\n" + "Ticket ID: " + aTicketID;
    }
}

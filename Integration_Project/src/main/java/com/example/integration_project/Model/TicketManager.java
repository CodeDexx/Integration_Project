package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * <h1>TicketManager</h1>
 * The TicketManager class implements the **Singleton pattern**
 * to manage a central list of all movie tickets in the system.
 * It provides methods for accessing and modifying the ticket list,
 * as well as static utility methods for counting tickets based on criteria.
 *
 * @author Dieudonné
 * @version 1.0
 * @see Ticket
 */
public class TicketManager {

    /**
     * The single instance of the TicketManager class (Singleton implementation).
     */
    private static TicketManager aInstance;

    /**
     * An observable list containing all Ticket objects currently in the system.
     */
    private final ObservableList<Ticket> aTickets;

    /**
     * Private constructor to enforce the Singleton pattern.
     * Initializes the observable list for tickets.
     */
    private TicketManager() {
        aTickets = FXCollections.observableArrayList();
    }

    /**
     * Returns the single instance of the TicketManager class.
     * If the instance does not exist, it is created.
     *
     * @return The sole instance of the TicketManager.
     */
    public static TicketManager getInstance() {
        if (aInstance == null) {
            aInstance = new TicketManager();
        }
        return aInstance;
    }

    /**
     * Returns the observable list of all tickets managed by this instance.
     *
     * @return An {@code ObservableList<Ticket>} of all tickets.
     */
    public ObservableList<Ticket> getTickets() {
        return aTickets;
    }

    /**
     * Adds a new ticket to the managed list.
     *
     * @param ticket The {@link Ticket} object to be added.
     */
    public void addTicket(Ticket ticket) {
        aTickets.add(ticket);
    }

    /**
     * Counts the number of tickets for a given movie name.
     *
     * @param tickets The list of all tickets to search within.
     * @param movieName The name of the movie to filter by (e.g., "Inception").
     * @return The number of tickets sold for that movie.
     */
    public static long countByMovie(List<Ticket> tickets, String movieName) {
        long count = 0;

        for (Ticket t : tickets) {
            // Note: Assuming Ticket has a public getName() method that returns the movie name
            if (t.getName().equals(movieName)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of tickets for a given showtime identifier.
     *
     * @param tickets The list of all tickets to search within.
     * @param showtime The identifier or time string of the showtime to filter by.
     * @return The number of tickets sold for that specific showtime.
     */
    public static long countByShowtime(List<Ticket> tickets, String showtime) {
        long count = 0;

        for (Ticket t : tickets) {
            // Note: Assuming Ticket has a public getTime() method that returns the showtime identifier
            if (t.getTime().equals(showtime)) {
                count++;
            }
        }
        return count;
    }
}

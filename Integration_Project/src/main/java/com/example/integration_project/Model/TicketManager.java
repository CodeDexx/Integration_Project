package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TicketManager {

    private static TicketManager aInstance;

    private final ObservableList<Ticket> aTickets;

    private TicketManager() {
        aTickets = FXCollections.observableArrayList();
    }

    public static TicketManager getInstance() {
        if (aInstance == null) {
            aInstance = new TicketManager();
        }
        return aInstance;
    }

    public ObservableList<Ticket> getTickets() {
        return aTickets;
    }

    public void addTicket(Ticket ticket) {
        aTickets.add(ticket);
    }

    /**
     * Counts the number of tickets for a given movie
     * @param tickets the list of all tickets
     * @param movieName the movie to filter
     * @return number of tickets sold for that movie
     */

    public static long countByMovie(List<Ticket> tickets, String movieName) {
        long count = 0;

        for (Ticket t : tickets) {
            if (t.getName().equals(movieName)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of tickets for a given showtime
     * @param tickets the list of all tickets
     * @param showtime the showtime to filter
     * @return number of tickets sold for that showtime
     */

    public static long countByShowtime(List<Ticket> tickets, String showtime) {
        long count = 0;

        for (Ticket t : tickets) {
            if (t.getTime().equals(showtime)) {
                count++;
            }
        }
        return count;
    }
}

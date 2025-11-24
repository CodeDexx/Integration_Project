package com.example.integration_project.Model;

public class Ticket {
    private final String aTicketID;
    private final String aMovieName;
    private final String aShowtime;


    public Ticket(String pTicketId, String pMovieName, String pShowtime) {
        aTicketID = pTicketId;
        aMovieName = pMovieName;
        aShowtime = pShowtime;
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

    public String toString() {
        return "Ticket Confirmation\n" + "Ticket ID: " + aTicketID + "\n" +
                "Movie: " + aMovieName + "\n" + "Showtime: " + aShowtime + "\n";
    }
}

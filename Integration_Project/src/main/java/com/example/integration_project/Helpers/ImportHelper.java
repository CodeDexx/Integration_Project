package com.example.integration_project.Helpers;

import com.example.integration_project.Model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * The {@code ImportHelper} class loads demo/sample data for the
 * movie theater system, including clients, movies, Showroom, and showtime.
 *
 * <p>This helper keeps your application clean by centralizing all
 * sample initialization logic in one place.</p>
 */
public class ImportHelper {

    /**
     *
     * @return
     */
    public static void loadClients() {
        ClientManager clients= ClientManager.getInstance();
        clients.addClient(new Client("Rachel Booker", "rachel@yourcompany.com", "qwertyu"));
        clients.addClient(new Client("Laura Grey", "laura@yourcompany.com", "asdfgh"));
        clients.addClient(new Client("Craig Johnson", "craig@yourcompany.com", "zxcvbn"));
        clients.addClient(new Client("Mary Jenkins", "mary@yourcompany.com", "lkjhgfd"));
        clients.addClient(new Client("James Smith", "jamesSmith@gmail.com", "mnbvcx"));
        clients.addClient(new Client("Christopher Andersen", "christopherAnderson@gmail.com", "rghbnjk"));

    }

    public static void loadTickets() {
        TicketManager  ticketManagerInstance = TicketManager.getInstance();
        List<Showtime> Showtime = ShowtimeManager.getShowtime();

        ticketManagerInstance.addTicket(new Ticket("T002", Showtime.get(1)));
        ticketManagerInstance.addTicket(new Ticket("T003", Showtime.get(1)));
        ticketManagerInstance.addTicket(new Ticket("T004", Showtime.get(3)));
        ticketManagerInstance.addTicket(new Ticket("T005", Showtime.get(5)));
    }



    /**
     *
     * @return
     */
    public static void loadMovies() {
        MovieManager  movieManagerInstance = MovieManager.getMovieManagerInstance();


        movieManagerInstance.addMovie(new Movie("SpiderMan"));
        movieManagerInstance.addMovie(new Movie("The Matrix"));
        movieManagerInstance.addMovie(new Movie("Barbie"));
    }

    // -------------------------------------------------------------
    // Showroom
    // -------------------------------------------------------------
    public static void loadShowroom() {
        ShowroomManager  rooms = ShowroomManager.getShowroomManagerInstance();

        rooms.addShowroom(new Showroom(101, 15));
        rooms.addShowroom(new Showroom(102, 19));
        rooms.addShowroom(new Showroom(103, 20));
        rooms.addShowroom(new Showroom(104, 21));
        rooms.addShowroom(new Showroom(105, 22));
    }

    // -------------------------------------------------------------
    // SHOWTIME — rewritten for LocalDateTime compatibility
    // -------------------------------------------------------------
    public static void loadShowtime() {
        ShowtimeManager showtime = ShowtimeManager.getShowtimeManagerInstance();
        List<Movie> movies = MovieManager.getMovies();
        List<Showroom> rooms = ShowroomManager.getShowroom();

        // Lookup helpers
        Movie spiderman = findMovie(movies, "SpiderMan");
        Movie matrix = findMovie(movies, "The Matrix");
        Movie barbie = findMovie(movies, "Barbie");

        Showroom r101 = findRoom(rooms, 101);
        Showroom r102 = findRoom(rooms, 102);
        Showroom r103 = findRoom(rooms, 103);
        Showroom r104 = findRoom(rooms, 104);
        Showroom r105 = findRoom(rooms, 105);

        // Formatters for parsing
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", java.util.Locale.ENGLISH);

        // Helper lambda to build LocalDateTime
        BiFunction<String, String, LocalDateTime> buildDateTime = (date, time) -> {
            LocalDate d = LocalDate.parse(date, dateFormatter);
            LocalTime t = LocalTime.parse(time, timeFormatter);
            return LocalDateTime.of(d, t);
        };

        // Spider-Man Showtime
        showtime.addShowtime(new Showtime(spiderman, buildDateTime.apply("12/20/2025", "10:30 AM"), r101));
        showtime.addShowtime(new Showtime(spiderman, buildDateTime.apply("12/21/2025", "12:30 AM"), r101));
        showtime.addShowtime(new Showtime(spiderman, buildDateTime.apply("12/22/2025", "12:30 PM"), r101));
        showtime.addShowtime(new Showtime(spiderman, buildDateTime.apply("12/23/2025", "1:30 PM"), r101));

        // Matrix Showtime
        showtime.addShowtime(new Showtime(matrix, buildDateTime.apply("12/24/2025", "10:30 AM"), r102));
        showtime.addShowtime(new Showtime(matrix, buildDateTime.apply("12/24/2025", "12:30 AM"), r103));
        showtime.addShowtime(new Showtime(matrix, buildDateTime.apply("12/24/2025", "12:30 PM"), r104));

        // Barbie showtime
        showtime.addShowtime(new Showtime(barbie, buildDateTime.apply("12/25/2025", "1:30 PM"), r105));

    }

    // -------------------------------------------------------------
    // FINDERS
    // -------------------------------------------------------------
    private static Movie findMovie(List<Movie> list, String name) {
        
        for (Movie movie : list) {
            if (movie.getName().equals(name)) {
                return movie;
            }
        }
        return null;
    }
    
     private static Showroom findRoom(List<Showroom> list, int number) {
        for (Showroom room : list) {
            if (room.getRoomNumber() == number) {
                return room;
            }
    	}
    	return null;
    }   
}

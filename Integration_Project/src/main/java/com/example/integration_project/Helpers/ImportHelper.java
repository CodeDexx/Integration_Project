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
 * movie theater system, including clients, movies, showrooms, and showtime.
 *
 * <p>This helper keeps your application clean by centralizing all
 * sample initialization logic in one place.</p>
 */
public class ImportHelper {

    /**
     *
     * @return
     */
    public static List<Client> loadClients() {
        List<Client> clients = new ArrayList<>();

        clients.add(new Client("Rachel Booker", "rachel@yourcompany.com", "qwertyu"));
        clients.add(new Client("Laura Grey", "laura@yourcompany.com", "asdfgh"));
        clients.add(new Client("Craig Johnson", "craig@yourcompany.com", "zxcvbn"));
        clients.add(new Client("Mary Jenkins", "mary@yourcompany.com", "lkjhgfd"));
        clients.add(new Client("James Smith", "jamesSmith@gmail.com", "mnbvcx"));
        clients.add(new Client("Christopher Andersen", "christopherAnderson@gmail.com", "rghbnjk"));

        return clients;
    }

    public static List<Ticket> loadTickets(List<Showtimes> showtimes) {
        List<Ticket> tickets = new ArrayList<>();

        tickets.add(new Ticket("T002", showtimes.get(1)));
        tickets.add(new Ticket("T003", showtimes.get(1)));
        tickets.add(new Ticket("T004", showtimes.get(3)));
        tickets.add(new Ticket("T005", showtimes.get(5)));

        return tickets;
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
    // SHOWROOMS
    // -------------------------------------------------------------
    public static void loadShowrooms() {
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
    public static List<Showtime> loadShowtime(List<Movie> movies, List<Showroom> rooms) {
        ShowtimeManager showtime = ShowtimeManager.getShowtimeManagerInstance();

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
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        // Helper lambda to build LocalDateTime
        BiFunction<String, String, LocalDateTime> buildDateTime = (date, time) -> {
            LocalDate d = LocalDate.parse(date, dateFormatter);
            LocalTime t = LocalTime.parse(time, timeFormatter);
            return LocalDateTime.of(d, t);
        };

        // Spider-Man showtimes
        showtime.addShowtime(new Showtime(spiderman, buildDateTime.apply("12/20/2025", "10:30 AM"), r101));
        showtime.addShowtime(new Showtime(spiderman, buildDateTime.apply("12/21/2025", "12:30 AM"), r101));
        showtime.addShowtime(new Showtime(spiderman, buildDateTime.apply("12/22/2025", "12:30 PM"), r101));
        showtime.addShowtime(new Showtime(spiderman, buildDateTime.apply("12/23/2025", "1:30 PM"), r101));

        // Matrix showtimes
        showtime.addShowtime(new Showtime(matrix, buildDateTime.apply("12/24/2025", "10:30 AM"), r102));
        showtime.addShowtime(new Showtime(matrix, buildDateTime.apply("12/24/2025", "12:30 AM"), r103));
        showtime.addShowtime(new Showtime(matrix, buildDateTime.apply("12/24/2025", "12:30 PM"), r104));

        // Barbie showtime
        showtime.addShowtime(new Showtime(barbie, buildDateTime.apply("12/25/2025", "1:30 PM"), r105));

        return showtime.getShowtimes();
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

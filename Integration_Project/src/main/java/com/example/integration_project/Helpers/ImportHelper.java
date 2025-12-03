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
    public static List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie("SpiderMan"));
        movies.add(new Movie("The Matrix"));
        movies.add(new Movie("Barbie"));

        return movies;
    }

    /**
     *
     * @return
     */
    public static List<Showrooms> loadShowrooms() {
        List<Showrooms> rooms = new ArrayList<>();

        rooms.add(new Showrooms(101, 15));
        rooms.add(new Showrooms(102, 19));
        rooms.add(new Showrooms(103, 20));
        rooms.add(new Showrooms(104, 21));
        rooms.add(new Showrooms(105, 22));

        return rooms;
    }

    /**
     *
     * @param movies
     * @param rooms
     * @return
     */
    public static List<Showtimes> loadShowtime(List<Movie> movies, List<Showrooms> rooms) {
        List<Showtimes> showtime = new ArrayList<>();

        // Lookup helpers
        Movie spiderman = findMovie(movies, "SpiderMan");
        Movie matrix = findMovie(movies, "The Matrix");
        Movie barbie = findMovie(movies, "Barbie");

        Showrooms r101 = findRoom(rooms, 101);
        Showrooms r102 = findRoom(rooms, 102);
        Showrooms r103 = findRoom(rooms, 103);
        Showrooms r104 = findRoom(rooms, 104);
        Showrooms r105 = findRoom(rooms, 105);

        // Formatters for parsing
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        // Helper lambda to build LocalDateTime
        BiFunction<String, String, LocalDateTime> buildDateTime = (date, time) -> {
            LocalDate d = LocalDate.parse(date, dateFormatter);
            LocalTime t = LocalTime.parse(time, timeFormatter);
            return LocalDateTime.of(d, t);
        };

        // Spider-Man showtime
        showtime.add(new Showtimes(spiderman, buildDateTime.apply("11/20/2025", "10:30 AM"), r101));
        showtime.add(new Showtimes(spiderman, buildDateTime.apply("11/21/2025", "11:30 AM"), r101));
        showtime.add(new Showtimes(spiderman, buildDateTime.apply("11/22/2025", "12:30 PM"), r101));
        showtime.add(new Showtimes(spiderman, buildDateTime.apply("11/23/2025", "1:30 PM"), r101));

        // Matrix showtime
        showtime.add(new Showtimes(matrix, buildDateTime.apply("11/24/2025", "10:30 AM"), r102));
        showtime.add(new Showtimes(matrix, buildDateTime.apply("11/24/2025", "11:30 AM"), r103));
        showtime.add(new Showtimes(matrix, buildDateTime.apply("11/24/2025", "12:30 PM"), r104));

        // Barbie showtime
        showtime.add(new Showtimes(barbie, buildDateTime.apply("11/25/2025", "1:30 PM"), r105));

        return showtime;
    }

    /**
     *
     * @param list
     * @param name
     * @return
     */
    private static Movie findMovie(List<Movie> list, String name) {
        return list.stream()
                .filter(movie -> movie.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     *
     * @param list
     * @param number
     * @return
     */
    private static Showrooms findRoom(List<Showrooms> list, int number) {
        return list.stream()
                .filter(room -> room.getRoomNumber() == number)
                .findFirst()
                .orElse(null);
    }
}

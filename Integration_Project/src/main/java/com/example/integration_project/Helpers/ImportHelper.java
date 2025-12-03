package com.example.integration_project.Helpers;

import com.example.integration_project.Model.Movie;
import com.example.integration_project.Model.Showroom;
import com.example.integration_project.Model.Showtime;
import com.example.integration_project.Model.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * The {@code ImportHelper} class loads demo/sample data for the
 * movie theater system, including clients, movies, showrooms, and showtimes.
 *
 * <p>This helper keeps your application clean by centralizing all
 * sample initialization logic in one place.</p>
 */
public class ImportHelper {

    // -------------------------------------------------------------
    // CLIENTS
    // -------------------------------------------------------------
    public static List<Client> loadClients() {
        List<Client> clients = new ArrayList<>();

        clients.add(new Client("Rachel Booker", "rachel@yourcompany.com", "qwertyu"));
        clients.add(new Client("Laura Grey", "laura@yourcompany.com", "asdfgh"));
        clients.add(new Client("Craig Johnson", "craig@yourcompany.com", "zxcvbn"));
        clients.add(new Client("Mary Jenkins", "mary@yourcompany.com", "lkjhgfd"));
        clients.add(new Client("James Smith", "JamesSmith@gmail.com", "mnbvcx"));
        clients.add(new Client("Christopher Andersen", "ChristopherAnderson@gmail.com", "rghbnjk"));

        return clients;
    }

    // -------------------------------------------------------------
    // MOVIES
    // -------------------------------------------------------------
    public static List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie("SpiderMan"));
        movies.add(new Movie("The Matrix"));
        movies.add(new Movie("Barbie"));

        return movies;
    }

    // -------------------------------------------------------------
    // SHOWROOMS
    // -------------------------------------------------------------
    public static List<Showroom> loadShowrooms() {
        List<Showroom> rooms = new ArrayList<>();

        rooms.add(new Showroom(101, 15));
        rooms.add(new Showroom(102, 19));
        rooms.add(new Showroom(103, 20));
        rooms.add(new Showroom(104, 21));
        rooms.add(new Showroom(105, 22));

        return rooms;
    }

    // -------------------------------------------------------------
    // SHOWTIME — rewritten for LocalDateTime compatibility
    // -------------------------------------------------------------
    public static List<Showtime> loadShowtime(List<Movie> movies, List<Showroom> rooms) {
        List<Showtime> showtime = new ArrayList<>();

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
        showtime.add(new Showtime(spiderman, buildDateTime.apply("11/20/2025", "10:30 AM"), r101));
        showtime.add(new Showtime(spiderman, buildDateTime.apply("11/21/2025", "11:30 AM"), r101));
        showtime.add(new Showtime(spiderman, buildDateTime.apply("11/22/2025", "12:30 PM"), r101));
        showtime.add(new Showtime(spiderman, buildDateTime.apply("11/23/2025", "1:30 PM"), r101));

        // Matrix showtimes
        showtime.add(new Showtime(matrix, buildDateTime.apply("11/24/2025", "10:30 AM"), r102));
        showtime.add(new Showtime(matrix, buildDateTime.apply("11/24/2025", "11:30 AM"), r103));
        showtime.add(new Showtime(matrix, buildDateTime.apply("11/24/2025", "12:30 PM"), r104));

        // Barbie showtime
        showtime.add(new Showtime(barbie, buildDateTime.apply("11/25/2025", "1:30 PM"), r105));

        return showtime;
    }

    // -------------------------------------------------------------
    // FINDERS
    // -------------------------------------------------------------
    private static Movie findMovie(List<Movie> list, String name) {
        return list.stream()
                .filter(movie -> movie.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private static Showroom findRoom(List<Showroom> list, int number) {
        return list.stream()
                .filter(room -> room.getRoomNumber() == number)
                .findFirst()
                .orElse(null);
    }
}

# Grand View Movie Theatre System Design and Plan

## User Hierarchy
- **Admin**: Full access to manage movies, showtimes, and user accounts.
- **Manager**: Can manage showtimes and view reports.
- **Customer**: Can browse movies, book tickets, and manage bookings.

## Movie and Showtime Classes
- **Movie Class**: Represents a movie with attributes like title, genre, duration, director, and cast.
  - Methods: `getMovieDetails()`, `updateMovie()`, `deleteMovie()`

- **Showtime Class**: Represents showtimes for movies with attributes like movieID, showtime, and available seats.
  - Methods: `getAvailableShowtimes()`, `bookTicket()`, `cancelTicket()`

## Ticket System
- **Ticket Class**: Represents a ticket with attributes like ticketID, customerID, showtimeID, and seat number.
  - Methods: `generateTicket()`, `modifyTicket()`, `refundTicket()`

## Manager and Controller Classes
- **Manager Class**: Interacts with Movie and Showtime classes to facilitate showtime management.
  - Methods: `addShowtime()`, `removeShowtime()`, `viewReports()`

- **Controller Class**: Manages the flow of data between the UI and the model (movie and ticket classes).
  - Methods: `handleUserRequest()`, `updateView()`

## UI Classes
- **UI Class**: Represents the user interface, displaying options to users and capturing input.
  - Methods: `displayMovies()`, `getUserInput()`, `showBookingConfirmation()`

## Exception Handling
- **Custom Exceptions**: Define exceptions for handling specific errors such as `MovieNotFoundException`, `ShowtimeConflictException`, `TicketNotAvailableException`.

## Project Phases
1. **Phase 1**: Requirements gathering and analysis.
2. **Phase 2**: System design and architecture planning.
3. **Phase 3**: Implementation of classes and methods.
4. **Phase 4**: Testing and validation.
5. **Phase 5**: Deployment and user training.

## Feature Checklists
- [ ] User registration and login.
- [ ] Movie browsing functionality.
- [ ] Showtime management.
- [ ] Ticket booking and cancellation.
- [ ] Reports generation for managers.

## OOP Concepts Application
- Encapsulation: Classes will encapsulate their data and methods.
- Inheritance: Manager and Admin classes can inherit from a base User class.
- Polymorphism: Methods can be overridden in derived classes for specific behaviors.

## Coding Standards
- Use meaningful names for classes and methods.
- Maintain consistent indentation and spacing.
- Document code with comments and docstrings for clarity.

---

*Document created on 2025-11-20 14:21:21 by CodeDexx*
package learn.ui;

import learn.models.Guest;
import learn.models.Host;
import learn.models.Reservation;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class View {

    private ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    // Get user input
    public MainMenuOptions selectMainMenuOptions() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOptions option : MainMenuOptions.values()) {
            io.printf("%s: %s%n", option.getValue(), option.getMessage());
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }
        String prompt = String.format("Select an option [%s-%s]: ", min, max);
        return MainMenuOptions.fromValue(io.readInt(prompt, min, max));
    }

    public String getEmail(String prompt) {
        return io.readRequiredString(prompt);
    }

    public LocalDate getDate(String prompt) {
        return io.readLocalDate(prompt);
    }

    public Reservation chooseReservation(List<Reservation> reservations) {
        while (true) {
            int id = io.readInt("Reservation ID: ");
            for (Reservation r : reservations) {
                if (r.getId() == id) {
                    return r;
                }
            }
            io.println("That ID is not available");
        }
    }



    //Create and updates
    public Reservation createReservation(Host host, Guest guest, LocalDate start, LocalDate end) {
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setStartDate(start);
        reservation.setEndDate(end);
        reservation.setTotal(reservation.calcTotal());
        displaySummary(reservation);
        if(io.readBoolean("Is this okay? [y/n]: ")) {
            return reservation;
        }
        return null;
    }

    public Reservation updateReservation(Reservation reservation, LocalDate start, LocalDate end) {
        reservation.setStartDate(start);
        reservation.setEndDate(end);
        reservation.setTotal(reservation.calcTotal());
        displaySummary(reservation);
        if(io.readBoolean("Is this okay? [y/n]: ")) {
            return reservation;
        }
        return null;
    }













    //display only
    public void displayHeader(String prompt) {
        io.println("");
        io.println(prompt);
        io.println("=".repeat(prompt.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred");
        io.println(ex.getMessage());
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public void displayReservations(List<Reservation> reservations, Host host) {
        if (reservations == null || reservations.isEmpty()) {
            displayHeader(host.getLastName() + ": " + host.getCity() +
                    ", " + host.getState());
            io.println("No Reservations Found");
            return;
        }
        String format = "%-3s   %-27s %-13s %-13s %-20s%n";
        io.printf(format, "ID.", "Start Date - End Date", "First Name", "Last Name", "Email");
        List<Reservation> sorted = reservations.stream()
                .sorted(Comparator.comparing(Reservation::getStartDate))
                .collect(Collectors.toList());
        for (Reservation r : sorted) {
            io.printf(format, r.getId() + ".", r.getStartDate() + " - " + r.getEndDate(),
                    r.getGuest().getFirstName(), r.getGuest().getLastName(), r.getGuest().getEmail());
        }
    }

    public List<Reservation> displayReservations(List<Reservation> reservations, Host host, Guest guest) {
        if (reservations == null || reservations.isEmpty()) {
            displayHeader(host.getLastName() + ": " + host.getCity() +
                    ", " + host.getState());
            io.println("No Reservations Found");
            return reservations;
        }
        List<Reservation> guestReservations = reservations.stream()
                .filter(reservation -> reservation.getGuest().equals(guest))
                .collect(Collectors.toList());
        String format = "%-3s   %-27s %-13s %-13s %-20s%n";
        displayHeader(host.getLastName() + ": " + host.getCity() +
                ", " + host.getState());
        io.printf(format, "ID.", "Start Date - End Date", "First Name", "Last Name", "Email");
        List<Reservation> sorted = guestReservations.stream()
                .sorted(Comparator.comparing(Reservation::getStartDate))
                .collect(Collectors.toList());
        for (Reservation r : sorted) {
            io.printf(format, r.getId() + ".", r.getStartDate() + " - " + r.getEndDate(),
                    r.getGuest().getFirstName(), r.getGuest().getLastName(), r.getGuest().getEmail());
        }
        return sorted;
    }

    public void displaySummary(Reservation reservation) {
        displayHeader("Summary");
        io.println("Start: " + reservation.getStartDate());
        io.println("End: " + reservation.getEndDate());
        io.println("Total: $" + reservation.getTotal());
    }

}

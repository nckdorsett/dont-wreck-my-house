package learn.ui;

import learn.domain.Result;
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

    public LocalDate getDate(String prompt, LocalDate date) {
        return io.readLocalDate(prompt, date);
    }

    public LocalDate getRequiredDate(String prompt) {
        return io.readRequiredLocalDate(prompt);
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
    public Reservation createReservation(Host host, Guest guest, List<Reservation> reservations) {
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setHost(host);
        do {
            reservation.setStartDate(getRequiredDate("Start Date: "));
            reservation.setEndDate(getRequiredDate("End Date: "));
        } while(!dateAvailabilityAdd(reservation, reservations));
        reservation.setTotal(reservation.calcTotal());
        displaySummary(reservation);
        if(io.readBoolean("Is this okay? [y/n]: ")) {
            return reservation;
        }
        return null;
    }

    public Reservation updateReservation(Reservation reservation, List<Reservation> reservations) {
        Reservation updatedReservation = reservation;
        do {
            updatedReservation.setStartDate(getDate("Start Date: ", updatedReservation.getStartDate()));
            updatedReservation.setEndDate(getDate("End Date: ", updatedReservation.getEndDate()));
        } while (!dateAvailabilityUpdate(updatedReservation, reservations));
        updatedReservation.setTotal(reservation.calcTotal());
        displaySummary(reservation);
        if(io.readBoolean("Is this okay? [y/n]: ")) {
            return updatedReservation;
        }
        return null;
    }

    public boolean confirm(String message) {
        return io.readBoolean(message);
    }

    public Guest createGuest() {
        Guest guest = new Guest();
        guest.setFirstName(io.readRequiredName("First Name: "));
        guest.setLastName(io.readRequiredName("Last Name: "));
        guest.setEmail(io.readRequiredEmail("Email: "));
        guest.setPhoneNumber(io.readRequiredPhoneNumber("Phone Number (###) #######: "));
        guest.setState(io.readRequiredState("State Abbreviation: "));
        return guest;
    }

    public Guest updateGuest(Guest guest) {
        io.println("Press [Enter] to keep the same value");
        io.println("");

        String firstName = io.readName("First Name (" + guest.getFirstName() + "): ");
        if (firstName.trim().length() > 0) {
            guest.setFirstName(firstName);
        }
        String lastName = io.readName("Last Name (" + guest.getLastName() + "): ");
        if (lastName.trim().length() > 0) {
            guest.setLastName(lastName);
        }
        String email = io.readEmail("Email (" + guest.getEmail() + "): ");
        if (email.trim().length() > 0) {
            guest.setEmail(email);
        }
        String phoneNumber = io.readPhoneNumber("Phone Number (" + guest.getPhoneNumber() + "): ");
        if (phoneNumber.trim().length() > 0) {
            guest.setPhoneNumber(phoneNumber);
        }
        String state = io.readState("State (" + guest.getState() + "): ");
        if (state.trim().length() > 0) {
            guest.setState(state);
        }
        return guest;
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

    public List<Reservation> displayAndReturnReservations(List<Reservation> reservations, Host host, Guest guest) {
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





    private boolean dateAvailabilityAdd(Reservation reservation, List<Reservation> reservations) {

        if (reservation.getStartDate() == null || reservation.getEndDate() == null) {
            return false;
        }

        if (reservation.getStartDate().isBefore(LocalDate.now())
                || reservation.getEndDate().isBefore(LocalDate.now())) {
            io.println("Date is not in the future");
            return false;
        }
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            io.println("Start Date is after End Date");
            return false;
        }

        if (reservation.getStartDate().isEqual(reservation.getEndDate())) {
            io.println("Start Date cannot be the same as End Date");
            return false;
        }

        int scheduleCounter = 0;
        for (Reservation r : reservations) {
            if (r.getEndDate().isBefore(reservation.getStartDate())
                    || r.getEndDate().equals(reservation.getStartDate())
                    || r.getStartDate().equals(reservation.getEndDate())
                    || r.getStartDate().isAfter(reservation.getEndDate())) {
                scheduleCounter++;
            }
        }
        if (scheduleCounter == reservations.size()) {
            return true;
        }
        io.println("Those dates are not available.");
        return false;
    }

    private boolean dateAvailabilityUpdate(Reservation reservation, List<Reservation> reservations) {
        if (reservation.getStartDate() == null || reservation.getEndDate() == null) {
            return false;
        }

        if (reservation.getStartDate().isBefore(LocalDate.now())
                || reservation.getEndDate().isBefore(LocalDate.now())) {
            io.println("Date is not in the future");
            return false;
        }
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            io.println("Start Date is after End Date");
            return false;
        }

        if (reservation.getStartDate().isEqual(reservation.getEndDate())) {
            io.println("Start Date cannot be the same as End Date");
            return false;
        }

        int scheduleCounter = 0;
        for (Reservation r : reservations) {
            if (r.getId() == reservation.getId()
                    ||r.getEndDate().isBefore(reservation.getStartDate())
                    || r.getEndDate().equals(reservation.getStartDate())
                    || r.getStartDate().equals(reservation.getEndDate())
                    || r.getStartDate().isAfter(reservation.getEndDate())) {
                scheduleCounter++;
            }
        }
        if (scheduleCounter == reservations.size()) {
            return true;
        }
        io.println("Those dates are not available.");
        return false;
    }

}

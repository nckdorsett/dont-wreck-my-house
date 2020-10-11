package learn.ui;

import learn.data.DataException;
import learn.domain.GuestService;
import learn.domain.HostService;
import learn.domain.ReservationService;
import learn.domain.Result;
import learn.models.Guest;
import learn.models.Host;
import learn.models.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller {

    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService,
                      ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        try {
            runMenuLoop();
        } catch (DataException ex) {
            view.displayHeader("Critical Error Shutting Down");
        }
    }

    public void runMenuLoop() throws DataException {
        MainMenuOptions option;
        do {
            option = view.selectMainMenuOptions();
            switch (option) {
                case VIEW_RESERVATIONS_BY_HOST:
                    viewReservationsByHost();
                    break;
                case ADD_RESERVATION:
                    addReservation();
                    break;
                case UPDATE_RESERVATION:
                    updateReservation();
                    break;
                case DELETE_RESERVATION:
                    deleteReservation();
                    break;
                case ADD_GUEST:
                    addGuest();
                    break;
                case UPDATE_GUEST:
                    updateGuest();
                    break;
                case DELETE_GUEST:
                    deleteGuest();
                    break;
                case ADD_HOST:
                    addHost();
                    break;
                case UPDATE_HOST:
                    updateHost();
                    break;
                case DELETE_HOST:
                    deleteHost();
                    break;
            }
        } while (option != MainMenuOptions.EXIT);
        view.displayHeader("Goodbye");
    }

    private void viewReservationsByHost() {
        view.displayHeader("View Reservations By Host");
        Host host = validateHost();
        List<Reservation> reservations = reservationService.findByHost(host);
        view.displayReservations(reservations, host);
    }

    private void addReservation() throws DataException {
        view.displayHeader("Add a Reservation");
        Guest guest = validateGuest();
        Host host = validateHost();
        List<Reservation> reservations = reservationService.findByHost(host);
        view.displayReservations(reservations, host);
        Reservation newReservation = view.createReservation(host, guest, reservations);
        if (newReservation == null) {
            view.displayHeader("Reservation addition canceled.");
            return;
        }
        Result<Reservation> result = reservationService.add(newReservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String success = String.format("Reservation %s created.", result.getPayload().getId());
            view.displayStatus(result.isSuccess(), success);
        }

    }

    private void updateReservation() throws DataException {
        view.displayHeader("Add a Reservation");
        Guest guest = validateGuest();
        Host host = validateHost();
        List<Reservation> reservations = reservationService.findByHost(host);
        List<Reservation> guestReservations = view.displayAndReturnReservations(reservations, host, guest);
        Reservation updateChoice = view.chooseReservation(guestReservations);
        Reservation newReservation = view.updateReservation(updateChoice, reservations);
        if (newReservation == null) {
            view.displayHeader("Reservation update canceled.");
            return;
        }
        Result<Reservation> result = reservationService.update(newReservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String success = String.format("Reservation %s updated.", result.getPayload().getId());
            view.displayStatus(result.isSuccess(), success);
        }
    }

    private void deleteReservation() throws DataException {
        view.displayHeader("Add a Reservation");
        Guest guest = validateGuest();
        Host host = validateHost();
        List<Reservation> reservations = reservationService.findByHost(host);
        List<Reservation> guestReservations = view.displayAndReturnReservations(reservations, host, guest);
        Reservation deleteChoice = view.chooseReservation(guestReservations);
        if (!view.confirm("Is this okay to remove? [y/n]: ")) {
            view.displayHeader("Reservation cancellation canceled.");
            return;
        }
        Result<Reservation> result = reservationService.delete(deleteChoice);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String success = String.format("Reservation %s canceled.", result.getPayload().getId());
            view.displayStatus(result.isSuccess(), success);
        }
    }

    private void addGuest() throws DataException {
        view.displayHeader("Add a Guest");
        Guest guest = view.createGuest();
        if (guest == null) {
            view.displayHeader("Guest addition canceled.");
            return;
        }
        Result<Guest> result = guestService.add(guest);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String success = String.format("Guest ID.%s %s %s was added to the guest list.", result.getPayload().getId()
            , result.getPayload().getFirstName(), result.getPayload().getLastName());
            view.displayStatus(result.isSuccess(), success);
        }
    }

    private void updateGuest() throws DataException {
        view.displayHeader("Update a Guest");
        Guest guest = validateGuest();
        guest = view.updateGuest(guest);
        if (guest == null) {
            view.displayHeader("Guest update canceled.");
            return;
        }
        Result<Guest> result = guestService.update(guest);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String success = String.format("Guest %s %s %s was updated successfully.", result.getPayload().getId()
                    , result.getPayload().getFirstName(), result.getPayload().getLastName());
            view.displayStatus(result.isSuccess(), success);
        }
    }

    private void deleteGuest() throws DataException {
        view.displayHeader("Remove a Guest");
        Guest guest = validateGuest();
        view.displaySummary(guest);
        if (!view.confirm("Is this okay to remove? [y/n]: ")) {
            view.displayHeader("Guest removal canceled.");
            return;
        }
        Result<Guest> result = guestService.delete(guest);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String success = String.format("Guest %s %s %s was removed successfully.", result.getPayload().getId()
                    , result.getPayload().getFirstName(), result.getPayload().getLastName());
            view.displayStatus(result.isSuccess(), success);
        }
    }

    private void addHost() throws DataException {
        view.displayHeader("Add a Host");
        Host host = view.createHost();
        if (host == null) {
            view.displayHeader("Host addition canceled.");
            return;
        }
        Result<Host> result = hostService.add(host);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String success = String.format("Host ID.%s %s was added to the host list.", result.getPayload().getId()
                    , result.getPayload().getLastName());
            view.displayStatus(result.isSuccess(), success);
        }
    }

    private void updateHost() throws DataException {
        view.displayHeader("Update a Host");
        Host host = validateHost();
        host = view.updateHost(host);
        if (host == null) {
            view.displayHeader("Host update canceled.");
            return;
        }
        Result<Host> result = hostService.update(host);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String success = String.format("Host ID.%s %s was updated successfully.", result.getPayload().getId()
                    , result.getPayload().getLastName());
            view.displayStatus(result.isSuccess(), success);
        }
    }

    private void deleteHost() throws DataException {
        view.displayHeader("Remove a Host");
        Host host = validateHost();
        view.displaySummary(host);
        if (!view.confirm("Is this okay to remove? [y/n]: ")) {
            view.displayHeader("Host removal canceled.");
            return;
        }
        Result<Host> result = hostService.delete(host);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String success = String.format("Host ID.%s %s was removed successfully.", result.getPayload().getId()
                    , result.getPayload().getLastName());
            view.displayStatus(result.isSuccess(), success);
        }
    }

    // Helper methods
    private Host validateHost() {
        Host host;
        do {
            String hostEmail = view.getEmail("Host Email: ");
            host = hostService.findByEmail(hostEmail);
            if (host == null) {
                System.out.println("That Host does not exist");
            }
        } while (host == null);
        return host;
    }

    private Guest validateGuest() {
        Guest guest;
        do {
            String guestEmail = view.getEmail("Guest Email: ");
            guest = guestService.findByEmail(guestEmail);
            if (guest == null) {
                System.out.println("That Guest does not exist");
            }
        } while (guest == null);
        return guest;
    }




}

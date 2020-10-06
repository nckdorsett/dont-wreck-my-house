package learn.domain;

import learn.data.DataException;
import learn.data.GuestRepository;
import learn.models.Guest;

import java.util.Optional;

public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Result<Guest> add(Guest guest) throws DataException {
        Result<Guest> result = validation(guest);
        if (!result.isSuccess()) {
            return result;
        }
        result.setPayload(repository.add(guest));
        return result;
    }


    private Result<Guest> validation(Guest guest) {
        Result<Guest> result = new Result<>();

        if (guest == null) {
            result.addErrorMessage("Guest is not real");
            return result;
        }

        if (guest.getFirstName() == null || guest.getFirstName().isBlank()) {
            result.addErrorMessage("First name cannot be empty");
        } else if (isNumericValidation(guest.getFirstName())) {
            result.addErrorMessage("First name cannot be a number");
        }

        if (guest.getLastName() == null || guest.getLastName().isBlank()) {
            result.addErrorMessage("Last name cannot be empty");
        } else if (isNumericValidation(guest.getLastName())) {
            result.addErrorMessage("Last name cannot be a number");
        }

        if (guest.getEmail() == null || guest.getEmail().isBlank()) {
            result.addErrorMessage("Email cannot be empty");
        } else if (isNumericValidation(guest.getEmail())) {
            result.addErrorMessage("Email cannot be a number");
        }

        if (guest.getPhoneNumber() == null || guest.getPhoneNumber().isBlank()) {
            result.addErrorMessage("Phone number cannot be empty");
        }

        if (guest.getState() == null || guest.getState().isBlank()) {
            result.addErrorMessage("State cannot be empty");
        } else if (isNumericValidation(guest.getState())) {
            result.addErrorMessage("State cannot be a number");
        } else if (!isAState(guest.getState())) {
            result.addErrorMessage("State cannot be a number");
        }

        if (repository.findAll().stream()
        .anyMatch(g -> g.getEmail().equals(guest.getEmail()))) {
            result.addErrorMessage("Guest is a duplicate");
        }
        return result;
    }

    private boolean isNumericValidation(String name) {
        try {
            Double.parseDouble(name);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    private boolean isAState(String state) {

        switch (state) {
            case "AL":
            case "AK":
            case "AZ":
            case "AR":
            case "CA":
            case "CO":
            case "CT":
            case "DE":
            case "FL":
            case "GA":
            case "HI":
            case "ID":
            case "IL":
            case "IN":
            case "IA":
            case "KS":
            case "KY":
            case "LA":
            case "ME":
            case "MD":
            case "MA":
            case "MI":
            case "MN":
            case "MS":
            case "MO":
            case "MT":
            case "NE":
            case "NV":
            case "NH":
            case "NJ":
            case "NM":
            case "NY":
            case "NC":
            case "ND":
            case "OH":
            case "OK":
            case "OR":
            case "PA":
            case "RI":
            case "SC":
            case "SD":
            case "TN":
            case "TX":
            case "UT":
            case "VT":
            case "VA":
            case "WA":
            case "WV":
            case "WI":
            case "WY":
            case "DC":
                return true;
        }
        return false;
    }

}

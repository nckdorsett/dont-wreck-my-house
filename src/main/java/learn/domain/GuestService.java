package learn.domain;

import learn.data.DataException;
import learn.data.GuestRepository;
import learn.models.Guest;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
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

        if (findByEmail(guest.getEmail()) != null) {
            result.addErrorMessage("Guest is a duplicate");
        }
        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.add(guest));
        return result;
    }

    public Result<Guest> update(Guest guest) throws DataException {
        Result<Guest> result = validation(guest);
        if (!result.isSuccess()) {
            return result;
        }

        for (Guest g : repository.findAll()) {
            if (guest.getEmail().equals(g.getEmail())
                    && guest.getId() != g.getId()) {
                result.addErrorMessage("Guest is using a duplicate email");
            }
        }
        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.update(guest);
        if (success) {
            result.setPayload(guest);
        } else {
            result.addErrorMessage("Guest does not exist to be updated");
        }
        return result;
    }

    public Result<Guest> delete(Guest guest) throws DataException {
        Result<Guest> result = new Result<>();
        boolean success = repository.delete(guest);
        if (success) {
            result.setPayload(guest);
        } else {
            result.addErrorMessage("Guest did not exist to be removed");
        }
        return result;
    }

    private Result<Guest> validation(Guest guest) {
        Result<Guest> result = new Result<>();

        if (guest == null) {
            result.addErrorMessage("Guest is not real");
            return result;
        }

        if (!isNotNullOrEmpty(guest.getFirstName())) {
            result.addErrorMessage("First name cannot be empty");
        } else if (!containsOnlyCharacters(guest.getFirstName())) {
            result.addErrorMessage("Invalid first name");
        }

        if (!isNotNullOrEmpty(guest.getLastName())) {
            result.addErrorMessage("Last name cannot be empty");
        } else if (!containsOnlyCharacters(guest.getLastName())) {
            result.addErrorMessage("Invalid last name");
        }

        if (!isEmailAddress(guest.getEmail())) {
            result.addErrorMessage("Invalid email address");
        }

        if (!isPhoneNumber(guest.getPhoneNumber())) {
            result.addErrorMessage("Invalid phone number");
        }

        if (!isNotNullOrEmpty(guest.getState())) {
            result.addErrorMessage("State cannot be empty");
        } else if (!isAState(guest.getState())) {
            result.addErrorMessage("Invalid State");
        }

        return result;
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

    private boolean isNotNullOrEmpty(String name) {
        return name != null && !name.isBlank();
    }

    private boolean isPhoneNumber(String phone) {
        if (phone == null) {
            return false;
        }
        String phoneRegex = "^\\(\\d{3}\\)\\s\\d{7}$";
        Pattern phonePattern = Pattern.compile(phoneRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = phonePattern.matcher(phone);
        return matcher.find();
    }

    private boolean isEmailAddress(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    private boolean containsOnlyCharacters(String input) {
        return Pattern.matches("[a-zA-Z'\\s]+", input);
    }

}

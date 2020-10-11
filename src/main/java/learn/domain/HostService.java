package learn.domain;

import learn.data.DataException;
import learn.data.HostRepository;
import learn.models.Host;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HostService {

    public final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Result<Host> add(Host host) throws DataException {
        Result<Host> result = validation(host);
        if (!result.isSuccess()) {
            return result;
        }

        if (findByEmail(host.getEmail()) != null) {
            result.addErrorMessage("Host is a duplicate");
        }
        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.add(host));
        return result;
    }

    public Result<Host> update(Host host) throws DataException {
        Result<Host> result = validation(host);
        if (!result.isSuccess()) {
            return result;
        }

        for (Host h : repository.findAll()) {
            if (host.getEmail().equals(h.getEmail())
                    && !host.getId().equals(h.getId())) {
                result.addErrorMessage("Host is using a duplicate email");
            }
        }
        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.update(host);
        if (success) {
            result.setPayload(host);

        } else {
            result.addErrorMessage("Host does not exist to be updated");
        }
        return result;
    }

    public Result<Host> delete(Host host) throws DataException {
        Result<Host> result = new Result<>();
        boolean success = repository.delete(host);
        if (success) {
            result.setPayload(host);
        } else {
            result.addErrorMessage("Host did not exist to be removed");
        }
        return result;
    }

    private Result<Host> validation(Host host) {
        Result<Host> result = new Result<>();

        if (host == null) {
            result.addErrorMessage("Guest is not real");
            return result;
        }

        if (!isNotNullOrEmpty(host.getLastName())) {
            result.addErrorMessage("Last name cannot be empty");
        } else if (!containsOnlyCharacters(host.getLastName())) {
            result.addErrorMessage("Invalid last name");
        }

        if (!isEmailAddress(host.getEmail())) {
            result.addErrorMessage("Invalid email address");
        }

        if (!isPhoneNumber(host.getPhoneNumber())) {
            result.addErrorMessage("Invalid phone number");
        }

        if (!isNotNullOrEmpty(host.getAddress())) {
            result.addErrorMessage("Address cannot be empty");
        }

        if (!isNotNullOrEmpty(host.getCity())) {
            result.addErrorMessage("City cannot be empty");
        } else if (!containsOnlyCharacters(host.getCity())) {
            result.addErrorMessage("Invalid City");
        }

        if (!isNotNullOrEmpty(host.getState())) {
            result.addErrorMessage("State cannot be empty");
        } else if (!isAState(host.getState())) {
            result.addErrorMessage("Invalid State");
        }

        if (!isPostalCode(host.getPostalCode())) {
            result.addErrorMessage("Invalid postal code");
        }

        if (host.getStdRate() == null) {
            result.addErrorMessage("Missing standard rate");
        } else if (host.getStdRate().compareTo(BigDecimal.ZERO) <= 0) {
            result.addErrorMessage("Standard rate must be greater than 0");
        }

        if (host.getWkndRate() == null) {
            result.addErrorMessage("Missing weekend rate");
        } else if (host.getWkndRate().compareTo(BigDecimal.ZERO) <= 0) {
            result.addErrorMessage("Weekend rate must be greater than 0");
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

    private boolean isPostalCode(String postal) {
        if (postal == null) {
            return false;
        }
        String postalRegex = "^\\d{5}$";
        Pattern postalPattern = Pattern.compile(postalRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = postalPattern.matcher(postal);
        return matcher.find();
    }

    private boolean containsOnlyCharacters(String input) {
        return Pattern.matches("[a-zA-Z'\\s]+", input);
    }


}

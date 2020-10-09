package learn.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleIO {

    private static final String INVALID_NUMBER
            = "[INVALID] Enter a valid number.";
    private static final String NUMBER_OUT_OF_RANGE
            = "[INVALID] Enter a number between %s and %s.";
    private static final String REQUIRED
            = "[INVALID] Value is required.";
    private static final String INVALID_DATE
            = "[INVALID] Enter a date in yyyy-MM-dd format.";
    private static final String INVALID_EMAIL
            = "[INVALID] Enter a valid email.";
    private static final String INVALID_PHONE
            = "[INVALID] Enter a phone in (###) ####### format.";
    private static final String INVALID_STATE
            = "[INVALID] Enter a valid State abbreviation.";
    private static final String CHARACTER_ERROR
            = "[INVALID] Name cannot contain special characters or numbers.";

    private final Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }

    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    public String readRequiredString(String prompt) {
        while (true) {
            String result = readString(prompt);
            if (!result.isBlank()) {
                return result;
            }
            println(REQUIRED);
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            int result = readInt(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readRequiredString(prompt).toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            println("[INVALID] Please enter 'y' or 'n'.");
        }
    }

    public LocalDate readRequiredLocalDate(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ex) {
                println(INVALID_DATE);
            }
        }
    }

    public LocalDate readLocalDate(String prompt, LocalDate date) {
        while (true) {
            String input = readString(prompt);
            if (input.isBlank()) {
                return date;
            }
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ex) {
                println(INVALID_DATE);
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return new BigDecimal(input);
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        while (true) {
            BigDecimal result = readBigDecimal(prompt);
            if (result.compareTo(min) >= 0 && result.compareTo(max) <= 0) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public String readEmail(String prompt) {
        while (true) {
            String input = readString(prompt);
            if (input.isBlank()) {
                return input;
            }
            if (isEmailAddress(input)) {
                return input;
            }
            println(INVALID_EMAIL);
        }
    }

    public String readPhoneNumber(String prompt) {
        while (true) {
            String input = readString(prompt);
            if (input.isBlank()) {
                return input;
            }
            if (isPhoneNumber(input)) {
                return input;
            }
            println(INVALID_PHONE);
        }
    }

    public String readState(String prompt) {
        while (true) {
            String input = readString(prompt);
            if (input.isBlank()) {
                return input;
            }
            if (isAState(input)) {
                return input;
            }
            println(INVALID_STATE);
        }
    }

    public String readName(String prompt) {
        while (true) {
            String input = readString(prompt);
            if (input.isBlank()) {
                return input;
            }
            if (containsOnlyCharacters(input)) {
                return input;
            }
            println(CHARACTER_ERROR);
        }
    }

    public String readRequiredEmail(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            if (isEmailAddress(input)) {
                return input;
            }
            println(INVALID_EMAIL);
        }
    }

    public String readRequiredPhoneNumber(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            if (isPhoneNumber(input)) {
                return input;
            }
            println(INVALID_PHONE);
        }
    }

    public String readRequiredState(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            if (isAState(input)) {
                return input;
            }
            println(INVALID_STATE);
        }
    }

    public String readRequiredName(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            if (containsOnlyCharacters(input)) {
                return input;
            }
            println(CHARACTER_ERROR);
        }
    }


    // validation methods
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

    public boolean containsOnlyCharacters(String input) {
        return Pattern.matches("[a-zA-Z']+", input);
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

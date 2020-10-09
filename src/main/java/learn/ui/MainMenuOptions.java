package learn.ui;

import com.sun.tools.javadoc.Main;

public enum MainMenuOptions {
    EXIT(0, "EXIT"),
    VIEW_RESERVATIONS_BY_HOST(1, "View Reservations by Host"),
    ADD_RESERVATION(2, "Create a reservation"),
    UPDATE_RESERVATION(3, "Update a reservation"),
    DELETE_RESERVATION(4, "Cancel a reservation"),
    ADD_GUEST(5, "Add a Guest"),
    UPDATE_GUEST(6, "Update a Guest"),
    DELETE_GUEST(7, "Remove a Guest");

    private int value;
    private String message;

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    private MainMenuOptions(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static MainMenuOptions fromValue(int value) {
        for (MainMenuOptions option : MainMenuOptions.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }




}

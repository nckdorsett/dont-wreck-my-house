package learn.ui;

public enum MainMenuOptions {
    EXIT(0, "EXIT"),
    VIEW_RESERVATIONS_BY_HOST(1, "View Reservations by Host"),
    ADD_RESERVATION(2, "Create a reservation"),
    UPDATE_RESERVATION(3, "Update a reservation"),
    DELETE_RESERVATION(4, "Cancel a reservation"),
    ADD_GUEST(5, "Add a Guest"),
    UPDATE_GUEST(6, "Update a Guest"),
    DELETE_GUEST(7, "Remove a Guest"),
    ADD_HOST(8,"Add a Host"),
    UPDATE_HOST(9, "Update a Host"),
    DELETE_HOST(10, "Remove a Host");

    private final int value;
    private final String message;

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    MainMenuOptions(int value, String message) {
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

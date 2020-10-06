package learn.domain;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

    private ArrayList<String> messages = new ArrayList<>();
    private T payload;

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return messages.size() == 0;
    }

    public List<String> getErrorMessages() {
        return new ArrayList<>(messages);
    }

    public void addErrorMessage(String message) {
        messages.add(message);
    }

}

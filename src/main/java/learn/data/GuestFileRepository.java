package learn.data;

import learn.models.Guest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepository implements GuestRepository{

    private static final String HEADER = "guest_id,first_name,last_name,email,phone,stateguest_id,first_name,last_name,email,phone,state";
    private final String filePath;
    private final String DELIMITER = ",";

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(DELIMITER,-1);
                if (fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // do nothing
        }
        return result;
    }

    @Override
    public Guest findByEmail(String email) {
        List<Guest> all = findAll();
        for (Guest g : all) {
            if (g.getEmail().equals(email)) {
                return g;
            }
        }
        return null;
    }

    @Override
    public Guest add(Guest guest) throws DataException {
        if (guest == null) {
            return null;
        }
        List<Guest> all = findAll();
        int nextId = all.stream().mapToInt(Guest::getId).max().orElse(0) + 1;
        guest.setId(nextId);
        all.add(guest);
        writeAll(all);
        return guest;
    }

    @Override
    public boolean update(Guest guest) throws DataException {

        if (guest == null) {
            return false;
        }

        List<Guest> all = findAll();
        for (int index = 0; index < all.size(); index++) {
            if (guest.getId() == all.get(index).getId()) {
                all.set(index, guest);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Guest guest) throws DataException {

        if (guest == null) {
            return false;
        }

        List<Guest> all = findAll();
        for (int index = 0; index < all.size(); index++) {
            if (guest.getId() == all.get(index).getId()) {
                all.remove(index);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    private String serialize(Guest guest) {
        return String.format("%s%s%s%s%s%s%s%s%s%s%s",
                guest.getId(),
                DELIMITER,
                guest.getFirstName(),
                DELIMITER,
                guest.getLastName(),
                DELIMITER,
                guest.getEmail(),
                DELIMITER,
                guest.getPhoneNumber(),
                DELIMITER,
                guest.getState());
    }

    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setId(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhoneNumber(fields[4]);
        result.setState(fields[5]);
        return result;
    }

    private void writeAll(List<Guest> guests) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {

            writer.println(HEADER);

            if (guests == null) {
                return;
            }

            for (Guest guest : guests) {
                writer.println(serialize(guest));
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }
}

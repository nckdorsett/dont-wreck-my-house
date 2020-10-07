package learn.data;

import learn.models.Guest;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepositoryDouble implements GuestRepository{

    private ArrayList<Guest> allGuests = new ArrayList<Guest>();
    private final Guest GUEST = new Guest(1,"Nick",
            "Danger", "testemail@email.com",
            "(111) 1112233", "WI");

    public GuestFileRepositoryDouble() {
        allGuests.add(GUEST);
        allGuests.add(new Guest(2,"Nicholas","Doos",
                "thisemailisfake@fakie.net",
                "(123) 4567890","IL"));
    }

    @Override
    public List<Guest> findAll() {
        return allGuests;
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
    public Guest findById(int id) {
        List<Guest> all = findAll();
        for (Guest g : all) {
            if (g.getId() == id) {
                return g;
            }
        }
        return null;
    }

    @Override
    public Guest add(Guest guest) throws DataException {
        return guest;
    }

    @Override
    public boolean update(Guest guest) throws DataException {
        return true;
    }

    @Override
    public boolean delete(Guest guest) throws DataException {
        List<Guest> all = findAll();
        for (int index = 0; index < all.size(); index++) {
            if (guest.getId() == all.get(index).getId()) {
                all.remove(index);
                return true;
            }
        }
        return false;
    }
}

package learn.data;

import learn.models.Guest;

import java.util.List;

public interface GuestRepository {

    List<Guest> findAll();

    Guest findByEmail(String email);

    Guest findById(int id);

    Guest add(Guest guest) throws DataException;

    boolean update(Guest guest) throws DataException;

    boolean delete(Guest guest) throws DataException;
}

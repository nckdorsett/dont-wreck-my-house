package learn.data;

import learn.models.Host;
import learn.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findByHost(Host host);

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean delete(Reservation reservation) throws DataException;
}

package learn.data;

import learn.models.Guest;
import learn.models.Host;
import learn.models.Reservation;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationFileRepositoryDouble implements ReservationRepository {

    private final ArrayList<Reservation> reservations = new ArrayList<>();
    private final Host HOST = new Host("AAA-BBB-CCC-DDD",
            "Dorsett","ndfakie@email.com",
            "(111) 1234567","fake address",
            "Milwaukee","WI",53212, new BigDecimal(477), new BigDecimal(596.25));
    private final Guest GUEST = new Guest(1,"Nick",
            "Danger", "testemail@email.com",
            "(111) 1112233", "WI");

    public ReservationFileRepositoryDouble() {
        Reservation reservation1 = new Reservation();
        reservation1.setId(1);
        reservation1.setStartDate(LocalDate.of(2020, 10, 26));
        reservation1.setEndDate(LocalDate.of(2020,10,28));
        reservation1.setGuest(GUEST);
        reservation1.setHost(HOST);
        reservation1.setTotal(reservation1.calcTotal());
        reservations.add(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        reservation2.setStartDate(LocalDate.of(2020, 11, 1));
        reservation2.setEndDate(LocalDate.of(2020,11,3));
        reservation2.setGuest(GUEST);
        reservation2.setHost(HOST);
        reservation2.setTotal(reservation2.calcTotal());
        reservations.add(reservation2);
    }



    @Override
    public List<Reservation> findByHost(Host host) {
        return reservations.stream()
                .filter(r -> r.getHost().equals(host))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return true;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {
        return true;
    }
}

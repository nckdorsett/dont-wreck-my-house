package learn.domain;

import learn.data.*;
import learn.models.Guest;
import learn.models.Host;
import learn.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    private ReservationService service;

    private final Host HOST = new Host("AAA-BBB-CCC-DDD",
            "Dorsett","ndfakie@email.com",
            "(111) 1234567","fake address",
            "Milwaukee","WI",53212, new BigDecimal(477), new BigDecimal(596.25));
    private final Guest GUEST = new Guest(1,"Nick",
            "Danger", "testemail@email.com",
            "(111) 1112233", "WI");

    @BeforeEach
    void setUp() {
        service = new ReservationService(new ReservationFileRepositoryDouble(),
                new GuestFileRepositoryDouble(), new HostFileRepositoryDouble());
    }

    @Test
    void shouldFindByHost() {
        List<Reservation> actual = service.findByHost(HOST);
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.add(reservation);
        assertNotNull(actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullStartDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(null);
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(null);
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullGuest() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(null);
        reservation.setHost(HOST);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotNonExistentGuest() throws DataException {
        Guest guestFail = new Guest();
        guestFail.setId(1000000);

        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(guestFail);
        reservation.setHost(HOST);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullHost() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(null);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNonExistentHost() throws DataException {
        Host hostFail = new Host();
        hostFail.setId("1000000");

        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(hostFail);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullTotal() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(null);

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }





}
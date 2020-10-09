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
            "Milwaukee","WI","53212", new BigDecimal(477), new BigDecimal(596.25));
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
        assertEquals(3, actual.size());
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
    void shouldAddStartSameAsEndOfOther() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 28));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.add(reservation);
        assertNotNull(actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldAddEndDateSameAsStartOfOther() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 23));
        reservation.setEndDate(LocalDate.of(2020,10,26));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.add(reservation);
        assertNotNull(actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldSqueezeInStartAndEnd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 28));
        reservation.setEndDate(LocalDate.of(2020,11,1));
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
    void shouldNotAddNonExistentGuest() throws DataException {
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

    @Test
    void shouldNotAddReservationEndInsideAnotherReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 25));
        reservation.setEndDate(LocalDate.of(2020,10,27));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddReservationStartStartInsideAnother() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 27));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddReservationInsideAnotherReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 26));
        reservation.setEndDate(LocalDate.of(2020,10,27));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddReservationSurroundingAnother() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 25));
        reservation.setEndDate(LocalDate.of(2020,10,29));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddDateInPast() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(1995, 10, 25));
        reservation.setEndDate(LocalDate.of(1995,10,29));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddStartDateAfterEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2025, 10, 24));
        reservation.setEndDate(LocalDate.of(2025,10,20));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddStartSameAsEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2025, 10, 20));
        reservation.setEndDate(LocalDate.of(2025,10,20));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.add(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.update(reservation);
        assertNotNull(actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldUpdateStartSameAsEndOfOther() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 28));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.update(reservation);
        assertNotNull(actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldUpdateEndDateSameAsStartOfOther() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 23));
        reservation.setEndDate(LocalDate.of(2020,10,26));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.update(reservation);
        assertNotNull(actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldUpdateSqueezeStartAndEnd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(3);
        reservation.setStartDate(LocalDate.of(2020, 10, 28));
        reservation.setEndDate(LocalDate.of(2020,11,1));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.add(reservation);
        assertNotNull(actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateMissingId() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(100000);
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.update(reservation);
        assertNotNull(actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullStartDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(null);
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(null);
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullGuest() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(null);
        reservation.setHost(HOST);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNonExistentGuest() throws DataException {
        Guest guestFail = new Guest();
        guestFail.setId(1000000);

        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(guestFail);
        reservation.setHost(HOST);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullHost() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(null);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNonExistentHost() throws DataException {
        Host hostFail = new Host();
        hostFail.setId("1000000");

        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(hostFail);
        reservation.setTotal(new BigDecimal("100"));

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullTotal() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 29));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(null);

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateReservationEndInsideAnotherReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 25));
        reservation.setEndDate(LocalDate.of(2020,10,27));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateReservationStartStartInsideAnother() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 27));
        reservation.setEndDate(LocalDate.of(2020,10,31));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateReservationInsideAnotherReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 26));
        reservation.setEndDate(LocalDate.of(2020,10,27));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateReservationSurroundingAnother() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 10, 25));
        reservation.setEndDate(LocalDate.of(2020,10,29));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateDateInPast() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(1995, 10, 25));
        reservation.setEndDate(LocalDate.of(1995,10,29));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateStartDateAfterEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2025, 10, 24));
        reservation.setEndDate(LocalDate.of(2025,10,20));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateStartSameAsEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2025, 10, 20));
        reservation.setEndDate(LocalDate.of(2025,10,20));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.update(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldDeleteExisting() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2020, 11, 1));
        reservation.setEndDate(LocalDate.of(2020,11,3));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);

        Result<Reservation> actual = service.delete(reservation);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotDeleteMissingId() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(100000);
        reservation.setStartDate(LocalDate.of(2020, 11, 1));
        reservation.setEndDate(LocalDate.of(2020,11,3));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);

        Result<Reservation> actual = service.delete(reservation);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotDeleteStartDateInPast() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(3);
        reservation.setStartDate(LocalDate.of(1995, 1, 13));
        reservation.setEndDate(LocalDate.of(1995,1,15));
        reservation.setGuest(GUEST);
        reservation.setHost(HOST);
        reservation.setTotal(reservation.calcTotal());

        Result<Reservation> actual = service.delete(reservation);
        assertFalse(actual.isSuccess());
    }


}
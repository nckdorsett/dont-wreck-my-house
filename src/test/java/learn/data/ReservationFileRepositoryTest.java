package learn.data;

import learn.models.Guest;
import learn.models.Host;
import learn.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    private final String TEST_DIR_PATH = "./test-data";
    private final String SEED_FILE_PATH = "./test-data/reservation-seed.csv";
    private final String TEST_FILE_PATH = "./test-data/AAA-BBB-CCC-DDD.csv";
    private final int LIST_SIZE_ALL = 13;

    final Host host = new Host("AAA-BBB-CCC-DDD",
            "Danger","ndlearner@learncoding.com",
            "(111) 1112233","1 Winner Lane",
            "Milwaukee","WI",53212,
            new BigDecimal("200"), new BigDecimal("300"));

    ReservationRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setUp() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindByHost() {
        List<Reservation> actual = repository.findByHost(host);
        assertNotNull(actual);
        assertEquals(LIST_SIZE_ALL, actual.size());
    }

    @Test
    void shouldReturnEmptyListFromNoData() {
        Host empty = new Host();
        empty.setId("1234");
        List<Reservation> actual = repository.findByHost(new Host());
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void shouldAdd() throws DataException {
        Guest guest = new Guest();
        guest.setId(1024);
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 26));
        reservation.setEndDate(LocalDate.of(2020,10,28));
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setTotal(reservation.calcTotal());


        assertNotNull(repository.add(reservation));
        List<Reservation> all = repository.findByHost(host);

        assertEquals(LIST_SIZE_ALL+1, reservation.getId());
        assertEquals(LIST_SIZE_ALL+1, all.size());
        assertEquals(new BigDecimal(400),reservation.getTotal());
    }

    @Test
    void shouldNotAddNull() throws DataException {
        Reservation reservation = null;
        assertNull(repository.add(reservation));
    }

    @Test
    void shouldUpdate() throws DataException {
        Guest guest = new Guest();
        guest.setId(1024);
        Reservation reservation = new Reservation();
        reservation.setId(13);
        reservation.setStartDate(LocalDate.of(2020, 10, 26));
        reservation.setEndDate(LocalDate.of(2020,10,28));
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setTotal(reservation.calcTotal());

        assertTrue(repository.update(reservation));
    }

    @Test
    void shouldNotUpdateNull() throws DataException {
        Reservation reservation = null;
        assertFalse(repository.update(reservation));
    }

    @Test
    void shouldNotUpdateNonExisting() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(100000);
        reservation.setHost(host);

        assertFalse(repository.update(reservation));
    }

    @Test
    void shouldDelete() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(13);
        reservation.setHost(host);

        assertTrue(repository.delete(reservation));
    }

    @Test
    void shouldNotDeleteNull() throws DataException {
        Reservation reservation = null;
        assertFalse(repository.delete(reservation));
    }

    @Test
    void shouldNotDeleteNonExisting() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(100000);
        reservation.setHost(host);

        assertFalse(repository.delete(reservation));
    }

    @Test
    void shouldMakeNewFile() throws DataException {
        Host host1 = new Host("123-456-789-0",
                "Dooser","ndlearner@learncoding.com",
                "(111) 1112233","1 Winner Lane",
                "Milwaukee","WI",53212,
                new BigDecimal("200"), new BigDecimal("300"));

        Guest guest = new Guest();
        guest.setId(1024);
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2020, 10, 26));
        reservation.setEndDate(LocalDate.of(2020,10,28));
        reservation.setGuest(guest);
        reservation.setHost(host1);
        reservation.setTotal(reservation.calcTotal());

        String path = "./test-data/" + reservation.getHost().getId() + ".csv";
        File file = new File(path);
        file.delete();

        reservation = repository.add(reservation);
        List<Reservation> all = repository.findByHost(reservation.getHost());

        assertEquals(1, reservation.getId());
        assertEquals(1, all.size());
    }

}
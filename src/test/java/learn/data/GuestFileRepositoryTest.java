package learn.data;

import learn.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {


    private final String SEED_PATH = "./test-data/guest-seed.csv";
    private final String TEST_PATH = "./test-data/guest-test.csv";
    private final int NEXT_ID = 21;
    private final int LIST_SIZE_ALL = 20;

    GuestRepository repository = new GuestFileRepository(TEST_PATH);

    @BeforeEach
    void setUp() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Guest> actual = repository.findAll();
        assertNotNull(actual);
        assertEquals(LIST_SIZE_ALL, actual.size());
    }

    @Test
    void shouldFindByEmail() {
        Guest actual = repository.findByEmail("rwongj@eventbrite.com");
        assertNotNull(actual);
    }

    @Test
    void shouldNotFindNonExistingEmail() {
        Guest actual = repository.findByEmail("thisisafakeemailforsure@fakie.com");
        assertNull(actual);
    }

    @Test
    void shouldAdd() throws DataException {
        Guest guest = new Guest();
        repository.add(guest);
        List<Guest> all = repository.findAll();

        assertEquals(NEXT_ID, guest.getId());
        assertEquals(21, all.size());
    }

    @Test
    void shouldNotAddNull() throws DataException {
        Guest guest = null;
        assertNull(repository.add(guest));

        List<Guest> all = repository.findAll();
        assertEquals(LIST_SIZE_ALL, all.size());
    }

    @Test
    void shouldUpdate() throws DataException {
        Guest guest = new Guest(20, "Ryley", "Wong", "rwong69@gmail.com", "(369) 7456269", "NV");
        assertTrue(repository.update(guest));
    }

    @Test
    void shouldNotUpdateNull() throws DataException {
        Guest guest = null;
        assertFalse(repository.update(guest));
    }

    @Test
    void shouldNotUpdateNonExistent() throws DataException {
        Guest guest = new Guest(100000, "Ryley", "Wong", "rwong69@gmail.com", "(369) 7456269", "NV");
        assertFalse(repository.update(guest));
    }

    @Test
    void shouldDelete() throws DataException {
        Guest guest = new Guest(20, "Ryley", "Wong", "rwongj@eventbrite.com", "(414) 5700854", "WI");
        assertTrue(repository.delete(guest));

        List<Guest> all = repository.findAll();
        assertEquals(LIST_SIZE_ALL - 1, all.size());
    }

    @Test
    void shouldNotDeleteNull() throws DataException {
        Guest guest = null;
        assertFalse(repository.delete(guest));
    }

    @Test
    void shouldNotDeleteNonExistent() throws DataException {
        Guest guest = new Guest(10000, "Ryley", "Wong", "rwongj@eventbrite.com", "(414) 5700854", "WI");
        assertFalse(repository.delete(guest));
    }

}
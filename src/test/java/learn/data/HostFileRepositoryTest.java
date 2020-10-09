package learn.data;

import learn.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    private final String SEED_PATH = "./test-data/host-seed.csv";
    private final String TEST_PATH = "./test-data/host-test.csv";
    private final int LIST_SIZE_ALL = 20;

    HostRepository repository = new HostFileRepository(TEST_PATH);

    @BeforeEach
    void setUp() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Host> actual = repository.findAll();
        assertNotNull(actual);
        assertEquals(LIST_SIZE_ALL, actual.size());
    }

    @Test
    void shouldFindByEmail() {
        Host actual = repository.findByEmail("bmoorcroftj@topsy.com");
        assertNotNull(actual);
    }

    @Test
    void shouldNotFindNonExistingEmail() {
        Host actual = repository.findByEmail("thisisafakeemailforsure@fakie.com");
        assertNull(actual);
    }

    @Test
    void shouldFindById() {
        Host actual = repository.findById("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertNotNull(actual);
    }

    @Test
    void shouldNotFindMissingId() {
        Host actual = repository.findById("f92ace2e6");
        assertNull(actual);
    }

    @Test
    void shouldAdd() throws DataException {
        Host host = new Host("",
                "Dorsett","bmoorcroftj@topsy.com",
                "(352) 5465804","696 Love lane",
                "Dallas","Texas","15568", new BigDecimal(477), new BigDecimal(596.25));
        repository.add(host);
        List<Host> all = repository.findAll();
        assertEquals(36, host.getId().length());
        assertEquals(LIST_SIZE_ALL+1, all.size());
    }

    @Test
    void shouldNotAddNull() throws DataException {
        Host host = null;
        assertNull(repository.add(host));

        List<Host> all = repository.findAll();
        assertEquals(LIST_SIZE_ALL, all.size());
    }

    @Test
    void shouldUpdate() throws DataException {
        Host host = new Host("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6",
                "Moorcroft","bmoorcroftj@topsy.com",
                "(352) 5465804","696 Love lane",
                "Dallas","Texas","15568", new BigDecimal(477), new BigDecimal(596.25));
        assertTrue(repository.update(host));
    }

    @Test
    void shouldNotUpdateNull() throws DataException {
        Host host = null;
        assertFalse(repository.update(host));
    }

    @Test
    void shouldNotUpdateNonExistent() throws DataException {
        Host host = new Host("",
                "Moorcroft","bmoorcroftj@topsy.com",
                "(352) 5465804","696 Love lane",
                "Dallas","Texas","15568", new BigDecimal(477), new BigDecimal(596.25));
        assertFalse(repository.update(host));
    }

    @Test
    void shouldDelete() throws DataException {
        Host host = new Host("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6",
                "Moorcroft","bmoorcroftj@topsy.com",
                "(352) 5465804","28 Badeau Avenue",
                "Ocala","FL","34479", new BigDecimal(477), new BigDecimal(596.25));
        assertTrue(repository.delete(host));

        List<Host> all = repository.findAll();
        assertEquals(LIST_SIZE_ALL - 1, all.size());
    }

    @Test
    void shouldNotDeleteNull() throws DataException {
        Host host = null;
        assertFalse(repository.delete(host));
    }

    @Test
    void shouldNotDeleteNonExistent() throws DataException {
        Host host = new Host("",
                "Moorcroft","bmoorcroftj@topsy.com",
                "(352) 5465804","28 Badeau Avenue",
                "Ocala","FL","34479", new BigDecimal(477), new BigDecimal(596.25));
        assertFalse(repository.delete(host));
    }

}
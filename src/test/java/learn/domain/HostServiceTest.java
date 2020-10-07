package learn.domain;

import learn.data.HostFileRepositoryDouble;
import learn.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService service;

    @BeforeEach
    void setUp() {
        service = new HostService(new HostFileRepositoryDouble());
    }

    @Test
    void shouldFindExistingEmail() {
        Host actual = service.findByEmail("ndfakie@email.com");
        assertNotNull(actual);
        assertEquals("Dorsett", actual.getLastName());
    }

    @Test
    void shouldNotFindNonExistentEmail() {
        Host actual = service.findByEmail("faketestemail@faketestemail.gov");
        assertNull(actual);
    }

}
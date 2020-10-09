package learn.domain;

import learn.data.DataException;
import learn.data.HostFileRepositoryDouble;
import learn.models.Guest;
import learn.models.Host;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService service;

    private final Host HOST = new Host("AAA-BBB-CCC-DDD",
            "Doooo","notreal@notreal.com",
            "(111) 1234567","fake address",
            "Milwaukee","WI","53212",
            new BigDecimal(477), new BigDecimal(596.25));

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

    @Test
    void shouldAdd() throws DataException {
        Result<Host> actual = service.add(HOST);
        assertNotNull(actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldAddSpaceInCityName() throws DataException {
        Host host = HOST;
        host.setCity("Mil Waukee");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNull() throws DataException {
        Result<Host> actual = service.add(null);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddDuplicateEmail() throws DataException {
        Host host = new Host("",
                "Doorbell","fakedanger@email.net",
                "(111) 0987654","fake address 2",
                "Milwaukee","WI","53212",
                new BigDecimal(477), new BigDecimal(596.25));
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullLastName() throws DataException {
        Host host = new Host("AAA-BBB-CCC-DDD",
                null,"notreal@notreal.com",
                "(111) 1234567","fake address",
                "Milwaukee","WI","53212",
                new BigDecimal(477), new BigDecimal(596.25));
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddBlankLastName() throws DataException {
        Host host = new Host("AAA-BBB-CCC-DDD",
                "","notreal@notreal.com",
                "(111) 1234567","fake address",
                "Milwaukee","WI","53212",
                new BigDecimal(477), new BigDecimal(596.25));
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullEmail() throws DataException {
        Host host = HOST;
        host.setEmail(null);
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddBlankEmail() throws DataException {
        Host host = HOST;
        host.setEmail("");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddInvalidEmail() throws DataException {
        Host host = HOST;
        host.setEmail("fake.fake.fakie.com");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullPhoneNumber() throws DataException {
        Host host = HOST;
        host.setPhoneNumber(null);
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddBlankPhoneNumber() throws DataException {
        Host host = HOST;
        host.setPhoneNumber("");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddInvalidPhoneNumber() throws DataException {
        Host host = HOST;
        host.setPhoneNumber("1234567890");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddInvalidPhoneNumber2() throws DataException {
        Host host = HOST;
        host.setPhoneNumber("123 4567890");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddInvalidPhoneNumber3() throws DataException {
        Host host = HOST;
        host.setPhoneNumber("(123)4567890");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullAddress() throws DataException {
        Host host = HOST;
        host.setAddress(null);
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddBlankAddress() throws DataException {
        Host host = HOST;
        host.setAddress("");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullCity() throws DataException {
        Host host = HOST;
        host.setCity(null);
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddBlankCity() throws DataException {
        Host host = HOST;
        host.setCity("");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddInvalidCity() throws DataException {
        Host host = HOST;
        host.setCity("Mil2Wauk33");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullState() throws DataException {
        Host host = HOST;
        host.setState(null);
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAdd() {

    }




}
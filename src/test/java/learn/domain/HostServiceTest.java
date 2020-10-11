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

    private final Host HOST = new Host("Abc-def-ghi",
            "Doooo","notreal@notreal.com",
            "(111) 1234567","fake address",
            "Milwaukee","WI","53212",
            new BigDecimal(477), new BigDecimal(596.25));

    private final Host HOST_UPDATE = new Host("123-456-789-0",
            "Danger","fakedanger@email.net",
            "(123) 4567890","fake address two",
            "Dallas","TX","15568",
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
        assertTrue(actual.isSuccess());
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
    void shouldNotAddBlankState() throws DataException {
        Host host = HOST;
        host.setState("");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddInvalidState() throws DataException {
        Host host = HOST;
        host.setState("25");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());

        host.setState("BU");
        actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullPostalCode() throws DataException {
        Host host = HOST;
        host.setPostalCode(null);
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddBlankPostalCode() throws DataException {
        Host host = HOST;
        host.setPostalCode("");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddInvalidPostalCode() throws DataException {
        Host host = HOST;
        host.setPostalCode("ABCDE");
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());

        host.setPostalCode("123456");
        actual = service.add(host);
        assertFalse(actual.isSuccess());

        host.setPostalCode("123AB");
        actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullStdRate() throws DataException {
        Host host = HOST;
        host.setStdRate(null);
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddStdRateZero() throws DataException {
        Host host = HOST;
        host.setStdRate(BigDecimal.ZERO);
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddStdRateNegative() throws DataException {
        Host host = HOST;
        host.setStdRate(new BigDecimal("-100"));
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullWkndRate() throws DataException {
        Host host = HOST;
        host.setWkndRate(null);
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddWkndRateZero() throws DataException {
        Host host = HOST;
        host.setWkndRate(BigDecimal.ZERO);
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddWkndRateNegative() throws DataException {
        Host host = HOST;
        host.setWkndRate(new BigDecimal("-100"));
        Result<Host> actual = service.add(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldUpdate() throws DataException {
        Result<Host> actual = service.update(HOST_UPDATE);
        assertNotNull(actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldUpdateSpaceInCityName() throws DataException {
        Host host = HOST_UPDATE;
        host.setCity("Mil Waukee");
        Result<Host> actual = service.update(host);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNull() throws DataException {
        Result<Host> actual = service.update(null);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateDuplicateEmailAndDifferentId() throws DataException {
        Host host = HOST_UPDATE;
        host.setId("different");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullLastName() throws DataException {
        Host host = HOST_UPDATE;
        host.setLastName(null);
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateBlankLastName() throws DataException {
        Host host = HOST_UPDATE;
        host.setLastName("");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullEmail() throws DataException {
        Host host = HOST_UPDATE;
        host.setEmail(null);
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateBlankEmail() throws DataException {
        Host host = HOST_UPDATE;
        host.setEmail("");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidEmail() throws DataException {
        Host host = HOST_UPDATE;
        host.setEmail("fake.fake.fakie.com");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullPhoneNumber() throws DataException {
        Host host = HOST_UPDATE;
        host.setPhoneNumber(null);
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateBlankPhoneNumber() throws DataException {
        Host host = HOST_UPDATE;
        host.setPhoneNumber("");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidPhoneNumber() throws DataException {
        Host host = HOST_UPDATE;
        host.setPhoneNumber("1234567890");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidPhoneNumber2() throws DataException {
        Host host = HOST_UPDATE;
        host.setPhoneNumber("123 4567890");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidPhoneNumber3() throws DataException {
        Host host = HOST_UPDATE;
        host.setPhoneNumber("(123)4567890");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullAddress() throws DataException {
        Host host = HOST_UPDATE;
        host.setAddress(null);
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateBlankAddress() throws DataException {
        Host host = HOST_UPDATE;
        host.setAddress("");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullCity() throws DataException {
        Host host = HOST_UPDATE;
        host.setCity(null);
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateBlankCity() throws DataException {
        Host host = HOST_UPDATE;
        host.setCity("");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidCity() throws DataException {
        Host host = HOST_UPDATE;
        host.setCity("Mil2Wauk33");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullState() throws DataException {
        Host host = HOST_UPDATE;
        host.setState(null);
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateBlankState() throws DataException {
        Host host = HOST_UPDATE;
        host.setState("");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidState() throws DataException {
        Host host = HOST_UPDATE;
        host.setState("25");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());

        host.setState("BU");
        actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullPostalCode() throws DataException {
        Host host = HOST_UPDATE;
        host.setPostalCode(null);
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateBlankPostalCode() throws DataException {
        Host host = HOST_UPDATE;
        host.setPostalCode("");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidPostalCode() throws DataException {
        Host host = HOST_UPDATE;
        host.setPostalCode("ABCDE");
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());

        host.setPostalCode("123456");
        actual = service.update(host);
        assertFalse(actual.isSuccess());

        host.setPostalCode("123AB");
        actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullStdRate() throws DataException {
        Host host = HOST_UPDATE;
        host.setStdRate(null);
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateStdRateZero() throws DataException {
        Host host = HOST_UPDATE;
        host.setStdRate(BigDecimal.ZERO);
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateStdRateNegative() throws DataException {
        Host host = HOST_UPDATE;
        host.setStdRate(new BigDecimal("-100"));
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullWkndRate() throws DataException {
        Host host = HOST_UPDATE;
        host.setWkndRate(null);
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateWkndRateZero() throws DataException {
        Host host = HOST_UPDATE;
        host.setWkndRate(BigDecimal.ZERO);
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateWkndRateNegative() throws DataException {
        Host host = HOST_UPDATE;
        host.setWkndRate(new BigDecimal("-100"));
        Result<Host> actual = service.update(host);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldDelete() throws DataException {
        Host host = HOST_UPDATE;
        Result<Host> actual = service.delete(host);
        assertNotNull(actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotDeleteMissing() throws DataException {
        Host host = HOST;
        Result<Host> actual = service.delete(host);
        assertFalse(actual.isSuccess());
    }




}
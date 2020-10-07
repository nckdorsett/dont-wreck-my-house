package learn.domain;

import learn.data.DataException;
import learn.data.GuestFileRepositoryDouble;
import learn.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    private GuestService service;

    private Guest guest = new Guest(1000, "Dick", "Norsett", "legitemail@test.com",
            "(111) 2345678","WI");

    @BeforeEach
    void setUp() {
        service = new GuestService(new GuestFileRepositoryDouble());
    }

    @Test
    void shouldFindExistingEmail() {
        Guest actual = service.findByEmail("testemail@email.com");
        assertNotNull(actual);
        assertEquals(1, actual.getId());
    }

    @Test
    void shouldNotFindNonExistentEmail() {
        Guest actual = service.findByEmail("thisisfaketestemail@email.net");
        assertNull(actual);
    }

    @Test
    void shouldAdd() throws DataException {
        Result<Guest> actual = service.add(guest);
        assertTrue(actual.isSuccess());
        assertEquals(1000, actual.getPayload().getId());
    }

    @Test
    void shouldNotAddNull() throws DataException {
        Result<Guest> actual = service.add(null);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullNames() throws DataException {
        Guest guestFail = new Guest(1000, null, "Norsett", "legitemail@test.com",
                "(111) 2345678","WI");
        Result<Guest> actual = service.add(guestFail);
        assertFalse(actual.isSuccess());

        guestFail = new Guest(1000, "Dick", null, "legitemail@test.com",
                "(111) 2345678","WI");
        actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddBlankNames() throws DataException {
        Guest guestFail = new Guest(1000, "", "Norsett", "legitemail@test.com",
                "(111) 2345678","WI");
        Result<Guest> actual = service.add(guestFail);
        assertFalse(actual.isSuccess());

        guestFail = new Guest(1000, "Dick", "", "legitemail@test.com",
                "(111) 2345678","WI");
        actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullEmail() throws DataException {
        Guest guestFail = new Guest(1000, "", "Norsett", null,
                "(111) 2345678","WI");
        Result<Guest> actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddBlankEmail() throws DataException {
        Guest guestFail = new Guest(1000, "", "Norsett", "",
                "(111) 2345678","WI");
        Result<Guest> actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddInvalidEmail() throws DataException {
        Guest guestFail = new Guest(1000, "", "Norsett", "invalid.email.fake.com",
                "(111) 2345678","WI");
        Result<Guest> actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullPhone() throws DataException {
        Guest guestFail = new Guest(1000, "Dick", "Norsett", "legitemail@test.com",
                null,"WI");
        Result<Guest> actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddBlankPhone() throws DataException {
        Guest guestFail = new Guest(1000, "Dick", "Norsett", "legitemail@test.com",
                "","WI");
        Result<Guest> actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddInvalidPhone() throws DataException {
        Guest guestFail = new Guest(1000, "Dick", "Norsett", "legitemail@test.com",
                "1234567890","WI");
        Result<Guest> actual = service.add(guestFail);
        assertFalse(actual.isSuccess());

        guestFail = new Guest(1000, "Dick", "Norsett", "legitemail@test.com",
                "123 4567890","WI");
        actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullState() throws DataException {
        Guest guestFail = new Guest(1000, "Dick", "Norsett", "legitemail@test.com",
                "(111) 2345678",null);
        Result<Guest> actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddBlankState() throws DataException {
        Guest guestFail = new Guest(1000, "Dick", "Norsett", "legitemail@test.com",
                "(111) 2345678","");
        Result<Guest> actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddFakeState() throws DataException {
        Guest guestFail = new Guest(1000, "Dick", "Norsett", "legitemail@test.com",
                "(111) 2345678","BU");
        Result<Guest> actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldUpdate() throws DataException {
        Guest guest1 = new Guest(1, "Dick", "Norsett", "legitemail@test.com",
                "(111) 2345678","WI");
        Result<Guest> actual = service.update(guest1);
        assertTrue(actual.isSuccess());
        assertEquals("Dick", actual.getPayload().getFirstName());
    }

    @Test
    void shouldNotUpdateNull() throws DataException {
        Result<Guest> actual = service.update(null);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullNames() throws DataException {
        Guest guestFail = new Guest(1, null, "Norsett", "legitemail@test.com",
                "(111) 2345678","WI");
        Result<Guest> actual = service.update(guestFail);
        assertFalse(actual.isSuccess());

        guestFail = new Guest(1, "Dick", null, "legitemail@test.com",
                "(111) 2345678","WI");
        actual = service.update(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateBlankNames() throws DataException {
        Guest guestFail = new Guest(1, "", "Norsett", "legitemail@test.com",
                "(111) 2345678","WI");
        Result<Guest> actual = service.update(guestFail);
        assertFalse(actual.isSuccess());

        guestFail = new Guest(1, "Dick", "", "legitemail@test.com",
                "(111) 2345678","WI");
        actual = service.update(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullEmail() throws DataException {
        Guest guestFail = new Guest(1, "", "Norsett", null,
                "(111) 2345678","WI");
        Result<Guest> actual = service.update(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateBlankEmail() throws DataException {
        Guest guestFail = new Guest(1, "", "Norsett", "",
                "(111) 2345678","WI");
        Result<Guest> actual = service.update(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidEmail() throws DataException {
        Guest guestFail = new Guest(1, "", "Norsett", "invalid.email.fake.com",
                "(111) 2345678","WI");
        Result<Guest> actual = service.update(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullPhone() throws DataException {
        Guest guestFail = new Guest(1, "Dick", "Norsett", "legitemail@test.com",
                null,"WI");
        Result<Guest> actual = service.update(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateBlankPhone() throws DataException {
        Guest guestFail = new Guest(1, "Dick", "Norsett", "legitemail@test.com",
                "","WI");
        Result<Guest> actual = service.update(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidPhone() throws DataException {
        Guest guestFail = new Guest(1, "Dick", "Norsett", "legitemail@test.com",
                "1234567890","WI");
        Result<Guest> actual = service.update(guestFail);
        assertFalse(actual.isSuccess());

        guestFail = new Guest(1, "Dick", "Norsett", "legitemail@test.com",
                "123 4567890","WI");
        actual = service.add(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullState() throws DataException {
        Guest guestFail = new Guest(1, "Dick", "Norsett", "legitemail@test.com",
                "(111) 2345678",null);
        Result<Guest> actual = service.update(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateBlankState() throws DataException {
        Guest guestFail = new Guest(1, "Dick", "Norsett", "legitemail@test.com",
                "(111) 2345678","");
        Result<Guest> actual = service.update(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateFakeState() throws DataException {
        Guest guestFail = new Guest(1, "Dick", "Norsett", "legitemail@test.com",
                "(111) 2345678","BU");
        Result<Guest> actual = service.update(guestFail);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldDelete() throws DataException {
        Guest guest1 = new Guest(1,"Nick",
                "Danger", "testemail@email.com",
                "(111) 1112233", "WI");
        Result<Guest> actual = service.delete(guest1);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotDeleteMissingId() throws DataException {
        Result<Guest> actual = service.delete(guest);
        assertFalse(actual.isSuccess());
    }


}
package pl.marta.kopp.registration;

import pl.marta.kopp.communication.Response;
import pl.marta.kopp.domain.model.User;
import pl.marta.kopp.domain.service.UserService;
import pl.marta.kopp.persistence.UserStorageJpa;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegistrationControllerTest {
    private static final String SOME_LOGIN = "JohnnyCash";
    private static final String SOME_PASSWORD = "password123";
    private static final String INVALID_PASSWORD = "123";
    private static final String EXISTS_LOGIN = "JohnyDepp";
    private final UserStorageJpa userStorageJpa = new UserStorageJpa();
    private final UserService service=new UserService(userStorageJpa);
    private final RegistrationController registrationController = new RegistrationController(service);

    @Before
    public void setUp() {
        service.add(new User(EXISTS_LOGIN, SOME_PASSWORD));
    }

    @Test
    public void shouldCreateNewUser() {
        Response result = registrationController.createUser(SOME_LOGIN, SOME_PASSWORD);
        assertTrue(result.getSuccess());
    }

    @Test
    public void shouldNotCreateUserWhenPasswordIsTooShort() {
        Response result = registrationController.createUser(SOME_LOGIN, INVALID_PASSWORD);
        assertFalse(result.getSuccess());
        assertEquals("Password is to short",result.getMessage());
    }

    @Test
    public void shouldNotCreateNewUserWhenUserIsAlreadyExists() {
        Response result = registrationController.createUser(EXISTS_LOGIN,SOME_PASSWORD);
        assertFalse(result.getSuccess());
    }
}

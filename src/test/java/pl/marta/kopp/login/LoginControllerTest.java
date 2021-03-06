package pl.marta.kopp.login;

import pl.marta.kopp.communication.Response;
import pl.marta.kopp.domain.model.User;
import pl.marta.kopp.persistence.UserStorageJpa;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginControllerTest {

    private final UserStorageJpa userStorageJpa = new UserStorageJpa();
    private final LoginController loginController = new LoginController(userStorageJpa);
    private static final String SOME_LOGIN = "spider-man";
    private static final String ANOTHER_LOGIN = "iron-man";
    private static final String SOME_PASSWORD = "spider123";

    @Before
    public void setUp() {
        userStorageJpa.addUser(new User(SOME_LOGIN, SOME_PASSWORD));
    }

    @Test
    public void shouldLoginWhenUserExists() {
        Response result = loginController.login(SOME_LOGIN, SOME_PASSWORD);
        assertTrue(result.getSuccess());
    }

    @Test
    public void shouldNotLoginWhenPasswordOrLoginIsIncorrect()  {
        Response result =loginController.login(ANOTHER_LOGIN,SOME_PASSWORD);
        assertFalse(result.getSuccess());
        assertEquals("Login or password invalid",result.getMessage());
    }
}

package pl.marta.kopp.persistence;

import pl.marta.kopp.domain.model.User;

public interface UserStorage {
    boolean isUserExists(String login);
    boolean isUserExists(String login, String password);
    boolean isExists(long id);
    void addUser(User user);
    void delete (long id);
    void update(User user);
    User getById(long id);

    User getByLoginAndPassword(String login, String password);
}

package ru.itis.db;

import ru.itis.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAll();

    boolean save(User user);

    boolean delete(long id);

    Optional<User> get(String login);

    boolean isPresent(String username);
}

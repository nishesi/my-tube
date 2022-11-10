package ru.itis.MyTube.model.dao;

import ru.itis.MyTube.model.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> get(String login, String password);
    void update(User user);

    List<User> getAll();

    boolean isPresent(String username);

    boolean isSubscribed(String username, Long channelId);
}

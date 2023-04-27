package ru.itis.MyTube.dao;

import ru.itis.MyTube.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> get(String login, String password);
    Optional<User> get(String login);
    void update(User user);

    List<User> getAll();

    boolean isPresent(String username);

    boolean isSubscribed(String username, long channelId);
    void subscribe(String username, long channelId);
    void unsubscribe(String username, long channelId);
    void addAuthority(String username, String authority);
}

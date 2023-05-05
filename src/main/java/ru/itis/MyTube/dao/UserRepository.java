package ru.itis.MyTube.dao;

import ru.itis.MyTube.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(UserDto userDto);

    Optional<UserDto> get(String login, String password);
    Optional<UserDto> get(String login);
    void update(UserDto userDto);

    List<UserDto> getAll();

    boolean isPresent(String username);

    boolean isSubscribed(String username, long channelId);
    void subscribe(String username, long channelId);
    void unsubscribe(String username, long channelId);
    void addAuthority(String username, String authority);
}

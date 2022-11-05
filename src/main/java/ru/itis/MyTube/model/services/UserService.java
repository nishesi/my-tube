package ru.itis.MyTube.model.services;

import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.VideoCover;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean save(User user);

    Optional<User> get(String username, String password);

    boolean update(User user);

    boolean delete(String username);

    List<VideoCover> getSubscribedChannelsVideos(User user);

    boolean usernameIsExist(String username);
}

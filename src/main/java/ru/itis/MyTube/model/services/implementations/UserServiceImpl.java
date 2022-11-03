package ru.itis.MyTube.model.services.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.dao.interfaces.UserRepository;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public Optional<User> get(String username) {
        return userRepository.get(username);
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(String username) {
        return false;
    }

    @Override
    public List<VideoCover> getSubscribedChannelsVideos(User user) {
        return null;
    }
}

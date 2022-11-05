package ru.itis.MyTube.model.services.implementations;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.model.dao.interfaces.UserRepository;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.UserService;

import java.util.List;
import java.util.Optional;
@Builder
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UrlCreator urlCreator;

    @Override
    public boolean save(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Optional<User> get(String username, String password) {
        return userRepository.get(username, password);
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

    @Override
    public boolean usernameIsExist(String username) {
        try {
            return userRepository.isPresent(username);
        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}

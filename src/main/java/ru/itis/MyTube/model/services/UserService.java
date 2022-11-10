package ru.itis.MyTube.model.services;

import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.UserUpdateForm;

import java.util.Optional;

public interface UserService {
    void save(User user) throws ValidationException;

    Optional<User> get(String username, String password) throws ValidationException;

    void update(UserUpdateForm form, User user) throws ValidationException;

    boolean usernameIsExist(String username);

    boolean isSubscribed(User user, Long channelId);
}

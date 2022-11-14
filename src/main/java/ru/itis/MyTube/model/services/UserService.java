package ru.itis.MyTube.model.services;

import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.RegistrationForm;
import ru.itis.MyTube.model.dto.forms.UserUpdateForm;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    void save(RegistrationForm form) throws ValidationException;

    Optional<User> get(String username, String password) throws ValidationException;

    void update(UserUpdateForm form, User user) throws ValidationException;

    boolean isSubscribed(User user, Long channelId);

    Byte getUserReaction(UUID videoUuid, String username);
}

package ru.itis.MyTube.model.services.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.PassPerformer;
import ru.itis.MyTube.auxiliary.enums.FileType;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.auxiliary.validators.UserUpdateValidator;
import ru.itis.MyTube.model.dao.UserRepository;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.UserUpdateForm;
import ru.itis.MyTube.model.services.UserService;
import ru.itis.MyTube.model.storage.Storage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UrlCreator urlCreator;

    private final UserUpdateValidator userUpdateValidator = new UserUpdateValidator();

    private final Storage storage;

    @Override
    public void save(User user) {
        try {
            userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Optional<User> get(String username, String password) {
        try {
            Optional<User> userOpt = userRepository.get(username, password);
            userOpt.ifPresent(user ->
                    user.setUserImgUrl(urlCreator.createResourceUrl(FileType.USER_ICON, user.getUsername()))
            );
            return userOpt;
        } catch (RuntimeException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(UserUpdateForm form, User user) throws ValidationException, ServiceException {
        userUpdateValidator.validate(form);

        User updatedUser = User.builder()
                .username(user.getUsername())
                .password(PassPerformer.hash(form.getPassword()))
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .birthdate(LocalDate.parse(form.getBirthdate()))
                .country(form.getCountry())
                .build();
        try {
            userRepository.update(updatedUser);
            storage.save(FileType.USER_ICON, user.getUsername(), form.getIconPart().getInputStream());
        } catch (RuntimeException | IOException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public boolean usernameIsExist(String username) {
        try {
            return userRepository.isPresent(username);
        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public boolean isSubscribed(User user, Long channelId) {
        if (Objects.isNull(user) || Objects.isNull(user.getUsername()) || Objects.isNull(channelId)) {
            return false;
        }

        try {
            return userRepository.isSubscribed(user.getUsername(), channelId);
        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}

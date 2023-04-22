package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.controllers.validators.RegistrationValidator;
import ru.itis.MyTube.controllers.validators.UserUpdateValidator;
import ru.itis.MyTube.dao.ReactionRepository;
import ru.itis.MyTube.dao.UserRepository;
import ru.itis.MyTube.dto.User;
import ru.itis.MyTube.dto.forms.RegistrationForm;
import ru.itis.MyTube.dto.forms.SubscribeForm;
import ru.itis.MyTube.dto.forms.UserUpdateForm;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.storage.FileType;
import ru.itis.MyTube.storage.Storage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;
    private final Storage storage;
    private final UrlCreator urlCreator;
    private final UserUpdateValidator userUpdateValidator;
    private final RegistrationValidator registrationValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(RegistrationForm form) throws ValidationException {
        registrationValidator.validate(form);
        User user = User.builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .birthdate(LocalDate.parse(form.getBirthdate()))
                .country(form.getCountry())
                .build();
        try {
            userRepository.save(user);
            userRepository.addAuthority(user.getUsername(), "USER");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public User get(String username, String password) throws ServiceException, ValidationException {
        User user;
        try {
            user = userRepository.get(username)
                    .orElseThrow(() -> new ServiceException("User not found."));
            user.setUserImgUrl(urlCreator.createResourceUrl(FileType.USER_ICON, username));
        } catch (RuntimeException ex) {
            throw new ServiceException("something go wrong");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new ServiceException("Invalid login or password");
    }

    @Override
    public void update(UserUpdateForm form, User user) throws ValidationException, ServiceException {
        userUpdateValidator.validate(form);

        User updatedUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .birthdate(LocalDate.parse(form.getBirthdate()))
                .country(form.getCountry())
                .build();
        try {
            userRepository.update(updatedUser);
            if (form.getIconPart().getSize() != 0) {
                storage.save(FileType.USER_ICON, user.getUsername(), form.getIconPart().getInputStream());
            }
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
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
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public Byte getUserReaction(UUID videoUuid, String username) {
        try {
            return reactionRepository.getReaction(videoUuid, username).orElse((byte) 0);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public void userChannel(SubscribeForm form) throws ServiceException {
        if (Objects.isNull(form.getUser()) ||
                Objects.isNull(form.getToSubscribe()) ||
                form.getToSubscribe().equals("") ||
                Objects.isNull(form.getChannelId())
        ) {
            throw new ServiceException("Can not to subscribe");
        }
        long channelId;
        boolean toSubscribe;
        try {
            channelId = Long.parseLong(form.getChannelId());
            toSubscribe = Boolean.parseBoolean(form.getToSubscribe());

        } catch (RuntimeException ex) {
            throw new ServiceException("Can not to subscribe");
        }

        if (toSubscribe) {
            subscribe(form.getUser(), channelId);
        } else {
            unsubscribe(form.getUser(), channelId);
        }
    }

    public void subscribe(User user, long channelId) throws ServiceException {

        try {
            userRepository.subscribe(user.getUsername(), channelId);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    public void unsubscribe(User user, long channelId) throws ServiceException {

        try {
            userRepository.unsubscribe(user.getUsername(), channelId);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }
}

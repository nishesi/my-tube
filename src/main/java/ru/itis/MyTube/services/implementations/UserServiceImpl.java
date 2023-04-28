package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.exceptions.ExistsException;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.dao.ReactionRepository;
import ru.itis.MyTube.dao.UserRepository;
import ru.itis.MyTube.dto.forms.NewUserForm;
import ru.itis.MyTube.dto.forms.SubscribeForm;
import ru.itis.MyTube.dto.forms.UpdateUserForm;
import ru.itis.MyTube.model.User;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.storage.FileType;
import ru.itis.MyTube.storage.Storage;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;
    private final Storage storage;
    private final UrlCreator urlCreator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(NewUserForm form) {
        if (userRepository.isPresent(form.getEmail()))
            throw new ExistsException("Username is exists.");

        User user = User.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .birthdate(form.getBirthdate())
                .country(form.getCountry())
                .build();
        try {
            userRepository.save(user);
            userRepository.addAuthority(user.getEmail(), "USER");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public User get(String username, String password) throws ServiceException {
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
    public void update(UpdateUserForm form, User user) throws ServiceException {

        if (form.getPassword() != null) user.setPassword(passwordEncoder.encode(form.getPassword()));
        if (form.getFirstName() != null) user.setFirstName(form.getFirstName());
        if (form.getLastName() != null) user.setLastName(form.getLastName());
        if (form.getBirthdate() != null) user.setBirthdate(form.getBirthdate());
        if (form.getCountry() != null) user.setCountry(form.getCountry());

        try {
            userRepository.update(user);
            MultipartFile icon = form.getIconFile();
            if (icon != null && !icon.isEmpty()) {
                storage.save(FileType.USER_ICON, user.getEmail(), icon.getInputStream());
            }
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public boolean isSubscribed(User user, Long channelId) {
        if (Objects.isNull(user) || Objects.isNull(user.getEmail()) || Objects.isNull(channelId)) {
            return false;
        }

        try {
            return userRepository.isSubscribed(user.getEmail(), channelId);
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
            userRepository.subscribe(user.getEmail(), channelId);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    public void unsubscribe(User user, long channelId) throws ServiceException {

        try {
            userRepository.unsubscribe(user.getEmail(), channelId);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }
}

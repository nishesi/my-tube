package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.exceptions.ExistsException;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.dao.ReactionRepository;
import ru.itis.MyTube.dto.forms.SubscribeForm;
import ru.itis.MyTube.dto.forms.user.NewUserForm;
import ru.itis.MyTube.dto.forms.user.UpdateUserForm;
import ru.itis.MyTube.entities.User;
import ru.itis.MyTube.entities.enums.Authority;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.repositories.UserRepository;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.storage.FileType;
import ru.itis.MyTube.storage.Storage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;
    private final Storage storage;
    private final UrlCreator urlCreator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(NewUserForm form) {
        User user = User.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .birthdate(form.getBirthdate())
                .country(form.getCountry())
                .authorities(List.of(Authority.ROLE_USER))
                .build();
        try {
            userRepository.save(user);
        } catch (RuntimeException ex) {
            if (ex instanceof DataIntegrityViolationException &&
                    ex.getCause() instanceof ConstraintViolationException)
                throw new ExistsException("Email already exists.");

            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public UserDto get(String username, String password) throws ServiceException {
        User user;
        try {
            user = userRepository.getByEmail(username)
                    .orElseThrow(() -> new ServiceException("User not found."));

//            user.setUserImgUrl(urlCreator.createResourceUrl(FileType.USER_ICON, username));
        } catch (RuntimeException ex) {
            throw new ServiceException("something go wrong");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UserDto();
//            return user;
        }
        throw new ServiceException("Invalid login or password");
    }

    @Override
    public void update(UpdateUserForm form, UserDto user) throws ServiceException {

        if (form.getPassword() != null) user.setPassword(passwordEncoder.encode(form.getPassword()));
        if (form.getFirstName() != null) user.setFirstName(form.getFirstName());
        if (form.getLastName() != null) user.setLastName(form.getLastName());
        if (form.getBirthdate() != null) user.setBirthdate(form.getBirthdate());
        if (form.getCountry() != null) user.setCountry(form.getCountry());

        try {
//            userRepository.update(user);
            MultipartFile icon = form.getIconFile();
            if (!icon.isEmpty()) {
                storage.save(FileType.USER_ICON, user.getEmail(), icon.getInputStream());
            }
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public boolean isSubscribed(UserDto user, Long channelId) {
        if (Objects.isNull(user) || Objects.isNull(user.getEmail()) || Objects.isNull(channelId)) {
            return false;
        }

        try {
//            return userRepository.isSubscribed(user.getEmail(), channelId);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
        throw new RuntimeException("broken method");
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
        if (Objects.isNull(form.getUserDto()) ||
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
//            subscribe(form.getUserDto(), channelId);
        } else {
//            unsubscribe(form.getUserDto(), channelId);
        }
    }

    public void subscribe(User user, long channelId) throws ServiceException {

        try {
//            userRepository.subscribe(user.getEmail(), channelId);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    public void unsubscribe(User user, long channelId) throws ServiceException {

        try {
//            userRepository.unsubscribe(user.getEmail(), channelId);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }
}

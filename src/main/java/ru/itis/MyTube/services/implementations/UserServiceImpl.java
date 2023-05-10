package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.MyTube.dto.forms.user.NewUserForm;
import ru.itis.MyTube.dto.forms.user.UpdateUserForm;
import ru.itis.MyTube.entities.Channel;
import ru.itis.MyTube.entities.Subscription;
import ru.itis.MyTube.entities.User;
import ru.itis.MyTube.entities.enums.Authority;
import ru.itis.MyTube.exceptions.DBConstraintException;
import ru.itis.MyTube.exceptions.ExistsException;
import ru.itis.MyTube.exceptions.NotFoundException;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.repositories.SubscriptionRepository;
import ru.itis.MyTube.repositories.UserRepository;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.storage.FileType;
import ru.itis.MyTube.storage.Storage;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final Storage storage;
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

            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public void update(UpdateUserForm form) throws ServiceException {
        User user = userRepository.findById(form.getId())
                .orElseThrow(() -> new NotFoundException("User not found."));

        if (form.getPassword() != null) user.setPassword(passwordEncoder.encode(form.getPassword()));
        if (form.getFirstName() != null) user.setFirstName(form.getFirstName());
        if (form.getLastName() != null) user.setLastName(form.getLastName());
        if (form.getBirthdate() != null) user.setBirthdate(form.getBirthdate());
        if (form.getCountry() != null) user.setCountry(form.getCountry());

        try {
            userRepository.save(user);

            MultipartFile icon = form.getIconFile();
            if (!icon.isEmpty()) {
                storage.save(FileType.USER_ICON, user.getEmail(), icon.getInputStream());
            }
        } catch (RuntimeException | IOException ex) {
            throwIfConstraintViolationException(ex, "Invalid data.");
            throwUnhandledException(ex);
        }
    }

    @Override
    public void changeSubscription(long channelId, long userId) throws ServiceException {
        Subscription.SubscriptionId subscriptionId = new Subscription.SubscriptionId(
                User.builder().id(userId).build(),
                Channel.builder().id(channelId).build()
        );
        if (subscriptionRepository.existsById(subscriptionId)) {
            subscriptionRepository.deleteById(subscriptionId);
        } else {
            subscriptionRepository.save(new Subscription(
                    subscriptionId.getUser(),
                    subscriptionId.getChannel()));
        }
    }

    private void throwUnhandledException(Exception exception) {
        exception.printStackTrace();
        throw new ServiceException("Something go wrong, please try again later.");
    }

    private void throwIfConstraintViolationException(Exception ex, String message) {
        if (ex instanceof DataIntegrityViolationException &&
                ex.getCause() instanceof ConstraintViolationException)
            throw new DBConstraintException(message);
    }
}

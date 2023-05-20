package ru.itis.nishesi.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.nishesi.MyTube.dto.forms.user.NewUserForm;
import ru.itis.nishesi.MyTube.dto.forms.user.UpdateUserForm;
import ru.itis.nishesi.MyTube.entities.Channel;
import ru.itis.nishesi.MyTube.entities.Subscription;
import ru.itis.nishesi.MyTube.entities.User;
import ru.itis.nishesi.MyTube.enums.Authority;
import ru.itis.nishesi.MyTube.enums.FileType;
import ru.itis.nishesi.MyTube.exceptions.DBConstraintException;
import ru.itis.nishesi.MyTube.exceptions.ExistsException;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.repositories.SubscriptionRepository;
import ru.itis.nishesi.MyTube.repositories.UserRepository;
import ru.itis.nishesi.MyTube.services.FileService;
import ru.itis.nishesi.MyTube.services.UserService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final FileService fileService;
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
        User user = userRepository.findById(form.getId()).orElseThrow();

        if (form.getPassword() != null) user.setPassword(passwordEncoder.encode(form.getPassword()));
        if (form.getFirstName() != null) user.setFirstName(form.getFirstName());
        if (form.getLastName() != null) user.setLastName(form.getLastName());
        if (form.getBirthdate() != null) user.setBirthdate(form.getBirthdate());
        if (form.getCountry() != null) user.setCountry(form.getCountry());

        try {
            userRepository.save(user);

            MultipartFile icon = form.getIconFile();
            if (!icon.isEmpty()) {
                fileService.save(FileType.USER_ICON, String.valueOf(user.getId()), icon.getInputStream());
            }
        } catch (RuntimeException | IOException ex) {
            if (ex instanceof DataIntegrityViolationException &&
                    ex.getCause() instanceof ConstraintViolationException)
                throw new DBConstraintException("Invalid data.");

            throw new ServiceException("Something go wrong, please try again later.");
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
}

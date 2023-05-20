package ru.itis.nishesi.MyTube.security.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.nishesi.MyTube.auxiliary.Converter;
import ru.itis.nishesi.MyTube.entities.User;
import ru.itis.nishesi.MyTube.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final Converter converter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.getByEmail(username);
        return userOptional.map(user -> new UserDetailsImpl(user, converter))
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }
}

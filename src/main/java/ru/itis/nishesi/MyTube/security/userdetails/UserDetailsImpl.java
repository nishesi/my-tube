package ru.itis.nishesi.MyTube.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.nishesi.MyTube.dto.Converter;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.entities.User;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;
    private final Converter converter;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(User user, Converter converter) {
        this.user = user;
        this.converter = converter;
        authorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .toList();
    }

    public UserDto getUser() {
        return converter.from(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}

package ru.itis.nishesi.MyTube.security.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.itis.nishesi.MyTube.dto.UserDto;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class OauthToken implements Authentication {

    private final String code;
    private final String error;
    private final String errorDescription;
    private boolean isAuthenticated;
    @Setter
    private UserDto userDto;
    @Setter
    private List<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return code;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return code;
    }
}

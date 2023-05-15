package ru.itis.MyTube.security.oauth;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.dto.UserDto;
import ru.itis.MyTube.entities.User;
import ru.itis.MyTube.services.OAuthService;

@Component
@RequiredArgsConstructor
public class OAuthProvider implements AuthenticationProvider {
    private final OAuthService oAuthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OauthToken token = (OauthToken) authentication;
        preAuthenticate(token);
        String accessToken = oAuthService.getAccessToken(token.getCode());
        UserDto userDetails = oAuthService.getUserDetails(accessToken);

        User user = oAuthService.synchronizeUser(userDetails);
        token.setUserDto(userDetails);
        token.setAuthorities(user.getAuthorities().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.name()))
                .toList());
        token.setAuthenticated(true);
        return token;
    }

    private void preAuthenticate(OauthToken token) {
        if (token.getCode() == null || token.getCode().isEmpty()) {
            throw new AuthenticationServiceException(token.getErrorDescription());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OauthToken.class.isAssignableFrom(authentication);
    }
}

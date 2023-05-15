package ru.itis.MyTube.services;

import org.springframework.security.authentication.AuthenticationServiceException;
import ru.itis.MyTube.dto.UserDto;
import ru.itis.MyTube.entities.User;

public interface OAuthService {
    String getAccessToken(String code) throws AuthenticationServiceException;
    UserDto getUserDetails(String accessToken) throws AuthenticationServiceException;
    User synchronizeUser(UserDto userDto);
}

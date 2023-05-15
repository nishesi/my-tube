package ru.itis.MyTube.services.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.dto.UserDto;
import ru.itis.MyTube.entities.User;
import ru.itis.MyTube.enums.Authority;
import ru.itis.MyTube.repositories.UserRepository;
import ru.itis.MyTube.services.OAuthService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final ObjectMapper objectMapper;
    private final OkHttpClient client = new OkHttpClient();
    private final UserRepository userRepository;

    @Override
    public String getAccessToken(String code) {
        Request request = accessTokenRequest(code);
        Call call = client.newCall(request);

        Map<String, Object> result = parseResponse(call, "Cant get access to user data. Try again later");
        return (String) result.get("access_token");
    }

    @Override
    public UserDto getUserDetails(String accessToken) throws AuthenticationServiceException {
        Request request = userDataRequest(accessToken);
        Call call = client.newCall(request);

        Map<String, Object> result = parseResponse(call, "Cant get user data. Try again later.");
        return formData(result);
    }

    @Override
    public User synchronizeUser(UserDto userDto) {
        userRepository.getByEmail(userDto.getEmail()).ifPresentOrElse(
                user -> {
                    user.setBirthdate(userDto.getBirthdate());
                    user.setFirstName(userDto.getFirstName());
                    user.setLastName(userDto.getLastName());
                    userRepository.save(user);
                },
                () -> {
                    User user = User.builder()
                            .email(userDto.getEmail())
                            .firstName(userDto.getFirstName())
                            .lastName(userDto.getLastName())
                            .birthdate(userDto.getBirthdate())
                            .password("")
                            .country("Unknown")
                            .isExternal(true)
                            .authorities(List.of(Authority.ROLE_USER))
                            .build();
                    userRepository.save(user);
                }
        );
        return userRepository.getByEmail(userDto.getEmail()).orElseThrow();
    }

    @NotNull
    private static Request userDataRequest(String accessToken) {
        HttpUrl url = HttpUrl.parse("https://login.yandex.ru/info?").newBuilder()
                .addQueryParameter("format", "json").build();
        return new Request.Builder()
                .url(url)
                .addHeader("Authorization", "OAuth " + accessToken)
                .get().build();
    }

    private UserDto formData(Map<String, Object> params) {
        return UserDto.builder()
                .email((String) params.get("default_email"))
                .firstName((String) params.get("first_name"))
                .lastName((String) params.get("last_name"))
                .birthdate(LocalDate.parse((String) params.get("birthday")))
                .userImgUrl("https://avatars.yandex.net/get-yapic/" + params.get("default_avatar_id") + "/islands-retina-50")
                .build();
    }

    private Map<String, Object> parseResponse(Call call, String message) {
        try (Response response = call.execute()) {
            if (response.code() != 200) {
                throw new AuthenticationServiceException(message);
            }
            String str = response.body().string();

            return objectMapper.readValue(str, new TypeReference<>() {
            });
        } catch (IOException | NullPointerException e) {
            throw new AuthenticationServiceException("Unknown error", e);
        }
    }

    public Request accessTokenRequest(String code) {
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", code)
                .add("client_id", "9a58369d0d904fa8a866eebce80a4ec3")
                .add("client_secret", "6dbf83dd942346f7a4c45d62b9359921")
                .build();

        return new Request.Builder()
                .url("https://oauth.yandex.ru/token")
                .post(body)
                .build();
    }
}

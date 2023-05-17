package ru.itis.nishesi.MyTube.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import ru.itis.nishesi.MyTube.security.oauth.OauthFilter;

@Component
@RequiredArgsConstructor
public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OauthFilter oauthFilter = new OauthFilter("/oauth/token", authenticationManager);
        oauthFilter.setSessionAuthenticationStrategy(http.getSharedObject(SessionAuthenticationStrategy.class));
        oauthFilter.setSecurityContextRepository(http.getSharedObject(SecurityContextRepository.class));
        http.addFilterAfter(oauthFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

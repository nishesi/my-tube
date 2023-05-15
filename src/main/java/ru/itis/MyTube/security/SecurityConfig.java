package ru.itis.MyTube.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.itis.MyTube.security.handlers.CustomAuthSuccessHandler;
import ru.itis.MyTube.security.handlers.RestOrLoginUrlAuthEntryPoint;
import ru.itis.MyTube.security.oauth.OAuthProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder encoder;
    private final CustomAuthSuccessHandler authSuccessHandler;
    private final RestOrLoginUrlAuthEntryPoint authEntryPoint;
    private final MyCustomDsl myCustomDsl;
    private final OAuthProvider oAuthProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").anonymous()
                        .requestMatchers("/user/register").anonymous()
                        .requestMatchers("/static/**").permitAll()

                        .requestMatchers("/user/**").authenticated()

                        .requestMatchers("/channel/add").authenticated()
                        .requestMatchers(HttpMethod.GET, "/channel/*").permitAll()
                        .requestMatchers("/channel/**").authenticated()

                        .requestMatchers("/video").authenticated()
                        .requestMatchers("/video/add").authenticated()
                        .requestMatchers(HttpMethod.GET, "/video/*").permitAll()
                        .requestMatchers("/video/**").authenticated()

                        .requestMatchers("/reaction/**").authenticated()
                        .anyRequest().permitAll()
                )
                .exceptionHandling(configurer -> {
                    // Unauthenticated error handlers
                    configurer.authenticationEntryPoint(authEntryPoint);
                })
                .formLogin(configurer -> configurer
                        .failureForwardUrl("/auth/err")
                        .successHandler(authSuccessHandler)
                )
                .logout(configurer -> configurer
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .authenticationProvider(oAuthProvider)
                .apply(myCustomDsl);
        return http.build();
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder,
                                      UserDetailsService service) throws Exception {
        builder
                .userDetailsService(service)
                .passwordEncoder(encoder);
    }
}

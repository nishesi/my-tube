package ru.itis.MyTube.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import ru.itis.MyTube.auxiliary.RestAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder encoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/login").anonymous()
                        .requestMatchers("/reaction/**").authenticated()
                        .requestMatchers("/user/update").authenticated()
                        .anyRequest().permitAll()
                )
                .exceptionHandling(configurer -> {
                    // Authorization error
                    var entryPoint1 = new LoginUrlAuthenticationEntryPoint("/login");
                    var entryPoint2 = new RestAuthenticationFailureHandler(entryPoint1);
                    configurer.authenticationEntryPoint(entryPoint2);
                })
                .formLogin(configurer -> {
                    // Authentication error
                    configurer.failureForwardUrl("/login/err");
                })
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .logout(configurer -> configurer
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
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

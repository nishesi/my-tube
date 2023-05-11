package ru.itis.MyTube.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import ru.itis.MyTube.auxiliary.RestAuthenticationFailureHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/reaction/**").authenticated()
                        .requestMatchers("/user/update").authenticated()
                        .anyRequest().permitAll()
                )
                .exceptionHandling(configurer -> {
                    var entryPoint1 = new LoginUrlAuthenticationEntryPoint("/login");
                    var entryPoint2 = new RestAuthenticationFailureHandler(entryPoint1);
                    configurer.authenticationEntryPoint(entryPoint2);
                })
                .formLogin(configurer -> {
                })
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .logout(configurer -> configurer
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}

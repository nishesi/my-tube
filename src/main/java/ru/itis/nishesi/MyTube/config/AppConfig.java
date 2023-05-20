package ru.itis.nishesi.MyTube.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;
import java.time.ZoneOffset;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = {"ru.itis.nishesi.MyTube.repositories"})
@ComponentScan("ru.itis.nishesi.MyTube")
@PropertySource("classpath:app.properties")
public class AppConfig {

    @Bean
    public Clock clock() {
        return Clock.system(ZoneOffset.ofHours(0));
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}

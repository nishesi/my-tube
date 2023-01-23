package ru.itis.MyTube.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@RequiredArgsConstructor
@ComponentScan("ru.itis.MyTube")
@PropertySource("classpath:db.properties")
@PropertySource("classpath:app.properties")
public class AppConfig {

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public HikariConfig hikariConfig(
            @Value("${db.username}") String username,
            @Value("${db.password}") String password,
            @Value("${db.url}") String url,
            @Value("${db.maxPoolSize}") int maxPoolSize
    ) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        return hikariConfig;
    }
}

package ru.itis.MyTube.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.itis.MyTube.model.MvUpdater;

import javax.sql.DataSource;

@Configuration
//@EnableWebMvc
@RequiredArgsConstructor
@ComponentScan("ru.itis.MyTube")
@PropertySource("classpath:db.properties")
@PropertySource("classpath:app.properties")
public class AppConfig {

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource(
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

        return new HikariDataSource(hikariConfig);
    }

    @Bean(initMethod = "start", destroyMethod = "finish")
    public MvUpdater mvUpdater(
            DataSource dataSource,
            @Value("${db.mvUpdateTimeout}") long timeout
    ) {
        return new MvUpdater(dataSource, timeout);
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}

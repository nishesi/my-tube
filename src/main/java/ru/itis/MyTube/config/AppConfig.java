package ru.itis.MyTube.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.itis.MyTube.MvUpdater;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@ComponentScan("ru.itis.MyTube")
@PropertySource("classpath:app.properties")
public class AppConfig {

//    @Bean(initMethod = "start", destroyMethod = "finish")
    public MvUpdater mvUpdater(
            DataSource dataSource,
            @Value("${db.mvUpdateTimeout}") long timeout
    ) {
        return new MvUpdater(dataSource, timeout);
    }
}

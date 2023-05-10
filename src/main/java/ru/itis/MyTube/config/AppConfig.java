package ru.itis.MyTube.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.itis.MyTube.auxiliary.MvUpdater;

import javax.sql.DataSource;
import java.time.Clock;
import java.time.ZoneOffset;

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

    @Bean
    public Clock clock() {
        return Clock.system(ZoneOffset.ofHours(0));
    }
}

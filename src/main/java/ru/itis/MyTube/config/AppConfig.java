package ru.itis.MyTube.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.itis.MyTube.model.MvUpdater;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
@ComponentScan("ru.itis.MyTube")
@PropertySource("classpath:db.properties")
@PropertySource("classpath:app.properties")
public class AppConfig implements WebMvcConfigurer {

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

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/static/");
    }
}

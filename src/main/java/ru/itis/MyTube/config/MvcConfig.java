package ru.itis.MyTube.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.Validator;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.itis.MyTube.auxiliary.AlertsDtoConverter;
import ru.itis.MyTube.model.User;

import java.time.LocalDate;

@Configuration
@EnableWebMvc
@ComponentScan({
        "ru.itis.MyTube.controllers",
})
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private AlertsDtoConverter alertsDtoConverter;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    ServletListenerRegistrationBean<HttpSessionListener> initSessList() {
        var bean = new ServletListenerRegistrationBean<HttpSessionListener>();
        bean.setListener(new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {
                se.getSession().setAttribute("user",
                        User.builder()
                                .email("email@gmail.com")
                                .password("askjfhkshdfjsdhkfhh")
                                .firstName("first name")
                                .lastName("last name")
                                .country("Prussia")
                                .birthdate(LocalDate.of(2003, 3, 22))
                                .channelId(7L)
                                .build());
            }
        });
        return bean;
    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(alertsDtoConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(10);
    }
}

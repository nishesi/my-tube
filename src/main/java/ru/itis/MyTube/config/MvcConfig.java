package ru.itis.MyTube.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import ru.itis.MyTube.auxiliary.AlertsDtoConverter;
import ru.itis.MyTube.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan({
        "ru.itis.MyTube.controllers",
})
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
    private final AlertsDtoConverter alertsDtoConverter;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

//    @Bean
//    ServletListenerRegistrationBean<HttpSessionListener> initSessList() {
//        var bean = new ServletListenerRegistrationBean<HttpSessionListener>();
//        bean.setListener(new HttpSessionListener() {
//            @Override
//            public void sessionCreated(HttpSessionEvent se) {
//                se.getSession().setAttribute("user",
//                        UserDto.builder()
//                                .id(3L)
//                                .email("email@gmail.com")
//                                .firstName("first name")
//                                .lastName("last name")
//                                .country("Prussia")
//                                .birthdate(LocalDate.of(2003, 3, 22))
//                                .channelId(7L)
//                                .build());
//            }
//        });
//        return bean;
//    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(alertsDtoConverter);
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

        WebMvcConfigurer.super.configureHandlerExceptionResolvers(resolvers);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(10);
    }
}

package ru.itis.MyTube.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.itis.MyTube.auxiliary.ListConverter;
import ru.itis.MyTube.controllers.listeners.SessionListener;

@Configuration
@EnableWebMvc
@ComponentScan({
        "ru.itis.MyTube.controllers",
})
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private ListConverter listConverter;

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
        bean.setListener(new SessionListener());
        return bean;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(listConverter);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(10);
    }
}

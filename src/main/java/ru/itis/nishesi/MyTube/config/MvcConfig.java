package ru.itis.nishesi.MyTube.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pebbletemplates.spring.servlet.PebbleViewResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.itis.nishesi.MyTube.auxiliary.AlertsDtoConverter;
import ru.itis.nishesi.MyTube.auxiliary.ZoneIdListener;

@Configuration
@EnableWebMvc
@ComponentScan({
        "ru.itis.nishesi.MyTube.controllers",
})
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
    private final AlertsDtoConverter alertsDtoConverter;

    @Autowired
    public void init(PebbleViewResolver viewResolver) {
        viewResolver.setRedirectContextRelative(false);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public FilterRegistrationBean<MultipartFilter> multipartFilterBean() {
        var bean = new FilterRegistrationBean<>(new MultipartFilter());
        bean.setOrder(0);
        return bean;
    }

    @Bean
    FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
        var bean = new FilterRegistrationBean<>(new HiddenHttpMethodFilter());
        bean.setOrder(1);
        return bean;
    }

    @Bean
    ServletListenerRegistrationBean<ZoneIdListener> zoneIdListenerBean(ZoneIdListener zoneIdListener) {
        return new ServletListenerRegistrationBean<>(zoneIdListener);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(alertsDtoConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(10);
    }
}

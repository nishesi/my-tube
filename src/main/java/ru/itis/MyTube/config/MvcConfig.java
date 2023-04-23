package ru.itis.MyTube.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ru.itis.MyTube.controllers.listeners.SessionListener;
import ru.itis.MyTube.view.Attributes;

@Configuration
@EnableWebMvc
@ComponentScan({
        "ru.itis.MyTube.controllers",
})
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setRedirectContextRelative(false);
        return viewResolver;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    ServletListenerRegistrationBean<ServletContextListener> initdefaultValues() {
        var bean = new ServletListenerRegistrationBean<ServletContextListener>();
        bean.setListener(new ServletContextListener() {
            @Override
            public void contextInitialized(ServletContextEvent sce) {
                ServletContext context = sce.getServletContext();
                context.setAttribute("logoUrl",
                        context.getContextPath() + "/static/images/reg-background-img.jpg"
                );
                context.setAttribute("appName", "MyTube");
                context.setAttribute(Attributes.COMMON_CSS_URL, context.getContextPath() + "/static/css/common.css");
                context.setAttribute("contextPath", context.getContextPath());
            }
        });
        return bean;
    }

    @Bean
    ServletListenerRegistrationBean<HttpSessionListener> initSessList() {
        var bean = new ServletListenerRegistrationBean<HttpSessionListener>();
        bean.setListener(new SessionListener());
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/static/");
    }
}

package ru.itis.MyTube.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ru.itis.MyTube.controllers.listeners.SessionListener;
import ru.itis.MyTube.MvUpdater;
import ru.itis.MyTube.view.Attributes;

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
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF");
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

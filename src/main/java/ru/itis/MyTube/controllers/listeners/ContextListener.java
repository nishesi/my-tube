package ru.itis.MyTube.controllers.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.MyTube.Attributes;
import ru.itis.MyTube.model.dao.implementations.UserRepJdbcImpl;
import ru.itis.MyTube.model.dao.interfaces.UserRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        DataSource dataSource = initDataSource();

        UserRepository userRepository = new UserRepJdbcImpl(dataSource);
        context.setAttribute(Attributes.USER_REP.toString(), userRepository);
    }

    private DataSource initDataSource() {
        Properties properties = new Properties();

        try {
            properties.load(ContextListener.class.getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));

        return new HikariDataSource(config);
    }
}

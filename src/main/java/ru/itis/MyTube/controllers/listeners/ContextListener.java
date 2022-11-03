package ru.itis.MyTube.controllers.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.MyTube.auxiliary.Attributes;
import ru.itis.MyTube.model.dao.implementations.ChannelRepositoryJdbcImpl;
import ru.itis.MyTube.model.dao.implementations.UserRepJdbcImpl;
import ru.itis.MyTube.model.dao.implementations.VideoRepositoryJdbcImpl;
import ru.itis.MyTube.model.dao.interfaces.ChannelRepository;
import ru.itis.MyTube.model.dao.interfaces.UserRepository;
import ru.itis.MyTube.model.dao.interfaces.VideoRepostiory;
import ru.itis.MyTube.model.services.implementations.ChannelServiceImpl;
import ru.itis.MyTube.model.services.implementations.UserServiceImpl;
import ru.itis.MyTube.model.services.implementations.VideoServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    private HikariDataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        initDataSource();

        setServices(context);

        initPageAttributes(context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dataSource.close();
    }

    private void initDataSource() {
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

        dataSource = new HikariDataSource(config);
    }

    private void initPageAttributes(ServletContext context) {
        context.setAttribute(
                Attributes.APP_LOGO_URL.toString(),
                context.getContextPath() + "/images/reg-background-img.jpg"
        );
        context.setAttribute(Attributes.APP_NAME.toString(), "MyTube");
        context.setAttribute(Attributes.COMMON_CSS_URL.toString(), context.getContextPath() + "/css/common.css");
    }

    private void setServices(ServletContext context) {
        UserRepository userRepository = new UserRepJdbcImpl(dataSource);
        VideoRepostiory videoRepostiory = new VideoRepositoryJdbcImpl(dataSource);
        ChannelRepository channelRepository = new ChannelRepositoryJdbcImpl(dataSource);

        context.setAttribute(
                Attributes.USER_SERVICE.toString(),
                new UserServiceImpl(userRepository)
        );
        context.setAttribute(
                Attributes.VIDEO_SERVICE.toString(),
                new VideoServiceImpl(videoRepostiory)
        );
        context.setAttribute(
                Attributes.CHANNEL_SERVICE.toString(),
                new ChannelServiceImpl(channelRepository)
        );
    }
}

package ru.itis.MyTube.controllers.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.constants.Attributes;
import ru.itis.MyTube.auxiliary.constants.Beans;
import ru.itis.MyTube.auxiliary.validators.SearchValidator;
import ru.itis.MyTube.model.dao.implementations.ChannelRepositoryJdbcImpl;
import ru.itis.MyTube.model.dao.implementations.UserRepositoryJdbcImpl;
import ru.itis.MyTube.model.dao.implementations.VideoRepositoryJdbcImpl;
import ru.itis.MyTube.model.dao.interfaces.ChannelRepository;
import ru.itis.MyTube.model.dao.interfaces.UserRepository;
import ru.itis.MyTube.model.dao.interfaces.VideoRepository;
import ru.itis.MyTube.model.services.implementations.ChannelServiceImpl;
import ru.itis.MyTube.model.services.implementations.UserServiceImpl;
import ru.itis.MyTube.model.services.implementations.VideoServiceImpl;
import ru.itis.MyTube.model.storage.FileStorageImpl;
import ru.itis.MyTube.model.storage.Storage;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    private HikariDataSource dataSource;

    private UrlCreator urlCreator;

    private Storage storage;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        urlCreator = new UrlCreator("http://localhost:8080/MyTube");
        context.setAttribute(Beans.URL_CREATOR, urlCreator);

        storage = new FileStorageImpl();
        context.setAttribute(Beans.STORAGE, storage);

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
                Attributes.APP_LOGO_URL,
                context.getContextPath() + "/images/reg-background-img.jpg"
        );
        context.setAttribute(Attributes.APP_NAME, "MyTube");
        context.setAttribute(Attributes.COMMON_CSS_URL, context.getContextPath() + "/css/common.css");
    }

    private void setServices(ServletContext context) {
        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);
        VideoRepository videoRepository = new VideoRepositoryJdbcImpl(dataSource);
        ChannelRepository channelRepository = new ChannelRepositoryJdbcImpl(dataSource);

        SearchValidator searchValidator = new SearchValidator();

        context.setAttribute(
                Beans.USER_SERVICE,
                new UserServiceImpl(userRepository, urlCreator)
        );
        context.setAttribute(
                Beans.VIDEO_SERVICE,
                new VideoServiceImpl(videoRepository, searchValidator, urlCreator, storage)
        );
        context.setAttribute(
                Beans.CHANNEL_SERVICE,
                new ChannelServiceImpl(channelRepository)
        );
    }
}

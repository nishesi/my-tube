package ru.itis.MyTube.controllers.listeners;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.MyTube.auxiliary.constants.Attributes;
import ru.itis.MyTube.config.AppConfig;
import ru.itis.MyTube.model.MVUpdater;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ContextListener implements ServletContextListener {
    private AnnotationConfigApplicationContext context;
    private MVUpdater mvUpdater;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("context", context);

        initPageAttributes(servletContext);

        mvUpdater = new MVUpdater(context.getBean(DataSource.class), 5000);
        mvUpdater.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        mvUpdater.end();
        context.close();
    }

    private void initPageAttributes(ServletContext context) {
        context.setAttribute("logoUrl",
                context.getContextPath() + "/images/reg-background-img.jpg"
        );
        context.setAttribute("appName", "MyTube");
        context.setAttribute(Attributes.COMMON_CSS_URL, context.getContextPath() + "/css/common.css");
    }
}

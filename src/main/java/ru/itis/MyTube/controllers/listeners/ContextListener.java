package ru.itis.MyTube.controllers.listeners;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.MyTube.view.Attributes;
import ru.itis.MyTube.config.AppConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private AnnotationConfigApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("context", context);

        initPageAttributes(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
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

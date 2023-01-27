package ru.itis.MyTube.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import ru.itis.MyTube.security.SecurityConfig;
import ru.itis.MyTube.view.Attributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebConfigurer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);
        context.register(SecurityConfig.class);

        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.setAttribute("context", context);
        initContextAttributes(servletContext);

        ServletRegistration.Dynamic registration =
                servletContext.addServlet("dispatcher", new DispatcherServlet(context));

        registration.setLoadOnStartup(1);
        registration.addMapping("/");

        servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
                .addMappingForUrlPatterns(null, false, "/*");
    }

    private void initContextAttributes(ServletContext context) {
        context.setAttribute("logoUrl",
                context.getContextPath() + "/static/images/reg-background-img.jpg"
        );
        context.setAttribute("appName", "MyTube");
        context.setAttribute(Attributes.COMMON_CSS_URL, context.getContextPath() + "/css/common.css");
        context.setAttribute("contextPath", context.getContextPath());
    }
}

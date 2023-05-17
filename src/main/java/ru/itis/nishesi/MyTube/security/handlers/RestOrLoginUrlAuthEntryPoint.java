package ru.itis.nishesi.MyTube.security.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestOrLoginUrlAuthEntryPoint extends LoginUrlAuthenticationEntryPoint {
    @Value("${mvc.rest-path}")
    private String restPath;

    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public RestOrLoginUrlAuthEntryPoint(@Value("${security.login.mapping}") String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException
    ) throws IOException, ServletException {

        if (request.getServletPath().startsWith(restPath)) {
            request.getRequestDispatcher(restPath + "/auth/err").forward(request, response);
        } else {
            super.commence(request, response, authException);
        }
    }
}

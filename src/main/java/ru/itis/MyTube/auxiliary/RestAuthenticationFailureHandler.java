package ru.itis.MyTube.auxiliary;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;

@RequiredArgsConstructor
public class RestAuthenticationFailureHandler implements AuthenticationEntryPoint {
    private final LoginUrlAuthenticationEntryPoint entryPoint;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException
    ) throws IOException, ServletException {

        if (request.getServletPath().startsWith("/reaction")) {
            request.getRequestDispatcher("/error/authorize/rest").forward(request, response);
        } else {
            entryPoint.commence(request, response, authException);
        }
    }
}

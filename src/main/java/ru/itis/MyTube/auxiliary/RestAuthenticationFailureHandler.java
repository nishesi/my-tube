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

    private final LoginUrlAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (request.getServletPath().startsWith("/reaction")) {
            response.setStatus(403);
            String resp = """
                    {
                        "message" : "You not authenticated."
                    }
                    """;
            response.getWriter().println(resp);
        } else {
            authenticationEntryPoint.commence(request, response, authException);
        }
    }
}

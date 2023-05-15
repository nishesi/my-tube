package ru.itis.MyTube.security.oauth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class OauthFilter extends AbstractAuthenticationProcessingFilter {
    public OauthFilter(@Value("/oauth/token") String mappedUrl, AuthenticationManager authenticationManager) {
        super(mappedUrl, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException {
        String code = request.getParameter("code");
        String error = request.getParameter("error");
        String desc = request.getParameter("error_description");
        OauthToken token = new OauthToken(code, error, desc);
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException failed
    ) throws IOException, ServletException {
        request.setAttribute("message", failed.getMessage());
        request.getRequestDispatcher("/oauth/err").forward(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        OauthToken oauthToken = (OauthToken) authResult;
        request.getSession().setAttribute("user", oauthToken.getUserDto());
        super.successfulAuthentication(request, response, chain, authResult);
    }
}

package ru.itis.MyTube.controllers.listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@WebListener
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest req =(HttpServletRequest) sre.getServletRequest();

        try {
            req.setCharacterEncoding("UTF-8");

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("regPageCss", req.getContextPath() + "/css/reg-page.css");
        req.setAttribute("problems", new HashMap<String, String>());
    }
}

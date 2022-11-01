package ru.itis.MyTube.controllers.listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@WebListener
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest req =(HttpServletRequest) sre.getServletRequest();

        try {
            req.setCharacterEncoding("UTF-8");
            req.setAttribute("logoUrl", sre.getServletContext().getContextPath() + "/images/reg-background-img.jpg");
            req.setAttribute("appName", "MyTube");

            req.setAttribute("appName", "MyTube");
            req.setAttribute("firstName", "Nurislam");
            req.setAttribute("lastName", "Zaripov");
            req.setAttribute("userImgUrl", sre.getServletContext().getContextPath() + "/images/reg-background-img.jpg");

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }
}

package ru.itis.MyTube.controllers.listeners;

import org.springframework.stereotype.Component;
import ru.itis.MyTube.view.Alert;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.LinkedList;
import java.util.Queue;

import static ru.itis.MyTube.view.Attributes.ALERTS;

@Component
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        Queue<Alert> alertQueue = new LinkedList<>();
//        se.getSession().setAttribute(ALERTS, alertQueue);
    }
}

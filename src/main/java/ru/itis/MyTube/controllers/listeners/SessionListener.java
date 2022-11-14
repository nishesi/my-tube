package ru.itis.MyTube.controllers.listeners;

import ru.itis.MyTube.auxiliary.Alert;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.LinkedList;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.ALERT_QUEUE;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        Queue<Alert> alertQueue = new LinkedList<>();
        alertQueue.add(new Alert(Alert.alertType.SUCCESS, "Session created."));
        alertQueue.add(new Alert(Alert.alertType.INFO, "Pleasant watching."));
        se.getSession().setAttribute(ALERT_QUEUE, alertQueue);
    }
}

package ru.itis.nishesi.MyTube.auxiliary;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ZoneIdListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        var request = (HttpServletRequest) sre.getServletRequest();
        if (request.getSession().getAttribute("zoneId") == null) {
            request.setAttribute("zoneIdRequired", true);
        }
    }
}

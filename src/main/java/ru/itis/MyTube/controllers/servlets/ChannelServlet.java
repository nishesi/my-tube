package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.constants.UrlPatterns;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.model.dto.Channel;
import ru.itis.MyTube.model.services.ChannelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.itis.MyTube.auxiliary.constants.Beans.CHANNEL_SERVICE;

@WebServlet(UrlPatterns.CHANNEL_PAGE)
public class ChannelServlet extends HttpServlet {
    private ChannelService channelService;

    @Override
    public void init() {
        channelService = (ChannelService) getServletContext().getAttribute(CHANNEL_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Channel channel = null;
        try {
            channel = channelService.getChannel(req.getParameter("id"));
        } catch (ServiceException ex) {
            ((List<Alert>) req.getAttribute("alerts")).add(new Alert(Alert.alertType.WARNING, ex.getMessage()));
            req.getRequestDispatcher("/WEB-INF/jsp/BaseWindow.jsp").forward(req, resp);
        }
        req.setAttribute("channel", channel);
        req.getRequestDispatcher("/WEB-INF/jsp/ChannelPage.jsp").forward(req, resp);
    }
}

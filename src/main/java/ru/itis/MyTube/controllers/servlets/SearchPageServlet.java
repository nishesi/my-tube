package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.enums.Bean;
import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.model.services.VideoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class SearchPageServlet extends HttpServlet {

    private VideoService videoService;

    @Override
    public void init() {
        videoService = (VideoService) getServletContext().getAttribute(Bean.VIDEO_SERVICE.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VideoCover> list = null;
        List<Alert> alerts = new ArrayList<>();
        try {
            list = videoService.getVideosByNameSubstring(req.getParameter("substring"));
        } catch (ServiceException ex) {
            alerts.add(new Alert(Alert.alertType.DANGER, ex.getMessage()));
        }


        req.setAttribute("alerts", alerts);
        req.setAttribute("substring", req.getParameter("substring"));
        req.setAttribute(Bean.VIDEO_COVER_LIST.toString(), list);
        req.getRequestDispatcher("WEB-INF/jsp/searchPage.jsp").forward(req, resp);
    }
}

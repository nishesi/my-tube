package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.constants.Attributes;
import ru.itis.MyTube.auxiliary.constants.Beans;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.VideoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.SEARCH_PAGE;

@WebServlet(SEARCH_PAGE)
public class SearchPageServlet extends HttpServlet {

    private VideoService videoService;

    @Override
    public void init() {
        videoService = (VideoService) getServletContext().getAttribute(Beans.VIDEO_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VideoCover> list = null;
        try {
            list = videoService.getVideosByNameSubstring(req.getParameter("substring"));
        } catch (ServiceException ex) {
            ((List<Alert>) req.getAttribute("alerts"))
                    .add(new Alert(Alert.alertType.DANGER, ex.getMessage()));
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }


        req.setAttribute("substring", req.getParameter("substring"));
        req.setAttribute(Attributes.VIDEO_COVER_LIST, list);
        req.getRequestDispatcher("WEB-INF/jsp/SearchPage.jsp").forward(req, resp);
    }
}

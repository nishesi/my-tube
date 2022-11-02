package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Attributes;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.VideoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("")
public class BaseWindowServlet extends HttpServlet {

    private VideoService videoService;

    @Override
    public void init() {
        videoService = (VideoService) getServletContext().getAttribute(Attributes.VIDEO_SERVICE.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO reaction for another request parameters
        List<VideoCover> list = videoService.getRandomVideos();

        req.setAttribute(Attributes.VIDEO_COVER_LIST.toString(), list);

        req.getRequestDispatcher("/WEB-INF/jsp/BaseWindow.jsp").forward(req, resp);
    }
}

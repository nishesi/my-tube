package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Attributes;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.VideoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/watch")
public class VideoPageServlet extends HttpServlet {

    private VideoService videoService;

    @Override
    public void init() {
        videoService =(VideoService) getServletContext().getAttribute(Attributes.VIDEO_SERVICE.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid;

        try {
            uuid = UUID.fromString(req.getParameter("v"));
        } catch (IllegalArgumentException | NullPointerException ex) {
            resp.sendRedirect(getServletContext().getContextPath());
            return;
        }

        Video video = videoService.getVideo(uuid);

        List<VideoCover> list = videoService.getRandomVideos();

        req.setAttribute("video", video);
        req.setAttribute("videoCoverList", list);

        req.getRequestDispatcher("WEB-INF/jsp/videoPage.jsp").forward(req, resp);
    }
}

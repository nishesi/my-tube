package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.constants.Beans;
import ru.itis.MyTube.auxiliary.enums.FileType;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
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

import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.WATCH_PAGE;

@WebServlet(WATCH_PAGE)
public class WatchingServlet extends HttpServlet {

    private VideoService videoService;

    @Override
    public void init() {
        videoService = (VideoService) getServletContext().getAttribute(Beans.VIDEO_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid;

        try {
            uuid = UUID.fromString(req.getParameter(FileType.VIDEO.getType()));
        } catch (IllegalArgumentException | NullPointerException ex) {
            ((List<Alert>) req.getAttribute("alerts"))
                    .add(new Alert(Alert.alertType.WARNING, "Couldn't find or load a video."));
            req.getRequestDispatcher("/WEB-INF/jsp/BaseWindow.jsp").forward(req, resp);
            return;
        }

        Video video;
        try {
            video = videoService.getVideo(uuid);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }

        List<VideoCover> list = videoService.getRandomVideos();

        req.setAttribute("video", video);
        req.setAttribute("videoCoverList", list);

        req.getRequestDispatcher("WEB-INF/jsp/videoPage.jsp").forward(req, resp);
    }
}

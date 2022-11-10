package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.constants.Beans;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.forms.VideoForm;
import ru.itis.MyTube.model.services.VideoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.VIDEO_UPLOAD_PAGE;

@WebServlet(VIDEO_UPLOAD_PAGE)
@MultipartConfig
public class VideoUploadingServlet extends HttpServlet {
    private VideoService videoService;


    @Override
    public void init() {
        videoService = (VideoService) getServletContext().getAttribute(Beans.VIDEO_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/videoUploadingPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VideoForm videoForm = VideoForm.builder()
                .channelId(((User)req.getSession().getAttribute("user")).getChannelId())
                .name(req.getParameter("name"))
                .info(req.getParameter("info"))
                .iconPart(req.getPart("icon"))
                .videoPart(req.getPart("video"))
                .build();
        List<Alert> alerts = (List<Alert>) req.getAttribute("alerts");
        try {
            videoService.addVideo(videoForm);
        } catch (ValidationException e) {
            alerts.add(new Alert(Alert.alertType.DANGER, e.getMessage()));
        }
        alerts.add(new Alert(Alert.alertType.SUCCESS, "video Added"));
        req.getRequestDispatcher("/WEB-INF/jsp/BaseWindow.jsp").forward(req, resp);
    }
}

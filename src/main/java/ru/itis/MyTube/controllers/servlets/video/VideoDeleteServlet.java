package ru.itis.MyTube.controllers.servlets.video;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.services.VideoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.USER;
import static ru.itis.MyTube.auxiliary.constants.Beans.VIDEO_SERVICE;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.CHANNEL;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.PRIVATE_VIDEO_DELETE;

@WebServlet(PRIVATE_VIDEO_DELETE)
public class VideoDeleteServlet extends HttpServlet {
    private VideoService videoService;

    @Override
    public void init() throws ServletException {
        videoService = (VideoService) getServletContext().getAttribute(VIDEO_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String videoUuid = req.getParameter("videoUuid");
        Long userChannelId = ((User)req.getSession().getAttribute(USER)).getChannelId();

        try {
            videoService.deleteVideo(videoUuid, userChannelId);

        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());
        } catch (ServiceException e) {
            ((Queue<? super Alert>) req.getSession().getAttribute("alerts"))
                    .add(new Alert(Alert.alertType.WARNING, e.getMessage()));
        }

        resp.sendRedirect(getServletContext().getContextPath() + CHANNEL + "?id=" + userChannelId);
    }
}

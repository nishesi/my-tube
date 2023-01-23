package ru.itis.MyTube.controllers.servlets.video;

import org.springframework.context.ApplicationContext;
import ru.itis.MyTube.view.Alert;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.UserService;
import ru.itis.MyTube.model.services.VideoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.USER;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.VIDEO;

@WebServlet(VIDEO)
public class WatchingServlet extends HttpServlet {

    private VideoService videoService;
    private UserService userService;

    @Override
    public void init() {
        videoService = ((ApplicationContext)getServletContext().getAttribute("context")).getBean(VideoService.class);
        userService = ((ApplicationContext)getServletContext().getAttribute("context")).getBean(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Queue<? super Alert> alerts = (Queue<? super Alert>) req.getAttribute("alerts");
        Byte reaction = null;

        try {
            Video video = videoService.getVideo(req.getParameter("uuid"));

            User user = (User) req.getSession().getAttribute(USER);
            if (Objects.nonNull(user)) {
                reaction = userService.getUserReaction(video.getUuid(), user.getUsername());
            }
            List<VideoCover> list = videoService.getRandomVideos();


            req.setAttribute("reaction", reaction);
            req.setAttribute("video", video);
            req.setAttribute("videoCoverList", list);
            req.setAttribute("videoReactionsScriptUrl", getServletContext().getContextPath() + "/js/ReactionRequester.js");
            req.getRequestDispatcher("WEB-INF/jsp/VideoPage.jsp").forward(req, resp);

        } catch (ServiceException ex) {
            alerts.add(new Alert(Alert.alertType.DANGER, ex.getMessage()));
            req.getRequestDispatcher("WEB-INF/jsp/BaseWindow.jsp").forward(req, resp);
        }
    }
}

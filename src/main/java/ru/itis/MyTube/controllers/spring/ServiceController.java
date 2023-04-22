package ru.itis.MyTube.controllers.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.VideoService;
import ru.itis.MyTube.view.Alert;
import ru.itis.MyTube.view.Attributes;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

import static ru.itis.MyTube.view.Attributes.ALERTS;
import static ru.itis.MyTube.view.Attributes.USER;

@Controller
@RequiredArgsConstructor
public class ServiceController {
    private final VideoService videoService;

    @GetMapping("/")
    public void getBaseWindow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String listType = req.getParameter("listType");
        List<VideoCover> list = null;

        try {
            if ("subs".equals(listType)) {
                list = videoService.getSubscriptionsVideos(((User) req.getSession().getAttribute(USER)));
            } else {
                list = videoService.getRandomVideos();
            }

        } catch (ServiceException ex) {
            ((Queue<? super Alert>) req.getSession().getAttribute(ALERTS))
                    .add(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
        }

        req.setAttribute(Attributes.VIDEO_COVER_LIST, list);
        req.setAttribute("listType", req.getParameter("listType"));
        req.getRequestDispatcher("/WEB-INF/jsp/BaseWindow.jsp").forward(req, resp);
    }
}

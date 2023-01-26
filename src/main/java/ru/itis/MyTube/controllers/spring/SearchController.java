package ru.itis.MyTube.controllers.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.VideoService;
import ru.itis.MyTube.view.Alert;
import ru.itis.MyTube.view.Attributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

import static ru.itis.MyTube.view.Attributes.ALERTS;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final VideoService videoService;

    @GetMapping("/search")
    public void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VideoCover> list = null;
        try {
            list = videoService.getVideosByNameSubstring(req.getParameter("substring"));
        } catch (ServiceException e) {
            ((Queue<? super Alert>)req.getSession().getAttribute(ALERTS))
                    .add(new Alert(Alert.alertType.DANGER, e.getMessage()));
        }

        req.setAttribute("substring", req.getParameter("substring"));
        req.setAttribute(Attributes.VIDEO_COVER_LIST, list);
        req.getRequestDispatcher("WEB-INF/jsp/SearchPage.jsp").forward(req, resp);
    }
}

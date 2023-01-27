package ru.itis.MyTube.controllers.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.Channel;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.dto.forms.ChannelForm;
import ru.itis.MyTube.model.services.ChannelService;
import ru.itis.MyTube.model.services.UserService;
import ru.itis.MyTube.model.services.VideoService;
import ru.itis.MyTube.view.Alert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

import static ru.itis.MyTube.controllers.UrlPatterns.CHANNEL;
import static ru.itis.MyTube.view.Attributes.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {
    @Value("${context.path}")
    private String contextPath;
    private final ChannelService channelService;
    private final VideoService videoService;
    private final UserService userService;

    @GetMapping("/{id}")
    public void getChannel(@PathVariable String id, HttpServletRequest req, HttpServletResponse resp
    ) throws ServletException, IOException {

        Channel channel = null;
        List<VideoCover> channelVideos = null;
        Boolean isSubscribed = null;
        try {
            channel = channelService.getChannel(id);
            channelVideos = videoService.getChannelVideoCovers(channel.getId());
            isSubscribed = userService.isSubscribed((User)req.getSession().getAttribute(USER), channel.getId());
        } catch (ServiceException ex) {
            ((List<Alert>) req.getAttribute("alerts"))
                    .add(new Alert(Alert.AlertType.WARNING, ex.getMessage()));
            req.getRequestDispatcher("/WEB-INF/jsp/BaseWindow.jsp").forward(req, resp);
        }
        req.setAttribute("isSubscribed", isSubscribed);
        req.setAttribute("channel", channel);
        req.setAttribute(VIDEO_COVER_LIST, channelVideos);
        req.getRequestDispatcher("/WEB-INF/jsp/ChannelPage.jsp").forward(req, resp);
    }

    @GetMapping("/update")
    public String getChannelCreatePage() {
        return "/jsp/ChannelCreatePage";
    }

    @PostMapping
    public void createChannel(HttpServletRequest req, HttpServletResponse resp
    ) throws ServletException, IOException {

        ChannelForm channelForm = ChannelForm.builder()
                .user((User) req.getSession().getAttribute(USER))
                .name(req.getParameter("name"))
                .iconPart(req.getPart("icon"))
                .info(req.getParameter("info"))
                .build();
        Queue<? super Alert> alerts = (Queue<? super Alert>) req.getSession().getAttribute(ALERTS);

        try {
            Long channelId = channelService.create(channelForm);
            resp.sendRedirect(contextPath + CHANNEL + "?id=" + channelId);
            return;

        } catch (ServiceException ex) {
            alerts.add(new Alert(Alert.AlertType.DANGER, ex.getMessage()));

        } catch (ValidationException ex) {
            req.setAttribute(FORM, channelForm);
            req.setAttribute(PROBLEMS, ex.getProblems());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/ChannelCreatePage.jsp").forward(req, resp);
    }
}

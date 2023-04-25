package ru.itis.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.dto.Channel;
import ru.itis.MyTube.dto.User;
import ru.itis.MyTube.dto.VideoCover;
import ru.itis.MyTube.dto.forms.ChannelForm;
import ru.itis.MyTube.services.ChannelService;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.view.Alert;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

import static ru.itis.MyTube.controllers.UrlPatterns.CHANNEL;
import static ru.itis.MyTube.view.Attributes.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {

    private final ChannelService channelService;

    private final VideoService videoService;

    private final UserService userService;

    @Value("${context.path}")
    private String contextPath;

    @GetMapping("/{id}")
    public String getChannel(@PathVariable String id, HttpServletRequest req, HttpServletResponse resp
    ) throws ServletException, IOException {

        Channel channel = null;
        List<VideoCover> channelVideos = null;
        Boolean isSubscribed = null;
        try {
            channel = channelService.getChannel(id);
            channelVideos = videoService.getChannelVideoCovers(channel.getId());
            isSubscribed = userService.isSubscribed((User) req.getSession().getAttribute(USER), channel.getId());
        } catch (ServiceException ex) {
            ((List<Alert>) req.getAttribute("alerts"))
                    .add(new Alert(Alert.AlertType.WARNING, ex.getMessage()));
            return "homePage";
        }
        req.setAttribute("isSubscribed", isSubscribed);
        req.setAttribute("channel", channel);
        req.setAttribute(VIDEO_COVER_LIST, channelVideos);

        return "channelPage";
    }

    @GetMapping("/update")
    public String getChannelCreatePage() {
        return "channelCreatePage";
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
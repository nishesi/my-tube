package ru.itis.MyTube.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.forms.ChannelForm;
import ru.itis.MyTube.model.Channel;
import ru.itis.MyTube.model.User;
import ru.itis.MyTube.model.VideoCover;
import ru.itis.MyTube.services.ChannelService;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.view.Alert;

import java.io.IOException;
import java.util.List;

import static ru.itis.MyTube.view.Attributes.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {

    private final ChannelService channelService;

    private final VideoService videoService;

    private final UserService userService;

    @GetMapping("/{id}")
    public String getChannelPage(ModelMap modelMap,
                                 @PathVariable String id,
                                 @SessionAttribute User user
    ) {
        Channel channel;
        List<VideoCover> channelVideos;
        boolean isSubscribed;
        try {
            channel = channelService.getChannel(id);
            channelVideos = videoService.getChannelVideoCovers(channel.getId());
            isSubscribed = userService.isSubscribed(user, channel.getId());

        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.WARNING, ex.getMessage()));
            modelMap.put("alerts", alertsDto);
            return "homePage";
        }
        modelMap.put("isSubscribed", isSubscribed);
        modelMap.put("channel", channel);
        modelMap.put(VIDEO_COVER_LIST, channelVideos);

        return "channelPage";
    }

    @GetMapping("/add")
    public String getCreateChannelPage() {
        return "createChannelPage";
    }

    @PostMapping
    public String createChannel(ModelMap modelMap,
                                HttpServletRequest req,
                                RedirectAttributes redirectAttributes
    ) throws ServletException, IOException {

        ChannelForm channelForm = ChannelForm.builder()
                .user((User) req.getSession().getAttribute(USER))
                .name(req.getParameter("name"))
                .iconPart(req.getPart("icon"))
                .info(req.getParameter("info"))
                .build();
        try {
            Long channelId = channelService.create(channelForm);
            redirectAttributes.addAttribute("id", channelId);
            return "redirect:/channel";

        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
            modelMap.put("alerts", alertsDto);

        } catch (ValidationException ex) {
            req.setAttribute(FORM, channelForm);
            req.setAttribute(PROBLEMS, ex.getProblems());
        }
        return "createChannelPage";
    }
}

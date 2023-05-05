package ru.itis.MyTube.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.forms.channel.NewChannelForm;
import ru.itis.MyTube.model.Channel;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.model.VideoCover;
import ru.itis.MyTube.services.ChannelService;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.view.Alert;

import java.util.List;

import static ru.itis.MyTube.view.Attributes.VIDEO_COVER_LIST;

@Controller
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {

    private final ChannelService channelService;

    private final VideoService videoService;

    private final UserService userService;

    @GetMapping("/add")
    public String getCreateChannelPage() {
        return "channel/new";
    }

    @PostMapping
    public String createChannel(ModelMap modelMap,
                                @Valid NewChannelForm newChannelForm,
                                BindingResult bindingResult,
                                @SessionAttribute UserDto userDto,
                                RedirectAttributes redirectAttributes
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                Long channelId = channelService.create(newChannelForm, userDto);
                redirectAttributes.addAttribute("id", channelId);
                return "redirect:/channel";

            } catch (ServiceException ex) {
                AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
                modelMap.put("alerts", alertsDto);
            }
        }
        return "channel/new";
    }

    @GetMapping("/{id}")
    public String getChannelPage(ModelMap modelMap,
                                 @PathVariable String id,
                                 @SessionAttribute UserDto userDto
    ) {
        Channel channel;
        List<VideoCover> channelVideos;
        boolean isSubscribed;
        try {
            channel = channelService.getChannel(id);
            channelVideos = videoService.getChannelVideoCovers(channel.getId());
            isSubscribed = userService.isSubscribed(userDto, channel.getId());

        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.WARNING, ex.getMessage()));
            modelMap.put("alerts", alertsDto);
            return "homePage";
        }
        modelMap.put("isSubscribed", isSubscribed);
        modelMap.put("channel", channel);
        modelMap.put(VIDEO_COVER_LIST, channelVideos);

        return "channel/page";
    }
}

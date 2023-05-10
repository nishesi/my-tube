package ru.itis.MyTube.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.ChannelDto;
import ru.itis.MyTube.dto.forms.channel.NewChannelForm;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.services.ChannelService;
import ru.itis.MyTube.view.Alert;

@Controller
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {

    private final ChannelService channelService;

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
                                 @PathVariable Long id,
                                 @SessionAttribute(required = false) UserDto user,
                                 @RequestParam(defaultValue = "0") int pageInd
    ) {
        try {
            ChannelDto channelDto = (user != null)
                    ? channelService.getChannelRegardingUser(id, pageInd, user)
                    : channelService.getChannel(id, pageInd);
            modelMap.put("channel", channelDto);
            modelMap.put("url", "/channel/" + id + "?");
            return "channel/page";

        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.WARNING, ex.getMessage()));
            modelMap.put("alerts", alertsDto);
            return "homePage";
        }
    }
}

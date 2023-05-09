package ru.itis.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.VideoCover;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.view.Alert;
import ru.itis.MyTube.view.Attributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final VideoService videoService;

    @GetMapping("/{substring}")
    public String search(ModelMap modelMap, @PathVariable String substring) {
        List<VideoCover> list = null;
        try {
            list = videoService.getVideosByNameSubstring(substring);
        } catch (ServiceException e) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, e.getMessage()));
            modelMap.put("alerts", alertsDto);
        }
        modelMap.put("substring", substring);
        modelMap.put(Attributes.VIDEO_COVER_LIST, list);
        return "homePage";
    }
}

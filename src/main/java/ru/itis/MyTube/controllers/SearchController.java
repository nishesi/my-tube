package ru.itis.MyTube.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.model.VideoCover;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.view.Alert;
import ru.itis.MyTube.view.Attributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final VideoService videoService;

    @GetMapping
    public String search(ModelMap modelMap, HttpServletRequest req) {
        List<VideoCover> list = null;
        try {
            list = videoService.getVideosByNameSubstring(req.getParameter("substring"));
        } catch (ServiceException e) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, e.getMessage()));
            modelMap.put("alerts", alertsDto);
        }
        req.setAttribute("substring", req.getParameter("substring"));
        req.setAttribute(Attributes.VIDEO_COVER_LIST, list);
        return "homePage";
    }
}

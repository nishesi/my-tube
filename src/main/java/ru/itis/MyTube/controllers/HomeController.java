package ru.itis.MyTube.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.model.VideoCover;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.view.Alert;
import ru.itis.MyTube.view.Attributes;

import java.util.List;

import static ru.itis.MyTube.view.Attributes.USER;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final VideoService videoService;

    @GetMapping("/")
    public String getHomePage(HttpServletRequest req, ModelMap modelMap) {
        String listType = req.getParameter("listType");
        List<VideoCover> list = null;

        try {
            if ("subs".equals(listType)) {
                list = videoService.getSubscriptionsVideos(((UserDto) req.getSession().getAttribute(USER)));
            } else {
                list = videoService.getRandomVideos();
            }
        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
            modelMap.put("alerts", alertsDto);
        }
        req.setAttribute(Attributes.VIDEO_COVER_LIST, list);
        req.setAttribute("listType", req.getParameter("listType"));
        return "homePage";
    }
}

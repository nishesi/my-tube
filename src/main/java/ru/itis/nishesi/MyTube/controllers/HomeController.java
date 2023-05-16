package ru.itis.nishesi.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.itis.nishesi.MyTube.dto.AlertsDto;
import ru.itis.nishesi.MyTube.enums.VideoCollectionType;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.services.SearchService;
import ru.itis.nishesi.MyTube.dto.Alert;
import ru.itis.nishesi.MyTube.enums.AlertType;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final SearchService searchService;

    @GetMapping("/")
    public String getHomePage(ModelMap modelMap,
                              @RequestParam(defaultValue = "0") int pageInd,
                              @RequestParam(defaultValue = "RANDOM") VideoCollectionType type,
                              @SessionAttribute(required = false) UserDto user) {

        modelMap.put("type", type);
        modelMap.put("title", "Home");
        try {
            Page<?> page = searchService.getVideoCollection(type, user, pageInd);
            modelMap.put("videosPage", page);
            modelMap.put("url", "/?type=" + type.toString() + "&");

        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(AlertType.DANGER, ex.getMessage()));
            modelMap.put("alerts", alertsDto);
        }
        return "homePage";
    }
}

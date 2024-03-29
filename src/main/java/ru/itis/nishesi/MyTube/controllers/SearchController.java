package ru.itis.nishesi.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.nishesi.MyTube.dto.Alert;
import ru.itis.nishesi.MyTube.dto.AlertsDto;
import ru.itis.nishesi.MyTube.dto.VideoCover;
import ru.itis.nishesi.MyTube.enums.AlertType;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.services.SearchService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService videoService;

    @GetMapping
    public String search(ModelMap modelMap,
                         @RequestParam(defaultValue = "") String substring,
                         @RequestParam(defaultValue = "0") int pageInd) {
        try {
            Page<VideoCover> videoCovers = videoService.findVideosByNameSubstring(substring, pageInd);
            modelMap.put("videosPage", videoCovers);

        } catch (ServiceException e) {
            AlertsDto alertsDto = new AlertsDto(new Alert(AlertType.DANGER, e.getMessage()));
            modelMap.put("alerts", alertsDto);
        }
        modelMap.put("substring", substring);
        return "homePage";
    }
}

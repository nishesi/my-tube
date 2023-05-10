package ru.itis.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.VideoCover;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.services.SearchService;
import ru.itis.MyTube.view.Alert;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService videoService;

    @GetMapping("/{substring}")
    public String search(ModelMap modelMap,
                         @RequestParam(defaultValue = "0") int pageInd,
                         @PathVariable String substring) {
        try {
            Page<VideoCover> videoCovers = videoService.findVideosByNameSubstring(substring, pageInd);
            modelMap.put("videosPage", videoCovers);

        } catch (ServiceException e) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, e.getMessage()));
            modelMap.put("alerts", alertsDto);
        }
        modelMap.put("substring", substring);
        return "homePage";
    }
}

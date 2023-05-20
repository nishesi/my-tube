package ru.itis.nishesi.MyTube.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.nishesi.MyTube.dto.AlertsDto;
import ru.itis.nishesi.MyTube.dto.forms.video.NewVideoForm;
import ru.itis.nishesi.MyTube.dto.forms.video.UpdateVideoForm;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.services.VideoService;
import ru.itis.nishesi.MyTube.dto.Alert;
import ru.itis.nishesi.MyTube.enums.AlertType;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {
    private final VideoService videoService;

    @GetMapping("/add")
    public String getAddVideoPage() {
        return "video/new";
    }

    @PostMapping
    public String addVideo(@SessionAttribute UserDto user,
                           @Valid NewVideoForm newVideoForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                videoService.addVideo(newVideoForm, user);

                AlertsDto alertsDto = new AlertsDto(new Alert(AlertType.SUCCESS, "Video added."));
                redirectAttributes.addFlashAttribute("alerts", alertsDto);

                redirectAttributes.addAttribute("id", user.getChannelId());
                return "redirect:" + MvcUriComponentsBuilder
                        .fromMappingName("CC#getChannelPage")
                        .arg(1, user.getChannelId())
                        .build();

            } catch (ServiceException ex) {
                AlertsDto alertsDto = new AlertsDto(new Alert(AlertType.DANGER, ex.getMessage()));
                redirectAttributes.addFlashAttribute("alerts", alertsDto);
            }
        }
        return "video/new";
    }

    @GetMapping("/{id}")
    public String getVideoPage(ModelMap modelMap,
                               @PathVariable String id,
                               @RequestParam(defaultValue = "0") int pageInd,
                               @SessionAttribute Optional<UserDto> user) {
        try {
            user.ifPresentOrElse(
                    userDto -> modelMap.put("video", videoService.getVideoRegardingUser(id, pageInd, userDto)),
                    () -> modelMap.put("video", videoService.getVideo(id, pageInd))
            );
            return "video/page";

        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(AlertType.DANGER, ex.getMessage()));
            modelMap.put("alerts", alertsDto);
            return "homePage";
        }
    }

    @GetMapping("/{id}/update")
    public String getUpdateVideoPage(ModelMap modelMap,
                                     @PathVariable UUID id,
                                     RedirectAttributes redirectAttributes) {
        try {
            UpdateVideoForm video = videoService.getVideoForUpdate(id);
            modelMap.put("updateVideoForm", video);
            return "video/update";

        } catch (ServiceException e) {
            AlertsDto alertsDto = new AlertsDto(Alert.of(AlertType.DANGER, e.getMessage()));
            redirectAttributes.addFlashAttribute("alerts", alertsDto);
            return "redirect:" + MvcUriComponentsBuilder
                    .fromMappingName("HC#getHomePage")
                    .build();
        }
    }

    @PutMapping
    public String updateVideo(ModelMap modelMap,
                              @Valid UpdateVideoForm updateVideoForm,
                              BindingResult bindingResult,
                              @SessionAttribute UserDto user,
                              RedirectAttributes redirectAttributes
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                videoService.updateVideo(updateVideoForm, user);

                AlertsDto alertsDto = new AlertsDto(new Alert(AlertType.SUCCESS, "Video updated."));
                redirectAttributes.addFlashAttribute("alerts", alertsDto);
                return "redirect:" + MvcUriComponentsBuilder
                        .fromMappingName("CC#getChannelPage")
                        .arg(1, user.getChannelId())
                        .build();

            } catch (ServiceException ex) {
                AlertsDto alertsDto = new AlertsDto(new Alert(AlertType.DANGER, ex.getMessage()));
                modelMap.put("alerts", alertsDto);
            }
        }
        return "video/update";
    }

    @DeleteMapping("/{id}")
    public String deleteVideo(@PathVariable UUID id,
                              @SessionAttribute UserDto user,
                              RedirectAttributes redirectAttributes) {
        AlertsDto alertsDto;
        try {
            videoService.deleteVideo(id, user);
            alertsDto = new AlertsDto(Alert.of(AlertType.SUCCESS, "Video deleted."));

        } catch (ServiceException e) {
            alertsDto = new AlertsDto(Alert.of(AlertType.DANGER, e.getMessage()));
        }

        redirectAttributes.addFlashAttribute("alerts", alertsDto);
        return "redirect:" + MvcUriComponentsBuilder
                .fromMappingName("CC#getChannelPage")
                .arg(1, user.getChannelId())
                .build();
    }
}

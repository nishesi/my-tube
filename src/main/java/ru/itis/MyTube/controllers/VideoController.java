package ru.itis.MyTube.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.forms.video.NewVideoForm;
import ru.itis.MyTube.dto.forms.video.UpdateVideoForm;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.view.Alert;

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
    public String addVideo(@SessionAttribute UserDto userDto,
                           @Valid NewVideoForm newVideoForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                videoService.addVideo(newVideoForm, userDto);

                AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.SUCCESS, "Video added."));
                redirectAttributes.addFlashAttribute("alerts", alertsDto);

                redirectAttributes.addAttribute("id", userDto.getChannelId());
                return "redirect:/channel";

            } catch (ServiceException ex) {
                AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
                redirectAttributes.addFlashAttribute("alerts", alertsDto);
            }
        }
        return "video/new";
    }

    @GetMapping("/{id}")
    public String getVideoPage(ModelMap modelMap,
                               @PathVariable UUID id,
                               @RequestParam(defaultValue = "0") int pageInd,
                               @SessionAttribute Optional<UserDto> user) {
        try {
            user.ifPresentOrElse(
                    userDto -> modelMap.put("video", videoService.getVideoRegardingUser(id, pageInd, userDto)),
                    () -> modelMap.put("video", videoService.getVideo(id, pageInd))
            );
            modelMap.put("url", "/video/" + id + "?");
            return "video/page";

        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
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
            AlertsDto alertsDto = new AlertsDto(Alert.of(Alert.AlertType.DANGER, e.getMessage()));
            redirectAttributes.addFlashAttribute("alerts", alertsDto);
            return "redirect:/";
        }
    }

    @PutMapping
    public String updateVideo(ModelMap modelMap,
                              @Valid UpdateVideoForm updateVideoForm,
                              BindingResult bindingResult,
                              @SessionAttribute UserDto userDto,
                              RedirectAttributes redirectAttributes
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                videoService.updateVideo(updateVideoForm, userDto);

                AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.SUCCESS, "Video updated."));
                redirectAttributes.addFlashAttribute("alerts", alertsDto);
                return "redirect:/channel/" + userDto.getChannelId();

            } catch (ServiceException ex) {
                AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
                modelMap.put("alerts", alertsDto);
            }
        }
        return "video/update";
    }

    @DeleteMapping("/{id}")
    public String deleteVideo(@PathVariable UUID videoId,
                              @SessionAttribute UserDto userDto,
                              RedirectAttributes redirectAttributes) {
        AlertsDto alertsDto;
        try {
            videoService.deleteVideo(videoId, userDto);
            alertsDto = new AlertsDto(Alert.of(Alert.AlertType.SUCCESS, "Video deleted."));

        } catch (ServiceException e) {
            alertsDto = new AlertsDto(Alert.of(Alert.AlertType.DANGER, e.getMessage()));
        }

        redirectAttributes.addFlashAttribute("alerts", alertsDto);
        return "redirect:/channel/" + userDto.getChannelId();
    }
}

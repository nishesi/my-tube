package ru.itis.MyTube.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.forms.video.NewVideoForm;
import ru.itis.MyTube.dto.forms.video.UpdateVideoForm;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.model.Video;
import ru.itis.MyTube.dto.VideoCover;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.view.Alert;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {
    private final VideoService videoService;
    private final UserService userService;

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
                               @PathVariable String id,
                               @SessionAttribute(required = false) UserDto userDto) {
        Byte reaction = null;
        try {
            Video video = videoService.getVideo(id);

//            if (userDto != null)
//                reaction = userService.getUserReaction(video.getUuid(), userDto.getEmail());

            List<VideoCover> list = videoService.getRandomVideos();

            modelMap.put("reaction", reaction);
            modelMap.put("video", video);
            modelMap.put("videoCoverList", list);
            return "video/page";

        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
            modelMap.put("alerts", alertsDto);
            return "homePage";
        }
    }

    @GetMapping("/{id}/update")
    public String getUpdateVideoPage(ModelMap modelMap,
                                     @PathVariable String id,
                                     RedirectAttributes redirectAttributes) {
        try {
            Video video = videoService.getVideo(id);
            UpdateVideoForm videoForm = UpdateVideoForm.builder()
                    .uuid(video.getUuid().toString())
                    .name(video.getVideoCover().getName())
                    .info(video.getInfo())
                    .build();
            modelMap.put("updateVideoForm", videoForm);
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
    public String deleteVideo(@PathVariable String id,
                              @SessionAttribute UserDto userDto,
                              RedirectAttributes redirectAttributes) {
        Long userChannelId = userDto.getChannelId();
        AlertsDto alertsDto;
        try {
            videoService.deleteVideo(id, userChannelId);
            alertsDto = new AlertsDto(Alert.of(Alert.AlertType.SUCCESS, "Video deleted."));

        } catch (ServiceException e) {
            alertsDto = new AlertsDto(Alert.of(Alert.AlertType.DANGER, e.getMessage()));
        }

        redirectAttributes.addFlashAttribute("alerts", alertsDto);
        return "redirect:/channel/" + userChannelId;
    }
}

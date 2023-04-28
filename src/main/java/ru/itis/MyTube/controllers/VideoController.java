package ru.itis.MyTube.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.forms.VideoForm;
import ru.itis.MyTube.model.User;
import ru.itis.MyTube.model.Video;
import ru.itis.MyTube.model.VideoCover;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.view.Alert;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static ru.itis.MyTube.view.Attributes.FORM;

@Controller
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {
    private final VideoService videoService;
    private final UserService userService;
    @Value("${context.path}")
    private String contextPath;

    @GetMapping("/{id}")
    public String getVideoPage(ModelMap modelMap,
                               @PathVariable String id,
                               @SessionAttribute(required = false) User user) {
        Byte reaction = null;
        try {
            Video video = videoService.getVideo(id);

            if (Objects.nonNull(user)) {
                reaction = userService.getUserReaction(video.getUuid(), user.getEmail());
            }
            List<VideoCover> list = videoService.getRandomVideos();

            modelMap.put("reaction", reaction);
            modelMap.put("video", video);
            modelMap.put("videoCoverList", list);
            modelMap.put("videoReactionsScriptUrl", contextPath + "/js/ReactionRequester.js");
            return "videoPage";

        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
            modelMap.put("alerts", alertsDto);
            return "homePage";
        }
    }

    @GetMapping("/update/{id}")
    public String getUpdateVideoPage(ModelMap modelMap, @PathVariable String id) {
        modelMap.put("pageType", "Update");
        modelMap.put("url", contextPath + "/update/" + id);
        return "utilVideoPage";
    }

    @PostMapping("/update/{id}")
    public String updateVideo(@PathVariable String id,
                              @SessionAttribute User user,
                              HttpServletRequest req,
                              RedirectAttributes redirectAttributes
    ) throws ServletException, IOException {

        VideoForm videoForm = VideoForm.builder()
                .videoUuid(id)
                .channelId(user.getChannelId())
                .name(req.getParameter("name"))
                .info(req.getParameter("info"))
                .iconPart(req.getPart("icon"))
                .videoPart(req.getPart("video"))
                .build();

        try {
            videoService.updateVideo(videoForm);
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.SUCCESS, "Video updated."));
            redirectAttributes.addFlashAttribute("alerts", alertsDto);
            redirectAttributes.addAttribute("id", videoForm.getChannelId());
            return "redirect:/channel";

        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());
            req.setAttribute(FORM, videoForm);

        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
            redirectAttributes.addFlashAttribute("alerts", alertsDto);
        }
        req.setAttribute("pageType", "Update");
        return "utilVideoPage";
    }

    @GetMapping("/add")
    public String getAddVideoPage(ModelMap modelMap) {
        modelMap.put("pageType", "Upload");
        modelMap.put("url", contextPath + "/video");
        return "utilVideoPage";
    }

    @PostMapping
    public String addVideo(@SessionAttribute User user,
                           HttpServletRequest req,
                           RedirectAttributes redirectAttributes
    ) throws IOException, ServletException {
        VideoForm videoForm = VideoForm.builder()
                .channelId(user.getChannelId())
                .name(req.getParameter("name"))
                .info(req.getParameter("info"))
                .iconPart(req.getPart("icon"))
                .videoPart(req.getPart("video"))
                .build();

        try {
            videoService.addVideo(videoForm);
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.SUCCESS, "Video added."));
            redirectAttributes.addFlashAttribute("alerts", alertsDto);
            redirectAttributes.addAttribute("id", videoForm.getChannelId());
            return "redirect:/channel";

        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());
            req.setAttribute(FORM, videoForm);

        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
            redirectAttributes.addFlashAttribute("alerts", alertsDto);
        }
        req.setAttribute("pageType", "Upload");
        return "utilVideoPage";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable String id,
                                         @SessionAttribute User user) {
        try {
            Long userChannelId = user.getChannelId();
            videoService.deleteVideo(id, userChannelId);
            return ResponseEntity.ok().build();

        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();

        } catch (ServiceException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

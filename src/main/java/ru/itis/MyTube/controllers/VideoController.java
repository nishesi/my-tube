package ru.itis.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.User;
import ru.itis.MyTube.model.Video;
import ru.itis.MyTube.model.VideoCover;
import ru.itis.MyTube.dto.forms.VideoForm;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.view.Alert;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import static ru.itis.MyTube.controllers.UrlPatterns.CHANNEL;
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
    public String getVideo(Model model,
                           @PathVariable String id,
                           @SessionAttribute(required = false) User user) {
        Byte reaction = null;
        try {
            Video video = videoService.getVideo(id);

            if (Objects.nonNull(user)) {
                reaction = userService.getUserReaction(video.getUuid(), user.getEmail());
            }
            List<VideoCover> list = videoService.getRandomVideos();

            model.addAttribute("reaction", reaction);
            model.addAttribute("video", video);
            model.addAttribute("videoCoverList", list);
            model.addAttribute("videoReactionsScriptUrl",
                    contextPath + "/js/ReactionRequester.js");
            return "videoPage";

        } catch (ServiceException ex) {
//            alerts.add(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
            return "homePage";
        }
    }

    @GetMapping("/update/{id}")
    public String getVideoUpdatePage(Model model, @PathVariable String id) {
        model.addAttribute("pageType", "Update");
        model.addAttribute("url", contextPath + "/update/" + id);
        return "utilVideoPage";
    }

    @PostMapping("/update/{id}")
    public String updateVideo(@PathVariable String id,
                              @SessionAttribute User user,
                              @SessionAttribute(required = false) Queue<? super Alert> alerts,
                              HttpServletRequest req,
                              HttpServletResponse resp
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
            alerts.add(new Alert(Alert.AlertType.SUCCESS, "Video updated."));
            return "redirect:" + contextPath + CHANNEL + "?id=" + videoForm.getChannelId();

        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());
            req.setAttribute(FORM, videoForm);

        } catch (ServiceException ex) {
            alerts.add(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
        }

        req.setAttribute("pageType", "Update");
        return "utilVideoPage";
    }

    @GetMapping("/add")
    public String getVideoAddPage(Model model) {
        model.addAttribute("pageType", "Upload");
        model.addAttribute("url", contextPath + "/video");
        return "utilVideoPage";
    }

    @PostMapping
    public String addVideo(@SessionAttribute User user,
                           @SessionAttribute(required = false) Queue<? super Alert> alerts,
                           HttpServletRequest req,
                           HttpServletResponse resp) throws IOException, ServletException {
        VideoForm videoForm = VideoForm.builder()
                .channelId(user.getChannelId())
                .name(req.getParameter("name"))
                .info(req.getParameter("info"))
                .iconPart(req.getPart("icon"))
                .videoPart(req.getPart("video"))
                .build();

        try {
            videoService.addVideo(videoForm);
            alerts.add(new Alert(Alert.AlertType.SUCCESS, "Video added."));
            return "redirect:" + contextPath + CHANNEL + "?id=" + videoForm.getChannelId();

        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());
            req.setAttribute(FORM, videoForm);


        } catch (ServiceException ex) {
            alerts.add(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
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
